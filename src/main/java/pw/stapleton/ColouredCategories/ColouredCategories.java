package pw.stapleton.colouredcategories;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pw.stapleton.colouredcategories.events.TooltipEventHandler;
import pw.stapleton.colouredcategories.handler.RandomHexColour;
import pw.stapleton.colouredcategories.util.Config;
import pw.stapleton.colouredcategories.util.Reference;

import java.util.*;
import java.util.regex.Pattern;

@Mod(Reference.MODID)
public class ColouredCategories {

    public static final LinkedHashMap<Item, Map<String, String>> ITEM_MAP = new LinkedHashMap<>(100);
    public static final RandomHexColour RANDOM_HEX_COLOUR = new RandomHexColour();
    public static Logger Logger = LogManager.getLogger(Reference.MOD_NAME);
    public static ColouredCategories INSTANCE;

    public ColouredCategories() {
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

        ModEventBus.register(this);
        ModEventBus.addListener(this::processConfig);
        ModEventBus.addListener(this::reloadConfig);
    }

    public void reloadConfig(ModConfigEvent.Reloading event) {
        ITEM_MAP.clear();
        loadConfig();
    }

    public void processConfig(FMLCommonSetupEvent event) {
        loadConfig();
    }

    private void loadConfig() {
        for (String category : Config.COLOUR_CATEGORIES.get()) {
            ForgeConfigSpec.BooleanValue randomAll = Config.RANDOM_ALL;

            String[] p = category.split(Pattern.quote("|"));
            Map<String, String> hex = parseHexCodes(p[0], category);
            ArrayList<Item> items = parseItems(p[1], category);

            for (Item i : items) {
                if (randomAll.get().equals(true)) {
                    ITEM_MAP.put(i, RANDOM_HEX_COLOUR.randomAll());
                    continue;
                }
                ITEM_MAP.put(i, hex);
            }
            //Logger.info(ITEM_MAP.toString());
        }
    }

    private ArrayList<Item> parseItems(String stackString, String category) {
        String[] stackStrings = stackString.split(",");
        ArrayList<Item> items = new ArrayList<>();

        for (String stack : stackStrings) {
            try {
                items.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack))).getItem());
            } catch (Error e) {
                Logger.error("Malformed itemids in one of your coloured categories." +
                        "\nHeres the category from the config: '" + category + "'" +
                        "\nAs well as the error thrown: " + e);
            }
        }

        return items;
    }

    private Map<String, String> parseHexCodes(String codeString, String category) {
        String[] codeStrings = codeString.split(",");
        Map<String, String> codes = new HashMap<>();

        codes.put("backStart", codeStrings[0]);
        codes.put("backEnd", codeStrings[1]);
        codes.put("bordStart", codeStrings[2]);
        codes.put("bordEnd", codeStrings[3]);

        for (Map.Entry<String, String> code : codes.entrySet()) {
            String k = code.getKey();
            String v = code.getValue().toLowerCase();

            if (v.startsWith("#")) v = v.replace("#", "");
            if (v.startsWith("0x")) v = v.replace("0x", "");

            if (v.length() == 8) continue;
            if (v.length() == 6){
                codes.put(k, "cc"+v);
                continue;
            }
            Logger.error("Invalid hexcode in one of your coloured categories. Setting hexcode to 'ff0000' (BrightRed)" +
                    "\nInvalid Category: '" + category + "'" +
                    "\nInvalid Rule: '" + k + ": " + v);
            codes.put(k, "ccff0000");
        }

        return codes;
    }
}

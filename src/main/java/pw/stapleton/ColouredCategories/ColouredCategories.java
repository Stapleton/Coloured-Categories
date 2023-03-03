package pw.stapleton.colouredcategories;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

    public static final LinkedHashMap<Item, Map<String, String>> ITEM_MAP = new LinkedHashMap<>(1000);
    public static final RandomHexColour RANDOM_HEX_COLOUR = new RandomHexColour();
    public static Logger Logger = LogManager.getLogger(Reference.MOD_NAME);
    public static ColouredCategories INSTANCE;
    public static ColouredCategories instance() {
        return ColouredCategories.INSTANCE;
    }


    public ColouredCategories() {
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

        ModEventBus.register(this);
        ModEventBus.addListener(this::processConfig);
    }


    private void processConfig(FMLCommonSetupEvent event) {
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
                        "\n\nHeres the category from the config: \n'" + category + "'" +
                        "\n\nAs well as the error thrown: " + e);
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
            String v = code.getValue();

            if (v.length() == 10) continue;
            if (v.length() == 8){
                codes.put(k, v.replace("0x", "0xf0"));
                continue;
            }
            Logger.error("Invalid hexcode in one of your coloured categories. " +
                    "Setting " + v + " to bright red. " +
                    "Invalid Category: '" + category + "'");
            codes.put(k, "0xF0FF0000");
        }

        return codes;
    }
}

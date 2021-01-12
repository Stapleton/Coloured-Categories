package pw.stapleton.ColouredCategories;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pw.stapleton.ColouredCategories.util.Config;
import pw.stapleton.ColouredCategories.util.Reference;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = Reference.MODID, version = Reference.VERSION, clientSideOnly = true, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES)
public class ColouredCategories {

    public static final Map<Item, Map<String, String>> INGREDIENT_MAP = new HashMap<>();

    public static Logger Logger = LogManager.getLogger(Reference.MOD_NAME);

    @Mod.Instance(Reference.MOD_NAME)
    public static ColouredCategories instance;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {

        new Config(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltipColour(RenderTooltipEvent.Color event) {
        //Logger.info(INGREDIENT_MAP);

        if (!INGREDIENT_MAP.containsKey(event.getStack().getItem())) {
            onNullTooltip(event);
        } else {
            Map<String, String> values = INGREDIENT_MAP.get(event.getStack().getItem());

            event.setBackground(Long.decode(values.get("background")).intValue());
            event.setBorderStart(Long.decode(values.get("borderStart")).intValue());
            event.setBorderEnd(Long.decode(values.get("borderEnd")).intValue());
        }
    }

    public void onNullTooltip(RenderTooltipEvent.Color event) {
        event.setBackground(Long.decode(Config.background).intValue());
        event.setBorderStart(Long.decode(Config.borderStart).intValue());
        event.setBorderEnd(Long.decode(Config.borderEnd).intValue());
    }
}

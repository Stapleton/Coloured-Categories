package pw.stapleton.ColouredCategories;

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

    public static final Map<String, Map<String, Long>> INGREDIENT_MAP = new HashMap<>();

    public static Logger Logger = LogManager.getLogger(Reference.MOD_NAME);

    @Mod.Instance(Reference.MOD_NAME)
    public static ColouredCategories instance;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {

        new Config(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTooltipColour(RenderTooltipEvent.Color event) {

        if (INGREDIENT_MAP.containsKey(event.getStack().getDisplayName())) {

            Map<String, Long> values = INGREDIENT_MAP.get(event.getStack().getDisplayName());

            event.setBackground((int) (long) values.get("background"));
            event.setBorderStart((int) (long) values.get("borderStart"));
            event.setBorderEnd((int) (long) values.get("borderEnd"));

        } else {

            event.setBackground((int) Config.background);
            event.setBorderStart((int) Config.start);
            event.setBorderEnd((int) Config.end);
        }
    }
}

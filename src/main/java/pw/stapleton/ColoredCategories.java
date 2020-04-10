package pw.stapleton;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod("coloredcategories")
public class ColoredCategories {

    public static final Map<Item, Map<String, Long>> ITEM_MAP = new HashMap<>();

    public ColoredCategories() {}

    @SubscribeEvent
    public static void onColor(RenderTooltipEvent.Color event) {
        Map<String, Long> colors;

        colors = ITEM_MAP.get(event.getStack());
        event.setBackground((int) (long) colors.get("background"));
        event.setBorderStart((int) (long) colors.get("borderStart"));
        event.setBorderEnd((int) (long) colors.get("borderEnd"));
    }
}

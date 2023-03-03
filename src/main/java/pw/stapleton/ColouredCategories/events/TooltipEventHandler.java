package pw.stapleton.colouredcategories.events;

import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pw.stapleton.colouredcategories.ColouredCategories;
import pw.stapleton.colouredcategories.util.Config;

import java.util.HashMap;
import java.util.Map;

public class TooltipEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTooltipColor(RenderTooltipEvent.Color event) {
        Item i = event.getItemStack().getItem();

        // itemMapHexCodes is sourced from ITEM_MAP if the item exists already
        // if the item doesnt exist in ITEM_MAP then fallback to the first item in the list
        // Default config makes it so there is always at least 1 entry, but should prolly have an extra default just in case
        Map<String, String> itemMapHexCodes = ColouredCategories.ITEM_MAP.containsKey(i) ? ColouredCategories.ITEM_MAP.get(i) : ColouredCategories.ITEM_MAP.values().iterator().next();
        Map<String, String> configModifiedHexCodes = new HashMap<>();

        if (Config.RANDOM_ALL.get()) {
            ColouredCategories.ITEM_MAP.putIfAbsent(i, ColouredCategories.RANDOM_HEX_COLOUR.randomAll());
        } else {
            configModifiedHexCodes.putIfAbsent("backStart", Config.RANDOM_BACKGROUND_START.get() ? ColouredCategories.RANDOM_HEX_COLOUR.get() : itemMapHexCodes.get("backStart"));
            configModifiedHexCodes.putIfAbsent("backEnd", Config.RANDOM_BACKGROUND_END.get() ? ColouredCategories.RANDOM_HEX_COLOUR.get() : itemMapHexCodes.get("backEnd"));
            configModifiedHexCodes.putIfAbsent("bordStart", Config.RANDOM_BORDER_START.get() ? ColouredCategories.RANDOM_HEX_COLOUR.get() : itemMapHexCodes.get("bordStart"));
            configModifiedHexCodes.putIfAbsent("bordEnd", Config.RANDOM_BORDER_END.get() ? ColouredCategories.RANDOM_HEX_COLOUR.get() : itemMapHexCodes.get("bordEnd"));
            ColouredCategories.ITEM_MAP.putIfAbsent(i, configModifiedHexCodes);
        }

        colorTooltip(event, itemMapHexCodes);
    }

    public void colorTooltip(RenderTooltipEvent.Color event, Map<String, String> m) {
        event.setBackgroundStart(Long.decode(m.get("backStart")).intValue());
        event.setBackgroundEnd(Long.decode(m.get("backEnd")).intValue());
        event.setBorderStart(Long.decode(m.get("bordStart")).intValue());
        event.setBorderEnd(Long.decode(m.get("bordEnd")).intValue());
    }
}

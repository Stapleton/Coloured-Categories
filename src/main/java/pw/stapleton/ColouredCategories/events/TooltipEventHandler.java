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

    private Map<String, String> controlDefaultColours(RenderTooltipEvent.Color event) {
        if (Config.CONTROL_DEFAULT_COLOURS.get()) {
            try {
                return ColouredCategories.ITEM_MAP.values().iterator().next();
            } catch (Error e) {
                ColouredCategories.Logger.error(e);
                return getOriginals(event);
            }
        }

        return getOriginals(event);
    }

    private Map<String, String> getOriginals(RenderTooltipEvent.Color event) {
        Map<String, String> codes = new HashMap<>();
        codes.put("backStart", Integer.toHexString(event.getOriginalBackgroundStart()));
        codes.put("backEnd", Integer.toHexString(event.getOriginalBackgroundEnd()));
        codes.put("bordStart", Integer.toHexString(event.getOriginalBorderStart()));
        codes.put("bordEnd", Integer.toHexString(event.getOriginalBorderEnd()));
        return codes;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onTooltipColor(RenderTooltipEvent.Color event) {
        Item i = event.getItemStack().getItem();

        // itemMapHexCodes is sourced from ITEM_MAP if the item exists already
        // if the item doesn't exist in ITEM_MAP then check the config if we want to even set a default colour
        // if CONTROL_DEFAULT_COLOUR is true, return first item in ITEM_MAP, if no first item, return original colours
        // if CONTROL_DEFAULT_COLOUR is false, return original colours. clearly there are possibly conflicting mods
        Map<String, String> itemMapHexCodes = ColouredCategories.ITEM_MAP.containsKey(i) ? ColouredCategories.ITEM_MAP.get(i) : controlDefaultColours(event);
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
        event.setBackgroundStart((int) Long.parseLong(m.get("backStart"), 16));
        event.setBackgroundEnd((int) Long.parseLong(m.get("backEnd"), 16));
        event.setBorderStart((int) Long.parseLong(m.get("bordStart"), 16));
        event.setBorderEnd((int) Long.parseLong(m.get("bordEnd"), 16));
    }
}

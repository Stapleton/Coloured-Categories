package pw.stapleton.cc.api;

import net.minecraft.world.item.Item;
import pw.stapleton.cc.ColouredCategories;
import pw.stapleton.cc.colour.Colourway;

import java.util.Map;

public class Common {
    public static Map<Item, Map<String, String>> getCategories() {
        return ColouredCategories.ITEM_MAP;
    }

    public static Map<String, String> colourway(String backStart, String backEnd, String bordStart, String bordEnd) {
        return new Colourway(backStart, backEnd, bordStart, bordEnd).get();
    }

    public static Map<String, String> randomColourway() {
        return ColouredCategories.RANDOM_HEX_COLOUR.randomAll();
    }

    public static String randomHexColour() {
        return ColouredCategories.RANDOM_HEX_COLOUR.get();
    }
}

package pw.stapleton.cc.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import pw.stapleton.cc.ColouredCategories;

import java.util.Map;

public class Forge extends Common {
    public static Map<String, String> get(Item item) {
        return ColouredCategories.ITEM_MAP.get(item);
    }

    public static void setDefault(Map<String, String> colourway) {
        ColouredCategories.ITEM_MAP.clear();
        ColouredCategories.ITEM_MAP.put(Items.AIR, colourway);
    }

    public static void add(Item item, Map<String, String> colourway) {
        ColouredCategories.ITEM_MAP.put(item, colourway);
    }

    public static void remove(Item item) {
        ColouredCategories.ITEM_MAP.remove(item);
    }

}

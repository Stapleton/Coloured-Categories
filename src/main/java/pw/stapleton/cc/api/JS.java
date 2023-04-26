package pw.stapleton.cc.api;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import pw.stapleton.cc.ColouredCategories;

import java.util.HashMap;
import java.util.Map;

public class JS extends Common {
    public static Map<String, String> get(ItemStack item) {
        return ColouredCategories.ITEM_MAP.get(item.getItem());
    }

    public static void setDefault(HashMap<String, String> colourway) {
        ColouredCategories.ITEM_MAP.clear();
        ColouredCategories.ITEM_MAP.put(Items.AIR, colourway);
    }

    public static void add(ItemStack item, HashMap<String, String> colourway) {
        ColouredCategories.ITEM_MAP.put(item.getItem(), colourway);
    }

    public static void remove(ItemStack item) {
        ColouredCategories.ITEM_MAP.remove(item.getItem());
    }
}

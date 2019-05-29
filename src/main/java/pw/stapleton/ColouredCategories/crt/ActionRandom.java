package pw.stapleton.ColouredCategories.crt;

import crafttweaker.IAction;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pw.stapleton.ColouredCategories.ColouredCategories;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ActionRandom implements IAction {

    private NonNullList<ItemStack> allItemStacks = NonNullList.create();
    private String[] hexChars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
    private boolean locked;

    public ActionRandom(boolean locked) {
        this.locked = locked;

        for (Item item : ForgeRegistries.ITEMS) {
            item.getSubItems(CreativeTabs.SEARCH, allItemStacks);
        }
    }

    private String random() {

        StringBuilder hexBuild = new StringBuilder("0x");
        Random ran = new Random();

        int loop = 8;

        if (this.locked) {
            hexBuild.append("f0");
            loop = 6;
        }

        for (int i = 0; i < loop; i++) {
            int ranI = ran.nextInt(hexChars.length);
            hexBuild.append(hexChars[ranI]);
        }

        return hexBuild.toString();
    }

    @Override
    public void apply() {
        for (ItemStack item : allItemStacks) {
            Map<String, Long> values = new HashMap<>();
            values.put("background", Long.decode(random()));
            values.put("borderStart", Long.decode(random()));
            values.put("borderEnd", Long.decode(random()));
            ColouredCategories.INGREDIENT_MAP.put(item.getDisplayName(), values);
        }
    }

    @Override
    public String describe() {

        return "Randomized tooltip colours on " + ForgeRegistries.ITEMS.getValuesCollection().size() + " items.";
    }
}

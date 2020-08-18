package pw.stapleton.ColouredCategories.crt;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import pw.stapleton.ColouredCategories.ColouredCategories;

import java.util.HashMap;
import java.util.Map;

public class ActionColour implements IAction {

    private ItemStack itemStack;
    private String bg;
    private String bs;
    private String be;

    public ActionColour(IIngredient ingredients, String background, String borderStart, String borderEnd) {
        this.itemStack = CraftTweakerMC.getItemStack(ingredients);

        if (background.length() == 6) this.bg = "0xf0" + background;
        else if (background.length() == 8) this.bg = "0x" + background;
        else throw new IllegalArgumentException("background colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderStart.length() == 6) this.bs = "0xf0" + borderStart;
        else if (borderStart.length() == 8) this.bs = "0x" + borderStart;
        else throw new IllegalArgumentException("borderStart colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderEnd.length() == 6) this.be = "0xf0" + borderEnd;
        else if (borderEnd.length() == 8) this.be = "0x" + borderEnd;
        else throw new IllegalArgumentException("borderEnd colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");
    }

    @Override
    public void apply() {
        Map<String, String> values = new HashMap<>();

        values.put("background", this.bg);
        values.put("borderStart", this.bs);
        values.put("borderEnd", this.be);

        ColouredCategories.INGREDIENT_MAP.put(itemStack.toString(), values);
    }

    @Override
    public String describe() {
        return "Coloured Categories randomly coloured the tooltip of " + itemStack.toString();
    }
}

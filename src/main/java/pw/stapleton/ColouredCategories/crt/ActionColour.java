package pw.stapleton.ColouredCategories.crt;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import pw.stapleton.ColouredCategories.ColouredCategories;

import java.util.HashMap;
import java.util.Map;

public class ActionColour implements IAction {

    private ItemStack ingredient;
    private String background;
    private String borderStart;
    private String borderEnd;

    public ActionColour(IIngredient ingredients, String background, String borderStart, String borderEnd) {

        this.ingredient = CraftTweakerMC.getItemStack(ingredients);

        if (background.length() == 6) this.background = "0xf0" + background;
        else if (background.length() == 8) this.background = "0x" + background;
        else throw new IllegalArgumentException("background colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderStart.length() == 6) this.borderStart = "0xf0" + borderStart;
        else if (borderStart.length() == 8) this.borderStart = "0x" + borderStart;
        else throw new IllegalArgumentException("borderStart colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderEnd.length() == 6) this.borderEnd = "0xf0" + borderEnd;
        else if (borderEnd.length() == 8) this.borderEnd = "0x" + borderEnd;
        else throw new IllegalArgumentException("borderEnd colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");
    }

    @Override
    public void apply() {

        if (this.ingredient.isEmpty()) throw new IllegalArgumentException("IIngredient is an empty/air stack!");
        if (this.background.isEmpty()) throw new IllegalArgumentException("Empty background colour!");
        if (this.borderStart.isEmpty()) throw new IllegalArgumentException("Empty borderStart colour!");
        if (this.borderEnd.isEmpty()) throw new IllegalArgumentException("Empty borderEnd colour!");

        Map<String, Long> values = new HashMap<>();

        values.put("background", Long.decode(this.background));
        values.put("borderStart", Long.decode(this.borderStart));
        values.put("borderEnd", Long.decode(this.borderEnd));

        ColouredCategories.INGREDIENT_MAP.put(this.ingredient.getDisplayName(), values);
    }

    @Override
    public String describe() {

        return this.ingredient.getDisplayName() + " has been added and coloured.";
    }
}

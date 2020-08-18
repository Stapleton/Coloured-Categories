package pw.stapleton.ColouredCategories.crt;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import pw.stapleton.ColouredCategories.ColouredCategories;

import java.util.*;

public class ActionRandom implements IAction {

    private final String[] hexChars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
    private ItemStack itemStack;
    private String transparency;

    public ActionRandom(IIngredient ingredient, String transparency) {
        itemStack = CraftTweakerMC.getItemStack(ingredient);
        this.transparency = transparency;
    }

    public String random(String transparency) {

        StringBuilder hexBuild = new StringBuilder("0x");
        Random ran = new Random();

        int loop = 8;

        if (!transparency.isEmpty()) {
            hexBuild.append(transparency);
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
        Map<String, String> values = new HashMap<>();

        values.put("background", random(this.transparency));
        values.put("borderStart", random(this.transparency));
        values.put("borderEnd", random(this.transparency));

        ColouredCategories.INGREDIENT_MAP.put(itemStack.toString(), values);
    }

    @Override
    public String describe() {

        return "Coloured Categories randomly coloured the tooltip of " + itemStack.toString();
    }
}

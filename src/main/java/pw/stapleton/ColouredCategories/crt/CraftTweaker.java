package pw.stapleton.ColouredCategories.crt;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ColouredCategories")
public class CraftTweaker {

    @ZenMethod
    public static void colour(IIngredient ingredients, String background, String borderStart, String borderEnd) {
        CraftTweakerAPI.apply(new ActionColour(ingredients, background, borderStart, borderEnd));
    }

    @ZenMethod
    public static void colour(IIngredient[] ingredients, String background, String borderStart, String borderEnd) {
        for (IIngredient ingredient : ingredients) {
            CraftTweakerAPI.apply(new ActionColour(ingredient, background, borderStart, borderEnd));
        }
    }

    @ZenMethod
    public static void random(IIngredient ingredient, String transparency) {
        CraftTweakerAPI.apply(new ActionRandom(ingredient, transparency));
    }

    @ZenMethod
    public static void random(IIngredient[] ingredients, String transparency) {
        for (IIngredient ingredient : ingredients) {
            CraftTweakerAPI.apply(new ActionRandom(ingredient, transparency));
        }
    }
}

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
    public static void random(boolean locked) {
        CraftTweakerAPI.apply(new ActionRandom(locked));
    }
}

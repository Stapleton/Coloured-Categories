package pw.stapleton.colouredcategories.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.colouredcategories.crt")
public class CrT {

    @ZenCodeType.Method
    public static void colour(IIngredient ingredient, String backgroundStart, String backgroundEnd, String borderStart, String borderEnd) {
        CraftTweakerAPI.apply(new ActionColour(ingredient, backgroundStart, backgroundEnd, borderStart, borderEnd));
    }

    @ZenCodeType.Method
    public static void colour(IIngredient[] ingredients, String backgroundStart, String backgroundEnd, String borderStart, String borderEnd) {
        for (IIngredient ingredient : ingredients) {
            CraftTweakerAPI.apply(new ActionColour(ingredient, backgroundStart, backgroundEnd, borderStart, borderEnd));
        }
    }

    @ZenCodeType.Method
    public static void random(IIngredient ingredient) {
        CraftTweakerAPI.apply(new ActionRandom(ingredient));
    }

    @ZenCodeType.Method
    public static void random(IIngredient[] ingredients) {
        for (IIngredient ingredient : ingredients) {
            CraftTweakerAPI.apply(new ActionRandom(ingredient));
        }
    }
}

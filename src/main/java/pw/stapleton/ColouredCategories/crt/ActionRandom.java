package pw.stapleton.colouredcategories.crt;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import net.minecraft.world.item.Item;
import pw.stapleton.colouredcategories.ColouredCategories;

import java.util.*;

public class ActionRandom implements IAction {
    private final Item item;

    public ActionRandom(IIngredient ingredient) {
        item = ingredient.asVanillaIngredient().getItems()[0].getItem();
    }


    @Override
    public void apply() {
        Map<String, String> values = new HashMap<>();

        values.put("backStart", ColouredCategories.RANDOM_HEX_COLOUR.get());
        values.put("backEnd", ColouredCategories.RANDOM_HEX_COLOUR.get());
        values.put("bordStart", ColouredCategories.RANDOM_HEX_COLOUR.get());
        values.put("bordEnd", ColouredCategories.RANDOM_HEX_COLOUR.get());

        ColouredCategories.ITEM_MAP.put(item, values);
    }

    @Override
    public String describe() {

        return "Coloured Categories randomly coloured the tooltip of " + item.toString();
    }
}

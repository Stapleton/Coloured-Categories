package pw.stapleton.colouredcategories.crt;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import net.minecraft.world.item.Item;
import pw.stapleton.colouredcategories.ColouredCategories;

import java.util.HashMap;
import java.util.Map;

public class ActionColour implements IAction {

    private final Item item;
    private final String bgs;
    private final String bge;
    private final String bds;
    private final String bde;

    public ActionColour(IIngredient ingredients, String backgroundStart, String backgroundEnd, String borderStart, String borderEnd) {
        this.item = ingredients.asVanillaIngredient().getItems()[0].getItem();

        if (backgroundStart.length() == 6) this.bgs = "0xf0" + backgroundStart;
        else if (backgroundStart.length() == 8) this.bgs = "0x" + backgroundStart;
        else throw new IllegalArgumentException("background colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (backgroundEnd.length() == 6) this.bge = "0xf0" + backgroundEnd;
        else if (backgroundEnd.length() == 8) this.bge = "0x" + backgroundEnd;
        else throw new IllegalArgumentException("background colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderStart.length() == 6) this.bds = "0xf0" + borderStart;
        else if (borderStart.length() == 8) this.bds = "0x" + borderStart;
        else throw new IllegalArgumentException("borderStart colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");

        if (borderEnd.length() == 6) this.bde = "0xf0" + borderEnd;
        else if (borderEnd.length() == 8) this.bde = "0x" + borderEnd;
        else throw new IllegalArgumentException("borderEnd colour value must be either 6 characters long (RGB) or 8 characters long (ARGB)");
    }

    @Override
    public void apply() {
        Map<String, String> values = new HashMap<>();

        values.put("backStart", this.bgs);
        values.put("backEnd", this.bge);
        values.put("bordStart", this.bds);
        values.put("bordEnd", this.bde);

        ColouredCategories.ITEM_MAP.put(item, values);
    }

    @Override
    public String describe() {
        return "Coloured Categories randomly coloured the tooltip of " + item.toString();
    }
}

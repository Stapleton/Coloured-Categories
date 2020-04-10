package pw.stapleton;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.LogicalSide;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;

@ZenCodeType.Name("mods.stapleton.CC")
@ZenRegister
public class CCManager {

    @ZenCodeType.Method
    public static void setMany(IItemStack[] stacks, String background, String borderStart, String borderEnd) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                for (IItemStack stack : stacks) {
                    CCManager.setOne(stack, background, borderStart, borderEnd);
                }
            }

            @Override
            public String describe() {
                return stacks.length + " items => Background: " + background + " | BorderStart: " + borderStart + " | BorderEnd: " + borderEnd;
            }

            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return side.isClient();
            }
        });
    }

    @ZenCodeType.Method
    public static void setOne(IItemStack stack, String background, String borderStart, String borderEnd) {
        Map<String, Long> colors = new HashMap<>();

        if (background.length() == 6) background = "0xf0" + background;
        else if (background.length() == 8) background = "0x" + background;
        else throw new IllegalArgumentException("backgroundColor value must be either 6 characters long (RGB Hex format) or 8 characters long (ARGB Hex format)");

        if (borderStart.length() == 6) borderStart = "0x50" + borderStart;
        else if (borderStart.length() == 8) borderStart = "0x" + borderStart;
        else throw new IllegalArgumentException("borderStart value must be either 6 characters long (RGB Hex format) or 8 characters long (ARGB Hex format)");

        if (borderEnd.length() == 6) borderEnd = "0x50" + borderEnd;
        else if (borderEnd.length() == 8) borderEnd = "0x" + borderEnd;
        else throw new IllegalArgumentException("borderEnd value must be either 6 characters long (RGB Hex format) or 8 characters long (ARGB Hex format)");

        final String b1 = background;
        final String b2 = borderStart;
        final String b3 = borderEnd;

        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                colors.put("background", Long.decode(b1));
                colors.put("borderStart", Long.decode(b2));
                colors.put("borderEnd", Long.decode(b3));

                ColoredCategories.ITEM_MAP.put(stack, colors);
            }

            @Override
            public String describe() {
                return stack.getCommandString() + " => Background: " + b1 + " | BorderStart: " + b2 + " | BorderEnd: " + b3;
            }

            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return side.isClient();
            }
        });
    }
}

package pw.stapleton.colouredcategories.util;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue RANDOM_ALL;
    public static ForgeConfigSpec.BooleanValue RANDOM_BACKGROUND_START;
    public static ForgeConfigSpec.BooleanValue RANDOM_BACKGROUND_END;
    public static ForgeConfigSpec.BooleanValue RANDOM_BORDER_START;
    public static ForgeConfigSpec.BooleanValue RANDOM_BORDER_END;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COLOUR_CATEGORIES;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupRandomConfig(configBuilder);
        setupColourConfig(configBuilder);
        CLIENT_CONFIG = configBuilder.build();
    }

    private static void setupRandomConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment(" All Random Settings");
        configBuilder.push("random");
        configBuilder.comment(" Randomly colour for all tooltip parts");
        RANDOM_ALL = configBuilder.define("randomAll", true);

        configBuilder.comment(" Randomly colour only the top of the tooltip background");
        RANDOM_BACKGROUND_START = configBuilder.define("randomBackgroundStart", false);
        configBuilder.comment(" Randomly colour only the bottom of the tooltip background");
        RANDOM_BACKGROUND_END = configBuilder.define("randomBackgroundEnd", false);

        configBuilder.comment(" Randomly colour only the top of the tooltip border");
        RANDOM_BORDER_START = configBuilder.define("randomBorderStart", false);
        configBuilder.comment(" Randomly colour only the bottom of the tooltip border");
        RANDOM_BORDER_END = configBuilder.define("randomBorderEnd", false);
        configBuilder.pop();
    }

    private static void setupColourConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment(" Colour Settings");
        configBuilder.push("colours");
        configBuilder.comment(" Define your Coloured Categories here! Theres a format to follow, and an example below!\n Format: BackgroundStart,BackgroundEnd,BorderStart,BorderEnd|ItemStack or ItemStack,ItemStack,...\n You can use either RGB or ARGB hex codes. Use '0x' instead of the '#' on the hexcode.");
        COLOUR_CATEGORIES = configBuilder.defineList("categories", Arrays.asList("0xf08A7568,0xf08A7568,0xf03F352F,0xf03F352F|minecraft:wheat_seeds,minecraft:dirt", "0xf0F50D00,0xf0FA5E0D,0xf0DE0B50,0xf0FA0DD2|minecraft:sand,minecraft:oak_sapling"), entry -> true);
        configBuilder.pop();
    }
}

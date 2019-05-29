package pw.stapleton.ColouredCategories.util;

import net.minecraftforge.common.config.Configuration;
import pw.stapleton.ColouredCategories.ColouredCategories;

import java.awt.Color;
import java.io.File;

public class Config {

    public static Configuration config;

    // rgb(97, 0, 255)
    public static long start;
    // rgb(97, 0, 255)
    public static long end;
    // rgb(96, 72, 135)
    public static long background;

    public Config(File file) {

        config = new Configuration(file);
        config.setCategoryComment(Configuration.CATEGORY_GENERAL, "Colour values are ARGB hex. The # is not needed at the beginning. Opaque RGB is FF as the first two characters.");
        this.syncConfigData();
    }

    private void syncConfigData() {

        start = this.getColour("borderStart", "506100ff", "top");
        end = this.getColour("borderEnd", "506100ff", "bottom");
        background = this.getColour("background", "f0604887", "background");

        if (config.hasChanged()) {
            config.save();
        }
    }

    private long getColour(String name, String defaultValue, String explain) {

        final String colorValue = "0x" + config.getString(name, Configuration.CATEGORY_GENERAL, defaultValue, "The color for the " + explain + " of the tooltip. This should be 8 characters.");

        try {

            return Long.decode(colorValue);
        }
        catch (final Exception e) {

            ColouredCategories.Logger.trace("Could not read colour for " + name + ". Invalid colour: " + colorValue + " Default: " + defaultValue);
            ColouredCategories.Logger.trace(e);
        }

        return Color.WHITE.getRGB();

    }
}

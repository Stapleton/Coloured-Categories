package pw.stapleton.ColouredCategories.util;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class Config {

    public static Configuration config;

    // rgb(97, 0, 255)
    public static String borderStart;
    // rgb(97, 0, 255)
    public static String borderEnd;
    // rgb(96, 72, 135)
    public static String background;

    public Config(File file) {

        config = new Configuration(file);
        config.setCategoryComment(Configuration.CATEGORY_GENERAL, "Colour values are ARGB hex. The # is not needed at the beginning. Opaque RGB is FF as the first two characters.");
        this.syncConfigData();
    }

    private void syncConfigData() {

        borderStart = this.getColour("borderStart", "506100ff", "top");
        borderEnd = this.getColour("borderEnd", "506100ff", "bottom");
        background = this.getColour("background", "f0604887", "background");

        if (config.hasChanged()) {
            config.save();
        }
    }

    private String getColour(String name, String defaultValue, String explain) {
        return "0x" + config.getString(name, Configuration.CATEGORY_GENERAL, defaultValue, "The color for the " + explain + " of the tooltip. This should be 8 characters.");
    }
}

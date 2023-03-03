package pw.stapleton.colouredcategories.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomHexColour {
    Random r = new Random();
    public RandomHexColour() {
    }

    private String generateColor(Random r) {
        int newColor = 0x1000000 + r.nextInt(0x1000000);
        return "0xf0" + Integer.toHexString(newColor).substring(1, 7);
    }

    public Map<String, String> randomAll() {
        Map<String, String> codes = new HashMap<>();

        codes.put("backStart", this.get());
        codes.put("backEnd", this.get());
        codes.put("bordStart", this.get());
        codes.put("bordEnd", this.get());

        return codes;
    }

    public String get() {
        return generateColor(r);
    }
}

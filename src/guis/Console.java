package guis;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Console {

    private static boolean active = false;
    private static List<String> chatFeed = new ArrayList<String>();

    public Console() {
    }

    public static void addFeed(String content) {
        if (content.startsWith("/")) {
            System.exit(-1);
        } else {
            chatFeed.add(content);
        }
    }

    public static void init() {
        if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
            if (!active) active = true;
            if (active) active = false;
            openConsole();
        }
    }

    private static void openConsole() {
        for (int i = 0; i < chatFeed.size(); i++) {
            System.out.println(chatFeed.get(i));
        }
    }
}

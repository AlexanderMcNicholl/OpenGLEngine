package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

import java.util.Random;

public class DisplayManager {

    private static final int randomInt0 = new Random().nextInt(800), randomInt1 = new Random().nextInt(800), randomIntSum = randomInt0 + randomInt1;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;

    private static long lastFrameTime;
    private static float delta;

    private static final String ApplicationTitle[] = {
            "OpenGLGame", "SuperDuper OpenGLGame (Best Game Of The Year)", "Bad Game",
            "Eh", "What?", "Sooooooo, Its A Title Your Looking For?", "Just Go Away",
            ":-( Your PC ran into a problem and must restart.", "12:18", "Jiggy Jiggy",
            "Looks Like You're Playing A Game.... Would You Like Some Help?", "BOO!",
            "Domino's Pizza", "", "lean mean fighting machine",
            "Tip Top!", "I completely ripped-off Terraria's random title sequence", "Made with Java!", "(/*.*)/",
            "I am running out of ideas for titles", "ONLY $29.99(99)!", "terms and conditions may apply",
            "public static void main (String [] args) {}", ("HERE IS A RANDOM NUMBER! " + new Random().nextInt(1000)),
            "Shrinky Dink has Good Day!", ("My will to live: " + new Random().nextInt(80)),
            ("" + randomInt0 + " + " + randomInt1 + " = " + randomIntSum),
            "Title tim was shot on his way here..... R.I.P tim ~ 2017-2017", "Crucifixion is a doddle..",
            "Zoo-Wee Mamma!", "Micheal.J.Fox makes the BEST milkshakes!", "Humphrey Bogart"
    };

    private static final int randomInt = new Random().nextInt(ApplicationTitle.length);

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("Untitled Game: " + ApplicationTitle[randomInt]);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

}

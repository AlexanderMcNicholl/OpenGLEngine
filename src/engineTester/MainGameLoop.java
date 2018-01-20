package engineTester;

import States.GameSaver;
import States.LevelEditor;
import entities.*;
import guis.Console;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    private static LevelEditor le;

    public static void callSaves() {

    }

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //TODO: TerrainStuff
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        //TODO: TerrainStuff

        //ENTITIES
        RawModel stallModel = OBJLoader.loadObjModel("stall", loader);
        RawModel treeModel = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("flower")));
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("fern")));
        TexturedModel bobble = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
                new ModelTexture(loader.loadTexture("lowPolyTree")));
        TexturedModel bunnyTex = new TexturedModel(OBJLoader.loadObjModel("stanfordBunny", loader),
                new ModelTexture(loader.loadTexture("image")));
        TexturedModel barrel = new TexturedModel(OBJLoader.loadObjModel("stanfordBunny", loader),
                new ModelTexture(loader.loadTexture("image")));

        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setUseFakeLighting(true);

        //Array Lists
        List<Terrain> terrains = new ArrayList<Terrain>();
        List<Entity> entities = new ArrayList<Entity>();

        //Terrains
        Terrain t = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        Terrain t1 = new Terrain(1, 1, loader, texturePack, blendMap, "heightmap");
        terrains.add(t);
        terrains.add(t1);

        Random random = new Random(676452);
        for (Terrain terrain : terrains) {
            for (int i = 0; i < 400; i++) {
                if (i % 20 == 0) {
                    float x = random.nextFloat() * 800 - 400;
                    float z = random.nextFloat() * -600;
                    float y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360,
                            0, 0.9f));
                }
                if (i % 5 == 0) {
                    float x = random.nextFloat() * 800 - 400;
                    float z = random.nextFloat() * -600;
                    float y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360,
                            0, random.nextFloat() * 0.1f + 0.6f));
                    x = random.nextFloat() * 800 - 400;
                    z = random.nextFloat() * -600;
                    y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360,
                            0, 8));
                }
            }
        }

        entities.add(new Entity(barrel, new Vector3f(0, 6, 0), 0, 0, 0, 0));

        //Lights and Shader.
        Light light = new Light(new Vector3f(0, -25, -25), new Vector3f(1, 1, 1));
        List<Light> lights = new ArrayList<Light>();
        lights.add(light);

        MasterRenderer renderer = new MasterRenderer();

        //Player
        Player player = new Player(barrel, new Vector3f(100, -4, -50), 0, 0, 0, 0.6f, 1f);
        Camera camera = new Camera(player);

        //Level Editor
        le = new LevelEditor(player);

        //GUI
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        //TODO: Include Entities Here.
        entities.add(new Dummy(bunnyTex, new Vector3f(100, -4, -60), 0, 0, 0, 1, 1f));

        //GameSaver
        GameSaver saver = new GameSaver(player);

        //Chat
        Console console;
        console = new Console();

        while (!Display.isCloseRequested()) {

            //Console
            console.init();

            //Game Saves
            if (Keyboard.isKeyDown(Keyboard.KEY_F5)) {
                System.out.println("Quick Saving...");
                saver.SaveGame();
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F9)) {
                System.out.println("Quick Loading...");
                saver.loadSave();
            }

            //Terrains
            for (Terrain terrain : terrains) {
                player.move(terrain);
                renderer.processTerrain(terrain);
            }
            //Camera
            camera.move();

            //Renderer
            renderer.processEntity(player);

            //Entities
            for (Entity e : entities) {
                renderer.processEntity(e);
                if (!e.active) entities.remove(e);
            }
            //Display
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        //More Renderer
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

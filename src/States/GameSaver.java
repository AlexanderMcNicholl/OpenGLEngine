package States;

import entities.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GameSaver {

    private Player player;
    private String saveFilePath = "res/saves/playerPosSave.txt";

    public GameSaver(Player player) {
        this.player = player;
    }

    public void SaveGame() {
        try {
            PrintWriter writer = new PrintWriter(saveFilePath, "UTF-8");

            //Player Position
            writer.println(player.getPosition().x);
            writer.println(player.getPosition().y);
            writer.println(player.getPosition().z);

            //TODO: Write pos for all other entities here!
            /*
            writer.println(Entity Pos);
             */
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSave() {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(saveFilePath);
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(saveFilePath));
            while ((sCurrentLine = br.readLine()) != null) {

                //Player Position
                player.getPosition().setX(Float.parseFloat(sCurrentLine));
                sCurrentLine = br.readLine();
                player.getPosition().setY(Float.parseFloat(sCurrentLine));
                sCurrentLine = br.readLine();
                player.getPosition().setZ(Float.parseFloat(sCurrentLine));

                //TODO: add reader positions for all other entities here!

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
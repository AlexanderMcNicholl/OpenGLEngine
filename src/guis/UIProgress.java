package guis;

import renderEngine.Loader;

public class UIProgress {

    private GuiTexture texture;
    private int progress;
    private Loader loader;

    public UIProgress(Loader loader, GuiTexture texture, int progress) {
        this.texture = texture;
        this.progress = progress;
    }

    //Getters & Setters
    public GuiTexture getTexture() {
        return texture;
    }

    public int getProgress() {
        return progress;
    }
}

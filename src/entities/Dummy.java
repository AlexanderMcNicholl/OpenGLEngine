package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Dummy extends Entity {

    private float health;
    private boolean active = true;

    public Dummy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float health) {
        super(model, position, rotX, rotY, rotZ, scale);

        this.health = health;
    }

    private void checkHealth() {
        if (health <= 0) {
            active = false;
        } else if (health > 0) {
            active = true;
        }
    }
    public float getHealth() {
        return health;
    }

    public boolean isActive() {
        return active;
    }
}

package objects;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float theta = 0;

	private Vector3f position = new Vector3f(0, 3, 0);
	private float pitch = ((float) Math.PI)/2;
	private float yaw = 0;
	private float roll;
	
	public Camera() {}
	
	public void move() {
		if(Mouse.isButtonDown(0)) {
			pan(Mouse.getDX() * 0.3f, Mouse.getDY() * 0.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y += 0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y -= 0.2f;
        }
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	
	public void pan(float y, float p) {
		 yaw += y;
		 pitch -= p;
	}
}

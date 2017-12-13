package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objects.Camera;
import objects.Entity;
import objects.Light;
import render.DisplayManager;
import render.Loader;
import render.OBJLoader;
import render.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class EngineLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("white_preview"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested() ) {
			// game logic
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

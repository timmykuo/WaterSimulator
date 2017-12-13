package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objects.Camera;
import objects.Entity;
import objects.Light;
import render.DisplayManager;
import render.Loader;
import render.MasterRenderer;
import render.OBJLoader;
import render.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class EngineLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		ModelTexture texture = staticModel.getTexture();
//		texture.setShineDamper(10);
//		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		
//		List<Entity> allDragons = new ArrayList<Entity>();
//		Random random = new Random();
//		
//		for(int i = 0; i < 200; i++) {
//			float x = random.nextFloat() * 100 - 50;
//			float y = random.nextFloat() * 100 - 50;
//			float z = random.nextFloat() * -300;
//			allDragons.add(new Entity(staticModel, new Vector3f(x,y,z), 
//					random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
//		}
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested() ) {
			// game logic
//			entity.increaseRotation(0, 1, 0);
			camera.move();
			
//			for(Entity dragon: allDragons) {
//				renderer.processEntity(dragon);
//			}
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

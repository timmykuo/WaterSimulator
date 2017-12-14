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
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class EngineLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), 
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), 
				new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
//		ModelTexture texture = staticModel.getTexture();
//		texture.setShineDamper(10);
//		texture.setReflectivity(1);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 200; i++) {
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 400 - 200, 0, 
					random.nextFloat() * -300), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 400 - 200, 0, 
					random.nextFloat() * -300), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 400 - 200, 0, 
					random.nextFloat() * -300), 0, 0, 0, 0.6f));
		}
		
//		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")), "heightmap");
		Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")), "heightmap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
		terrains.add(terrain2);
		
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
		List<WaterTile> waters = new ArrayList<WaterTile>();
		waters.add(new WaterTile(0, -75, 1));
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		
		while (!Display.isCloseRequested() ) {
			// game logic
//			entity.increaseRotation(0, 1, 0);
			camera.move();
			
			fbos.bindReflectionFrameBuffer();
			renderer.renderScene(entities, terrains, light, camera);
			fbos.unbindCurrentFrameBuffer();
			
			renderer.renderScene(entities, terrains, light, camera);
//			renderer.processTerrain(terrain);
//			renderer.processTerrain(terrain2);
//			for(Entity entity: entities) {
//				renderer.processEntity(entity);
//			}
			waterRenderer.render(waters, camera);
//			renderer.processEntity(entity);
			
//			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		fbos.cleanUp();
		waterShader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

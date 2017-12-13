package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objects.Camera;
import objects.Entity;
import render.DisplayManager;
import render.Loader;
import render.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class EngineLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		// testing the engine
		float[] vertices = {		// vertices for a cube
				-0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                0.5f,-0.5f,0,   
                0.5f,0.5f,0,        
                 
                -0.5f,0.5f,1,   
                -0.5f,-0.5f,1,  
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                0.5f,0.5f,0,    
                0.5f,-0.5f,0,   
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                -0.5f,-0.5f,1,  
                -0.5f,0.5f,1,
                 
                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,
                 
                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1
		};
		
		int[] indices = {
				0,1,3,  
                3,1,2,  
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,   
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
		};
		
		float[] textureCoords = {
				0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("green_face_texture"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-5), 0, 0, 0, 1);
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested() ) {
			// game logic
//			entity.increasePosition(0, 0, -0.1f);
			entity.increaseRotation(1, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
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

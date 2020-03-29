package com.lukauranic.main.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.lukauranic.main.MainClass;
import com.lukauranic.main.graphics.Renderer;
import com.lukauranic.main.input.Keyboard;

public class Game {	

	Arm arm;

	public final int baseX = MainClass.WIDTH / 2, baseY = MainClass.HEIGHT / 10 * 6, baseZ = 200;
	int counter = 0, speed = 1;
	double rotSpeed = 0.02, rotX = 0.0, rotY = 0.0, rotZ = 0.0;
	
	public Game() {
		init();
	}
	
	public void init() {
		arm = new Arm(baseX, baseY, baseZ);
	}
	
//	public void moveAroundTheArmWithKeyboard() {
//		if(Keyboard.key(KeyEvent.VK_A)) {
//			rotY = rotSpeed;
//		}
//		if(Keyboard.key(KeyEvent.VK_D)) {
//			rotY = -rotSpeed;			
//		}
//		if(Keyboard.key(KeyEvent.VK_D) && Keyboard.key(KeyEvent.VK_A) ||
//		!Keyboard.key(KeyEvent.VK_D) && !Keyboard.key(KeyEvent.VK_A)) {
//			rotY = 0.0;
//		}			
//		if(Keyboard.key(KeyEvent.VK_W)) {
//			rotX = rotSpeed;
//		}
//		if(Keyboard.key(KeyEvent.VK_S)) {
//			rotX = -rotSpeed;			
//		}
//		if(Keyboard.key(KeyEvent.VK_W) && Keyboard.key(KeyEvent.VK_S) ||
//		!Keyboard.key(KeyEvent.VK_W) && !Keyboard.key(KeyEvent.VK_S)) {
//			rotX = 0.0;
//		}
//		if(Keyboard.key(KeyEvent.VK_I)) {
//			rotZ = rotSpeed;
//		}
//		if(Keyboard.key(KeyEvent.VK_K)) {
//			rotZ = -rotSpeed;			
//		}
//		if(Keyboard.key(KeyEvent.VK_I) && Keyboard.key(KeyEvent.VK_K) ||
//		!Keyboard.key(KeyEvent.VK_I) && !Keyboard.key(KeyEvent.VK_K)) {
//			rotZ = 0.0;
//		}
//		
//		arm.rx += rotX;
//		arm.ry += rotY;
//		arm.rz += rotZ;	
//	}
	
	
	
	public void update() {			
		arm.update();		
//		moveAroundTheArmWithKeyboard();
	}
	
	public void render() {
		Renderer.renderBackground();				
		arm.render();		
		for(int i = 0; i < Renderer.pixels.length; i++) {
			MainClass.pixels[i] = Renderer.pixels[i];
		}
	}
	
	public void postRender(Graphics2D g) {
		arm.postRender(g);
	}
}







































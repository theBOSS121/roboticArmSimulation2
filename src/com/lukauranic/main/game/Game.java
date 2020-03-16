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
	
	public void moveArm() {
		if(Keyboard.key(KeyEvent.VK_A)) {
			rotY = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_D)) {
			rotY = -rotSpeed;			
		}
		if(Keyboard.key(KeyEvent.VK_D) && Keyboard.key(KeyEvent.VK_A) ||
		!Keyboard.key(KeyEvent.VK_D) && !Keyboard.key(KeyEvent.VK_A)) {
			rotY = 0.0;
		}			
		if(Keyboard.key(KeyEvent.VK_W)) {
			rotX = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_S)) {
			rotX = -rotSpeed;			
		}
		if(Keyboard.key(KeyEvent.VK_W) && Keyboard.key(KeyEvent.VK_S) ||
		!Keyboard.key(KeyEvent.VK_W) && !Keyboard.key(KeyEvent.VK_S)) {
			rotX = 0.0;
		}
		if(Keyboard.key(KeyEvent.VK_I)) {
			rotZ = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_K)) {
			rotZ = -rotSpeed;			
		}
		if(Keyboard.key(KeyEvent.VK_I) && Keyboard.key(KeyEvent.VK_K) ||
		!Keyboard.key(KeyEvent.VK_I) && !Keyboard.key(KeyEvent.VK_K)) {
			rotZ = 0.0;
		}
		
		arm.rx += rotX;
		arm.ry += rotY;
		arm.rz += rotZ;
		
//		joint5.moveToOrigin();
//		Point3d p;
//		for(int i = 0; i < joint5.points.length; i++) {			
//			p = Renderer.rotateX(joint5.points[i], rotX);
//			joint5.points[i].x = p.x;
//			joint5.points[i].y = p.y;
//			joint5.points[i].z = p.z;
//			p = Renderer.rotateY(joint5.points[i], rotY);
//			joint5.points[i].x = p.x;
//			joint5.points[i].y = p.y;
//			joint5.points[i].z = p.z;
//			p = Renderer.rotateZ(joint5.points[i], rotZ);
//			joint5.points[i].x = p.x;
//			joint5.points[i].y = p.y;
//			joint5.points[i].z = p.z;	
//		}
//		joint5.moveToPosition();
//		joint5.rx += rotX;
//		joint5.ry += rotY;
//		joint5.rz += rotZ;
//
//
//		joint5.moveToOrigin();
//		if(Keyboard.key(KeyEvent.VK_A)) {
//			joint5.x -= speed;
//		}
//		if(Keyboard.key(KeyEvent.VK_D)) {
//			joint5.x += speed;			
//		}
//		if(Keyboard.key(KeyEvent.VK_W)) {
//			joint5.y -= speed;
//		}
//		if(Keyboard.key(KeyEvent.VK_S)) {
//			joint5.y += speed;		
//		}
//		if(Keyboard.key(KeyEvent.VK_UP)) {
//			joint5.z += speed;
//		}
//		if(Keyboard.key(KeyEvent.VK_DOWN)) {
//			joint5.z -= speed;		
//		}
//		joint5.moveToPosition();		
	}
	
	
	
	public void update() {	
		
		
		
		arm.update();
		
		moveArm();
		

		counter++;
	}
	
	public void render() {
		Renderer.renderBackground();		
		
		arm.render();
		
		for(int i = 0; i < Renderer.pixels.length; i++) {
			MainClass.pixels[i] = Renderer.pixels[i];
		}
	}
	
	public void postRender(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke());
		g.drawString("Joint rotation speed: " + ((int) (arm.rotSpeed * 1000) / 1000.0), 10, 10);
	}
}







































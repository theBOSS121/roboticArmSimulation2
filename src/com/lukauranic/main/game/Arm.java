package com.lukauranic.main.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.lukauranic.main.graphics.Renderer;
import com.lukauranic.main.input.Keyboard;

public class Arm {
	
	public List<Cuboid> parts = new ArrayList<>();
	public List<Cuboid> renderParts = new ArrayList<>(parts);

	public int baseX, baseY, baseZ, targetX, targetY, targetZ;
	public double rx, ry, rz;
	
	public double rotSpeed = 0.05;

	public Arm(int baseX, int baseY, int baseZ) {
		this.baseX = baseX;
		this.baseY = baseY;
		this.baseZ = baseZ;
		parts.add(new Cuboid(baseX, baseY + 1, baseZ,  40, 2, 40));
		parts.add(new Cuboid(baseX, baseY - 22 / 2, baseZ,  9, 22, 9));
		parts.add(new Cuboid(baseX + 3 / 2 + 9 / 2, baseY - 35 / 2 - 18 + 4, baseZ,  3, 35, 7));
		parts.add(new Cuboid(baseX, baseY - 44, baseZ,  9, 6, 6));
		parts.add(new Cuboid(baseX, baseY - 44, baseZ - 17 / 2 - 6 / 2,  6, 6, 17));
		parts.add(new Cuboid(baseX, baseY - 44, baseZ - 17 - 6 / 2 - 4 / 2,  4, 4, 7));
		parts.add(new Cuboid(baseX, baseY - 44, baseZ - 26,  2, 2, 2));
		


//		axis changes (joints will be rotated around those coordinates so in some cases that is not the middle)
//		by default rotation will occur around the middle of the object; those changes are important some others are not
		parts.get(5).z = baseZ - 20;		
		parts.get(2).y = baseY - 18;		
		parts.get(1).y += 10;
		
		parts.get(1).rz = Math.PI / 2;
		
		rotate(-Math.PI / 2, 0, 0, 0, -Math.PI / 2, 0);
	}
	
	
	public void moveWithKeyboard() {
//		seperate joints
		double jr1 = 0.0, jr2 = 0.0, jr3 = 0.0, jr4 = 0.0, jr5 = 0.0, jr6 = 0.0;
		if(Keyboard.key(KeyEvent.VK_G)) {
			if(rotSpeed > 0.01) rotSpeed -= 0.001;
		}
		if(Keyboard.key(KeyEvent.VK_F)) {
			if(rotSpeed < 0.15) rotSpeed += 0.001;
		}
		if(Keyboard.key(KeyEvent.VK_1)) {
			jr1 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_2)) {
			jr2 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_3)) {
			jr3 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_4)) {
			jr4 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_5)) {
			jr5 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_6)) {
			jr6 = rotSpeed;
		}
		if(Keyboard.key(KeyEvent.VK_SHIFT)) {
			jr1 *= -1;
			jr2 *= -1;
			jr3 *= -1;
			jr4 *= -1;
			jr5 *= -1;
			jr6 *= -1;
		}
		rotate(jr1, jr2, jr3, jr4, jr5, jr6);
		
//		move target coordinates
		if(Keyboard.key(KeyEvent.VK_UP)) {
			targetY--;
		}
		if(Keyboard.key(KeyEvent.VK_DOWN)) {
			targetY++;
		}
		if(Keyboard.key(KeyEvent.VK_LEFT)) {
			targetX--;
		}
		if(Keyboard.key(KeyEvent.VK_RIGHT)) {
			targetX++;
		}
		if(Keyboard.key(KeyEvent.VK_O)) {
			targetZ--;
		}
		if(Keyboard.key(KeyEvent.VK_L)) {
			targetZ++;
		}
	}	
	
	public void update() {
		Point3d p;
		moveWithKeyboard();
		
//		todo change this method to the one i used in the other simulation add start and end point to every joint
//		targetX = baseX + 20;
//		targetY = (int) (parts.get(2).y - 25);
//		targetZ = baseZ + 25;
		
		double xDiff = targetX - baseX;
		double yDiff = targetY - parts.get(2).y;
		double zDiff = targetZ - baseZ;
		
		
		double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
		if(dist == 0) dist = 0.001;
		if(dist > 46) dist = 46;
		if(dist < 6) dist = 6;
		
		double angle1Prefix = Math.atan2(yDiff, xDiff);
		double angle2;
		if(targetX >= baseX) {
			angle2 = -Math.PI / 2 + Math.acos((dist * dist + 26 * 26 - 20 * 20) / (2 * dist * 26)) - angle1Prefix;			
		}else {
			angle2 = +Math.PI / 2 + Math.acos((dist * dist + 26 * 26 - 20 * 20) / (2 * dist * 26)) + angle1Prefix;						
		}
		double angle3 = -Math.PI / 2 + Math.acos((26 * 26 + 20 * 20 - dist * dist) / (2 * 26 * 20));
		
		if(Math.abs(angle2 - parts.get(2).rx) > rotSpeed) {
			if(angle2 > parts.get(2).rx) {
				rotate(0, rotSpeed, 0, 0, 0, 0);
			}else if(angle2 < parts.get(2).rx) {
				rotate(0, -rotSpeed, 0, 0, 0, 0);			
			}			
		}else {
			rotate(0, angle2 - parts.get(2).rx, 0, 0, 0, 0);			
		}
		if(Math.abs(angle3 - parts.get(3).rx) > rotSpeed) {
			if(angle3 > parts.get(3).rx) {
				rotate(0, 0, rotSpeed, 0, 0, 0);
			}else if(angle3 < parts.get(3).rx) {
				rotate(0, 0, -rotSpeed, 0, 0, 0);			
			}			
		}else {
			rotate(0, 0, angle3 - parts.get(3).rx, 0, 0, 0);			
		}
		

		double angle1 = Math.atan2(xDiff, zDiff) - Math.PI;
		
		if(Math.abs(angle1 - parts.get(1).ry) > rotSpeed) {
			if(angle1 > parts.get(1).ry) {
				rotate(rotSpeed, 0, 0, 0, 0, 0);
			}else if(angle1 < parts.get(1).ry) {
				rotate(-rotSpeed, 0, 0, 0, 0, 0);			
			}			
		}else {
			rotate(angle1 - parts.get(1).ry, 0, 0, 0, 0, 0);			
		}
		

//		this will get displayed !!!
		renderParts = new ArrayList<>();
		for(int i = 0; i < parts.size(); i++) {
			renderParts.add(new Cuboid(parts.get(i)));
		}
		
//		for rendering and virtual rotation of the arm
		for(int i = 0; i < renderParts.size(); i++) {
			for(int j = 0; j < renderParts.get(i).points.length; j++) {
				renderParts.get(i).points[j].x -= this.baseX;
				renderParts.get(i).points[j].y -= this.baseY - 25;
				renderParts.get(i).points[j].z -= this.baseZ;	
				p = Renderer.rotateY(renderParts.get(i).points[j], ry);
				renderParts.get(i).points[j].x = p.x;
				renderParts.get(i).points[j].y = p.y;
				renderParts.get(i).points[j].z = p.z;					
				p = Renderer.rotateX(renderParts.get(i).points[j], rx);
				renderParts.get(i).points[j].x = p.x;
				renderParts.get(i).points[j].y = p.y;
				renderParts.get(i).points[j].z = p.z;						
//				not using it
//				p = Renderer.rotateZ(renderParts.get(i).points[j], rz);
//				renderParts.get(i).points[j].x = p.x;
//				renderParts.get(i).points[j].y = p.y;
//				renderParts.get(i).points[j].z = p.z;	
				renderParts.get(i).points[j].x += this.baseX;
				renderParts.get(i).points[j].y += this.baseY - 25;
				renderParts.get(i).points[j].z += this.baseZ;			
			}		
		}		
	}
	
	private void rotate(double r1, double r2, double r3, double r4, double r5, double r6) {		
		for(int i = 1; i < parts.size(); i++) {
			parts.get(i).rotate(0, -parts.get(1).ry, 0, parts.get(1).x, parts.get(1).y,  parts.get(1).z, false);
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(-parts.get(2).rx, 0, 0, parts.get(2).x, parts.get(2).y,  parts.get(2).z, false);
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(-parts.get(3).rx, 0, 0, parts.get(3).x, parts.get(3).y,  parts.get(3).z, false);
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(0, 0, -parts.get(4).rz, parts.get(4).x, parts.get(4).y,  parts.get(4).z, false);
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(-parts.get(5).rx, 0, 0, parts.get(5).x, parts.get(5).y,  parts.get(5).z, false);
		}
//		for rotating 6th joint		
		for(int i = 6; i < parts.size(); i++) {	
			if(i == 6) {
				parts.get(i).rotate(0, 0, r6, parts.get(6).x, parts.get(6).y,  parts.get(6).z, true);					
			}else {
				parts.get(i).rotate(0, 0, r6, parts.get(6).x, parts.get(6).y,  parts.get(6).z, false);	
			}
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(parts.get(5).rx, 0, 0, parts.get(5).x, parts.get(5).y,  parts.get(5).z, false);
		}
//		for rotating 5th joint		
		for(int i = 5; i < parts.size(); i++) {	
			if(i == 5) {
				parts.get(i).rotate(r5, 0, 0, parts.get(5).x, parts.get(5).y,  parts.get(5).z, true);					
			}else {
				parts.get(i).rotate(r5, 0, 0, parts.get(5).x, parts.get(5).y,  parts.get(5).z, false);	
			}
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(0, 0, parts.get(4).rz, parts.get(4).x, parts.get(4).y,  parts.get(4).z, false);
		}
//		for rotating 4th joint		
		for(int i = 4; i < parts.size(); i++) {	
			if(i == 4) {
				parts.get(i).rotate(0, 0, r4, parts.get(4).x, parts.get(4).y,  parts.get(4).z, true);					
			}else {
				parts.get(i).rotate(0, 0, r4, parts.get(4).x, parts.get(4).y,  parts.get(4).z, false);	
			}
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(parts.get(3).rx, 0, 0, parts.get(3).x, parts.get(3).y,  parts.get(3).z, false);
		}	
//		for rotating 3rd joint		
		for(int i = 3; i < parts.size(); i++) {	
			if(i == 3) {
				parts.get(i).rotate(r3, 0, 0, parts.get(3).x, parts.get(3).y,  parts.get(3).z, true);	 
			}else {
				parts.get(i).rotate(r3, 0, 0, parts.get(3).x, parts.get(3).y,  parts.get(3).z, false);
			}	
		}		
		for(int i = 1; i < parts.size(); i++) {			
			parts.get(i).rotate(parts.get(2).rx, 0, 0, parts.get(2).x, parts.get(2).y,  parts.get(2).z, false);
		}		
//		for rotating 2nd joint		
		for(int i = 2; i < parts.size(); i++) {		
			if(i == 2) {
				parts.get(i).rotate(r2, 0, 0, parts.get(2).x, parts.get(2).y,  parts.get(2).z, true);				
			}else {
				parts.get(i).rotate(r2, 0, 0, parts.get(2).x, parts.get(2).y,  parts.get(2).z, false);
			}
		}		
		for(int i = 1; i < parts.size(); i++) {
			parts.get(i).rotate(0, parts.get(1).ry, 0, parts.get(1).x, parts.get(1).y,  parts.get(1).z, false);
		}
//		for rotating 1st joint
		for(int i = 1; i < parts.size(); i++) {
			if(i == 1) {
				parts.get(i).rotate(0, r1, 0, parts.get(1).x, parts.get(1).y,  parts.get(1).z, true);				
			}else {
				parts.get(i).rotate(0, r1, 0, parts.get(1).x, parts.get(1).y,  parts.get(1).z, false);
			}
		}
	}

	public void render() {
		Point3d p, p2;
		for(int i = 0; i < renderParts.size(); i++) {
			for(int j = 0; j < renderParts.get(i).connections.length; j++) {
				p = renderParts.get(i).points[renderParts.get(i).connections[j][0]];
				p2 = renderParts.get(i).points[renderParts.get(i).connections[j][1]];
//				to render it only when they are infront of camera (should have been fixed if only one is in front to draw it as well)
				if(p.z <= 0 || p2.z <= 0) continue;			
//				renders a line between two psoints
				Renderer.renderLine(p, p2);	
			}	
		}
		
	}


	public void postRender(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Verdana", 1, 14));
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke());
		g.drawString("Joint rotation speed: " + ((int) (rotSpeed * 1000) / 1000.0), 10, 20);
		g.drawString("baseX = " + baseX, 10, 40);
		g.drawString("baseY = " + baseY, 10, 60);
		g.drawString("baseZ = " + baseZ, 10, 80);
		g.drawString("targetX = " + targetX, 150, 40);
		g.drawString("targetY = " + targetY, 150, 60);
		g.drawString("targetZ = " + targetZ, 150, 80);
	}
	
}

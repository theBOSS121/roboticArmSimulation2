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
	
	public List<Joint> joints = new ArrayList<>();

	public int baseX, baseY, baseZ, targetX, targetY, targetZ;
//	public double rx, ry, rz;
	public Cuboid target;
	
	public double rotSpeed = 0.01;
	double armRotation = 0.0;

	public Arm(int baseX, int baseY, int baseZ) {
		this.baseX = baseX;
		this.baseY = baseY;
		this.baseZ = baseZ;
		
		this.targetX = baseX + 10;
		this.targetY = baseY - 10;
		this.targetZ = baseZ;
		
		target = new Cuboid(new Point3d(targetX, targetY, targetZ), 2, 2, 2);
		
		Point3d p, p2, pc;
		p = new Point3d(baseX, baseY, baseZ);
		p2 = new Point3d(baseX, baseY - 20, baseZ);
		pc = new Point3d(baseX, baseY - 10, baseZ);
		joints.add(new Joint(p, p2, new Cuboid(pc,  10, 20, 10)));

		for(int i = 0; i < 4; i++) {
			p = new Point3d(joints.get(joints.size() - 1).pos2);
			p2 = new Point3d(p.x, p.y - 20, p.z);
			pc.y -= 20;
			
			joints.add(new Joint(p, p2, new Cuboid(pc, 10, 20, 10)));
		}
		
	}
	
	private void moveTargetWithKeyboard() {
		if(Keyboard.key(KeyEvent.VK_S)) {
			targetY++;
		}
		if(Keyboard.key(KeyEvent.VK_W)) {
			targetY--;
		}
		if(Keyboard.key(KeyEvent.VK_UP)) {
			targetZ++;
		}
		if(Keyboard.key(KeyEvent.VK_DOWN)) {
			targetZ--;
		}
		if(Keyboard.key(KeyEvent.VK_D)) {
			targetX++;
		}
		if(Keyboard.key(KeyEvent.VK_A)) {
			targetX--;
		}
	}
	
	public void update() {
		moveTargetWithKeyboard();
		
		Point3d p;
		Point3d rotOrigin;
		Joint j;
		
		target.move(new Point3d(targetX - target.pos.x, targetY - target.pos.y, targetZ - target.pos.z));
		target.pos = new Point3d(targetX, targetY, targetZ);
		
		
		for(int i = 0; i < joints.size(); i++) {
			joints.get(i).moveRotationOrigin(joints.get(0).pos1);
			joints.get(i).rotate(0, -armRotation, 0, false);
		}
		
		
		j = joints.get(joints.size() - 1);
		j.moveRotationOrigin(new Point3d(j.pos1));
		rotOrigin = new Point3d(j.pos1);
		
		double xDiff = targetX - rotOrigin.x;
		double zDiff = targetZ - rotOrigin.z;
		double targetXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		
		double rz = Math.PI; // Math.atan2(targetXZ, rotOrigin.y - targetY);
		j.rotate(0, 0, rz - j.rz, true);
		

		xDiff = baseX - targetX;
		zDiff = baseZ - targetZ;
		targetXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

		j.movePos2(new Point3d(targetXZ + baseX, target.pos.y, target.pos.z));
		

		for(int i = joints.size() - 2; i >= 1; i--) {
			j = joints.get(i);
			j.moveRotationOrigin(j.pos1);
			rotOrigin = j.pos1;			
			rz = Math.atan2(joints.get(i + 1).pos1.y - rotOrigin.y, joints.get(i + 1).pos1.x - rotOrigin.x) + Math.PI / 2;
			j.rotate(0, 0, rz - j.rz, true);
			j.movePos2(new Point3d(joints.get(i + 1).pos1));			
		}
		Point3d n = new Point3d(joints.get(0).pos2);
		j.movePos1(n);		
		for(int i = 2; i < joints.size(); i++) {
			j = joints.get(i);
			j.movePos1(new Point3d(joints.get(i - 1).pos2));
		}
		
		armRotation = Math.atan2(baseZ - targetZ, -(baseX - targetX));						

		for(int i = 0; i < joints.size(); i++) {
			joints.get(i).moveRotationOrigin(joints.get(0).pos1);
			joints.get(i).rotate(0, armRotation, 0, false);
		}		
	}
		
	public void render() {
		Point3d p, p2;
		for(int i = 0; i < joints.size(); i++) {
			for(int j = 0; j < joints.get(i).cuboid.connections.length - 8; j++) {
				p = joints.get(i).cuboid.points[joints.get(i).cuboid.connections[j][0]];
				p2 = joints.get(i).cuboid.points[joints.get(i).cuboid.connections[j][1]];
//				to render it only when they are in front of camera (should have been fixed if only one is in front to draw it as well)
				if(p.z <= 0 || p2.z <= 0) continue;			
//				renders a line between two points
				Renderer.renderLine(p, p2, 0xffffffff);	
			}	
			p = joints.get(i).pos1;
			p2 = joints.get(i).pos2;
			if(p.z <= 0 || p2.z <= 0) continue;			
			Renderer.renderLine(p, p2, 0xff00ff00);	
		}
		
		for(int i = target.connections.length - 8; i < target.connections.length; i++) {
			p = target.points[target.connections[i][0]];
			p2 = target.points[target.connections[i][1]];
//			to render it only when they are in front of camera (should have been fixed if only one is in front to draw it as well)
			if(p.z <= 0 || p2.z <= 0) continue;			
//			renders a line between two points
			Renderer.renderLine(p, p2, 0xffffff00);	
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

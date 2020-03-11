package com.lukauranic.main.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.lukauranic.main.graphics.Renderer;

public class Arm {
	
	public List<Cuboid> parts = new ArrayList<>();
	public List<Cuboid> renderParts = new ArrayList<>(parts);

	public int baseX, baseY, baseZ;
	public double rx, ry, rz;
	
	public double rotSpeed = 0.1;

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
		

		parts.get(1).y += 10;
	}
	
	
	
	public void update() {
		rotate(0.02, 0.1, 0, 0, 0, 0);

//		this will get displayed !!!
		renderParts = new ArrayList<>();
		for(int i = 0; i < parts.size(); i++) {
			renderParts.add(new Cuboid(parts.get(i)));
		}
		
		Point3d p;
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
		Point3d p;

//		for rotating 2st joint (you should first rotate to 0.0 around y axis in order to rotate around x)
//		for(int i = 2; i < parts.size(); i++) {
//			for(int j = 0; j < parts.get(i).points.length; j++) {
//				parts.get(i).points[j].x -= parts.get(2).x;
//				parts.get(i).points[j].y -= parts.get(2).y;
//				parts.get(i).points[j].z -= parts.get(2).z;	
//				p = Renderer.rotateX(parts.get(i).points[j], r2);
//				parts.get(i).points[j].x = p.x;
//				parts.get(i).points[j].y = p.y;
//				parts.get(i).points[j].z = p.z;		
//				parts.get(i).points[j].x += parts.get(2).x;
//				parts.get(i).points[j].y += parts.get(2).y;
//				parts.get(i).points[j].z += parts.get(2).z;		
//			}			
//		}
		
//		for rotating 1st joint
//		for(int i = 1; i < parts.size(); i++) {
//			for(int j = 0; j < parts.get(i).points.length; j++) {
//				parts.get(i).points[j].x -= parts.get(1).x;
//				parts.get(i).points[j].y -= parts.get(1).y;
//				parts.get(i).points[j].z -= parts.get(1).z;	
//				p = Renderer.rotateY(parts.get(i).points[j], r1);
//				parts.get(i).points[j].x = p.x;
//				parts.get(i).points[j].y = p.y;
//				parts.get(i).points[j].z = p.z;		
//				parts.get(i).points[j].x += parts.get(1).x;
//				parts.get(i).points[j].y += parts.get(1).y;
//				parts.get(i).points[j].z += parts.get(1).z;		
//			}			
//		}
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
	
}

package com.lukauranic.main.game;

import com.lukauranic.main.graphics.Renderer;

public class Cuboid {
	public double x, y, z, rx, ry, rz;
	public int width, height, length;
	
	public Point3d[] points = {
			new Point3d(-1, -1, -1),
			new Point3d(1, -1, -1),
			new Point3d(1, 1, -1),
			new Point3d(-1, 1, -1),
			new Point3d(-1, -1, 1),
			new Point3d(1, -1, 1),
			new Point3d(1, 1, 1),
			new Point3d(-1, 1, 1),			
	};
	
	public int[][] connections = {
		{0, 1},
		{1, 2},
		{2, 3},
		{3, 0},

		{4, 5},
		{5, 6},
		{6, 7},
		{7, 4},

		{0, 4},
		{1, 5},
		{2, 6},
		{3, 7},
	};
	
	public Cuboid(Cuboid c) {
		this(c.x, c.y, c.z, c.width, c.height, c.length);
		this.rx = c.rx;
		this.ry = c.ry;
		this.rz = c.rz;
		for(int i = 0; i < points.length; i++) {
			points[i] = new Point3d(c.points[i].x, c.points[i].y, c.points[i].z);
		}
	}
	
	public Cuboid(double x, double y, double z, int width, int height, int length) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.length = length;
		
		for(int i = 0; i < points.length; i++) {
			points[i].x *= width / 2;
			points[i].y *= height / 2;
			points[i].z *= length / 2;

			points[i].x += x;
			points[i].y += y;
			points[i].z += z;
		}
	}
	
	public void rotate(double rx, double ry, double rz, double originX, double originY, double originZ, boolean changeRotationValues) {
		Point3d p;	
		for(int j = 0; j < points.length; j++) {
			points[j].x -= originX;
			points[j].y -= originY;
			points[j].z -= originZ;	
			p = Renderer.rotateX(points[j], rx);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;					
			p = Renderer.rotateY(points[j], ry);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;						
			p = Renderer.rotateZ(points[j], rz);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;	
			points[j].x += originX;
			points[j].y += originY;
			points[j].z += originZ;	
		}
		if(changeRotationValues) {
			this.rx += rx;
			this.ry += ry;
			this.rz += rz;			
		}
	}
	
	public void rotate(double rx, double ry, double rz) {
		Point3d p;	
		for(int j = 0; j < points.length; j++) {
			points[j].x -= x;
			points[j].y -= y;
			points[j].z -= z;	
			p = Renderer.rotateX(points[j], rx);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;					
			p = Renderer.rotateY(points[j], ry);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;						
			p = Renderer.rotateZ(points[j], rz);
			points[j].x = p.x;
			points[j].y = p.y;
			points[j].z = p.z;	
			points[j].x += x;
			points[j].y += y;
			points[j].z += z;	
		}
		this.rx += rx;
		this.ry += ry;
		this.rz += rz;
	}
	
	public void moveToOrigin() {
		for(int i = 0; i < points.length; i++) {
			points[i].x -= this.x;
			points[i].y -= this.y;
			points[i].z -= this.z;
		}
	}
	
	public void moveToPosition() {
		for(int i = 0; i < points.length; i++) {
			points[i].x += this.x;
			points[i].y += this.y;
			points[i].z += this.z;
		}
	}
}

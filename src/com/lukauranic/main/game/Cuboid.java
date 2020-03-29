package com.lukauranic.main.game;

import com.lukauranic.main.graphics.Renderer;

public class Cuboid {
	public double rx, ry, rz;
	public int width, height, length;	
	public Point3d pos, rotOrigin;
	public Point3d[] points = {
			new Point3d(-1, -1, -1),
			new Point3d(1, -1, -1),
			new Point3d(1, 1, -1),
			new Point3d(-1, 1, -1),
			new Point3d(-1, -1, 1),
			new Point3d(1, -1, 1),
			new Point3d(1, 1, 1),
			new Point3d(-1, 1, 1),
			new Point3d(0, 0, 0),
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
		
		{0, 8},
		{1, 8},
		{2, 8},
		{3, 8},
		{4, 8},
		{5, 8},
		{6, 8},
		{7, 8},
	};
	
	
//	not used
	public Cuboid(Cuboid c) {
		this(c.pos, c.width, c.height, c.length);
		this.rx = c.rx;
		this.ry = c.ry;
		this.rz = c.rz;
		for(int i = 0; i < points.length; i++) {
			points[i] = new Point3d(c.points[i].x, c.points[i].y, c.points[i].z);
		}
	}
	
	public Cuboid(Point3d pos, int width, int height, int length) {
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.length = length;
		this.rotOrigin = pos;		
		
		for(int i = 0; i < points.length; i++) {
			points[i].x *= width / 2;
			points[i].y *= height / 2;
			points[i].z *= length / 2;

			points[i].x += pos.x;
			points[i].y += pos.y;
			points[i].z += pos.z;
		}
	}
	
//	not used
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
			points[j].x -= rotOrigin.x;
			points[j].y -= rotOrigin.y;
			points[j].z -= rotOrigin.z;	
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
			points[j].x += rotOrigin.x;
			points[j].y += rotOrigin.y;
			points[j].z += rotOrigin.z;		
		}
		pos.x -= rotOrigin.x;
		pos.y -= rotOrigin.y;
		pos.z -= rotOrigin.z;
		p = Renderer.rotateX(pos, rx);
		pos.x = p.x;
		pos.y = p.y;
		pos.z = p.z;					
		p = Renderer.rotateY(pos, ry);
		pos.x = p.x;
		pos.y = p.y;
		pos.z = p.z;						
		p = Renderer.rotateZ(pos, rz);
		pos.x = p.x;
		pos.y = p.y;
		pos.z = p.z;			
		pos.x += rotOrigin.x;
		pos.y += rotOrigin.y;
		pos.z += rotOrigin.z;
	}
	
	public void moveRotationOrigin(Point3d rotOrigin) {
		this.rotOrigin = rotOrigin;
		points[8].x = rotOrigin.x;
		points[8].y = rotOrigin.y;
		points[8].z = rotOrigin.z;
	}

	public void move(Point3d diference) {
		for(int i = 0; i < points.length; i++) {
			points[i].x += diference.x;
			points[i].y += diference.y;
			points[i].z += diference.z;
		}
		pos.x += diference.x;
		pos.y += diference.y;
		pos.z += diference.z;
	}	
}

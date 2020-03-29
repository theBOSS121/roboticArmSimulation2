package com.lukauranic.main.game;

public class Point3d {
	
	public double x;
	public double y;
	public double z;
	
	public Point3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3d(Point3d p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}	
}

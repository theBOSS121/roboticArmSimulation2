package com.lukauranic.main.game;

import com.lukauranic.main.graphics.Renderer;

public class Joint {

	public Point3d pos1, pos2;
	public double length, rx, ry, rz;
	public Cuboid cuboid;
	
	public Joint(Point3d pos1, Point3d pos2, Cuboid cuboid) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.cuboid = cuboid;
		this.length = Math.sqrt(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2) + Math.pow(pos2.z - pos1.z, 2));
	}
	
	public void rotate(double rx, double ry, double rz, boolean changeJointRotationValues) {
		cuboid.rotate(rx, ry, rz);
		Point3d p;
		pos1.x -= cuboid.rotOrigin.x;
		pos1.y -= cuboid.rotOrigin.y;
		pos1.z -= cuboid.rotOrigin.z;
		p = Renderer.rotateX(pos1, rx);
		pos1.x = p.x;
		pos1.y = p.y;
		pos1.z = p.z;					
		p = Renderer.rotateY(pos1, ry);
		pos1.x = p.x;
		pos1.y = p.y;
		pos1.z = p.z;						
		p = Renderer.rotateZ(pos1, rz);
		pos1.x = p.x;
		pos1.y = p.y;
		pos1.z = p.z;			
		pos1.x += cuboid.rotOrigin.x;
		pos1.y += cuboid.rotOrigin.y;
		pos1.z += cuboid.rotOrigin.z;
		
		pos2.x -= cuboid.rotOrigin.x;
		pos2.y -= cuboid.rotOrigin.y;
		pos2.z -= cuboid.rotOrigin.z;
		p = Renderer.rotateX(pos2, rx);
		pos2.x = p.x;
		pos2.y = p.y;
		pos2.z = p.z;					
		p = Renderer.rotateY(pos2, ry);
		pos2.x = p.x;
		pos2.y = p.y;
		pos2.z = p.z;						
		p = Renderer.rotateZ(pos2, rz);
		pos2.x = p.x;
		pos2.y = p.y;
		pos2.z = p.z;			
		pos2.x += cuboid.rotOrigin.x;
		pos2.y += cuboid.rotOrigin.y;
		pos2.z += cuboid.rotOrigin.z;
		if(changeJointRotationValues) {
			this.rx += rx;
			this.ry += ry;
			this.rz += rz;
		}
	}

	public void moveRotationOrigin(Point3d rotOrigin) {
		cuboid.moveRotationOrigin(new Point3d(rotOrigin));
	}
// 	rewrite code for rotation
//	have to implement length and points calculations and when you move one pos the other has to move as well
	public void movePos1(Point3d p) {
		Point3d difference = new Point3d(p.x - pos1.x, p.y - pos1.y, p.z - pos1.z);
		cuboid.move(difference);
		this.pos1 = p;
		this.pos2.x += difference.x;
		this.pos2.y += difference.y;
		this.pos2.z += difference.z;
	}
	
	public void movePos2(Point3d p) {
		Point3d difference = new Point3d(p.x - pos2.x, p.y - pos2.y, p.z - pos2.z);
		cuboid.move(difference);
		this.pos2 = p;
		this.pos1.x += difference.x;
		this.pos1.y += difference.y;
		this.pos1.z += difference.z;
	}
}

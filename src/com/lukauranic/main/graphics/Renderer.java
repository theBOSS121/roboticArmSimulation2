package com.lukauranic.main.graphics;

import com.lukauranic.main.MainClass;
import com.lukauranic.main.game.Point3d;

public class Renderer {
	public static int width = MainClass.WIDTH, height = MainClass.HEIGHT;
	public static int[] pixels = new int[width * height];
	
	public static void renderBackground() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = 0xff000000;
			}
			
		}
	}
	
	public static void renderSprite(Sprite s, int xp, int yp) {
		if(xp < -s.width || yp < -s.height || xp >= width || yp >= height) return;
		
		for(int y = 0; y < s.height; y++) {
			int yy = y + yp;
			if(yy >= height || yy < 0) continue;
			for(int x = 0; x < s.width; x++) {
				int xx = x + xp;
				if(xx < 0 || x >= width) continue;
				int col = combineColors(s.pixels[x + y * s.width], xx, yy);
				pixels[xx + yy * width] = col;
			}
		}
	}

	private static int combineColors(int col, int x, int y) {
		int pCol = pixels[x + y * width];
		int a = (col >> 24) & 0xff;
		if(a == 0xff) return col;
		if(a <= 0) return pCol;
		
		int pr = (pCol >> 16) & 0xff;
		int pg = (pCol >> 8) & 0xff;
		int pb = (pCol) & 0xff;
		int r = (col >> 16) & 0xff;
		int g = (col >> 8) & 0xff;
		int b = (col) & 0xff;
		
		int nr = (int) (pr - ((pr - r) * (a / 255f)));
		int ng = (int) (pg - ((pg - g) * (a / 255f)));
		int nb = (int) (pb - ((pb - b) * (a / 255f)));
		
		return (nr << 16) | (ng << 8) | nb;
	}
	
	
	public static void renderSprite(Sprite s, int sx, int sy, int sz, double rx, double ry, double rz) {
		Point3d p;
		for(int y = 0; y < s.height; y++) {
			int screenY = y + sy;
			for(int x = 0; x < s.width; x++) {				
				int screenX = x + sx;
				Point3d point = new Point3d(x - s.width / 2, y - s.height / 2, 0);
				p = Renderer.rotateX(point, rx);
				point.x = p.x;
				point.y = p.y;
				point.z = p.z;
				p = Renderer.rotateY(point, ry);
				point.x = p.x;
				point.y = p.y;
				point.z = p.z;
				p = Renderer.rotateZ(point, rz);
				point.x = p.x;
				point.y = p.y;
				point.z = p.z;
				
				
				screenX = (int) point.x + sx;
				screenY = (int) point.y + sy;
				
				int index = (int) (screenX + screenY * width);
				if(index < 0 || index >= pixels.length) continue;
				
				pixels[index] = s.pixels[x + y * s.width];
			}
		}		
	}

	
	
	
//	some bugs
	public static void renderLine(int x1, int y1, int x2, int y2) {
		int xDiff = Math.abs(x2 - x1);
		int yDiff = Math.abs(y2 - y1);		
		if(xDiff >= yDiff) {
			if(x1 > x2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			for(int x = x1; x <= x2; x++) {
				if(x < 0 || (k * x + n) < 0) continue;
				if(x >= width || (k * x + n) >= height) break;
				pixels[x + (int) (k * x + n) * width] = 0xffffffff;
			}
		}else {
			if(y1 > y2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			if(x2 - x1 == 0) {
				for(int y = y1; y < y2; y++) {
					if(y < 0) continue;
					if(x1 < 0 || x1 > width || y >= height) break;
					pixels[x1 + y * width] = 0xffffffff;					
				}
			}else {
				for(int y = y1; y <= y2; y++) {
					if(y < 0 || (y - n) / k < 0) continue;
					if(y >= height || (y - n) / k >= height) break;
					pixels[(int) ((y - n) / k) + y * width] = 0xffffffff;
				}				
			}
		}		
	}
//	two points in space also does perspective
	public static void renderLine(Point3d p, Point3d p2, int col) {
		Point3d pp = perspective(p);
		Point3d pp2 = perspective(p2);
		int x1 = (int) pp.x;
		int x2 = (int) pp2.x;
		int y1 = (int) pp.y;
		int y2 = (int) pp2.y;
		int xDiff = Math.abs(x2 - x1);
		int yDiff = Math.abs(y2 - y1);		
		if(xDiff == 0 && yDiff == 0) return;
		if(xDiff >= yDiff) {
			if(x1 > x2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			for(int x = x1; x <= x2; x++) {
				if(x < 0 || (k * x + n) < 0) continue;
				if(x >= width || (k * x + n) >= height) break;

				pixels[x + (int) (k * x + n) * width] = col;
			}
		}else {
			if(y1 > y2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			if(x2 - x1 == 0) {
				for(int y = y1; y < y2; y++) {
					if(y < 0) continue;
					if(x1 < 0 || x1 >= width || y >= height) break;
					pixels[x1 + y * width] = col;
				}
			}else {
				for(int y = y1; y <= y2; y++) {
					if(y < 0 || (y - n) / k < 0) continue;
					if(y >= height || (y - n) / k >= height) break;
					pixels[(int) ((y - n) / k) + y * width] = col;
				}				
			}
		}
	}
	
	public static Point3d perspective(Point3d pp) {
		Point3d p = new Point3d(pp.x - MainClass.WIDTH / 2, pp.y - MainClass.HEIGHT / 2, pp.z);
		double x = p.x * 512 / p.z;
		double y = p.y * 512 / p.z;
		double z = 0;
		return new Point3d(x + MainClass.WIDTH / 2, y + MainClass.HEIGHT / 2, z);
	}
	
	public static Point3d rotateZ(Point3d pp, double angle) {
		Point3d p = new Point3d(pp.x, pp.y, pp.z);
		double x = p.x * Math.cos(angle) - p.y * Math.sin(angle);
		double y = p.x * Math.sin(angle) + p.y * Math.cos(angle);
		double z = p.z;
		return new Point3d(x, y, z);
	}
	
	public static Point3d rotateX(Point3d pp, double angle) {
		Point3d p = new Point3d(pp.x, pp.y, pp.z);
		double x = p.x;
		double y = p.z * Math.sin(angle) + p.y * Math.cos(angle);
		double z = p.z * Math.cos(angle) - p.y * Math.sin(angle);
		return new Point3d(x, y, z);
	}
	
	public static Point3d rotateY(Point3d pp, double angle) {
		Point3d p = new Point3d(pp.x, pp.y, pp.z);
		double x = p.z * Math.sin(angle) + p.x * Math.cos(angle);
		double y = p.y;
		double z = p.z * Math.cos(angle) - p.x * Math.sin(angle);
		return new Point3d(x, y, z);
	}
}

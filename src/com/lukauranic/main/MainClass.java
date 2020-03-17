package com.lukauranic.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.lukauranic.main.game.Game;
import com.lukauranic.main.input.Keyboard;
import com.lukauranic.main.input.Mouse;

public class MainClass extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400, HEIGHT = 400;
	public static float scale = 2.0f;
	public Thread thread;
	public boolean running = false;
	public JFrame frame;
	public Game game;	
	public static Keyboard key;
	public static Mouse mouse;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public MainClass() {
		Dimension size = new Dimension((int) (WIDTH * scale),(int)  (HEIGHT * scale));
		setPreferredSize(size);
		frame = new JFrame();
		game = new Game();
		key = new Keyboard();
		addKeyListener(key);
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "loopThread");
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frame.setTitle("ThreeD " + updates + " updates, " + frames +" frames");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update() {
		game.update();
		key.update();
		mouse.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		game.render();
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, (int) (WIDTH * scale), (int) (HEIGHT * scale), null);
		
		game.postRender(g);
		
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		MainClass h = new MainClass();
		h.frame.setResizable(false);
		h.frame.setTitle("ThreeD");
		h.frame.add(h);
		h.frame.pack();
		h.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		h.frame.setVisible(true);
		h.frame.setLocationRelativeTo(null);
		h.frame.setAlwaysOnTop(true);
		h.start();
	}
}

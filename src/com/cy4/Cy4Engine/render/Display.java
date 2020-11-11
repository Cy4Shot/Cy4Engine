package com.cy4.Cy4Engine.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.cy4.Cy4Engine.input.MouseInput;
import com.cy4.Cy4Engine.render.entity.EntityManager;

@SuppressWarnings("serial")
public class Display extends Canvas implements Runnable {

	// Settings
	public static final int HEIGHT = 500;
	public static final int WIDTH = 500;
	public static final int TARGET_FPS = 100;
	public static final double DEPTH_FACTOR = 1400;
	public static final boolean ORTHOGRAPHIC = false;
	public static final double AMBIENT_LIGHT = 0.05;

	//Window
	private Thread thread;
	private JFrame frame;
	private static String title = "Cy4 Engine Test";

	//Addons
	private EntityManager entityManager;
	private MouseInput mouse;

	//Window Data
	private static boolean running = false;
	private final double ns = 1000000000.0 / TARGET_FPS;
	public static final long serialVersionUUID = 1L;

	public Display() {
		this.frame = new JFrame();

		Dimension size = new Dimension(HEIGHT, WIDTH);
		this.setPreferredSize(size);

		this.mouse = new MouseInput();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);

		this.entityManager = new EntityManager();
	}

	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		display.start();
	}

	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int frames = 0;

		try {
			this.entityManager.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / this.ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " : " + frames + "fps");
				frames = 0;
			}
		}

		stop();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		this.entityManager.render(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		this.entityManager.update(this.mouse);
	}

}

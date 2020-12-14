package com.cy4.Cy4Engine.render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.cy4.Cy4Engine.input.UserInput;
import com.cy4.Cy4Engine.render.entity.EntityManager;

@SuppressWarnings("serial")
public class Display extends Canvas implements Runnable {

	// Settings
	public static final int HEIGHT = 720;
	public static final int WIDTH = 720;
	public static final int TARGET_FPS = 60;
	public static final double DEPTH_FACTOR = 1400;
	public static final boolean ORTHOGRAPHIC = false;
	public static final double AMBIENT_LIGHT = 0.05;

	// Window
	private Thread thread;
	private JFrame frame;
	private static String title = "Cy4 Engine Test";

	// Addons
	public EntityManager entityManager;
	public UserInput input;

	// Window Data
	private static boolean running = false;
	private final double ns = 1000000000.0 / TARGET_FPS;
	public static final long serialVersionUUID = 1L;
	public static Display instance;

	public Display() {
		this.frame = new JFrame();

		Dimension size = new Dimension(HEIGHT, WIDTH);
		this.setPreferredSize(size);

		this.input = new UserInput(this);
		this.entityManager = new EntityManager();

	}

	public static void main(String[] args) {
		Display display = new Display();
		Display.instance = display;

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
		this.createBufferStrategy(3);
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

		init();

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

	private void init() {
		try {
			this.entityManager.init(this.input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH * WIDTH, HEIGHT * HEIGHT);

		this.entityManager.render(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		this.entityManager.update();
	}
}

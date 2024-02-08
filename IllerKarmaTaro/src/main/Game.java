package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

public class Game extends InputHandler implements Runnable
{	
	/* Borderless-windowed technique:
	 * https://www.edureka.co/community/21336/fetching-screen-resolution-using-java#:~:text=You%20can%20fetch%20the%20screen,Dimension%20screenRes%20%3D%20Toolkit.
	 */
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int SCREEN_WIDTH  = gd.getDisplayMode().getWidth ();
	public static int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
	
	DisplayMode 	  displayMode;
	
	private       Thread           gameThread;
	public static GameStatePanel   statePanel;
	public static GameStateManager stateManager;
	
	//public static My3DTest 		   test;
	
	int mouseX = -1, mouseY = -1;
	
	public Game()
	{	
		setTitle("3D Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		setResizable  (false);
		setUndecorated(true);
		addKeyListener(this);
		setFocusable  (true);
		requestFocus();	
		
		statePanel = new GameStatePanel(SCREEN_WIDTH, SCREEN_HEIGHT);
		add(statePanel);									  
		init();
		setVisible(true);
	}
	
	public void init()
	{									
		//make a state manager that communicates with the panel we made
		stateManager = new GameStateManager();
		
		//create game states
		//test    = new My3DTest();
		
	
		//stateManager.pushState(test);
		gameThread   = new Thread(this);
		gameThread.start();
	}
	
	public void run()
	{
		//System.out.println(SCREEN_WIDTH + "x" + SCREEN_HEIGHT);
		while(true) //game loop
		{		
			stateManager.update();
			statePanel.repaint ();
			
			try					{gameThread.sleep(15);} //~60fps
			catch(Exception x)  {					  }
		}
	}
	
	public static void main(String args[])
	{
		new Game();
	}
	
}

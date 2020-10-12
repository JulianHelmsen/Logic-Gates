package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import gui.LogicGateFrame;
import logicGates.*;
import window.Keyboard;
import window.Renderable;
import window.Window;

public class Space extends MouseAdapter implements Renderable, KeyListener {
	
	private int cameraX, cameraY;
	private int mouseX, mouseY;
	private Window window = new Window();
	private List<LogicGateFrame> logicGateFrames = new ArrayList<LogicGateFrame>();
	
	private LogicGateFrame selectedFrame = null;
	
	public Space() {
		this.window.add(this);
		this.window.addMouseListener(this);
		this.window.addMouseMotionListener(this);
		this.window.addKeyListener(this);
		
		
		this.window.setVisible(true);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.translate(-this.cameraX, -this.cameraY);
		for(int i = 0; i < this.logicGateFrames.size(); i++) {
			this.logicGateFrames.get(i).render(g2d);
			if(this.logicGateFrames.get(i) == this.selectedFrame) {
				g2d.setColor(java.awt.Color.WHITE);
				g2d.draw(this.selectedFrame.getBounds());
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int mouseWorldX = e.getX() + this.cameraX;
		int mouseWorldY = e.getY() + this.cameraY;
		for(int i = 0; i < this.logicGateFrames.size(); i++) {
			if(this.logicGateFrames.get(i).getBounds().contains(mouseWorldX, mouseWorldY)) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					this.logicGateFrames.get(i).clicked();
				}else if(e.getButton() == MouseEvent.BUTTON3) {
					
					if(this.selectedFrame != null) {
						// do stuff with selectedFrame is there is one selected
						if(this.selectedFrame != this.logicGateFrames.get(i)) {
							
							if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL)) {
								this.selectedFrame.removeDependency(this.logicGateFrames.get(i));
							}else {
								this.selectedFrame.addDependency(this.logicGateFrames.get(i));
							}
						}
						this.selectedFrame = null;
					}else {
						this.selectedFrame = this.logicGateFrames.get(i);
					}
					
				}
			}
		}
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		this.window.repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		boolean draggedComponent = false;
		int deltaX = e.getX() - this.mouseX;
		int deltaY = e.getY() - this.mouseY;
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
		int mouseWorldX = e.getX() + this.cameraX;
		int mouseWorldY = e.getY() + this.cameraY;
		
		for(int i = 0; i < this.logicGateFrames.size(); i++) {
			if(this.logicGateFrames.get(i).getBounds().contains(mouseWorldX, mouseWorldY)) {
				LogicGateFrame gate = this.logicGateFrames.get(i);
				java.awt.Rectangle bounds = gate.getBounds();
				bounds.x += deltaX;
				bounds.y += deltaY;
				draggedComponent = true;
				break;
			}
		}
		
		if(!draggedComponent) {
			this.cameraX -= deltaX;
			this.cameraY -= deltaY;
		}
		
		this.window.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(this.selectedFrame != null && (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
			this.selectedFrame.delete();
			this.logicGateFrames.remove(this.selectedFrame);
			this.selectedFrame = null;
		}else if(e.getKeyCode() == KeyEvent.VK_PLUS) {
			String[] options = {"And", "Or", "Xor", "Not", "Pin"};
			int result = JOptionPane.showOptionDialog(this.window, "Add component", "Add logic gate", JOptionPane.PLAIN_MESSAGE, 0, null, options, options[0]);
			if(result == -1) {
				return;
			}
			LogicGate gate = null;
			switch(result) {
			case 0:
				gate = new And();
				break;
			case 1:
				gate = new Or();
				break;
			case 2:
				gate = new Xor();
				break;
			case 3:
				gate = new Not();
				break;
			case 4:
				gate = new Pin();
			}
			
			LogicGateFrame frame = new LogicGateFrame(gate);
			
			int x = (int) (this.cameraX + this.window.getClientRect().getWidth() / 2);
			int y = (int) (this.cameraY + this.window.getClientRect().getHeight() / 2);
			final int width = 100;
			final int height = 50;
			frame.setBounds(x, y, width, height);
			
			this.logicGateFrames.add(frame);
		}
		
		this.window.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	

}

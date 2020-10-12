package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import logicGates.LogicGate;
import logicGates.State;
import window.Renderable;

public class LogicGateFrame implements Renderable, State {
	
	private Rectangle bounds;
	private LogicGate gate;
	private List<LogicGateFrame> dependencies = new ArrayList<LogicGateFrame>();
	private List<LogicGateFrame> parents = new ArrayList<LogicGateFrame>();
	
	public LogicGateFrame(LogicGate gate) {
		this.gate = gate;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.setBounds(new Rectangle(x, y, width, height));
	}
	
	private void setBounds(Rectangle rectangle) {
		this.bounds = rectangle;
	}
	


	public boolean getOutput() {
		for(int i = 0; i < this.dependencies.size(); i++) {
			this.gate.setPin(i, this.dependencies.get(i).getOutput());
		}
		System.out.println();
		return this.gate.getOutput();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(this.getOutput() ? Color.GREEN : Color.RED);
		g2d.fill(this.bounds);
		g2d.setColor(Color.BLACK);
		g2d.draw(this.bounds);
		
		LogicGateFrame.drawString(g2d, this.gate.getName(), this.bounds);
		
		// Render Connections
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		for(int i = 0; i < this.dependencies.size(); i++) {
			Rectangle otherBounds = this.dependencies.get(i).bounds;
			
			Point[] points;
			
			if(this.bounds.getMaxX() < otherBounds.x) {
				// we are left
				points = new Point[]{new Point((int) otherBounds.getMaxX(), (int) otherBounds.getCenterY()),
						new Point((int) (otherBounds.x + this.bounds.getMaxX()) / 2,(int) otherBounds.getCenterY()),
						new Point((int) (otherBounds.x + this.bounds.getMaxX()) / 2,(int) this.bounds.getCenterY()),
						new Point((int) this.bounds.getMaxX(), (int) this.bounds.getCenterY())};
				
			}else {
				// we are right
				points = new Point[]{new Point((int) otherBounds.getMaxX(), (int) otherBounds.getCenterY()),
						new Point((int) (otherBounds.getMaxX() + this.bounds.x) / 2,(int) otherBounds.getCenterY()),
						new Point((int) (otherBounds.getMaxX() + this.bounds.x) / 2,(int) this.bounds.getCenterY()),
						new Point(this.bounds.x, (int) this.bounds.getCenterY())};
			}
			
			
			
			for(int pointIndex = 0; pointIndex < points.length - 1; pointIndex++) {
				g2d.drawLine(points[pointIndex].x, points[pointIndex].y, points[pointIndex + 1].x, points[pointIndex + 1].y);
				if(i == points.length - 1) {
					
				}
			}
			// TODO: draw arrow
		}
		g2d.setComposite(AlphaComposite.Src);
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void addParent(LogicGateFrame parent) {
		this.parents.add(parent);
	}
	
	public void addDependency(LogicGateFrame dependency) {
		if(this.gate.getPinCount() > this.dependencies.size()) {
			this.dependencies.add(dependency);
			dependency.addParent(this);
		}
	}
	
	public void removeDependency(LogicGateFrame dependency) {
		this.dependencies.remove(dependency);
		dependency.parents.remove(this);
	}
	
	public void delete() {
		for(int i = 0; i < this.parents.size(); i++) {
			LogicGateFrame parent = this.parents.get(i);
			parent.dependencies.remove(i);
			parent.dependencies.remove(this);
			parent.getOutput();
		}
	}
	
	public void clicked() {
		if(this.gate instanceof logicGates.Pin) {
			((logicGates.Pin) gate).toggle();
		}
	}
	
	private static void drawString(java.awt.Graphics2D g2d, String message, Rectangle bounds) {
		java.awt.FontMetrics metrics = g2d.getFontMetrics();
		int x = (int) (bounds.getX() + (bounds.getWidth() - metrics.stringWidth(message)) / 2);
		int y = (int) (bounds.getY() + ((bounds.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent()));
		
		g2d.drawString(message, x, y);
	}
	
}

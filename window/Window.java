package window;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	private static final long serialVersionUID = -7150710923108249953L;
	
	private Renderable renderFunction;
	private JPanel panel;

	public Window() {
		this("Logic Gates");
	}
	
	public Window(String title) {
		this.setDefaultCloseOperation(3);
		this.setSize(1020, 720);
		this.setTitle(title);
		this.center();
		this.addKeyListener(new Keyboard());
		this.add(this.panel = new Show());
	}
	
	public void add(Renderable renderFunction) {
		this.renderFunction = renderFunction;
	}
	
	@Override
	public void addMouseListener(MouseListener listener) {
		this.panel.addMouseListener(listener);
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener listener) {
		this.panel.addMouseMotionListener(listener);
	}

	private void center() {
		final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();		
		final int xSpace = (int) (size.getWidth() - this.getWidth()) / 2;
		final int ySpace = (int) (size.getHeight() - this.getHeight()) / 2;
		this.setBounds(xSpace, ySpace, this.getWidth(), this.getHeight());
	}
	
	public java.awt.Rectangle getClientRect() {
		return this.panel.getBounds();
	}
	
	class Show extends JPanel {
		private static final long serialVersionUID = 7530260172492680844L;
		
		private BasicStroke default_stroke = new BasicStroke(3);
		private Font default_font = new Font("Arial", Font.BOLD, 25);
		
		public Show() {
			this.setBackground(new java.awt.Color(51, 51, 51));
			this.setOpaque(true);
		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			
			g2d.setStroke(this.default_stroke);
			g2d.setFont(this.default_font);
			
			if(Window.this.renderFunction != null)
				Window.this.renderFunction.render(g2d);
		}
	}
}


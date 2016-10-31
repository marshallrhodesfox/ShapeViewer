/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package swingClient;

import java.awt.Graphics;

import javax.swing.JPanel;

import shapes.AbstractShape;

/**
 * @author Marshall Fox
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DrawingPanel extends JPanel {
	AbstractShape shape = null;
	double[] rotation = null;
	int dimension = 4;
	boolean isPerspectiveEnabled = true;

	public DrawingPanel(AbstractShape shape) {
		super();

		this.shape = shape;
		this.rotation = new double[500];

	}

	public void update(int dimension, double[] rotation,
			boolean isPerspectiveEnabled) {
		this.dimension = dimension;
		this.rotation = rotation;
		this.isPerspectiveEnabled = isPerspectiveEnabled;

		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.clearRect(0, 0, getBounds().width, getBounds().height);

		shape.setDimension(dimension);
		shape.setRotation(rotation);
		if (isPerspectiveEnabled) {
			shape.drawPersp(1.4, g);
		} else {
			shape.drawOrtho(g);
		}

	}
}

package shapes;

import java.awt.Graphics;
import java.awt.Point;

/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Marshall Fox
 * 
 * A cube in n-dimensional space
 */
public class Cube extends AbstractShape {
	protected Cube(int dimension) {
		super(dimension, new Point(150, 150), new Point(250, 250));
	}

	protected double[][] defineShape() {
		int nv = getNumberOfVertices(dimension);
		double[][] shape = new double[nv][dimension];

		// vertex ranges from 0..2^dimension and can be looked at as a binary
		// number
		// a 1 means that it on the positive side of a particular axis, a zero,
		// on the neg side.
		// E.g., a 3-D cube has eight vertices, 0..7. 0=000, 7=111. Point 0 is
		// on the negative side
		// of x, y, z axes. Point 7 is on the positive side of x, y, z axes.
		for (int vertex = 0; vertex < nv; vertex++) {
			for (int coord = 0, bit = 1; coord < dimension; coord++, bit <<= 1) {
				shape[vertex][coord] = (vertex & bit) != 0 ? -0.5 : 0.5;
			}
		}

		return shape;
	}

	public int getNumberOfVertices(int dimension) {
		return (int) Math.pow(2, dimension);
	}

	public void connectTheDots(Point[] points, Graphics g) {
		int nv = getNumberOfVertices(dimension);
		for (int vertex = 0; vertex < nv; vertex++) {
			// connect each vertex in a cube to all other vertices opposite
			// exactly one axis.
			// for each vertex, we can find the vertices to connect to by XORing
			// the vertex index with the axis number.
			// E.g., for a 3-d cube, vertex 0=000 and should be connected to
			// 001=1, 010=2, 100=4.
			// Vertex 7=111 should be connected to 110=6, 101=5, 011=3.
			for (int bit = 1; bit < nv; bit <<= 1) {
				if ((vertex ^ bit) > vertex) { //keeps lines from being drawn twice
					g.drawLine((int) points[vertex].x, (int) points[vertex].y,
							(int) points[vertex ^ bit].x, (int) points[vertex
									^ bit].y);
				}
			}
		}
	}
}

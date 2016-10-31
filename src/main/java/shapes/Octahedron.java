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
 * An octahedron in n-dimensional space
 */
public class Octahedron extends AbstractShape {
	protected Octahedron(int dimension) {
		super(dimension, new Point(400,400), new Point(250,250));
	}

	protected double[][] defineShape() {
		int nv = getNumberOfVertices(dimension);
		double[][] shape = new double[nv][dimension];

		// The first index in the vertices array is the vertex number and the
		// second is the coordinate (e.g. x,y,z...).
		// Let axis=0..n-1 be one of the axes in n-dimensional space. Then we
		// define vertex numbers 2*axis and 2*axis+1 so that
		// their coordinate values are +/- 0.5 along this axis and zeros
		// elsewhere.
		for (int axis = 0; axis < dimension; axis++) {
			for (int coord = 0; coord < dimension; coord++) {
				shape[2 * axis][coord] = (axis == coord) ? -0.5 : 0;
				shape[2 * axis + 1][coord] = (axis == coord) ? 0.5 : 0;
			}
		}

		return shape;
	}

	public int getNumberOfVertices(int dimension) {
		return 2 * dimension;
	}

	protected void connectTheDots(Point[] p, Graphics g) {
		int nv = getNumberOfVertices(dimension);

		// Every vertex in an n-dimensional octahedron should be connected to
		// every other vertex except the one directly across
		// from it, i.e. vertices[v] is connected to vertices[v'] for all v'
		// except when v+1=v' (with v even) or v-1=v'
		// (with v odd). But if v is connected to v', then v' must be connected
		// to v, so we can impose v'>v.
		for (int i = 0; i < nv; i++) {
			for (int j = i + 1; j < nv; j++) {
				if ((i % 2) != 0 || (i + 1 != j)) {
					g.drawLine(p[i].x, p[i].y, p[j].x, p[j].y);
				}
			}

		}

	}
}

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
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractShape {
	private static final double DEG = Math.PI / 180.0;

	protected int dimension;
	protected double[] rotations;
	protected final Point scale;
	protected final Point offset;

	protected AbstractShape(int dimension, Point scale, Point offset) {
		this.dimension = dimension;
		this.scale = scale;
		this.offset = offset;
	}

	public static AbstractShape getShape(Types type, int dimension) {
		if (type.equals(Types.CUBE)) {
			return new Cube(dimension);
		} else if (type.equals(Types.OCTAHEDRON)) {
			return new Octahedron(dimension);
		} else {
			throw new UnsupportedOperationException("Unknown shape");
		}
	}

	/**
	 * Compute the number of possible rotations in n-space: C(n,n-2)
	 */
	public static int getNumberOfRotations(int dimension) {
		return dimension * (dimension - 1) / 2;
	}

	/**
	 * Rotates a set of points through a set of rotations in n-space.
	 * 
	 * Objects in n-space rotate about subspaces of dimension n-2 (e.g. in
	 * 3-space we rotate about lines, in 4-space we rotate about planes, etc).
	 * There are C(n,n-2) = n*(n-1)/2 ways to chose the subspaces, so n*(n-1)/2
	 * possible rotations are available. In each rotation, only two coordinate
	 * values can change, e.g. when points in 3-space rotate about the x-axis,
	 * the x-coordinate does not change, but the y and z coordinates do. When
	 * points in 4-space rotate about the xy plane, the x and y coordinates do
	 * not change, but the z and t coordinates do. Therefore, for each vertex,
	 * pick every combination of two coordinates and perform one of the
	 * n*(n-1)/2 rotations on it.
	 */
	protected double[][] rotate(double[][] vertices) {
		for (int vertex = 0; vertex < getNumberOfVertices(dimension); vertex++) {
			int rotationIndex = 0;
			for (int i = 0; i < dimension - 1; i++) {
				for (int j = i + 1; j < dimension; j++) {
					double temp = vertices[vertex][i];
					vertices[vertex][i] = temp
							* Math.cos(rotations[rotationIndex] * DEG)
							+ vertices[vertex][j]
							* Math.sin(rotations[rotationIndex] * DEG);
					vertices[vertex][j] = vertices[vertex][j]
							* Math.cos(rotations[rotationIndex] * DEG) - temp
							* Math.sin(rotations[rotationIndex] * DEG);
					rotationIndex++;
				}
			}
		}

		return vertices;
	}

	/**
	 * Project points from n-space to 2-space by finding the intersection of
	 * n-dimensional vectors with a plane.
	 * 
	 * @param vertices --
	 *            first index is vertex number, second index is coordinate
	 * @param dimension
	 * @param perspective --
	 *            a perspective offset; shape is displaced from origin by this
	 *            amount, otherwise, we'd be inside it.
	 */
	protected Point[] perspectiveMapping(double[][] vertices, double perspective) {
		int nv = getNumberOfVertices(dimension);
		Point[] p = new Point[nv];
		double x;
		double y;
		for (int vertex = 0; vertex < nv; vertex++) {
			x = vertices[vertex][0];
			y = vertices[vertex][1];

			for (int coord = 2; coord < dimension; coord++) {
				x /= vertices[vertex][coord] - perspective;
				y /= vertices[vertex][coord] - perspective;
			}

			p[vertex] = new Point((int) (x * getScale().x + getOffset().x),
					(int) (y * getScale().y + getOffset().y));
		}

		return p;
	}

	/**
	 * Orthographic mapping--just ignore higher dimensions
	 * 
	 * @param vertices --
	 *            first index is vertex number, second index is coordinate
	 * @param dimension
	 * @return set of 2-D points
	 */
	protected Point[] orthoMapping(double[][] vertices) {
		int nv = getNumberOfVertices(dimension);
		Point[] p = new Point[nv];

		for (int vertex = 0; vertex < nv; vertex++) {
			p[vertex] = new Point(
					(int) (vertices[vertex][0] * getScale().x + getOffset().x),
					(int) (vertices[vertex][1] * getScale().y + getOffset().y));

		}

		return p;
	}

	/**
	 * Draw the shape in perspective at a particular rotation on the graphics
	 * context
	 */
	public void drawPersp(double perspective, Graphics g) {
		double[][] vertices = rotate(defineShape());
		connectTheDots(perspectiveMapping(vertices, perspective), g);
	}

	/**
	 * Draw the shape orthographically at a particular rotation on the graphics
	 * context
	 */
	public void drawOrtho(Graphics g) {
		double[][] vertices = rotate(defineShape());
		connectTheDots(orthoMapping(vertices), g);
	}

	public void setRotation(double[] rotations) {
		this.rotations = rotations;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	/**
	 * Draw the shape by connecting the vertices together
	 * 
	 * @param p --
	 *            set of vertices, mapped to a plane
	 * @param g --
	 *            graphics context
	 */
	abstract protected void connectTheDots(Point[] p, Graphics g);

	/**
	 * Construct the shape in n-space
	 * 
	 * @param dimension
	 * @return vertices of shape--first index is vertex number, second is
	 *         coordinate number
	 */
	abstract protected double[][] defineShape();

	/**
	 * Compute the number of vertices for this shape
	 * 
	 * @param dimension
	 * @return number vertices
	 */
	abstract public int getNumberOfVertices(int dimension);

	protected Point getScale() {
		return scale;
	}

	protected Point getOffset() {
		return offset;
	}

}

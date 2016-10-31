/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package swingClient;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import shapes.AbstractShape;
import shapes.Types;

/**
 * @author Marshall Fox
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShapeViewer extends JFrame {
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 700;

	public ShapeViewer() {
		setTitle("Higher-Dimensional Shape Viewer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu shapeMenu = new JMenu("Shape");
		menuBar.add(shapeMenu);

		for (Types type : Types.values()) {
			shapeMenu.add(new AddTabAction(type.toString(), this,
					AbstractShape.getShape(type, 4)));			
		}
		
		new AddTabAction(Types.CUBE.toString(), this, AbstractShape.getShape(
				Types.CUBE, 4)).addTabbedPane();
	}

	public static void main(String[] args) {
		ShapeViewer viewer = new ShapeViewer();
		viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewer.setVisible(true);
	}
}

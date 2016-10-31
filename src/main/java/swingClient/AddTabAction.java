/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package swingClient;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import shapes.AbstractShape;

/**
 * @author Marshall Fox
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AddTabAction extends AbstractAction {
	JTabbedPane tabbedPane = null;
	AbstractShape shape = null;
	String label = null;

	public AddTabAction(String label, JFrame frame, AbstractShape shape) {
		super(label);

		this.shape = shape;
		this.label = label;

		tabbedPane = (JTabbedPane) frame.getContentPane().getComponent(0);
	}

	public void actionPerformed(ActionEvent e) {
		addTabbedPane();
	}

	public void addTabbedPane() {
		tabbedPane.add(label, new ComboPanel(shape));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

}

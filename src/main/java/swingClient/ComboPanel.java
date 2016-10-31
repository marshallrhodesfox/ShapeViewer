/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package swingClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import shapes.AbstractShape;


/**
 * @author Marshall Fox
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ComboPanel extends JSplitPane implements ActionListener
{
    ControlPanel controlPanel = null;
    DrawingPanel drawingPanel = null;
    
    public ComboPanel(AbstractShape shape)
    {
        controlPanel = new ControlPanel();
        drawingPanel = new DrawingPanel(shape);
        setLeftComponent(new JScrollPane(controlPanel));
        setRightComponent(drawingPanel);
        setDividerLocation(350);
        
        Timer t = new Timer (50, this);
        t.start();
    }
    
    public void actionPerformed (ActionEvent e)
    {
        controlPanel.update();
        drawingPanel.update(controlPanel.getDimension(), controlPanel
				.getRotation(), controlPanel.isPerspectiveEnabled());
    }

}

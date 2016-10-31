/*
 * Created on Oct 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package swingClient;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import shapes.AbstractShape;


/**
 * @author Marshall Fox
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlPanel extends JPanel implements ActionListener
{
	private final int MAX_DIM = 10;
    private final int MAX_ROTS = AbstractShape.getNumberOfRotations(MAX_DIM);
    private JFormattedTextField dimensionTF;
    private JToggleButton perspectiveButton;
    private JButton resetButton;
    private JFormattedTextField[] rotationTFs;
    private JSlider[] sliders;
    
    public ControlPanel()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        dimensionTF = new JFormattedTextField(NumberFormat.getNumberInstance());
        dimensionTF.setColumns(3);
        dimensionTF.setValue(new Integer(4));
        dimensionTF.addActionListener(this);
        
        perspectiveButton = new JToggleButton(new PerspectiveAction());
        perspectiveButton.setSelected(true);
        
        resetButton = new JButton(new ResetAction());
        
        rotationTFs = new JFormattedTextField[MAX_ROTS];
        sliders = new JSlider[MAX_ROTS];
        for (int i=0; i<rotationTFs.length; i++)
        {
            rotationTFs[i] = new JFormattedTextField();
            rotationTFs[i].setColumns(3);
            rotationTFs[i].setValue(new Integer(0));
            
            sliders[i] = new JSlider(SwingConstants.HORIZONTAL, -2, 2, 0);
            sliders[i].setMajorTickSpacing(1);
            sliders[i].setPaintTicks(true);
            sliders[i].setSnapToTicks(true);
            sliders[i].setPaintLabels(true);
        }
        init();
        
        
    }
    
    public void init()
    {
        this.removeAll();
        
        add(new TopPanel(dimensionTF, perspectiveButton, resetButton));
        
        int dimension = getDimension();
        
        int nr = AbstractShape.getNumberOfRotations(dimension);
        
        for (int i=0; i<nr; i++)
        {
            add (new RotationPanel(rotationTFs[i], sliders[i], i));
        }
    }
    
    public int getDimension()
    {
        try
        {
        	int dimension = (int) Double.parseDouble(dimensionTF.getText().trim());
        	
        	if (dimension < 2)
        	{
        		return 2;
        	}
        	if (dimension > MAX_DIM)
        	{
        		return MAX_DIM;
        	}
        	else
        	{
        		return dimension;
        	}
        }
        catch (NumberFormatException e)
        {
            return 2;
        }
    }
    
    public double[] getRotation()
    {
        int dimension = getDimension();
        int nr = AbstractShape.getNumberOfRotations(dimension);
        double[] rotations = new double[nr];
        for (int i=0; i<nr; i++)
        {
            try
            {
                rotations[i] = Double.parseDouble(rotationTFs[i].getText().trim());
            }
            catch (NumberFormatException e)
            {
                rotations[i] = 0;
            }
        }
        
        return rotations;
    }
    
    public boolean isPerspectiveEnabled()
    {
        return perspectiveButton.isSelected();
    }
    
    public void update()
    {
        double[] rotations = getRotation();
        
        int nr = AbstractShape.getNumberOfRotations(getDimension());
        for (int i=0; i<nr; i++)
        {
            rotations[i] = (rotations[i] + sliders[i].getValue() + 360) % 360;
            if (!rotationTFs[i].hasFocus())
            {
                rotationTFs[i].setValue(new Double(rotations[i]));
            }
            
        }
        
        
        
    }
    
    private class TopPanel extends JPanel
    {
        
        public TopPanel(JComponent c1, JComponent c2, JComponent c3)
        {
            super();
            
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBorder(BorderFactory.createEtchedBorder());
            
            add(new JLabel("Dimension"));
            add(c1);
            add(new JLabel(" "));
            add(c2);
            add(new JLabel(" "));
            add(c3);
            
        }
        
    }
    
    private class RotationPanel extends JPanel
    {
        JFormattedTextField m_tf;
        public RotationPanel(JTextField tf, JSlider s, int i)
        {
            super();
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBorder(BorderFactory.createEtchedBorder());
            
            add(new JLabel ("Rotation " + i));
            add(tf);
            add(s);
            
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        init();
    }
    
    public class ResetAction extends AbstractAction
    {
        public ResetAction()
        {
            super("Reset");
        }
        
        public void actionPerformed(ActionEvent e)
        {
            for (int i=0; i<rotationTFs.length; i++)
            {
                rotationTFs[i].setValue(new Integer(0));
            }
            
            for (int i=0; i<sliders.length; i++)
            {
                sliders[i].setValue(0);
            }
        }
    }
    
    public class PerspectiveAction extends AbstractAction
    {
        public PerspectiveAction()
        {
            super("Perspective");
        }
        
        public void actionPerformed(ActionEvent e)
        {
            
        }
    }
}

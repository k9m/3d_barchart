import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChartApplet extends JApplet {
	//Called when this applet is loaded into the browser.
	public void init() {		
		
		
		Color backgroundColor;
		
		String bgrdR = this.getParameter("backgroundR");		
		String bgrdG = this.getParameter("backgroundG");
		String bgrdB = this.getParameter("backgroundB");
		
		if (bgrdR == null || bgrdG == null || bgrdB == null){		
			backgroundColor = new Color(255,255,255);			
		}		
		else
			backgroundColor = new Color(Integer.parseInt(bgrdR),Integer.parseInt(bgrdG),Integer.parseInt(bgrdB));
		
		String unitStr = this.getParameter("units");
		int units = 10;
		if (unitStr != null)
			units = Integer.parseInt(unitStr);
		
		String zoomStr = this.getParameter("zoom");
		int zoom = 1;
		if (zoomStr != null)
			zoom = Integer.parseInt(zoomStr);		
		
		String[] barNames = this.getParameters("columnName");
		
		
		
		String[] barValuesStr = this.getParameters("columnValue");
		int[] barValues = new int[barValuesStr.length];
		for (int i = 0; i < barValuesStr.length; i++) {
			barValues[i] = Integer.parseInt(barValuesStr[i]);
		}

		BarChart barChart = new BarChart(barValues, barNames);
		int height = barChart.getMax();		
		barChart.setUnit(units);
		barChart.setZoom(zoom);
		barChart.setBackground(backgroundColor);		
		
		Dimension dimension = new Dimension(800,height);
		this.setPreferredSize(dimension);
		this.setSize(dimension);
		
		
		
		JPanel dummy = new JPanel();
		dummy.setBackground(backgroundColor);
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(backgroundColor);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		//JLabel xxx = new JLabel("HelloBello");
		//dummy.add(xxx);
		
		Color[] barColors = barChart.getBarColors();
		
		JTextField[] fields = new JTextField[barColors.length];
		
		for (int i = 0; i < barColors.length; i++){
			JPanel tmpPanel = new JPanel();
			tmpPanel.setBackground(backgroundColor);
			tmpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			fields[i] = new JTextField(barValuesStr[i],4);
			fields[i].setEditable(false);
			fields[i].setBackground(barColors[i]);
			tmpPanel.add(fields[i]);
			
			JLabel tmpLabel = new JLabel(barNames[i]);
			tmpPanel.add(tmpLabel);
			
			labelPanel.add(tmpPanel);		
		}
		
		dummy.add(labelPanel);		
		
		this.setLayout(new BorderLayout());
		this.add(barChart, BorderLayout.CENTER);
		this.add(dummy, BorderLayout.EAST);		
	}	
	
	private String[] getParameters(String parameter)
	{
		int ix = 0;  

		List<String> tmpList = new ArrayList<String>();
		
		String value = "";
		while(value != null) {   
			value = this.getParameter(parameter + ix);
			if (value != null){				
				tmpList.add(value);
				ix++;
			}			 
		} 
		
		return tmpList.toArray(new String[tmpList.size()]);		
	}
}

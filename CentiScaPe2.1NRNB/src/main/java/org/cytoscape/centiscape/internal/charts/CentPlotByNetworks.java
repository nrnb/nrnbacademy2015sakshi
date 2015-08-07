package org.cytoscape.centiscape.internal.charts;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.cytoscape.centiscape.internal.visualizer.CentVisualizer;
import org.cytoscape.centiscape.internal.visualizer.Centrality;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class CentPlotByNetworks extends JFrame {
    public  MyBarRenderer mybarrenderer;
    public  DefaultCategoryDataset dds;
    private final HashMap CentralityHashMap;
    private int networkSize;
    private final String plottype;
    public CentPlotByNetworks(HashMap CentralityHashMap, String plottype) {
        super("Plot By Node visualization");
        this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
        this.CentralityHashMap=CentralityHashMap;
        this.plottype=plottype;
        dds = new DefaultCategoryDataset();
        JPanel jpanel = createDemoPanel();
        setContentPane(jpanel);
    }

    private CategoryDataset createDataset() {
     
        dds.clear();
        Object a[]=CentralityHashMap.keySet().toArray();
        networkSize=a.length;
        int centralitySize=((Vector)CentralityHashMap.get((CyNetwork)a[0])).size();
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        double data[][]=new double[centralitySize][networkSize+1];
       
        for(int i=0;i<networkSize;i++)
        {
            Vector v=(Vector)CentralityHashMap.get((CyNetwork)a[i]);
            for (int j=0;j<centralitySize;j++)
            {
                String name=((Centrality)v.get(j)).getName();
                if(plottype.equals("Max"))
                        data[j][i]=((Centrality)v.get(j)).getMaxValue();
                if(plottype.equals("Min"))
                        data[j][i]=((Centrality)v.get(j)).getMinValue();
                if(plottype.equals("Avg"))
                        data[j][i]=((Centrality)v.get(j)).getDefaultValue();
                defaultcategorydataset.addValue(data[j][i], a[i].toString(), name);
                dds.addValue(data[j][i],a[i].toString(), name);

            }
        }

        return defaultcategorydataset;
        // return null;
    }
  JFreeChart createChart(CategoryDataset categorydataset) {
 //   static JFreeChart createChart(CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart("Network chart", "centrality statistics", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
       // plotting setup
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setDomainGridlinePaint(Color.white);
        categoryplot.setDomainGridlinesVisible(true);
        categoryplot.setRangeGridlinePaint(Color.white);

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        //bar renderer setup
        mybarrenderer = new MyBarRenderer();
        // permit bar outline marking
        mybarrenderer.setDrawBarOutline(true);
        categoryplot.setRenderer(mybarrenderer);
        // gradients for plots
       
        for(int j=0;j<networkSize;j++)
            {   int val=(int)(Math.random()*1000)%256;
                 GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, val));
                 mybarrenderer.setSeriesPaint(0, gradientpaint);
            }
        return jfreechart;
    }

    public JPanel createDemoPanel() {
        JFreeChart jfreechart = createChart(createDataset());
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        MyBarRenderer mybarrenderer = (MyBarRenderer) categoryplot.getRenderer();
        DemoPanel demopanel = new DemoPanel(mybarrenderer);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.addChartMouseListener(demopanel);
        demopanel.add(chartpanel);
        //tooltip setup
        mybarrenderer.setToolTipGenerator(new CategoryToolTipGenerator() {

            public CategoryDataset realValues = dds;

            public String generateToolTip(CategoryDataset arg0, int arg1, int arg2) {
                return (realValues.getValue(arg1, arg2).toString());
            }
        });
        return demopanel;
    }
}

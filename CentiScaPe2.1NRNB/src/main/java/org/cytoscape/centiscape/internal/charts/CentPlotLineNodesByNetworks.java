package org.cytoscape.centiscape.internal.charts;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.ArrayList;
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
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class CentPlotLineNodesByNetworks extends JFrame {

    ArrayList <String> centralityNames;
   
    public ArrayList<CyNode> nodes;
   // public static DefaultCategoryDataset dds = new DefaultCategoryDataset();
    public  LineAndShapeRenderer  mybarrenderer;
   //  public static MyBarRenderer mybarrenderer;
    public  DefaultCategoryDataset dds = new DefaultCategoryDataset();
  
    public List <CyNetwork> networks;

    // polymorphic constructor
    public CentPlotLineNodesByNetworks(List <CyNetwork> networks,ArrayList<CyNode> nodes,ArrayList <String> centralityNames) {
      

        super("Plot By Node visualization");
        this.networks = networks;
        this.setDefaultCloseOperation(this.HIDE_ON_CLOSE);
        this.centralityNames = centralityNames;

        this.nodes = nodes;
        JPanel jpanel = createDemoPanel();
     
        setContentPane(jpanel);
    }

    private CategoryDataset createDataset() {
     
        dds.clear();
        int nodeSize=nodes.size();
        int networkSize=networks.size();
        int CentralitySize=centralityNames.size();
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        double data[][]=new double[CentralitySize][networkSize+1];
        boolean networkExists[]=new boolean[networkSize];
       
        for(int i=0;i<networkSize;i++)
        {
            CyRow row=networks.get(i).getDefaultNodeTable().getRow(nodes.get(0).getSUID());
            Double value=row.get(centralityNames.get(0), Double.class);
            for(int k=1;k<nodeSize;k++)
            {
                if(value!=null)
                    break;
                
                row =networks.get(i).getDefaultNodeTable().getRow(nodes.get(k).getSUID());
                value=row.get(centralityNames.get(0), Double.class);
                
            }
            
           if(value!=null)
           {
               networkExists[i]=true;
                for (int j=0;j<CentralitySize;j++)
                {
                    
                      data[j][i]=row.get(centralityNames.get(j), Double.class);
                     
                }
           }
           else
               System.out.println("Not found");
           
         }
        for (int i=0;i<CentralitySize;i++) {
            String  centralityname = centralityNames.get(i);
            for(int j=0;j<networkSize;j++)
            {
                if(networkExists[j]==true)
                {
                    defaultcategorydataset.addValue(data[i][j], centralityname,networks.get(j).getDefaultNetworkTable().toString());
                    dds.addValue(data[i][j], centralityname,networks.get(j).getDefaultNetworkTable().toString());
                }
                
                 
            }
            
        }
        return defaultcategorydataset;
        // return null;
    }
  JFreeChart createChart(CategoryDataset categorydataset) {
 //   static JFreeChart createChart(CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart("Node chart", "centrality statistics", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, false);
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
        mybarrenderer = new LineAndShapeRenderer ();
        // permit bar outline marking
//        mybarrenderer.setDrawBarOutline(true);
        categoryplot.setRenderer(mybarrenderer);
        // gradients for plots
       
        for(int j=0;j<networks.size();j++)
            {   int val=(int)(Math.random()*1000)%256;
                 GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, val));
                 mybarrenderer.setSeriesPaint(0, gradientpaint);
            }
        return jfreechart;
    }

    public JPanel createDemoPanel() {
        JFreeChart jfreechart = createChart(createDataset());
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        LineAndShapeRenderer  mybarrenderer = (LineAndShapeRenderer ) categoryplot.getRenderer();
        JPanel chartPlaceholderPanel = new JPanel();
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartPlaceholderPanel.add(chartpanel);
        //tooltip setup
        mybarrenderer.setToolTipGenerator(new CategoryToolTipGenerator() {

            public CategoryDataset realValues = dds;

            public String generateToolTip(CategoryDataset arg0, int arg1, int arg2) {
                return (realValues.getValue(arg1, arg2).toString());
            }
        });
        return chartPlaceholderPanel;
    }
}

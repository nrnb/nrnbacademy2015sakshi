/*
 * CentiScaPeHelp.java
 *
 * Created on 18 gennaio 2008, 12.30
 */
package org.cytoscape.centiscape.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.centiscape.internal.visualizer.Centrality;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;

/**
 *
 * @author scardoni
 */
public class NodesComparisonTable extends javax.swing.JFrame {

    HashMap CentralityHashMap;

    /**
     *Creates Centrality Results in a Table format
     */
    public NodesComparisonTable(HashMap CentralityHashMap ) {
        this.CentralityHashMap=CentralityHashMap;
        initComponents();
        this.setSize(620, 300);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
       Object a[]=CentralityHashMap.keySet().toArray();
       int networkSize=a.length;
       int centralitySize=((Vector)CentralityHashMap.get((CyNetwork)a[0])).size();
       String data[][]=new String[centralitySize*3][networkSize+1];
       Object columns[]=new Object[networkSize+1];
       columns[0]="Centralities";
       for(int i=0;i<networkSize;i++)
       {
           columns[i+1]=a[i];
           Vector v=(Vector)CentralityHashMap.get((CyNetwork)a[i]);
           for(int j=0;j<centralitySize;j++)
           {
               if(i==0)
               {
                   String name=((Centrality)v.get(j)).getName();
                   data[j*3][0]=name+" Average Value";
                   data[j*3+1][0]=name+" Maximum Value";
                   data[j*3+2][0]=name+" Minimum Value";
           
              
               }
               
               data[j*3][i+1]=String.valueOf(((Centrality)v.get(j)).getDefaultValue());
               data[j*3+1][i+1]=String.valueOf(((Centrality)v.get(j)).getMaxValue());
               data[j*3+2][i+1]=String.valueOf(((Centrality)v.get(j)).getMinValue());
           }
       }
      
        
        jScrollPane1 = new javax.swing.JScrollPane();
        CentrJTable=new JTable(data,columns);
        jScrollPane1.setViewportView(CentrJTable);
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().addContainerGap().add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().addContainerGap().add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE).addContainerGap()));
        
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private JTable CentrJTable;
    // End of variables declaration//GEN-END:variables

    
    
}

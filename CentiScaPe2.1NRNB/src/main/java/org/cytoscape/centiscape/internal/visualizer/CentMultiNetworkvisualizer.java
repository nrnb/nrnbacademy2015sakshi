/*
 * CentVisualizer.java
 *
 * Created on 18 dicembre 2007, 12.11
 */
package org.cytoscape.centiscape.internal.visualizer;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.centiscape.internal.CentiScaPeCore;
import org.cytoscape.centiscape.internal.CentralitiesTable;
import org.cytoscape.centiscape.internal.NodesComparisonTable;
import org.cytoscape.centiscape.internal.charts.CentPlotLineNodesByNetworks;
import org.cytoscape.centiscape.internal.charts.CentPlotBarNodesByNetworks;
import org.cytoscape.centiscape.internal.charts.CentPlotByNetworks;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;




/**
 *
 * @author  sakshi
 */
public class CentMultiNetworkvisualizer extends javax.swing.JPanel implements Observer, CytoPanelComponent {

    private BoxLayout CentralitiesSelectorLayout;
    public List<CyNetworkView> views;
    public List<CyNetwork> networks;
    public CyApplicationManager cyApplicationManager;
//    private PlotBy plotBy;
    // passed centralities vector
    //public  static Vector<Centrality> centralities;
    public   Vector<Centrality> centralities;


    private CentiScaPeCore centiscapecore;
    // visual options panel
    private CentVisualOptions vo;
    private boolean firstTime;
    private HashMap<CyNetwork, Vector> centralitiesHashMap;
    public Box[] boxes;
    ArrayList<JCheckBox> CentralityCheckboxes;
    HashMap<String,ArrayList<CyNode>> nodeMapping;

    /** Creates new form CentVisualizer */
    public CentMultiNetworkvisualizer(CyApplicationManager cyApplicationManager, CentiScaPeCore centiscapecore) {
        initComponents();
        //System.out.println("visualizer 1.5 backporting on");
        this.centiscapecore = centiscapecore;
        this.cyApplicationManager=cyApplicationManager;
        firstTime = true;
        views= cyApplicationManager.getSelectedNetworkViews();
        networks = cyApplicationManager.getSelectedNetworks();
        CentralitiesSelectorLayout = null;
    //    vo = new CentVisualOptions(currentview, centralities);
    //    vo.addObserver(this);
        CentralityCheckboxes=new ArrayList<JCheckBox>();
        nodeMapping=new HashMap<String, ArrayList<CyNode>>();
    }

  

    public void setEnabled(HashMap <CyNetwork,Vector> hm) {

        super.setVisible(false);
        
        
            firstTime=false;
             centralities = hm.get(networks.get(0));
             centralitiesHashMap=hm;
             Iterator i=centralities.iterator();
                
             while(i.hasNext())
             { 
                 String centralityName=((ImplCentrality)i.next()).getName();
                 System.out.println(networks.get(0).getDefaultNetworkTable().toString());
                 
                 addCentralityName(centralityName);
                
                 
             }
           
             
            for(int x=0;x<networks.size();x++)
            {
                CyNetwork currentNetwork=networks.get(x);
                for (i=currentNetwork.getNodeList().listIterator();i.hasNext();)
                {
                    CyNode el=(CyNode)i.next();
                    String nodeName=currentNetwork.getRow(el).get(CyNetwork.NAME, String.class);
                    ArrayList nodes;
                    if(nodeMapping.get(nodeName)!=null)
                    {
                        nodes=nodeMapping.get(nodeName);
                       
                    }
                    else{
                        nodes=new ArrayList();
                    }
                        nodes.add(el);
                        nodeMapping.put(nodeName,nodes);
                     
                    

                }
            }
            
            Object keySet[]=nodeMapping.keySet().toArray();
            
            for(int k=0;k<nodeMapping.size();k++)
            {
               nodeList.addItem((String)keySet[k]);
            
            }
       
     
        super.setVisible(true);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
     

    }

    // one observed slider has been moved => update view
    public void update(Observable o, Object arg) {
        CentralityPanel cp = (CentralityPanel) arg;
       // updateSelection();
    }


   
    
    public void addCentralityName (String name){
        JCheckBox j=new JCheckBox(name);
        if(name.equals("Edge Betweenness Dir")|| name.equals("Edge Betweenness unDir"))
        {
            j.setEnabled(false);
        }
        CentralityCheckboxes.add(j);
        GridLayout b= new GridLayout(0,2);
        CentralityCheckBoxPanel.setLayout(b);
        CentralityCheckBoxPanel.add(j);
    }
    

   

//    private Vector<Centrality> generateCentralitiesVector() {
//        Vector<Centrality> vc = new Vector();
//        for (int i = 0; i < 5; i++) {
//            System.out.println("generazione di centrality" + Integer.toString(i));
//            Centrality c = new FakeCentrality("centrality" + Integer.toString(i));
//            vc.add(c);
//        }
//        return (vc);
//    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        CentralityDisplay = new javax.swing.JPanel();
        ExitButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        CentralityNodesPanel = new javax.swing.JPanel();
        nodeComputationPanel = new javax.swing.JPanel();
        CentralityCheckBoxPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nodeList = new javax.swing.JComboBox();
        tabulateButton = new javax.swing.JButton();
        plotBarButton = new javax.swing.JButton();
        plotLineButton = new javax.swing.JButton();
        plotType = new javax.swing.JComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        networkComputationPanel = new javax.swing.JPanel();
        CentralityTabulateButton = new javax.swing.JButton();
        networkplotTypeList = new javax.swing.JComboBox();
        jSeparator8 = new javax.swing.JSeparator();
        plotNetworksBarButton = new javax.swing.JButton();

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jLayeredPane1Layout = new org.jdesktop.layout.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(500, 698));

        org.jdesktop.layout.GroupLayout CentralityDisplayLayout = new org.jdesktop.layout.GroupLayout(CentralityDisplay);
        CentralityDisplay.setLayout(CentralityDisplayLayout);
        CentralityDisplayLayout.setHorizontalGroup(
            CentralityDisplayLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        CentralityDisplayLayout.setVerticalGroup(
            CentralityDisplayLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        ExitButton.setText("Close Result Panel");
        ExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitButtonMouseClicked(evt);
            }
        });
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        nodeComputationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Nodewise Computation"));

        CentralityCheckBoxPanel.setInheritsPopupMenu(true);

        org.jdesktop.layout.GroupLayout CentralityCheckBoxPanelLayout = new org.jdesktop.layout.GroupLayout(CentralityCheckBoxPanel);
        CentralityCheckBoxPanel.setLayout(CentralityCheckBoxPanelLayout);
        CentralityCheckBoxPanelLayout.setHorizontalGroup(
            CentralityCheckBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 433, Short.MAX_VALUE)
        );
        CentralityCheckBoxPanelLayout.setVerticalGroup(
            CentralityCheckBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 46, Short.MAX_VALUE)
        );

        jLabel2.setText("Select Node");

        nodeList.setMaximumSize(new java.awt.Dimension(90, 30));
        nodeList.setPreferredSize(new java.awt.Dimension(90, 20));
        nodeList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodeListActionPerformed(evt);
            }
        });

        tabulateButton.setText("Tabulate result");
        tabulateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabulateButtonMouseClicked(evt);
            }
        });
        tabulateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabulateButtonActionPerformed(evt);
            }
        });

        plotBarButton.setText("Plot Bar graph");
        plotBarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plotBarButtonMouseClicked(evt);
            }
        });
        plotBarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotBarButtonActionPerformed(evt);
            }
        });

        plotLineButton.setText("Plot Line graph");
        plotLineButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plotLineButtonMouseClicked(evt);
            }
        });
        plotLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotLineButtonActionPerformed(evt);
            }
        });

        plotType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Original Value", "Norm by max" }));

        org.jdesktop.layout.GroupLayout nodeComputationPanelLayout = new org.jdesktop.layout.GroupLayout(nodeComputationPanel);
        nodeComputationPanel.setLayout(nodeComputationPanelLayout);
        nodeComputationPanelLayout.setHorizontalGroup(
            nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, nodeComputationPanelLayout.createSequentialGroup()
                .add(nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nodeComputationPanelLayout.createSequentialGroup()
                        .add(27, 27, 27)
                        .add(jLabel2)
                        .add(18, 18, 18)
                        .add(nodeList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(nodeComputationPanelLayout.createSequentialGroup()
                        .add(plotBarButton)
                        .add(18, 18, 18)
                        .add(plotLineButton)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nodeComputationPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(plotType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(24, 24, 24))
                    .add(tabulateButton)))
            .add(nodeComputationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(CentralityCheckBoxPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, nodeComputationPanelLayout.createSequentialGroup()
                .add(jSeparator7)
                .add(24, 24, 24))
        );
        nodeComputationPanelLayout.setVerticalGroup(
            nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(nodeComputationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(CentralityCheckBoxPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 12, Short.MAX_VALUE)
                .add(jSeparator7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(nodeList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(tabulateButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nodeComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(plotBarButton)
                    .add(plotLineButton)
                    .add(plotType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        org.jdesktop.layout.GroupLayout CentralityNodesPanelLayout = new org.jdesktop.layout.GroupLayout(CentralityNodesPanel);
        CentralityNodesPanel.setLayout(CentralityNodesPanelLayout);
        CentralityNodesPanelLayout.setHorizontalGroup(
            CentralityNodesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(CentralityNodesPanelLayout.createSequentialGroup()
                .add(nodeComputationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 458, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        CentralityNodesPanelLayout.setVerticalGroup(
            CentralityNodesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(CentralityNodesPanelLayout.createSequentialGroup()
                .add(nodeComputationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );

        networkComputationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Network Computations"));

        CentralityTabulateButton.setText("Tabulate All Centralities");
        CentralityTabulateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CentralityTabulateButtonMouseClicked(evt);
            }
        });
        CentralityTabulateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CentralityTabulateButtonActionPerformed(evt);
            }
        });

        networkplotTypeList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Max", "Min", "Avg" }));

        plotNetworksBarButton.setText("Plot graph");
        plotNetworksBarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plotNetworksBarButtonMouseClicked(evt);
            }
        });
        plotNetworksBarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotNetworksBarButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout networkComputationPanelLayout = new org.jdesktop.layout.GroupLayout(networkComputationPanel);
        networkComputationPanel.setLayout(networkComputationPanelLayout);
        networkComputationPanelLayout.setHorizontalGroup(
            networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(networkComputationPanelLayout.createSequentialGroup()
                .add(networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(networkComputationPanelLayout.createSequentialGroup()
                        .add(132, 132, 132)
                        .add(CentralityTabulateButton))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, networkComputationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(networkplotTypeList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(30, 30, 30)
                        .add(plotNetworksBarButton)))
                .addContainerGap(110, Short.MAX_VALUE))
            .add(networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(networkComputationPanelLayout.createSequentialGroup()
                    .add(5, 5, 5)
                    .add(jSeparator8)
                    .add(5, 5, 5)))
        );
        networkComputationPanelLayout.setVerticalGroup(
            networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, networkComputationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(networkplotTypeList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(plotNetworksBarButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                .add(CentralityTabulateButton))
            .add(networkComputationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(networkComputationPanelLayout.createSequentialGroup()
                    .add(39, 39, 39)
                    .add(jSeparator8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(networkComputationPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jSeparator4))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jSeparator4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(networkComputationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(50, 50, 50))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(CentralityNodesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, CentralityDisplay, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(155, 155, 155)
                        .add(ExitButton))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 449, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(ExitButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(CentralityNodesPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(504, 504, 504)
                .add(CentralityDisplay, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ExitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitButtonMouseClicked
        centiscapecore.closeCurrentMultiResultPanel(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_ExitButtonMouseClicked

    private void nodeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodeListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nodeListActionPerformed

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void tabulateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabulateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabulateButtonActionPerformed

    private void tabulateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabulateButtonMouseClicked
        // TODO add your handling code here:
        ArrayList<String> centralityNames=new ArrayList<String>();
        for (int i=0;i<CentralityCheckboxes.size();i++)
        {
            if(CentralityCheckboxes.get(i).isSelected())
                 centralityNames.add(CentralityCheckboxes.get(i).getText());
        }
        ArrayList<CyNode> node=nodeMapping.get((String)nodeList.getSelectedItem());
        
        if(centralityNames.size()==0)
         JOptionPane.showMessageDialog(null, "Please check atleast one centrality",
                            "CentiScaPe", JOptionPane.INFORMATION_MESSAGE);
        else{
            CentralitiesTable t=new CentralitiesTable(networks,node,centralityNames);
            t.setVisible(true);
            
        }
       
    }//GEN-LAST:event_tabulateButtonMouseClicked

    private void CentralityTabulateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CentralityTabulateButtonMouseClicked
        // TODO add your handling code here:
        NodesComparisonTable t=new NodesComparisonTable(centralitiesHashMap);
        t.setVisible(true);
    }//GEN-LAST:event_CentralityTabulateButtonMouseClicked

    private void CentralityTabulateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CentralityTabulateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CentralityTabulateButtonActionPerformed

    private void plotBarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotBarButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plotBarButtonActionPerformed

    private void plotBarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotBarButtonMouseClicked
        // TODO add your handling code here:
        ArrayList<String> centralityNames=new ArrayList<String>();
        for (int i=0;i<CentralityCheckboxes.size();i++)
        {
            if(CentralityCheckboxes.get(i).isSelected())
                 centralityNames.add(CentralityCheckboxes.get(i).getText());
        }
        if(centralityNames.size()==0)
         JOptionPane.showMessageDialog(null, "Please check atleast one centrality",
                            "CentiScaPe", JOptionPane.INFORMATION_MESSAGE);
        else{
            ArrayList<CyNode> node=nodeMapping.get((String)nodeList.getSelectedItem());
            String type=(String)plotType.getSelectedItem();
            CentPlotBarNodesByNetworks pbn=new CentPlotBarNodesByNetworks(networks,node,centralityNames,centralitiesHashMap,type);
            pbn.setSize(700,400);
            pbn.setVisible(true);
        }
        
    }//GEN-LAST:event_plotBarButtonMouseClicked

    private void plotLineButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotLineButtonMouseClicked
        ArrayList<String> centralityNames=new ArrayList<String>();
        for (int i=0;i<CentralityCheckboxes.size();i++)
        {
            if(CentralityCheckboxes.get(i).isSelected())
                 centralityNames.add(CentralityCheckboxes.get(i).getText());
        }
        if(centralityNames.size()==0)
         JOptionPane.showMessageDialog(null, "Please check atleast one centrality",
                            "CentiScaPe", JOptionPane.INFORMATION_MESSAGE);
        else{
            ArrayList<CyNode> node=nodeMapping.get((String)nodeList.getSelectedItem());
            String type=(String)plotType.getSelectedItem();
            CentPlotLineNodesByNetworks pbn=new CentPlotLineNodesByNetworks(networks,node,centralityNames,centralitiesHashMap,type);
            pbn.setSize(700,400);
            pbn.setVisible(true);
        }
    }//GEN-LAST:event_plotLineButtonMouseClicked

    private void plotLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotLineButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plotLineButtonActionPerformed

    private void plotNetworksBarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotNetworksBarButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plotNetworksBarButtonActionPerformed

    private void plotNetworksBarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotNetworksBarButtonMouseClicked
        
            String type=(String)networkplotTypeList.getSelectedItem();
            CentPlotByNetworks pbn=new CentPlotByNetworks(centralitiesHashMap,type);
            pbn.setSize(700,400);
            pbn.setVisible(true);
    }//GEN-LAST:event_plotNetworksBarButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CentralityCheckBoxPanel;
    private javax.swing.JPanel CentralityDisplay;
    private javax.swing.JPanel CentralityNodesPanel;
    private javax.swing.JButton CentralityTabulateButton;
    private javax.swing.JButton ExitButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JPanel networkComputationPanel;
    private javax.swing.JComboBox networkplotTypeList;
    private javax.swing.JPanel nodeComputationPanel;
    private javax.swing.JComboBox nodeList;
    private javax.swing.JButton plotBarButton;
    private javax.swing.JButton plotLineButton;
    private javax.swing.JButton plotNetworksBarButton;
    private javax.swing.JComboBox plotType;
    private javax.swing.JButton tabulateButton;
    // End of variables declaration//GEN-END:variables

 public Component getComponent() {
		return this;
	}


	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.EAST;
	}


	public String getTitle() {
		return "CentiScaPe Results";
	}


	public Icon getIcon() {
		return null;
	}



}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.centiscape.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JPanel;
import org.cytoscape.centiscape.internal.Betweenness.BetweennessMethods;
import org.cytoscape.centiscape.internal.Betweenness.EdgeBetweenness;
import org.cytoscape.centiscape.internal.Betweenness.FinalResultBetweenness;
import org.cytoscape.centiscape.internal.Centroid.CentroidMethods;
import org.cytoscape.centiscape.internal.Centroid.FinalResultCentroid;
import org.cytoscape.centiscape.internal.Closeness.DirectedCloseness;
import org.cytoscape.centiscape.internal.Eccentricity.DirectedEccentricity;
import org.cytoscape.centiscape.internal.EigenVector.CalculateEigenVector;
import org.cytoscape.centiscape.internal.Radiality.DirectedRadiality;
import org.cytoscape.centiscape.internal.visualizer.ImplCentrality;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.w3c.dom.NodeList;

/**
 *
 * @author faizi
 */
public class CentiScaPeDirectedAlgorithm {
    public CyNetwork network;
    public boolean[] checkedCentralities;
    public String[] directedCentralities;
    public JPanel menu;
    public static boolean stop;
    public boolean openResultsPanel;
    public boolean displayBetweennessResults = false;
    public static TreeMap Stressmap = new TreeMap();
    public double Diameter = 0.0;
    public double totalDist;
    //dataStructures
    public Map<CyNode,Vector> singleShortestPaths;
    public Map<CyNode,Double> directedOutDegreeValues;
    public Map<CyNode,Double> directedInDegreeValues;
    public Map<CyNode,Double> directedClosenessValues;
    public Map<CyNode,Double> directedEccentrityValues;
    public Map<CyNode,Double> directedRadialityValues;
    public Map<CyNode,Double> directedStressValues;
    public Map<CyNode,Double> directedBridgingValues;
    public EdgeBetweenness edgeBetweenness;
    private static Vector BetweennessVectorResults;
    private static Vector CentroidVectorResults;
    private static Vector CentroidVectorofNodes;
    public double[][] adjacencyMatrixOfNetwork;
    public CentiScaPeCore centiscapecore;
    public Vector vectorResults = new Vector();
    public Boolean isWeighted;
    public Boolean useNodeAttribute;
    public String nodeAttribute;
    public Class<?> nodeAttrtype;
    
    public static void stopAlgo(){
        stop = true;
    }
    public void executeCentralities(CyNetwork n, boolean[] c, String[] d, JPanel panel, CentiScaPeCore core){
        stop = false;
        openResultsPanel = false;
        network = n;
        checkedCentralities = c;
        directedCentralities = d;
        CentiScaPeStartMenu menu = (CentiScaPeStartMenu) panel;
        isWeighted = menu.isWeighted;
        isWeighted = menu.isWeighted;
        useNodeAttribute = menu.useNodeAttribute;
        nodeAttribute=menu.nodeAttribute;
        if(nodeAttribute!=null &&  network.getDefaultNodeTable().getColumn(nodeAttribute)!=null)
            nodeAttrtype=network.getDefaultNodeTable().getColumn(nodeAttribute).getType();
        if(!(nodeAttrtype == Double.class || nodeAttrtype == Integer.class || nodeAttrtype == Long.class)){
               useNodeAttribute=false;
         }
        this.centiscapecore = core;
        System.out.println("Entered Directed algo");
        
        String networkname = network.getDefaultNetworkTable().getRow(network.getSUID()).get("name", String.class);
        int totalnodecount = network.getNodeCount();
        int totalEdgeCount = network.getEdgeCount();
        Long networksuid = network.getSUID();
        int nodeworked = 0;
        CyTable nodeTable = network.getDefaultNodeTable();
        CyTable networkTable = network.getDefaultNetworkTable();
        List<CyNode> nodeList = network.getNodeList();
        Vector CentiScaPeMultiShortestPathVector = null;
        int nodeAttrList[]=new int[totalnodecount];
        int CentiScaPeMultiShortestPathVectorOriginalSize;
        if(useNodeAttribute)
            {   totalnodecount=0;
                for(int m=0;m< nodeList.size();m++){
                    CyNode node=(CyNode) nodeList.get(m);
                    CyRow row = nodeTable.getRow(node.getSUID());
                    if(useNodeAttribute && row.get(nodeAttribute, nodeAttrtype)!=null)
                    {
                         if(row.get(nodeAttribute, nodeAttrtype)!=null && ((Number)row.get(nodeAttribute, nodeAttrtype)).intValue()!=0)
                            {
                                int temp=((Number)(row.get(nodeAttribute, nodeAttrtype))).intValue();
                                totalnodecount=totalnodecount+temp;
                                nodeAttrList[m]=temp;
                            }
                        else {
                            nodeAttrList[m]=1;
                        }
                        
                    }
                }
            }
        
        if(checkedCentralities[7]){
            displayBetweennessResults = true;
        }else{
            displayBetweennessResults = false;
        }
        if(checkedCentralities[10] && !checkedCentralities[7]){
            checkedCentralities[7] = true;
        }
        
        //instantiate data stuctures
        if(checkedCentralities[1]){
            totalDist = 0.0;
        }
        if(checkedCentralities[2]){
            //Degree checkbox is selected
            directedOutDegreeValues = new HashMap<CyNode, Double>(totalnodecount);
            directedInDegreeValues = new HashMap<CyNode, Double>(totalnodecount);
            //openResultsPanel = false;
        }
        if(checkedCentralities[3]){
            directedEccentrityValues = new HashMap<CyNode,Double>(totalnodecount);
        }
        if(checkedCentralities[4]){
            directedRadialityValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if(checkedCentralities[5]){
            directedClosenessValues = new HashMap<CyNode,Double>(totalnodecount);
        }
        if(checkedCentralities[6]){
            directedStressValues = new HashMap<CyNode,Double>(totalnodecount);
        }
        if(checkedCentralities[7]){
            BetweennessVectorResults = new Vector();
            for (Iterator i = nodeList.iterator(); i.hasNext();) {
                if (stop) {
                    return;
                }
                CyNode root = (CyNode) i.next();
                BetweennessVectorResults.add(new FinalResultBetweenness(root, 0));
            }
        }
        if (checkedCentralities[8]) {
                CentroidVectorResults = new Vector();
                CentroidVectorofNodes = new Vector();
                for (Iterator i = nodeList.iterator(); i.hasNext();) {
                    if (stop) {
                     return;
                    }
                    CyNode root = (CyNode) i.next();
                    CentroidVectorofNodes.addElement(root.getSUID());
                }
        }
        if(checkedCentralities[9]){
            adjacencyMatrixOfNetwork = new double[totalnodecount][totalnodecount];
        }
        if(checkedCentralities[10]){
            directedBridgingValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if(checkedCentralities[11]){
            edgeBetweenness = new EdgeBetweenness(network);
        }
        //       
            /*CheckedCentralities[0] = DiameterCheckBox.isSelected();
            CheckedCentralities[1] = AverageDistanceCheckBox.isSelected();
            CheckedCentralities[2] = DegreeCheckBox.isSelected();
            CheckedCentralities[3] = EccentricityCheckBox.isSelected();
            CheckedCentralities[4] = RadialityCheckBox.isSelected();
            CheckedCentralities[5] = ClosenessCheckBox.isSelected();
            CheckedCentralities[6] = StressCheckBox.isSelected();
            CheckedCentralities[7] = BetweennessCheckBox.isSelected();
            CheckedCentralities[8] = CentroidCheckBox.isSelected();
            CheckedCentralities[9] = EigenVectorCheckBox.isSelected();
            */
        
        
	boolean StressisOn = true;
	boolean BetweennessisOn = true;
	Stressmap = new TreeMap();
	Stressmap.clear();
	for (CyNode root : nodeList) {
		Stressmap.put(root.getSUID(), new Double(0));
	}
	TreeMap inizializedmap = new TreeMap(Stressmap);
        int k=0;
        int currentNodeAttr=1;
        singleShortestPaths = new HashMap<CyNode,Vector>();
	for (CyNode root : nodeList) {
                if (stop) {
                    //unselectallnodes(network);
                    return;
                }
            CyRow row;
            if(useNodeAttribute)
            {   
                int ind=nodeList.indexOf(root);
                currentNodeAttr=nodeAttrList[ind];
            }
                
		CentiScaPeMultiShortestPathVector = new Vector();
		CentiScaPeMultiShortestPathVector = CentiScaPeMultiShortestPathTreeAlgorithm
				.ExecuteMultiShortestPathTreeAlgorithm(network,
						root, StressisOn, BetweennessisOn,inizializedmap, true, isWeighted,menu);
		Vector CentiScaPeSingleShortestPathVector = new Vector();
		Vector NodesFound = new Vector();
		CentiScaPeShortestPathList CurrentList;
                CentiScaPeMultiShortestPathVectorOriginalSize=CentiScaPeMultiShortestPathVector.size();
		for (int j = 0; j < CentiScaPeMultiShortestPathVectorOriginalSize; j++) {

			CurrentList = (CentiScaPeShortestPathList) CentiScaPeMultiShortestPathVector
					.get(j);
			String nodename = ((CentiScaPeMultiSPath) CurrentList
					.getLast()).getName();
			if (!NodesFound.contains(nodename)) {
                            //int x=0;
                            //for(Object node : CurrentList){
                                //x++;
                                //if(((CentiScaPeMultiSPath)node).getName()==nodename && x==CurrentList.size()){
                                    NodesFound.add(nodename);
                                    CentiScaPeSingleShortestPathVector.add(CurrentList);
                                      if(useNodeAttribute)
                                        {   CyNode node=((CentiScaPeMultiSPath) CurrentList.getLast()).getNode();
                                            int index=nodeList.indexOf(node);
                                            for(int m=1;m<nodeAttrList[index];m++)
                                            {
                                                CentiScaPeSingleShortestPathVector.add(CurrentList);

                                            }
                                        }
                                //}
                            //}
			}
                        
		}
                if(useNodeAttribute )
                {
                    
                    for (int j = 0; j < CentiScaPeMultiShortestPathVectorOriginalSize ; j++) {
                        CurrentList = (CentiScaPeShortestPathList) CentiScaPeMultiShortestPathVector.get(j);
                        int mult=1;
                        for(int m=0;m<CurrentList.size();m++){
                                CyNode node=((CentiScaPeMultiSPath)CurrentList.get(m)).getNode();
                                int index=nodeList.indexOf(node);
                                mult=mult*nodeAttrList[index];
                        }
                        for(int m=0;m<mult;m++){
                            CentiScaPeMultiShortestPathVector.add(CurrentList);
                        }

                      }
              }
                double currentdiametervalue;
                currentdiametervalue = CalculateDiameter(CentiScaPeSingleShortestPathVector);
                if (Diameter < currentdiametervalue) {
                    Diameter = currentdiametervalue;
                }
                
                if (checkedCentralities[7]) {
                    BetweennessMethods.updateBetweenness(CentiScaPeMultiShortestPathVector, BetweennessVectorResults);
                }
                if (checkedCentralities[11]){
                    edgeBetweenness.updateEdgeBetweenness(CentiScaPeMultiShortestPathVector,CentiScaPeMultiShortestPathVectorOriginalSize);
                }
                singleShortestPaths.put(root, CentiScaPeSingleShortestPathVector);
        }   
            //execute each centrality
                //System.out.println(network.getRow(root).get("name", String.class));
        for(Map.Entry<CyNode,Vector> element : singleShortestPaths.entrySet()){
            if (stop) {
                return;
            }
            CyNode root = element.getKey();
            int index=nodeList.indexOf(root);
            currentNodeAttr=nodeAttrList[index];
            if(currentNodeAttr==0)
                currentNodeAttr=1;
            Vector CentiScaPeSingleShortestPathVector = element.getValue();
            
            if(checkedCentralities[1]){
                CentiScaPeShortestPathList currentlist;
                for (int j = 0; j < CentiScaPeSingleShortestPathVector.size(); j++) {
                    currentlist = (CentiScaPeShortestPathList) CentiScaPeSingleShortestPathVector.elementAt(j);
                    totalDist = totalDist + ((CentiScaPeMultiSPath)currentlist.get(currentlist.size()-1)).getCost()*currentNodeAttr;
                }
            }
            if(checkedCentralities[2]){
                if(useNodeAttribute){
                double currentDegreeOut = network.getNeighborList(root, CyEdge.Type.OUTGOING).size();
                double currentDegreeIn = network.getNeighborList(root, CyEdge.Type.INCOMING).size();
                int totalDegreeCountOut=0,totalDegreeCountIn=0;
                for(int m=0;m<currentDegreeOut;m++)
                {
                    CyNode node=network.getNeighborList(root, CyEdge.Type.OUTGOING).get(m);
                    int ind=nodeList.indexOf(node);
                    totalDegreeCountOut=totalDegreeCountOut+nodeAttrList[ind];
                }
                for(int m=0;m<currentDegreeIn;m++)
                {
                    CyNode node=network.getNeighborList(root, CyEdge.Type.INCOMING).get(m);
                    int ind=nodeList.indexOf(node);
                    totalDegreeCountOut=totalDegreeCountOut+nodeAttrList[ind];
                }
                directedOutDegreeValues.put(root, totalDegreeCountOut*1.0);
                directedInDegreeValues.put(root, totalDegreeCountIn*1.0);
                }
                
                else{
                    directedOutDegreeValues.put(root, (double) (network.getNeighborList(root,CyEdge.Type.OUTGOING).size()));
                    directedInDegreeValues.put(root, (double) (network.getNeighborList(root,CyEdge.Type.INCOMING).size()));
                        }
                
            }
            if(checkedCentralities[3]){
                directedEccentrityValues.put(root, DirectedEccentricity.execute(CentiScaPeSingleShortestPathVector));
            }
            if(checkedCentralities[4]){
                directedRadialityValues.put(root, DirectedRadiality.execute(Diameter, totalnodecount, CentiScaPeSingleShortestPathVector)*currentNodeAttr);
               
            }
            if(checkedCentralities[5]){
                directedClosenessValues.put(root, DirectedCloseness.execute(CentiScaPeSingleShortestPathVector)/currentNodeAttr);
            }
            if (checkedCentralities[8]) {
                CentroidMethods.updateCentroid(CentiScaPeSingleShortestPathVector, root, totalnodecount, CentroidVectorofNodes, CentroidVectorResults);
            }
            if(checkedCentralities[9]){                
                List<CyNode> neighbors = network.getNeighborList(root, CyEdge.Type.OUTGOING);
                for(CyNode neighbor : neighbors){
                    adjacencyMatrixOfNetwork[k][nodeList.indexOf(neighbor)] = 1.0 ;
                }
                k++;
            }
            
                
            // increment node worked count    
            nodeworked++;
            CentiScaPeStartMenu.updatenodecounting(nodeworked, totalnodecount);
        }
        if (checkedCentralities[8]) {
            CentroidMethods.calculateCentroid(CentroidVectorResults, totalnodecount, CentroidVectorofNodes);
        }
        
        menu.endcalculus(totalnodecount);
        vectorResults.clear();
        
        //put in table
        if(checkedCentralities[0]){
            putValuesinTable(network, directedCentralities[0], Diameter);
        }
        if(checkedCentralities[1]){
            double average = totalDist / (totalnodecount * (totalnodecount - 1));
            putValuesinTable(network, directedCentralities[1], average);
        }
        if(checkedCentralities[2]){
            putValuesinTable(network, directedCentralities[2], directedOutDegreeValues, vectorResults);
            putValuesinTable(network, "InDegree", directedInDegreeValues, vectorResults);
        }
        if(checkedCentralities[3]){
            putValuesinTable(network, directedCentralities[3], directedEccentrityValues, vectorResults);
        }
        if(checkedCentralities[4]){
            putValuesinTable(network, directedCentralities[4], directedRadialityValues, vectorResults);
        }
        if(checkedCentralities[5]){
            putValuesinTable(network, directedCentralities[5], directedClosenessValues, vectorResults);
        }
        if(checkedCentralities[6]){
            Set stressSet = Stressmap.entrySet();
            for (Iterator i = stressSet.iterator(); i.hasNext();) {
                Map.Entry currentmapentry = (Map.Entry) i.next();
                long currentnodeSUID = (Long) currentmapentry.getKey();
                CyNode currentnode = network.getNode(currentnodeSUID);
                double currentstress = (double) (Double) (currentmapentry.getValue());
                directedStressValues.put(currentnode, currentstress);
            }
            putValuesinTable(network, directedCentralities[6], directedStressValues, vectorResults);
        }
        if (checkedCentralities[7] && displayBetweennessResults) {
            nodeTable.createColumn(directedCentralities[7], Double.class, false);
            
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;

            for (Iterator i = BetweennessVectorResults.iterator(); i.hasNext();) {

                FinalResultBetweenness currentnodebetweenness = (FinalResultBetweenness) i.next();

                double currentbetweenness = currentnodebetweenness.getBetweenness();

                if (currentbetweenness < min) {
                    min = currentbetweenness;
                }
                if (currentbetweenness > max) {
                    max = currentbetweenness;
                }
                totalsum = totalsum + currentbetweenness;
                
                
            CyRow row = nodeTable.getRow(currentnodebetweenness.getNode().getSUID());
             row.set(directedCentralities[7], new Double(currentbetweenness));
              }
            networkTable.createColumn(directedCentralities[7].split(" ")[0]+" Max value Dir", Double.class, false);
            networkTable.createColumn(directedCentralities[7].split(" ")[0]+" min value Dir", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[7].split(" ")[0]+" mean value Dir", Double.class, false);
            network.getRow(network).set(directedCentralities[7].split(" ")[0]+" Max value Dir", new Double(max));
            network.getRow(network).set(directedCentralities[7].split(" ")[0]+" min value Dir", new Double(min));
            network.getRow(network).set(directedCentralities[7].split(" ")[0]+" mean value Dir", new Double(mean));
            ImplCentrality betweennessCentrality = new ImplCentrality(directedCentralities[7], true, mean, min, max);
            vectorResults.add(betweennessCentrality);

        }
        if (checkedCentralities[8]) {
            nodeTable.createColumn(directedCentralities[8], Double.class, false);
            //vectorOfNodeAttributes.addElement("CentiScaPe Centroid");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = CentroidVectorResults.iterator(); i.hasNext();) {


                FinalResultCentroid currentnodeCentroid = (FinalResultCentroid) i.next();

                double currentcentroid = currentnodeCentroid.getCentroid();

                if (currentcentroid < min) {
                    min = currentcentroid;
                }
                if (currentcentroid > max) {
                    max = currentcentroid;
                }
                totalsum = totalsum + currentcentroid;
                CyRow row = nodeTable.getRow(currentnodeCentroid.getNode().getSUID());
                row.set(directedCentralities[8], new Double(currentcentroid));
            }
            networkTable.createColumn(directedCentralities[8].split(" ")[0]+" Max value Dir", Double.class, false);
            networkTable.createColumn(directedCentralities[8].split(" ")[0]+" min value Dir", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[8].split(" ")[0]+" mean value Dir", Double.class, false);
            network.getRow(network).set(directedCentralities[8].split(" ")[0]+" Max value Dir", new Double(max));
            network.getRow(network).set(directedCentralities[8].split(" ")[0]+" min value Dir", new Double(min));
            network.getRow(network).set(directedCentralities[8].split(" ")[0]+" mean value Dir", new Double(mean));
      
            ImplCentrality centroidCentrality = new ImplCentrality(directedCentralities[8], true, mean, min, max);
            vectorResults.add(centroidCentrality);
        }
        if(checkedCentralities[9]){
            if(useNodeAttribute)
            {
                int completedCount=nodeAttrList.length;
                for(int m=0;m<nodeAttrList.length;m++)
                {
                    for( int i=0;i<nodeAttrList[m]-1;i++)
                    {
                        for(int j=0;j<nodeAttrList.length;j++){
                             adjacencyMatrixOfNetwork[j][completedCount+i]=adjacencyMatrixOfNetwork[j][m];
                        }
                    }
                    completedCount=completedCount+nodeAttrList[m]-1;
                }
                completedCount=nodeAttrList.length;
               for(int m=0;m<nodeAttrList.length;m++)
                {
                    for( int i=0;i<nodeAttrList[m]-1;i++)
                    {
                        adjacencyMatrixOfNetwork[completedCount+i]=adjacencyMatrixOfNetwork[m];
                    }
                    completedCount=completedCount+nodeAttrList[m]-1;
                    }
            }
            CalculateEigenVector.executeAndWriteValues(adjacencyMatrixOfNetwork,network, nodeList,nodeTable,"EigenVector ", vectorResults, "Dir",nodeAttrList);
        }
        if (checkedCentralities[10]) {
            nodeTable.createColumn(directedCentralities[10], Double.class, false);
            
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;

            for (Iterator i = BetweennessVectorResults.iterator(); i.hasNext();) {
                FinalResultBetweenness currentnodebetweenness = (FinalResultBetweenness) i.next();

                double currentbetweenness = currentnodebetweenness.getBetweenness();
                CyNode root = currentnodebetweenness.getNode();
                List<CyNode> bridgingNeighborList = network.getNeighborList(root, CyEdge.Type.ANY);
                double bridgingCoefficient = 0;
                if(bridgingNeighborList.size() != 0){
                    double BCNumerator = (1/(double)(bridgingNeighborList.size()));
                    double BCDenominator = 0;                
                    int nodeCentMultiply=1;
                    for (CyNode bridgingNeighbor : bridgingNeighborList) {
                        if(useNodeAttribute )
                        {
                            for(int m=0;m<bridgingNeighborList.size();m++){
                                CyNode node=((CyNode)bridgingNeighborList.get(m));
                                int index=nodeList.indexOf(node);
                                nodeCentMultiply=nodeCentMultiply*nodeAttrList[index];
                            }
                            int index=nodeList.indexOf(root);
                            nodeCentMultiply=nodeCentMultiply*nodeAttrList[index];
                            BCNumerator=BCNumerator*nodeAttrList[index];
                        }
                        if(network.getNeighborList(bridgingNeighbor, CyEdge.Type.ANY).size() != 0){
                            BCDenominator = BCDenominator + 1/(double)(network.getNeighborList(bridgingNeighbor, CyEdge.Type.ANY).size())*nodeCentMultiply;
                        }
                    }
                    if(BCDenominator!=0){
                        bridgingCoefficient = BCNumerator/BCDenominator;
                    }else{
                        bridgingCoefficient = 0.0;
                    }
                }
                double bridgingCentrality = bridgingCoefficient*currentbetweenness;
                if ( bridgingCentrality < min) {
                    min = bridgingCentrality;
                }
                if (bridgingCentrality > max) {
                    max = bridgingCentrality;
                }
                totalsum = totalsum + bridgingCentrality;
            CyRow row = nodeTable.getRow(currentnodebetweenness.getNode().getSUID());
             row.set(directedCentralities[10], new Double(bridgingCentrality));
              }
            networkTable.createColumn(directedCentralities[10].split(" ")[0]+" Max value Dir", Double.class, false);
            networkTable.createColumn(directedCentralities[10].split(" ")[0]+" min value Dir", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[10].split(" ")[0]+" mean value Dir", Double.class, false);
            network.getRow(network).set(directedCentralities[10].split(" ")[0]+" Max value Dir", new Double(max));
            network.getRow(network).set(directedCentralities[10].split(" ")[0]+" min value Dir", new Double(min));
            network.getRow(network).set(directedCentralities[10].split(" ")[0]+" mean value Dir", new Double(mean));
            ImplCentrality bridgingCentrality = new ImplCentrality(directedCentralities[10], true, mean, min, max);
            vectorResults.add(bridgingCentrality);

        }
        if(checkedCentralities[11]){
            /*edgeBetweenness.displayEdgeBetweennessValues();
            
            List<CyNode> nodeList = network.getNodeList();
            for(CyNode r:nodeList){
                System.out.println(r.getSUID()+"-->"+network.getRow(r).get(CyNetwork.NAME, String.class));
            }
            */
            CyTable edgeTable = network.getDefaultEdgeTable();
            edgeTable.createColumn(directedCentralities[11], Double.class, false);
            Map<CyEdge,Double> values = edgeBetweenness.getEdgeBetweennessMap();
            Set<CyEdge> edges = values.keySet();
        
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            Iterator it = edges.iterator();
            
            while (it.hasNext()) {
                        
                CyEdge root = (CyEdge) it.next();
                currentvalue = values.get(root);
                if (currentvalue < min) {
                    min = currentvalue;
                }
                if (currentvalue > max) {
                    max = currentvalue;
                }
                totalsum = totalsum + currentvalue;
                CyRow row = edgeTable.getRow(root.getSUID());
                row.set(directedCentralities[11], new Double(currentvalue));
            }
            
            //CyTable networkTable = network.getDefaultNetworkTable();
                //int totalnodecount = network.getNodeCount();
            networkTable.createColumn(directedCentralities[11].split(" ")[0]+" Max value Dir", Double.class, false);
            networkTable.createColumn(directedCentralities[11].split(" ")[0]+" min value Dir", Double.class, false);
            double mean = totalsum / totalEdgeCount;
            networkTable.createColumn(directedCentralities[11].split(" ")[0]+" mean value Dir", Double.class, false);
            network.getRow(network).set(directedCentralities[11].split(" ")[0]+" Max value Dir", new Double(max));
            network.getRow(network).set(directedCentralities[11].split(" ")[0]+" min value Dir", new Double(min));
            network.getRow(network).set(directedCentralities[11].split(" ")[0]+" mean value Dir", new Double(mean));

            // for embending centrality in Results Panel -- These two lines are enough
            ImplCentrality edgeBetweennessCentrality = new ImplCentrality(directedCentralities[11], true, mean, min, max);
            vectorResults.add(edgeBetweennessCentrality);
        }
        if(!menu.analyzeMultiple)
        {
             centiscapecore.createCentiScaPeVisualizer();
             centiscapecore.getvisualizer().setEnabled(vectorResults);
        }
        
        
    }
    
    public static void putValuesinTable(CyNetwork network, String heading,double result){     
        CyTable networkTable = network.getDefaultNetworkTable();
        networkTable.createColumn(heading, Double.class, false);
        network.getRow(network).set(heading, new Double(result));
    }
    
    public static void putValuesinTable(CyNetwork network, String heading, Map<CyNode,Double> values, Vector vectorResults){
        CyTable nodeTable = network.getDefaultNodeTable();
        nodeTable.createColumn(heading, Double.class, false);
        
        Set<CyNode> nodes = values.keySet();
        String networkattheading = heading;
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            Iterator it = nodes.iterator();
            
            while (it.hasNext()) {
                        
                CyNode root = (CyNode) it.next();
                currentvalue = values.get(root);
                if (currentvalue < min) {
                    min = currentvalue;
                }
                if (currentvalue > max) {
                    max = currentvalue;
                }
                totalsum = totalsum + currentvalue;
                CyRow row = nodeTable.getRow(root.getSUID());
                row.set(heading, new Double(currentvalue));
            }
          
            if  (!heading.equals("InDegree") && !heading.equals("OutDegree")) {
               networkattheading = heading.split(" ")[0];
            }
            CyTable networkTable = network.getDefaultNetworkTable();
                int totalnodecount = network.getNodeCount();
            networkTable.createColumn(networkattheading + " Max value Dir", Double.class, false);
            networkTable.createColumn(networkattheading + " min value Dir", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(networkattheading + " mean value Dir", Double.class, false);
            network.getRow(network).set(networkattheading +" Max value Dir", new Double(max));
            network.getRow(network).set(networkattheading +" min value Dir", new Double(min));
            network.getRow(network).set(networkattheading +" mean value Dir", new Double(mean));

            // for embending centrality in Results Panel -- These two lines are enough
            ImplCentrality Centrality = new ImplCentrality(heading, true, mean, min, max);
            vectorResults.add(Centrality);
    }
    
    public double CalculateDiameter(Vector SingleShortestPathVector) {
        CentiScaPeShortestPathList currentdiameterlist;
        int currentmaxvalue = 0;
        double currentvalue = 0;
        int cost;
        for (int j = 0; j < SingleShortestPathVector.size(); j++) {
            currentdiameterlist = (CentiScaPeShortestPathList) SingleShortestPathVector.elementAt(j);
            cost = ((CentiScaPeMultiSPath)currentdiameterlist.getLast()).getCost();
            currentmaxvalue = Math.max(currentmaxvalue, cost);
        }
        if (currentmaxvalue > currentvalue) {
            currentvalue = ((double) currentmaxvalue);
        }
        return currentvalue;
    }
}

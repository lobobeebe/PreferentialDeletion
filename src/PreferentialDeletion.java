import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class PreferentialDeletion
{
	public static final int NUM_TIME_STEPS = 50000;
	public static final int LOG_STEP = 10000;
	
	public static int numEdges = 0;
	
	public static Random random = new Random(4072052578l);
	
	public static void main(String[] args)
	{
		simulate(.6f);
		simulate(.75f);
		simulate(.8f);
		simulate(.9f);
	}
	
	public static void simulate(float p)
	{
		numEdges = 0;
		try
		{
			BufferedWriter nodeWriter = new BufferedWriter(new FileWriter("nodes" + p + ".txt"));
			BufferedWriter edgeWriter = new BufferedWriter(new FileWriter("edges" + p + ".txt"));
			BufferedWriter kWriter = new BufferedWriter(new FileWriter("k" + p + ".txt"));
			
			// Create initial graph containing a single node with a self loop.
			ArrayList<Node> graph = new ArrayList<Node>();
			addNode(graph, null);
			
			for (int time = 1; time <= NUM_TIME_STEPS; ++time)
			{
				if (time % LOG_STEP == 0)
				{
					// Nodes
					nodeWriter.write(time + "\t" + graph.size());
					nodeWriter.newLine();
					
					// Edges
					edgeWriter.write(time + "\t" + numEdges);
					edgeWriter.newLine();
				}
				
				if (random.nextFloat() <= p)
				{
					// Birth				
					// Select which node to connect
					Node connectedNode = selectConnectedNodeForBirth(graph);
					addNode(graph, connectedNode);
				}
				else
				{
					// Deletion
					Node deletedNode = selectNodeForDeletion(graph);
					deleteNode(graph, deletedNode);
				}
			}
			
			int k = 1;
			int numGreaterThanOrEqualToK = 0;
			do
			{
				numGreaterThanOrEqualToK = 0;
				
				for (int i = 0; i < graph.size(); ++i)
				{
					Node node = graph.get(i);
					if (node.Incidents.size() >= k)
					{
						++numGreaterThanOrEqualToK;
					}
					else
					{
						// Unconnected node. 
					}
				}
				
				kWriter.write(k + "\t" + (numGreaterThanOrEqualToK / (float) graph.size()));
				kWriter.newLine();
				++k;
			}
			while (numGreaterThanOrEqualToK > 0);

			nodeWriter.close();
			edgeWriter.close();
			kWriter.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static void addNode(ArrayList<Node> graph, Node existingNode)
	{
		Node addedNode = new Node();
		
		if (existingNode != null)
		{
			// Node exists, add it to incidents
			addedNode.Incidents.add(existingNode);
			existingNode.Incidents.add(addedNode);
		}
		else
		{
			// No existing node provided, create a self loop
			addedNode.Incidents.add(addedNode);
			addedNode.Incidents.add(addedNode);
		}
		
		graph.add(addedNode);
		
		++numEdges;
	}
	
	public static void deleteNode(ArrayList<Node> graph, Node node)
	{
		if (node != null)
		{
			for (Node incidentNode : node.Incidents)
			{
				if (node != incidentNode)
				{
					incidentNode.Incidents.remove(node);
				}
			}
			
			numEdges -= node.Incidents.size();
			node.Incidents.clear();
			
			graph.remove(node);
		}
		else
		{
			System.err.println("Attempted to delete a null node.");
		}
	}
	
	public static Node selectConnectedNodeForBirth(ArrayList<Node> graph)
	{
		Node selectedNode = null;
		
		if (graph.size() > 0)
		{
			float randValue = random.nextFloat();
			
			int graphIndex = 0;
			selectedNode = graph.get(graphIndex);
			// P(u) = d(u)/2m
			float twoM = 2 * numEdges;
			float nodeProbability = selectedNode.Incidents.size() / twoM;
			
			while (nodeProbability < randValue && graphIndex + 1 < graph.size())
			{
				++graphIndex;				
				
				selectedNode = graph.get(graphIndex);
				nodeProbability += selectedNode.Incidents.size() / twoM;
			}
		}
		
		return selectedNode;
	}
	
	public static Node selectNodeForDeletion(ArrayList<Node> graph)
	{
		Node selectedNode = null;

		if (graph.size() > 0)
		{
			float randValue = random.nextFloat();
			
			int graphIndex = 0;
			// P(u) = (n - d(u)) / (n^2 - 2m)
			int n = graph.size();
			float nSquaredMinusTwoM = (n * n) - 2 * numEdges;
			selectedNode = graph.get(graphIndex);
			float nodeProbability = (n - selectedNode.Incidents.size()) / nSquaredMinusTwoM;
			
			while (nodeProbability < randValue && graphIndex + 1 < graph.size())
			{
				++graphIndex;	
				selectedNode = graph.get(graphIndex);
				nodeProbability += (n - selectedNode.Incidents.size()) / nSquaredMinusTwoM;
			}
		}
		
		return selectedNode;
	}
}

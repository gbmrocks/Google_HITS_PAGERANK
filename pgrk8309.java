//Gaurangkumar Mody CS610 8309 prp
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
/*
* 
* @author Gaurang Mody
* NJID # ****8309
*
*/
public class pgrk8309 {
	static DecimalFormat decimalFormat = new DecimalFormat("#0.0000000");
	static String fileName, m[];
	static double d = 0.85, initialValue;
	static int numberOfIterations, errorRate, njit_id = 8309, size, initVal, numberOfLines;
	static BufferedReader bufferedReader;
	public static void assignValue(String fileName, int... a) {
		setInitVal(a[1]);
		pgrk8309.numberOfIterations = a[0];
		pgrk8309.fileName = fileName;
	}

	public static void setInitVal(int initVal) {
		if (initVal >= -2 && initVal <= 1) {
			pgrk8309.initVal = initVal;
		} else {
			Utilph8309.printlogs("Invalid intial value");
			System.exit(0);
		}
	}

	public static void setErrorRate(int errorRate) {
		pgrk8309.errorRate = errorRate;
	}
	public static void main(String[] args) {
		try {
			if (args.length < 3) {
				throw new Exception(
						"Invalid Input\n\"Usage: java hits" + njit_id + " iterations initialvalue filename\"\n");
			}
			assignValue(args[2], Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			pgrkm8309();
		} catch (Exception ex) {
			Utilph8309.printException(ex);
		}
	}

	public static void pgrkm8309() throws Exception {
		ArrayList<Node8309> node8309;
		bufferedReader = new BufferedReader(new FileReader(fileName));
		m = bufferedReader.readLine().split(" ");
		size = Integer.parseInt(m[0]);
		numberOfLines = Integer.parseInt(m[1]);
		node8309 = new ArrayList<Node8309>(size);
		initialValue = getInitialValue8309(initVal);
		if (numberOfIterations < 0) {
			setErrorRate(1);
		} else if (numberOfIterations == 0) {
			setErrorRate(1);
			numberOfIterations = -5;
		}
		node8309 = intializeNodes8309(node8309, initialValue);
		node8309 = buildGraph8309(node8309);
		printIteratiors8309("Base", node8309, 0);
		Iterate8309(node8309);
	}
	public static double getInitialValue8309(int initVal) {
		double initialVal = 1 / ((double) size);
		if (!Utilph8309.isLarge(size)) {
			if (initVal == 0 || initVal == 1) {
				initialVal = Double.valueOf(initVal * 1);
			} else if (initVal == -2) {
				initialVal = 1 / Math.sqrt((double) size);
			}
		} else {
			numberOfIterations = 0;
		}
		return initialVal;
	}
	public static ArrayList<Node8309> intializeNodes8309(ArrayList<Node8309> Node8309, double initVal) {
		Node8309 = new ArrayList<Node8309>(size);
		int i = 0;
		while (i < size) {
			Node8309.add(new Node8309(initVal));
			i++;
		}
		return Node8309;
	}
	public static ArrayList<Node8309> buildGraph8309(ArrayList<Node8309> Node8309s) throws Exception {
		int i = 0;
		while (i < numberOfLines) {
			m = bufferedReader.readLine().split(" ");
			Node8309s.get(Integer.parseInt(m[1])).addIncomingNode(Integer.parseInt(m[0]));
			Node8309s.get(Integer.parseInt(m[0])).addOutgoingNode(Integer.parseInt(m[1]));
			i++;
		}
		return Node8309s;
	}
	public static void Iterate8309(ArrayList<Node8309> Node8309s) {
		int i = errorRate == 0 ? Iterators8309(Node8309s) : ErrorRates8309(Node8309s);
	}
	public static int ErrorRates8309(ArrayList<Node8309> currNode) {
		ArrayList<Node8309> prevNode = new ArrayList<Node8309>(size);
		int i = 0;
		double sum = 0.0, res = (1 - d) / (double) size, errVal = Math.pow(10.0, numberOfIterations);
		do {
			prevNode = getValuesOfNode8309(currNode);
			currNode = update8309(currNode, sum, res);
			if (!Utilph8309.isLarge(size)) {
				printIteratiors8309("Iter", currNode, i + 1);
			}
			i++;
		} while (!isConverge(currNode, prevNode, errVal));
		if (Utilph8309.isLarge(size)) {
			printIteratiors8309("Iter", currNode, i + 1);
		}
		return 0;
	}
	public static ArrayList<Node8309> getValuesOfNode8309(ArrayList<Node8309> node8309) {
		ArrayList<Node8309> node = new ArrayList<Node8309>(size);
		int i = 0;
		while (i < node8309.size()) {
			node.add(new Node8309(node8309.get(i++).getPgrk()));
		}
		return node;
	}
	public static Boolean isConverge(ArrayList<Node8309> currNode, ArrayList<Node8309> prevNode, double errVal) {
		boolean c = true, d[] = new boolean[size];
		int i = 0;
		while (i < size) {
			if ((Math.abs(currNode.get(i).getPgrk() - prevNode.get(i).getPgrk())) < errVal) {
				d[i] = true;
			} else {
				d[i] = false;
			}
			i++;
		}
		i = 0;
		while (i < d.length) {
			c = d[i++] && c;
		}
		return c;
	}

	public static int Iterators8309(ArrayList<Node8309> node8309) {
		int i = 0;
		double sum = 0.0, res = (1 - d) / (double) size;
		while (i < numberOfIterations) {
			printIteratiors8309("Iter", update8309(node8309, sum, res), i + 1);
			i++;
		}
		return 0;
	}

	public static ArrayList<Node8309> update8309(ArrayList<Node8309> node8309, double sum, double res) {
		ArrayList<Node8309> t1 = new ArrayList<Node8309>(size);
		int i = 0, j = 0;
		while (i < node8309.size()) {
			sum = 0.0;
			Node8309 Node8309 = node8309.get(i);
			j = 0;
			while (j < Node8309.incoming.size()) {
				int incoming_i = Node8309.incoming.get(j++);
				sum += ((node8309.get(incoming_i).getPgrk()) / (double) (node8309.get(incoming_i).outgoing.size()));
				j++;
			}
			t1.add(new Node8309(res + (d * sum)));
			i++;
		}
		i = 0;
		while (i < t1.size()) {
			Node8309 n = t1.get(i);
			node8309.get(t1.indexOf(n)).setPgrk(n.getPgrk());
			i++;
		}
		return node8309;
	}

	public static void printIteratiors8309(String type, ArrayList<Node8309> p, int iter) {
		Utilph8309.print(type + " : " + ((iter < 10 ? " " : "") + iter));
		int i = 0;
		if (Utilph8309.isLarge(size) && !type.equalsIgnoreCase("base")) {
			i = 0;
			Utilph8309.println("");
			while (i < p.size()) {
				Node8309 Node8309 = p.get(i);
				System.out.printf("P[" + ((p.indexOf(Node8309) < 10 ? " " : "") + p.indexOf(Node8309)) + "] = %.7f",
						+(Node8309.getPgrk()));
				Utilph8309.print(" ");
				Utilph8309.println("");
				i++;
			}
		}
		if (type.equalsIgnoreCase("base") || !Utilph8309.isLarge(size)) {
			i = 0;
			while (i < p.size()) {
				Node8309 Node8309 = p.get(i);
				System.out.printf(" P[" + (p.indexOf(Node8309) < 10 ? " " : "") + p.indexOf(Node8309) + "] = %.7f",
						+(Node8309.getPgrk()));
				i++;
			}
		}
		Utilph8309.println("");
	}


}

class Node8309 {
	ArrayList<Integer> incoming, outgoing;
	double pgrk = 0.0;

	public Node8309() {
		incoming = new ArrayList<Integer>(1);
		outgoing = new ArrayList<Integer>(1);
	}

	public Node8309(double initialValue) {
		this();
		setPgrk(initialValue);
	}

	public void setPgrk(double initialValue) {
		pgrk = initialValue;
	}

	public void addIncomingNode(Integer p) {
		if (!incoming.contains(p) && incoming.add(p))
			;
	}

	public void addOutgoingNode(Integer p) {
		if (!outgoing.contains(p) && outgoing.add(p))
			;
	}

	public double getPgrk() {
		return pgrk;
	}
}


//Gaurangkumar Mody CS610 8309 prp
import java.io.*;
import java.text.DecimalFormat;

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class hits8309 {
	static int errorRate, numberOfIterations, size, numberOfLines, initVal, mT[][], adjacencyMatrix[][], njit_id = 8309;
	static String fileName, m[] = null;
	static double TEN_M = 10000000.0, hub_Value[], auth_Value[];
	static DecimalFormat decimalFormat = new DecimalFormat("#0.0000000");
	static BufferedReader bufferedReader;
	
	// getter setters
	public static int getErrorRate() {
		return errorRate;
	}

	public static void assignValue(String fileName, int... a) {
		setInitVal(a[1]);
		hits8309.numberOfIterations = a[0];
		hits8309.fileName = fileName;
	}

	public static void setInitVal(int initVal) {
		if (initVal >= -2 && initVal <= 1) {
			hits8309.initVal = initVal;
		} else {
			Utilph8309.printlogs("Invalid intial value");
			System.exit(0);
		}
	}
	public static void setErrorRate(int errorRate) {
		hits8309.errorRate = errorRate;
	}
	
	public static void main(String[] args) {
		try {
			if (args.length < 3) {
				throw new Exception(
						"Invalid Input\n\"Usage: java hits" + njit_id + " iterations initialvalue filename\"\n");
			}
			assignValue(args[2], Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			hitsm8309();
		} catch (Exception ex) {
			Utilph8309.printException(ex);
		}
	}

	public static void hitsm8309() throws Exception {
		bufferedReader = new BufferedReader(new FileReader(fileName));
		m = bufferedReader.readLine().split(" ");
		size = Integer.parseInt(m[0]);
		numberOfLines = Integer.parseInt(m[1]);
		hub_Value = auth_Value = new double[size];
		adjacencyMatrix = mT = new int[size][size];
		initializeadjacencyMatrix8309(initVal);
		if (numberOfIterations < 0) {
			setErrorRate(1);
		} else if (numberOfIterations == 0) {
			setErrorRate(1);
			numberOfIterations = -5;
		}
		buildMatrix8309();
		mT = transpose8309();
		printIterations8309("Base", 0, auth_Value, hub_Value);
		Iterate8309();
		bufferedReader.close();

	}

	public static void initializeadjacencyMatrix8309(int initVal) {
		double initialVal = getInitialValue8309(initVal);
		Utilph8309.printlogs("Vectors initialized to " + initialVal);
		int i = 0;
		while (i < size) {
			auth_Value[i] = hub_Value[i++] = initialVal;
		}
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

	public static void buildMatrix8309() throws IOException {
		int i = 0;
		while (i < numberOfLines) {
			m = bufferedReader.readLine().split(" ");
			adjacencyMatrix[Integer.parseInt(m[0])][Integer.parseInt(m[1])] = 1;
			i++;
		}
	}

	public static int[][] transpose8309() {
		int i = 0, j = 0, a[][] = new int[size][size];
		while (i < size) {
			while (j < size) {
				a[i][j] = adjacencyMatrix[j++][i];
			}
			j = 0;
			i++;
		}
		return a;
	}

	public static void printIterations8309(String type, int i, double[]... a) {
		Utilph8309.print(type + " : " + ((i < 10 ? " " : "") + i) + " ");
		int j = 0;
		if (!type.equalsIgnoreCase("base") && Utilph8309.isLarge(size)) {
			j = 0;
			Utilph8309.println("");
			while (j < size) {
				Utilph8309.println("A/H[" + (j < 10 ? " " : "") + j + "] = " + decimalFormat.format(a[0][j]) + "/"
						+ decimalFormat.format(a[1][j]) + " ");
				j++;
			}
			Utilph8309.println("");
		}
		if (type.equalsIgnoreCase("base") || !Utilph8309.isLarge(size)) {
			j = 0;
			while (j < size) {
				Utilph8309.print("A/H[" + (j < 10 ? " " : "") + j + "] = "
						+ decimalFormat.format(Utilph8309.mulAndDivide(a[0][j], TEN_M)) + "/"
						+ decimalFormat.format(Utilph8309.mulAndDivide(a[1][j], TEN_M)) + " ");
				j++;
			}
			Utilph8309.println("");
		}
	}

	public static void Iterate8309() {
		int i = getErrorRate() == 0 ? Iterators8309() : ErrorRates8309();
	}

	public static int Iterators8309() {
		double[] auth, hub = hub_Value;
		int i = 0;
		while (i < numberOfIterations) {
			auth = matrixMultiplication8309(hub, mT);
			hub = matrixMultiplication8309(auth, adjacencyMatrix);
			auth = normalize8309(auth);
			hub = normalize8309(hub);
			i++;
			printIterations8309("Iter", i, auth, hub);
		}
		return 0;
	}

	public static int ErrorRates8309() {
		double p_A[], p_H[], auth[] = auth_Value, hub[] = hub_Value, errVal = Math.pow(10, numberOfIterations);
		int i = 0;
		while (size > 0) {
			p_A = auth;
			p_H = hub;
			auth = matrixMultiplication8309(hub, mT);
			hub = matrixMultiplication8309(auth, adjacencyMatrix);
			auth = normalize8309(auth);
			hub = normalize8309(hub);
			i++;
			if (!Utilph8309.isLarge(size)) {
				printIterations8309("Iter", i, auth, hub);
			}
			if (isConverged8309(errVal, auth, hub, p_A, p_H)) {
				break;
			}
		}
		if (Utilph8309.isLarge(size)) {
			printIterations8309("Iter", ++i, auth, hub);
		}
		return 0;
	}

	public static double[] matrixMultiplication8309(double[] v, int[][] a) {
		double[] res = new double[size];
		int i = 0, j = 0;
		while (i < size) {
			while (j < size) {
				res[i] = res[i] + (a[i][j] * v[j++]);
			}
			j = 0;
			i++;
		}
		return res;
	}
	
	public static double[] normalize8309(double[] v) {
		double[] res = new double[size];
		double sum = 0;
		int i = 0;
		while (i < size) {
			sum += (v[i] * v[i++]);
		}
		i = 0;
		sum = Math.sqrt(sum);
		while (i < size) {
			res[i] = v[i++] / sum;
		}
		return res;
	}

	public static boolean isConverged8309(double error, double[]... mat) {
		boolean check = true, d[] = new boolean[size];
		int i = 0;
		while (i < size) {
			if (Math.abs(mat[0][i] - mat[2][i]) < error && Math.abs(mat[1][i] - mat[3][i]) < error) {
				d[i] = true;
			} else {
				d[i] = false;
			}
			i++;
		}
		i = 0;
		while (i < size) {
			check = d[i++] && check;
		}
		return check;
	}

	
}

//Gaurangkumar Mody CS610 8309 prp
import java.io.PrintStream;
/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class Utilph8309 {
	public final static int MASK = 0xff;
	public static PrintStream p = new PrintStream(System.out) {
		@Override
		public void println(String string) {
			super.println(string);
		}
		@Override
		public void print(String string) {
			super.print(string);
		}
	};
	public static void println(String string) {
		p.println(string);
	}

	public static void print(String string) {
		p.print(string);
	}
	public static void printf(String string) {
		p.printf(string);
	}

	public static void printlogs(String string) {
		printPattern(string.length() + 6);
		p.println("++ " + string + " ++");
		printPattern(string.length() + 6);
	}

	public static void printPattern(int length) {
		for (int i = 0; i < length; i++) {
			p.print("+");
		}
		p.println();
	}

	
	public static void printException(Exception ex) {
		ex.printStackTrace();
	}

	
	public static double mulAndDivide(double b, double a) {
		return Math.round(b * a) / a;
	}
	public static void printMatrix(int[][] m) {
		try {
			int rows = m.length;
			int columns = m[0].length;
			String str = "|\t";
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					str += m[i][j] + "\t";
				}

				println(str + "|");
				str = "|\t";
			}
		} catch (Exception e) {
			println("Matrix is empty!!");
		}
	}
	public static boolean isLarge(int size) {
		return size > 10 ? true : false;
	}
}

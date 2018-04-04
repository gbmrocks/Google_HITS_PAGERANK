//Gaurangkumar Mody CS610 8309 prp

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.xml.bind.DatatypeConverter;
import java.io.PrintStream;

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class Utilhf8309 {
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

	public static void printExecTime(long start, long end) {
		printlogs("Execution time is " + new DecimalFormat("#0.00000").format((end - start) / 1000d) + " seconds");
	}

	public static void printMemorySizeSaved(String oldf, String newf) {
		long oldfileSize = FileUtil8309.getFileSize(oldf);
		long newfileSize = FileUtil8309.getFileSize(newf);
		Utilhf8309.printlogs("Old File Size : " + oldfileSize + " Bytes");
		Utilhf8309.printlogs("New File Size : " + newfileSize + " Bytes");
		long saved = oldfileSize - newfileSize;
		String msg = "Decrease";
		if (saved < 0) {
			saved = -saved;
			msg = "Increase";
		}
		Utilhf8309.printlogs(((float) (saved * 100) / oldfileSize) + " % " + msg + " in filesize.");
	}

	public static void printException(Exception ex) {
		ex.printStackTrace();
	}

	public static int bitWiseAndWithMask(int a) {
		return bitWiseAnd(a, MASK);
	}

	public static int bitWiseAnd(int a, int b) {
		return a & b;
	}

	public static int bitWiseOr(int a, int b) {
		return a | b;
	}

	public static int UnsignedRShift(int a, int b) {
		return a >>> b;
	}

}

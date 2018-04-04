
//Gaurangkumar Mody CS610 8309 prp

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class henc8309 {
	public static String[] codes = new String[256];
	private static FileOs8309 fOs;
	private static FileIs8309 fIs;
	public static String njit_id = "8309";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			if (args.length < 1) {
				throw new Exception("Invalid Input\nUsage java henc"+njit_id+" filename");
			}
			huffman_encode(args[0]);
		} catch (Exception ex) {
			Utilhf8309.printException(ex);
		}
		Utilhf8309.printExecTime(start, System.currentTimeMillis());// Execution time
	}

	public static void huffman_encode(String fileName) throws Exception {
		String input = null, hufFileName = "";
		fIs = new FileIs8309(fileName);
		try {
			input = fIs.readString();
		} catch (Exception e) {
			fIs.close();
			Utilhf8309.println(e.getMessage());
		}
		FileUtil8309.storeHash(fileName);
		hufFileName = fileName + ".huf";
		fOs = new FileOs8309(hufFileName);
		ArrayList<Tree8309> chars = Tree8309.convertToArrayList(Tree8309.countOccurrence(input));
		Tree8309.buildMinHeap(chars);
		Tree8309.encode(chars, fOs);
		fOs.writeInt(input.length());
		int i = 0, j = 0;
		char[] ch = input.toCharArray();
		while (i < ch.length) {
			char[] bit = codes[ch[i]].toCharArray();
			j = 0;
			while (j < bit.length) {
				switch (bit[j]) {
				case '0':
					fOs.writeBoolean(false);
					break;
				case '1':
					fOs.writeBoolean(true);
					break;
				default:
					break;
				}
				j++;
			}
			i++;
		}
		fIs.close();// close  file
		fOs.close();// close  file.
		Utilhf8309.printMemorySizeSaved(fileName, hufFileName);// print saved memory percentage.
		FileUtil8309.deleteFile(new File(fileName));// Delete Original file.
	}
}

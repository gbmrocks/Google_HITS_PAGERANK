//Gaurangkumar Mody CS610 8309 prp

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class hdec8309 {
	private static FileIs8309 reader;
	private static int njit_id=8309;
	private static FileOs8309 writer;
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			if (args.length < 1) {
				throw new Exception("Invalid Input\nUsage java hdec"+njit_id+ " filename");
			}
			huffman_decode(args[0]);
		} catch (Exception ex) {
			Utilhf8309.printException(ex);
		}
		Utilhf8309.printExecTime(start, System.currentTimeMillis());// Execution time
	}
	public static void huffman_decode(String hufFileName) {
		long start = System.currentTimeMillis();
		try {
			String fileName = hufFileName.substring(0, hufFileName.length() - 4);
			reader = new FileIs8309(hufFileName);
			writer = new FileOs8309(fileName);
			Tree8309 root = Tree8309.decode(reader);
			Tree8309.printHeap(root, 0);
			int l = reader.readInt(), i = 0;
			while (i < l) {
				Tree8309 node = root;
				while (node.isNode) {
					node = reader.readBit() ? node.right : node.left;
				}
				writer.writeChar(node.ch);
				i++;
			}
			reader.close();
			writer.close();

			Utilhf8309.printMemorySizeSaved(hufFileName, fileName);
			FileUtil8309.verifyHash(fileName); // verify hash with original file
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
	}
}
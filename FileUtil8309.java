//Gaurangkumar Mody CS610 8309 prp

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class FileUtil8309 {
	public static void deleteFile(File... file) {
		for (int i = 0; i < file.length; i++) {
			Utilhf8309.printlogs("Deleting " + file[i].getName());
			file[i].delete();
		}
	}
	public static long getFileSize(String fileName) {
		long length = 0;
		try {
			File file = new File(fileName);
			length = file.length();
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
		return length;
	}

	public static String calculateHash(String fileName) {
		byte[] b;
		String realHash = "";
		try {
			b = Files.readAllBytes(Paths.get(fileName));
			byte[] hash = MessageDigest.getInstance("MD5").digest(b);
			realHash = DatatypeConverter.printHexBinary(hash);
			Utilhf8309.printlogs("File md5 : " + realHash);
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
		return realHash;
	}

	public static void storeHash(String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".hash"));
			writer.write(calculateHash(fileName));
			writer.close();
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
	}

	public static void verifyHash(String fileName) {
		String storedHashfile = fileName + ".hash", storedHufffile = fileName + ".huf";
		try {
			String content = new String(Files.readAllBytes(Paths.get(storedHashfile)));
			if (calculateHash(fileName).equalsIgnoreCase(content)) {
				Utilhf8309.printlogs("Verified Data Integrity.");
				deleteFile(new File(storedHufffile), new File(storedHashfile));
			} else {
				Utilhf8309.printlogs("Data Integrity Failed");
			}
		} catch (IOException e) {
			Utilhf8309.printException(e);
		}
	}
}

class FileIs8309 {
	private static BufferedInputStream iS;
	private static int buffer, bufferLength;
	public FileIs8309(String filename) {
		try {
			iS = new BufferedInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			Utilhf8309.printException(e);
		}
	}
	public boolean readBit() throws Exception {
		if (bufferLength == 0) {
			readBuffer();
		} else if (bufferLength == -1) {
			throw new Exception("File ended....");
		}
		bufferLength--;
		return Utilhf8309.bitWiseAnd((buffer >> bufferLength), 1) == 1;
	}

	public void readBuffer() {
		try {
			buffer = iS.read();
			while(buffer == -1) {
				return;
			}
			bufferLength = 8;
		} catch (IOException e) {
			Utilhf8309.printException(e);
			initBuffer(-1, -1);
		}
	}

	public char readChar() throws Exception {
		return (char) Utilhf8309.bitWiseAndWithMask(readByte());
	}

	public int readInt() throws Exception {
		int number = 0,i=0;
		while(i < 4) {
			number <<= 8;
			number = Utilhf8309.bitWiseOr(number, Utilhf8309.bitWiseAndWithMask(readByte()));
			i++;
		}
		return number;
	}

	public char readByte() throws Exception {
		if (bufferLength == 0) {
			readBuffer();
			int myByte = Utilhf8309.bitWiseAndWithMask(buffer);
			readBuffer();
			return (char) Utilhf8309.bitWiseAndWithMask(myByte);
		} else if (bufferLength == -1) {
			throw new Exception("File ended");
		} else if (bufferLength == 8) {
			int myByte = buffer;
			readBuffer();
			return (char) Utilhf8309.bitWiseAndWithMask(myByte);
		} else {
			int length = bufferLength,bits = buffer;
			bits <<= (8 - length);
			readBuffer();
			if (bufferLength == -1 || buffer == -1) {
				throw new Exception("File ended");
			}

			bufferLength = length;
			bits = Utilhf8309.bitWiseOr(bits, Utilhf8309.UnsignedRShift(buffer, bufferLength));
			return (char) Utilhf8309.bitWiseAndWithMask(bits);
		}
	}

	public String readString() throws Exception {
		StringBuilder sb = new StringBuilder();
		do {
			sb.append(readChar());
		} 
		while (buffer != -1);
		return sb.toString();
	}

	public void close() throws IOException {
		iS.close();
	}

	public static void initBuffer(int buffer, int bufferLength) {
		FileIs8309.bufferLength = bufferLength;
		FileIs8309.buffer = buffer;
	}

}

class FileOs8309 {
	private static BufferedOutputStream oS;
	private static int buffer, bufferLength;

	public FileOs8309(String fileName) {
		try {
			initBuffer(0, 0);
			oS = new BufferedOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			Utilhf8309.printException(e);
		}
	}

	public void writeBoolean(boolean bit) {
		bufferLength++;
		buffer <<= 1;
		buffer = bit ? Utilhf8309.bitWiseOr(buffer, 1) : buffer;
		while (bufferLength == 8) {
			writeString();
		}

	}

	public void writeInt(int number) {
		int i = 3;
		while (i >= 0) {
			writeByte(Utilhf8309.bitWiseAndWithMask(Utilhf8309.UnsignedRShift(number, i * 8)));
			i--;
		}
	}

	public void writeChar(char ch) {
		writeByte(ch);
	}

	private void writeByte(int myByte) {
		if (bufferLength != 0) {
			int i = 0;
			while (i < 8) {
				writeBoolean(Utilhf8309.bitWiseAnd(Utilhf8309.UnsignedRShift(myByte, (7 - i++)), 1) == 1);
			}
		} else {
			commitBuffer(myByte);
		}
	}

	private static void writeString() {
		if (bufferLength == 0) {
			return;
		}
		buffer <<= 8 - bufferLength; // makes buffer
		commitBuffer(buffer);
		initBuffer(0, 0);
	}
	public static void initBuffer(int buffer, int bufferLength) {
		FileOs8309.bufferLength = bufferLength;
		FileOs8309.buffer = buffer;
	}

	public static void commitBuffer(int buffer) {
		try {
			oS.write(buffer);
		} catch (IOException e) {
			Utilhf8309.printException(e);
		}
	}
	public void close() {
		writeString(); // commit changes before closing
		try {
			oS.flush();
			oS.close();
		} catch (IOException e) {
			Utilhf8309.printException(e);
		}
	}
}

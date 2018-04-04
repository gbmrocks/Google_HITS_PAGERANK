//Gaurangkumar Mody CS610 8309 prp
import java.io.IOException;
import java.util.ArrayList;

/*
 * 
 * @author Gaurang Mody
 * NJID # ****8309
 *
 */
public class Tree8309 {
	public boolean isNode = false;
	public char ch;
	public int count;
	public String code = "";
	public Tree8309 left, right;
	public Tree8309(char ch, int count) {
		this.ch = ch;
		this.count = count;
	}
	public Tree8309(int count, Tree8309 left, Tree8309 right) {
		if (!isNull(left) && !isNull(right)) {
			isNode = true;
		}
		assignVal((char) 0, count, left, right);
	}
	public void assignVal(char ch, int count, Tree8309 left, Tree8309 right) {
		this.left = left;
		this.right = right;
		this.ch = ch;
		this.count = count;
	}
	@Override
	public String toString() {
		return ch + " = " + count;
	}
	public static boolean isNull(Tree8309 tree8309) {
		return tree8309 == null ? true : false;
	}
	public static ArrayList<Tree8309> convertToArrayList(int[] frequencies) {
		ArrayList<Tree8309> chars = new ArrayList<Tree8309>();
		int i = 0;
		while (i < frequencies.length) {
			while (frequencies[i] > 0) {
				chars.add(new Tree8309((char) i, frequencies[i]));
				break;
			}
			i++;
		}
		return chars;
	}
	public static void buildMinHeap(ArrayList<Tree8309> chars) throws IOException {
		int max = parent(chars.size()) - 1;
		while (max >= 0) {
			minDownHeap(chars, max--);
		}
	}
	public static int parent(int i) {
		return (int) Math.floor((i - 1) / 2);
	}
	public static int left(int i) {
		return 2 * i + 1;
	}
	public static int right(int i) {
		return left(i) + 1;
	}
	public static Tree8309 leftChild(ArrayList<Tree8309> input, int i) {
		return getLeaf(input, i, left(i));
	}
	public static Tree8309 rightChild(ArrayList<Tree8309> input, int i) {
		return getLeaf(input, i, right(i));
	}
	
	public static Tree8309 getLeaf(ArrayList<Tree8309> input, int ret, int i) {
		return input.get((i >= input.size() || i < 0) ? ret : i);
	}
	public static int getMinHeapIndex(ArrayList<Tree8309> input, int index) {
		Tree8309 x = input.get(index), y = leftChild(input, index), z = rightChild(input, index);
		return x.count <= y.count ? x.count <= z.count ? input.indexOf(x) : input.indexOf(z)
				: y.count <= z.count ? input.indexOf(y) : input.indexOf(z);
	}

	public static ArrayList<Tree8309> swap(ArrayList<Tree8309> input, int i, int smallest) {
		Tree8309 temp;
		temp = input.get(i);
		input.set(i, input.get(smallest));
		input.set(smallest, temp);
		return input;
	}

	public static void minDownHeap(ArrayList<Tree8309> input, int index) {
		int lowest = getMinHeapIndex(input, index);
		if (lowest != index) {
			input = swap(input, index, lowest);
			minDownHeap(input, lowest);
		}
	}

	public static void encode(ArrayList<Tree8309> chars, FileOs8309 fOs) throws Exception {
		for (; chars.size() > 1;) {
			Tree8309 left = Tree8309.extractMin(chars);
			Tree8309 right = Tree8309.extractMin(chars);
			Tree8309 node = new Tree8309(left.count + right.count, left, right);
			Tree8309.insertMin(chars, node);
		}
		Tree8309 root = Tree8309.extractMin(chars);
		assignCodes(root);
		writeHeap(root, fOs);

	}


	public static Tree8309 extractMin(ArrayList<Tree8309> chars) throws Exception {
		if (chars.size() <= 0) {
			throw new Exception("Size Error");
		}
		Tree8309 min = chars.get(0);
		Tree8309 last = chars.get(chars.size() - 1);
		chars.set(0, last);
		chars.set(chars.size() - 1, min);
		chars.remove(min);
		if (chars.size() > 1) {
			minDownHeap(chars, 0);
		}
		return min;
	}

	public static void assignCodes(Tree8309 root) {
		if (root.isNode) {
			if (!isNull(root.left)) {
				root.left.code += root.code + "0";
				assignCodes(root.left);
			}
			if (!isNull(root.right)) {
				root.right.code += root.code + "1";
				assignCodes(root.right);
			}
		} else {
			henc8309.codes[root.ch] = root.code;
		}
	}

	public static void writeHeap(Tree8309 root, FileOs8309 fOs) {
		if (root.isNode) {
			fOs.writeBoolean(true);
			if (!isNull(root.right)) {
				writeHeap(root.right, fOs);
			}
			if (!isNull(root.left)) {
				writeHeap(root.left, fOs);
			}
		} else {
			fOs.writeBoolean(false);
			fOs.writeChar(root.ch);
		}
	}

	public static void printHeap(Tree8309 character, int i) {
		try {
			if (character.isNode) {
				if (!isNull(character.right)) {
					printHeap(character.right, i);
				}
				if (!isNull(character.left)) {
					printHeap(character.left, i);
				}
			} else if (i == 1) {
				Utilhf8309.println(character + "  = " + character.code + " = " + Byte.parseByte(character.code, 2));
			}
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
	}

	public static int[] countOccurrence(String input) {
		int occurrence[] = new int[256], i = 0;
		char[] chars = input.toCharArray();
		while (i < chars.length) {
			occurrence[chars[i++]]++;
		}
		return occurrence;
	}

	public static void insertMin(ArrayList<Tree8309> chars, Tree8309 character) {
		int index = chars.size(), parentIndex = (int) Math.floor((index - 1) / 2);
		chars.add(character);
		while (index > 0) {
			if (character.count >= chars.get(parentIndex).count) {
				break;
			}
			chars.set(index, chars.get(parentIndex));
			index = parentIndex;
			parentIndex = (int) Math.floor((index - 1) / 2);
		}
		chars.set(index > 0 ? index : 0, character);
	}

	public static Tree8309 decode(FileIs8309 fIs) {
		try {
			if (!fIs.readBit()) {
				return new Tree8309(fIs.readChar(), -1);
			} else {
				Tree8309 left = decode(fIs);
				Tree8309 right = decode(fIs);
				return new Tree8309(-1, right, left);
			}
		} catch (Exception e) {
			Utilhf8309.printException(e);
		}
		return null;
	}
}

package graphs;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Exercise3 {
	
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader(args[0]);
		FileWriter fw = new FileWriter(args[1]);
		try {
			Exercise3 exercise3 = new Exercise3();
			exercise3.adjMatrixToAdjList(fr, fw);
		} finally {
			fr.close();
			fw.close();
		}
	}

	public void adjMatrixToAdjList(Reader reader, Writer writer) {
		boolean[][] a = fileToAdjMatrix(reader);
		List<Integer>[] adjList = adjMatrixToAdjList(a);
		adjListToFile(adjList, writer);
	}

	public boolean[][] fileToAdjMatrix(Reader reader) {
		Scanner scanner = new Scanner(reader);
		int n = scanner.nextInt();
		boolean[][] a = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int arrow = scanner.nextInt();
				a[i][j] = arrow == 1;
			}
		}
		scanner.close();
		return a;
	}

	public List<Integer>[] adjMatrixToAdjList(boolean[][] a) {
		int n = a.length;
		List<Integer>[] adjList = (List<Integer>[]) new List[n];
		for (int i = 0; i < n; i++) {
			adjList[i] = new LinkedList<Integer>();
			for (int j = 0; j < n; j++) {
				if (a[i][j]) {
					adjList[i].add(j + 1);
				}
			}
		}
		return adjList;
	}
	
	public void adjListToFile(List<Integer>[] adjList, Writer writer) {
		PrintWriter pw = new PrintWriter(writer);
		pw.print(adjList.length);
		for (int i = 0; i < adjList.length; i++) {
			pw.print('\n');
			Iterator<Integer> iter = adjList[i].iterator();
			while (iter.hasNext()) {
				pw.print(iter.next());
				if (iter.hasNext())
					pw.print(' ');
			}
		}
		pw.close();
	}
}

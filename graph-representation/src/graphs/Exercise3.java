package graphs;

import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class Exercise3 {
	
	public static void main(String args) {
		
	}

	public void adjMatrixToAdjList(Reader reader, Writer writer) {
		// TODO Auto-generated method stub
		
	}

	public boolean [][] fileToAdjMatrix(Reader reader) {
		Scanner scanner = new Scanner(reader);
		
		scanner.close();
		return null;
	}
}

//class AdjMatrix {
//	boolean [][] a;
//	
//	AdjMatrix(int n) {
//		a = new boolean[n][n];
//		for (int i = 0; i < n; i++)
//			for (int j = 0; j < n; j++)
//				a[i][j] = false;
//	}
//	
//	void setArrow(int u, int v) {
//		a[u - 1][v - 1] = true;
//	}
//	
//	boolean hasArrow(int u, int v) {
//		return a[u - 1][v - 1];
//	}
//}
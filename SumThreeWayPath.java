import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SumThreeWayPath {
	
	private static int[][] matrixSum;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[][] matrix = new int[80][80];
		File f = new File("p082_matrix.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		for (int i = 0; i < matrix.length; i++) {
			String line = br.readLine();
			String[] nums = line.split(",");
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = Integer.parseInt(nums[j]);
			}
		}
		br.close();
//		int[][] matrix = new int[][] {
//			{131, 673, 234, 103, 18},
//			{201, 96, 342, 965, 150},
//			{630, 803, 746, 422, 111},
//			{537, 699, 497, 121, 956},
//			{805, 732, 524, 37, 331}
//		};
		
		matrixSum = new int[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			matrixSum[i][matrix.length - 1] = matrix[i][matrix.length - 1];
		}
		
		for (int j = matrix.length - 2; j >= 0; j--) {
			for (int i = 0; i < matrix.length; i++) {
				matrixSum[i][j] = matrixSum[i][j + 1] + matrix[i][j];
			}
			
			int diff = 1;
			while (diff != 0) {
				diff = 0;
				for (int i = 0; i < matrix.length; i++) {
					int temp = matrixSum[i][j];
					if (i == 0) {
						matrixSum[i][j] = Math.min(matrixSum[i][j], matrix[i][j] + matrixSum[i + 1][j]);
					} else if (i == matrix.length - 1) {
						matrixSum[i][j] = Math.min(matrixSum[i][j], matrix[i][j] + matrixSum[i - 1][j]);
					} else {
						matrixSum[i][j] = Math.min(Math.min(matrixSum[i][j], matrix[i][j] + matrixSum[i + 1][j]), 
								matrix[i][j] + matrixSum[i - 1][j]);
					}
					diff += Math.abs(temp - matrixSum[i][j]);
				}
			}
			
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			if (matrixSum[i][0] < min) min = matrixSum[i][0];
		}
		System.out.println(min);
	}
}

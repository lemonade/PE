import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SumFourWayPath {
	
	private static int[][] matrixSum;
	
	public static void main(String[] args) throws IOException {
		int[][] matrix = new int[80][80];
		File f = new File("p083_matrix.txt");
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
//			{201, 96,  342, 965, 150},
//			{630, 803, 746, 422, 111},
//			{537, 699, 497, 121, 956},
//			{805, 732, 524, 37,  331}
//		};
		matrixSum = new int[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++)
			matrixSum[i][j] = -1;
		}
		matrixSum[matrix.length - 1][matrix.length - 1] = matrix[matrix.length - 1][matrix.length - 1];
		int sum = 0;
		boolean continueLoop = true;
		while (continueLoop) {
			sum = computeSum(matrixSum);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					int up = (i >= 1 && matrixSum[i - 1][j] != -1) ? (matrixSum[i - 1][j] + matrix[i][j]) : -1;
					int down = (i <= matrix.length - 2 && matrixSum[i + 1][j] != -1) ? (matrixSum[i + 1][j] + matrix[i][j]) : -1;
					int left = (j >= 1 && matrixSum[i][j - 1] != -1) ? (matrixSum[i][j - 1] + matrix[i][j]) : -1;
					int right = (j <= matrix.length - 2 && matrixSum[i][j + 1] != -1) ? (matrixSum[i][j + 1] + matrix[i][j]) : -1;
					int min = Integer.MAX_VALUE;
					if (min > up && up != -1) {
						min = up;
					}
					if (min > down && down != -1) {
						min = down;
					}
					if (min > left && left != -1) {
						min = left;
					}
					if (min > right && right != -1) {
						min = right;
					}
					if (min != Integer.MAX_VALUE && matrixSum[i][j] == -1) {
						matrixSum[i][j] = min;
					}
					if (min < matrixSum[i][j] && matrixSum[i][j] != -1) {
						matrixSum[i][j] = min;
					}
				}
			}
			continueLoop = (sum != computeSum(matrixSum));
			printMatrix(matrixSum);
		}
		
	}
	
	private static int computeSum(int[][] matrix) {
		int sum = 0;
		for (int[] row : matrix) {
			for (int cell : row) {
				sum += cell;
			}
		}
		return sum;
	}
	
	private static void printMatrix(int[][] matrix) {
		System.out.println("---------------------------");
		for (int[] row : matrix) {
			for (int cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}
}

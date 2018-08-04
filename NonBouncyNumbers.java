import java.math.BigInteger;

public class NonBouncyNumbers {

	private static final int LIMIT = 100;
	
	private static final BigInteger[][] decreaseMatrix = new BigInteger[9][LIMIT];
	private static final BigInteger[][] increaseMatrix = new BigInteger[9][LIMIT];
	
	public static void main(String[] args) {
		long countDuplicate = 9 * LIMIT;
		System.out.println(countIncreasing().add(countDecreasing()).subtract(new BigInteger(countDuplicate + "")));
	}
	
	private static BigInteger countIncreasing() {
		for (int i = 0; i < LIMIT; i++) {
			increaseMatrix[0][i] = BigInteger.ONE;
		}
		
		for (int i = 1; i < 9; i++) {
			for (int j = 0; j < LIMIT; j++) {
				if (j == 0) increaseMatrix[i][j] = increaseMatrix[i - 1][j].add(BigInteger.ONE);
				else increaseMatrix[i][j] = increaseMatrix[i - 1][j].add(increaseMatrix[i][j - 1]);
			}
		}
		
		BigInteger sum = BigInteger.ZERO;
		
		for (int i = 0; i < LIMIT; i++) {
			sum = sum.add(increaseMatrix[8][i]);
		}
		
		return sum;
	}
	
	private static BigInteger countDecreasing() {
		for (int i = 0; i < LIMIT; i++) {
			decreaseMatrix[0][i] = new BigInteger((i + 1) + "");
		}
		
		for (int i = 1; i < 9; i++) {
			for (int j = 0; j < LIMIT; j++) {
				if (j == 0) decreaseMatrix[i][j] = decreaseMatrix[i - 1][j].add(BigInteger.ONE);
				else decreaseMatrix[i][j] = decreaseMatrix[i - 1][j].add(decreaseMatrix[i][j - 1]).add(BigInteger.ONE);
			}
		}
		
		BigInteger sum = BigInteger.ZERO;
		
		for (int i = 0; i < LIMIT; i++) {
			sum = sum.add(decreaseMatrix[8][i]);
		}
		return sum;
	}

}

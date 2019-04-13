
public class CountingHexagon {
	
	private static int LIMIT = 12345;
	private static long[] results = new long[LIMIT + 1];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long S = 0;
		for (int i = 3; i < LIMIT + 1; i++) {
			S += countingHexagon(i);
		}
		System.out.println(S);
	}
	
	private static long countingHexagon(int n) {
		if (results[n] != 0) {
			return results[n];
		}
		
		if (n == 3) {
			results[n] = 1;
			return 1;
		}
		
		long out = countingHexagon(n - 1);
		for (int i = 1; i < n/3 + 1; i++) {
			out += i * (n - 3 * i + 1);
		}
		results[n] = out;
		return out;
	}

}

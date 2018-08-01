
public class BouncyNumbers {

	public static void main(String[] args) {

		long count = 0;
		long n = 99;
		while (((double) count / n) < 0.99) {
			n++;
			if (isBouncyNumber(n)) {
				count++;
			}
		}
		
		System.out.println("n = " + n);
		System.out.println("count = " + count);
	}
	
	private static boolean isBouncyNumber(long x) {
		char[] arr = (x + "").toCharArray();
		int j = 0;
		while ((j < arr.length - 1) && arr[j] == arr[j + 1]) {
			j++;
		}
		
		if (j == arr.length - 1) {
			return false;
		}
		
		for(int i = j + 1; i < arr.length - 1; i++) {
			if ((arr[j] - arr[j + 1]) * (arr[i] - arr[i + 1]) < 0) return true;
		}
		return false;
	}

}

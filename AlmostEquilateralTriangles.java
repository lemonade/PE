import java.math.BigInteger;

public class AlmostEquilateralTriangles {

	private static final BigInteger SIXTEEN = new BigInteger("16");
	private static final BigInteger TWENTY = new BigInteger("20");
	public static void main(String[] args) {
		
		BigInteger perimeterSum = BigInteger.ZERO;
		long a = 2;
		while (a <= (Math.pow(10, 9) - 1) / 3) {
			BigInteger perimeter1 = new BigInteger((3 * a + 1) + "");
			
			BigInteger SquareOfArea1 = perimeter1
					.multiply(new BigInteger((a - 1) + ""))
					.multiply(new BigInteger((a + 1) + ""))
					.multiply(new BigInteger((a + 1) + ""));

			if (SquareOfArea1.mod(SIXTEEN).intValue() == 0 && isSquare(SquareOfArea1.divide(SIXTEEN))) {
				perimeterSum = perimeterSum.add(perimeter1);
				System.out.println(a + ", " + a + ", " + (a + 1));
				if (a >= 100000) {
					// The rate between a root and previous one seem to be convergent
					a = (long) (a * 3.732);
				}
			}
			
			
			BigInteger perimeter2 = new BigInteger((3 * a - 1) + "");
			BigInteger SquareOfArea2 = perimeter2
					.multiply(new BigInteger((a + 1) + ""))
					.multiply(new BigInteger((a - 1) + ""))
					.multiply(new BigInteger((a - 1) + ""));
			if (SquareOfArea2.mod(SIXTEEN).intValue() == 0 && isSquare(SquareOfArea2.divide(SIXTEEN))) {
				perimeterSum = perimeterSum.add(perimeter2);
				System.out.println(a + ", " + a + ", " + (a - 1));
				if (a >= 100000) {
					a = (long) (a * 3.732);
				}
			}
			a++;
			if (a % 100000 == 0) {
				System.out.println("Please wait ...");
			}
		}
		System.out.println(perimeterSum);
	}
	
	private static boolean isSquare(BigInteger bi) {
		String inStr = bi.toString();
		if (inStr.length() % 2 == 1) {
			inStr = "0" + inStr;
		}
		
		int i = 0;
		String sqrt = "0";
		BigInteger a;
		String remain = "";
		while (i < inStr.length()) {
			a = new BigInteger(remain + inStr.charAt(i) + inStr.charAt(i + 1));
			BigInteger bigSqrt = new BigInteger(sqrt);
			int x = 0;
			while ((bigSqrt.multiply(TWENTY).add(new BigInteger(x + ""))).multiply(new BigInteger(x + "")).compareTo(a) <= 0) {
				x++;
			}
			remain = a.subtract((bigSqrt.multiply(TWENTY).add(new BigInteger((x - 1) + ""))).multiply(new BigInteger("" + (x - 1)))).toString();
			sqrt = sqrt + (x - 1);
			i = i + 2;
		}
		return Long.valueOf(remain) == 0;
	}
}

import java.math.BigInteger;

public class ArrangedProbability {

	private static BigInteger A_THOUSAND_BILLION = new BigInteger("1000000000000");
	
	private static BigInteger FOUR = new BigInteger("4");
	private static BigInteger THREE = new BigInteger("3");
	private static BigInteger TWO = new BigInteger("2");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//  m     m - 1     1
		// --- * ------- = ---
		//  n     n - 1     2
		// 2m(m - 1) = n(n - 1)
		// (2m - 1)^2 = n^2 + (n - 1)^2
		// -> n = 2xy && n - 1 = x^2 - y^2 (1)
		//
		//               OR
		//
		// -> n = x^2 - y^2 && n - 1 = 2xy (2)
		// and m = (x^2 + y^2 + 1)/2
		// (1) <=> y^2 + 2xy - x^2 = 1 <=> (x+y)^2 - 2x^2 = 1
		//     <=>  z(0) = 3, x(0) = 2, 
		//     	  / z(n+1) \     / 3  4 \    / z(n) \
		//       |          | = |        |  |        |,  y(n) = z(n) - x(n)
		//        \ x(n+1) /     \ 2  3 /    \ x(n) /
		// (2) <=> x^2 - 2xy - y^2 = 1 <=> 2x^2 - (x + y)^2 = 1 
		//     <=>  z0 = 1, x0 = 1
		//        / z(n+1) \     / 3  4 \    / z(n) \
		//       |          | = |        |  |        |, y(n) = z(n) - x(n)
		//        \ x(n+1) /     \ 2  3 /    \ x(n) /
		BigInteger a = new BigInteger("3");
		BigInteger b = new BigInteger("2");
		BigInteger c = BigInteger.ONE;
		BigInteger d = BigInteger.ONE;
		BigInteger n1 = b.multiply(TWO).multiply(a.subtract(b));
		BigInteger n2 = d.multiply(d).add((c.subtract(d)).multiply(c.subtract(d)));
		while (n1.compareTo(A_THOUSAND_BILLION) <= 0 && n2.compareTo(A_THOUSAND_BILLION) <= 0) {
			BigInteger nextA = a.multiply(THREE).add(b.multiply(FOUR));
			BigInteger nextB = a.multiply(TWO).add(b.multiply(THREE));
			a = nextA;
			b = nextB;
			BigInteger nextC = c.multiply(THREE).add(d.multiply(FOUR));
			BigInteger nextD = c.multiply(TWO).add(d.multiply(THREE));
			c = nextC;
			d = nextD;
			n1 = b.multiply(new BigInteger("2")).multiply(a.subtract(b));
			n2 = d.multiply(d).subtract((c.subtract(d)).multiply(c.subtract(d)));
		}
		
		if (n1.compareTo(A_THOUSAND_BILLION) > 0) {
			System.out.println(((b.multiply(b)).add((a.subtract(b)).multiply(a.subtract(b))).add(BigInteger.ONE)).divide(TWO));
		}
		
		if (n2.compareTo(A_THOUSAND_BILLION) > 0) {
			System.out.println(((d.multiply(d)).add((c.subtract(d)).multiply(c.subtract(d))).add(BigInteger.ONE)).divide(TWO));
		}
	}
}


public class CuboidRoute {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		long M = 2;
		while (count <= 1000000) {
			long M2 = M * M; 
			for (long i = 1; i <= M - 1; i++) {
				for (long j = i; j <= M; j++) {
					double shortestRoute = Math.sqrt(M2 + (i + j) * (i + j));
					boolean cond = shortestRoute == (long) shortestRoute;
					if (cond) {
						count++;
					}
				}
			}
			M++;
		}
		System.out.println(M - 1);
	}
	
	
}

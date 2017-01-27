import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AmicableChains {
	
	private static HashMap<Integer, Integer> sumList = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int chainLength = 0;
		Set<Integer> longestChain = null;
		for (int i = 2; i <= 1000000; i++) {
			List<Integer> list = new ArrayList<>();
			int temp = sumOfDividors(i);
			while (!list.contains(temp) && temp <= 1000000 && temp != 0) {
				list.add(temp);
				temp = sumOfDividors(temp);
			}
			list.add(temp);
			
			if (list.get(list.size() - 1) != 1 
					&& list.get(list.size() - 1) != 0 
					&& list.get(list.size() - 1).intValue() == list.get(0).intValue()) {
				
				HashSet<Integer> finalSet = new HashSet<>();
				finalSet.addAll(list);
				if (finalSet.size() > chainLength) {
					System.out.println("Set of " + i + ": " + list);
					longestChain = finalSet;
					chainLength = finalSet.size();
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		for (int elem : longestChain) {
			if (elem < min) min = elem;
		}
		System.out.println(min + " " + chainLength);
	}
	
	private static int sumOfDividors(int num) {
		if (sumList.containsKey(num)) return sumList.get(num);
		HashSet<Integer> divisorSet = new HashSet<>();
		for (int i = 1; i <= (int) Math.sqrt(num); i++) {
			if (num % i == 0) {
				divisorSet.add(i);
				divisorSet.add(num / i);
			}
		}
		
		int sum = 0;
		for (int elem : divisorSet) {
			sum += elem;
		}
		sum = sum - num;
		sumList.put(num, sum);
		return sum;
	}
}

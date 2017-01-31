import java.util.HashSet;
import java.util.Set;

public class ArithmeticExpressions {
	private static ArithmeticExpressions ae = new ArithmeticExpressions();
	static Operator[] availableOperators1 = new Operator[] {
			ae.new PlusOperator(),
			ae.new SubstractOperator(),
			ae.new MultiplyOperator(),
			ae.new DivideOperator()
	};
	
	static Operator[] availableOperators2 = new Operator[] {
			ae.new PlusOperator(),
			ae.new SubstractOperator(),
			ae.new MultiplyOperator(),
			ae.new DivideOperator()
	};
	
	static Operator[] availableOperators3 = new Operator[] {
			ae.new PlusOperator(),
			ae.new SubstractOperator(),
			ae.new MultiplyOperator(),
			ae.new DivideOperator()
	};
	
	static Expression[] availableExpression = new Expression[] {
			ae.new Expression1(),
			ae.new Expression2(),
			ae.new Expression3(),
			ae.new Expression4(),
			ae.new Expression5()
	};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] digits = new int[] {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		};
		
		int max = 0;
		
		for (int a : digits) {
			for (int b : digits) {
				if (b > a) {
					for (int c : digits) {
						if (c > b) {
							for (int d : digits) {
								if (d > c) {
									Set<Integer> allPossibleResults = findAllResultsOfAGroup(a, b, c, d);
									int i = 1;
									while (allPossibleResults.contains(i)) {
										i++;
									}
									if (max < i - 1) {
										max = i - 1;
										System.out.println("" + a + b + c + d + " (" + max + ")");
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(findAllResultsOfAGroup(1, 2, 3, 4));
		
	}
	
	private static Set<Integer> findAllResultsOfAGroup(int a, int b, int c, int d) {
		Set<Integer> results = new HashSet<>();
		int[] valueOptions = new int[] {
				a, b, c, d
		};
		
		for (int i : valueOptions) {
			for (int j : valueOptions) {
				if (j != i) {
					for (int k : valueOptions) {
						if (k != j && k != i) {
							for (int l : valueOptions) {
								if (l != k && l != j && l != i) {
									results.addAll(findAllResultsOfAnOrder(i, j, k, l));
								}
								
							}
						}
						
					}
				}
				
			}
		}
		return results;
	}
	
	private static Set<Integer> findAllResultsOfAnOrder(int a, int b, int c, int d) {
		Set<Integer> results = new HashSet<>();
		double result;
		for (Operator o1 : availableOperators1) {
			for (Operator o2 : availableOperators2) {
				for (Operator o3 : availableOperators3) {
					for (Expression expression : availableExpression) {
						expression.setO1(o1);
						expression.setO2(o2);
						expression.setO3(o3);
						expression.setA(a);
						expression.setB(b);
						expression.setC(c);
						expression.setD(d);
						result = expression.getResult();
						if ((int) result == result && result > 0) {
							results.add((int) result);
						}
					}
				}
			}
		}
		return results;
	}
	
	
	abstract class Operator {
		double a;
		double b;
		Operator() {
		}
		public abstract double compute();
		public void setA(double a) {
			this.a = a;
		}
		public void setB(double b) {
			this.b = b;
		}
		
	}
	
	class PlusOperator extends Operator {
		PlusOperator() {
			super();
		}

		@Override
		public double compute() {
			return ((double) a) + b;
		}
		
		@Override
		public String toString() {
			return "(" + a + " + " + b + ")";
		}
	}
	
	class SubstractOperator extends Operator {
		SubstractOperator() {
			super();
		}

		@Override
		public double compute() {
			return ((double) a) - b;
		}
		
		@Override
		public String toString() {
			return "(" + a + " - " + b + ")";
		}
	}
	
	class MultiplyOperator extends Operator {
		MultiplyOperator() {
			super();
		}

		@Override
		public double compute() {
			return ((double) a) * b;
		}
		
		@Override
		public String toString() {
			return "(" + a + " * " + b + ")";
		}
	}
	
	class DivideOperator extends Operator {
		DivideOperator() {
			super();
		}

		@Override
		public double compute() {
			return ((double) a) / b;
		}
		
		@Override
		public String toString() {
			return "(" + a + " / " + b + ")";
		}
	}
	
	abstract class Expression {
		Operator o1;
		Operator o2;
		Operator o3;
		double a;
		double b;
		double c;
		double d;
		public abstract double getResult();
		public void setO1(Operator o1) {
			this.o1 = o1;
		}
		public void setO2(Operator o2) {
			this.o2 = o2;
		}
		public void setO3(Operator o3) {
			this.o3 = o3;
		}
		public void setA(double a) {
			this.a = a;
		}
		public void setB(double b) {
			this.b = b;
		}
		public void setC(double c) {
			this.c = c;
		}
		public void setD(double d) {
			this.d = d;
		}
		
		@Override
		public String toString() {
			return o1.toString() + ", " + o2.toString() + ", " + o3.toString();
		}
	}
	
	class Expression1 extends Expression {

		@Override
		public double getResult() {
			// ((a b) c) d
			o1.setA(a);
			o1.setB(b);
			o2.setA(o1.compute());
			o2.setB(c);
			o3.setA(o2.compute());
			o3.setB(d);
			return o3.compute();
		}
		
		@Override
		public String toString() {
			return "((a b) c) d: " + super.toString();
		}
	}
	
	class Expression2 extends Expression {

		@Override
		public double getResult() {
			// (a b) (c d)
			o1.setA(a);
			o1.setB(b);
			o2.setA(c);
			o2.setB(d);
			o3.setA(o1.compute());
			o3.setB(o2.compute());
			return o3.compute();
		}
		
		@Override
		public String toString() {
			return "(a b) (c d): " + super.toString();
		}
		
	}
	
	class Expression3 extends Expression {

		@Override
		public double getResult() {
			// (a (b c)) d
			o1.setA(b);
			o1.setB(c);
			o2.setA(a);
			o2.setB(o1.compute());
			o3.setA(o2.compute());
			o3.setB(d);
			return o3.compute();
		}
		
		@Override
		public String toString() {
			return "(a (b c)) d: " + super.toString();
		}
	}
	
	class Expression4 extends Expression {

		@Override
		public double getResult() {
			// a ((b c) d)
			o1.setA(b);
			o1.setB(c);
			o2.setA(o1.compute());
			o2.setB(d);
			o3.setA(a);
			o3.setB(o2.compute());
			return o3.compute();
		}
		
		@Override
		public String toString() {
			return "a ((b c) d): " + super.toString();
		}
	}
	
	class Expression5 extends Expression {

		@Override
		public double getResult() {
			// a (b (c d))
			o1.setA(c);
			o1.setB(d);
			o2.setA(b);
			o2.setB(o1.compute());
			o3.setA(a);
			o3.setB(o2.compute());
			return o3.compute();
		}
		
		@Override
		public String toString() {
			return "a (b (c d)): " + super.toString();
		}
	}
}

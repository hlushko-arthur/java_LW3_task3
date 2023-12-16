import java.util.Arrays;

public class LW3_task3 {
	public static class Polynomial {
		private RationalFraction[] coefficients;

		public Polynomial(RationalFraction[] coefficients) {
			this.coefficients = coefficients;
		}

		public RationalFraction[] getCoefficients() {
			return coefficients;
		}

		public void setCoefficients(RationalFraction[] coefficients) {
			this.coefficients = coefficients;
		}

		public RationalFraction evaluate(RationalFraction x) {
			RationalFraction result = new RationalFraction(0, 1);
			RationalFraction xPower = new RationalFraction(1, 1);

			for (RationalFraction coefficient : coefficients) {
				result = result.add(xPower.multiply(coefficient));
				xPower = xPower.multiply(x);
			}

			return result;
		}

		public static Polynomial sumPolynomials(Polynomial[] polynomials) {
			if (polynomials.length == 0) {
				return null;
			}

			int maxDegree = Arrays.stream(polynomials)
					.mapToInt(p -> p.coefficients.length)
					.max()
					.orElse(0);

			RationalFraction[] sumCoefficients = new RationalFraction[maxDegree];

			for (int i = 0; i < maxDegree; i++) {
				RationalFraction sum = new RationalFraction(0, 1);
				for (Polynomial polynomial : polynomials) {
					if (i < polynomial.coefficients.length) {
						sum = sum.add(polynomial.coefficients[i]);
					}
				}
				sumCoefficients[i] = sum;
			}

			return new Polynomial(sumCoefficients);
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();

			for (int i = coefficients.length - 1; i >= 0; i--) {
				if (!coefficients[i].equals(new RationalFraction(0, 1))) {
					result.append(coefficients[i]);
					if (i > 0) {
						result.append("x^").append(i);
					}
					if (i > 0 && !coefficients[i - 1].equals(new RationalFraction(0, 1))) {
						result.append(" + ");
					}
				}
			}

			return result.toString();
		}
	}

	public static class RationalFraction {
		private int numerator;
		private int denominator;

		public RationalFraction(int numerator, int denominator) {
			if (denominator == 0) {
				throw new IllegalArgumentException("Denominator cannot be zero.");
			}

			int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
			this.numerator = numerator / gcd;
			this.denominator = denominator / gcd;

			if (this.denominator < 0) {
				this.numerator = -this.numerator;
				this.denominator = -this.denominator;
			}
		}

		public int getNumerator() {
			return numerator;
		}

		public int getDenominator() {
			return denominator;
		}

		public RationalFraction add(RationalFraction other) {
			int commonDenominator = this.denominator * other.denominator;
			int sumNumerator = this.numerator * other.denominator + other.numerator * this.denominator;

			return new RationalFraction(sumNumerator, commonDenominator);
		}

		public RationalFraction multiply(RationalFraction other) {
			return new RationalFraction(this.numerator * other.numerator, this.denominator * other.denominator);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			RationalFraction other = (RationalFraction) obj;
			return numerator == other.numerator && denominator == other.denominator;
		}

		@Override
		public String toString() {
			if (denominator == 1) {
				return Integer.toString(numerator);
			} else {
				return numerator + "/" + denominator;
			}
		}

		private int gcd(int a, int b) {
			while (b != 0) {
				int temp = b;
				b = a % b;
				a = temp;
			}
			return a;
		}
	}

	public static void main(String[] args) {
		RationalFraction[] coefficients1 = {
				new RationalFraction(1, 1),
				new RationalFraction(2, 1),
				new RationalFraction(3, 1)
		};
		Polynomial polynomial1 = new Polynomial(coefficients1);

		RationalFraction[] coefficients2 = {
				new RationalFraction(1, 1),
				new RationalFraction(-1, 1)
		};
		Polynomial polynomial2 = new Polynomial(coefficients2);

		RationalFraction[] coefficients3 = {
				new RationalFraction(0, 1),
				new RationalFraction(1, 2)
		};
		Polynomial polynomial3 = new Polynomial(coefficients3);

		Polynomial[] polynomials = { polynomial1, polynomial2, polynomial3 };

		System.out.println("Polynomial 1: " + polynomial1);
		System.out.println("Polynomial 2: " + polynomial2);
		System.out.println("Polynomial 3: " + polynomial3);

		Polynomial sum = Polynomial.sumPolynomials(polynomials);
		System.out.println("\nSum of Polynomials: " + sum);
	}
}

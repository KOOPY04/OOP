public class D1149488_HW4 extends MyStack2 {
	// use Character.isDigit() to decide whether the input character is a digit
	// char c1 = 'A', c2 = '4';
	// Character.isDigit(c1)); ==> False
	// Character.isDigit(c2)); ==> True
	// Character.digit(c2, 10); ==> 4

	public int evaluate(char[] input) {

		for (char i : input) {
			if (Character.isDigit(i)) {
				push(Character.digit(i, 10));
			} 
			else {
				int c2 = pop();
				int c1 = pop();
				switch (i) {
					case '+':
						push(c1 + c2);
						break;
					case '-':
						push(c1 - c2);
						break;
					case '*':
						push(c1 * c2);
						break;
					case '/':
						push(c1 / c2);
						break;
				}
			}

		}
		return pop();
	}

	public static void main(String args[]) {
		D1149488_HW4 pe = new D1149488_HW4();

		System.out.println(pe.evaluate("234*+".toCharArray()));
		System.out.println(pe.evaluate("23*4+".toCharArray()));
		System.out.println(pe.evaluate("12+7*".toCharArray()));
		System.out.println(pe.evaluate("68*2/".toCharArray()));
		System.out.println(pe.evaluate("123-4+*51-*3*".toCharArray()));
		System.out.println(pe.evaluate("42/3-4+5*13*-".toCharArray()));
	}
}

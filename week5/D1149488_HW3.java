public class D1149488_HW3 {

    private void Method1(int divisor, int dividend) {
        try {
            System.out.println(divisor / dividend);
        } 
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void Method2(int divisor, int dividend) {
        if (dividend == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        } 
        else {
            System.out.println(divisor / dividend);
        }
    }

    public static void main(String[] args) {
        D1149488_HW3 implement = new D1149488_HW3();

        System.out.println("Case 1¡G 8 / 2");
        System.out.println(" ");
        System.out.println("method 1¡G ");
        implement.Method1(10, 5);
        System.out.println("Method 2¡G ");
        try {
            implement.Method2(8, 2);
        } 
        catch (ArithmeticException e) {
            System.out.println("Arithmetic exception in main¡G " + e.getMessage());
        }

        System.out.println(" ");

        System.out.println("Case 2¡G 8 / 0");
        System.out.println(" ");
        System.out.println("Method 1¡G ");
        implement.Method1(10, 0);
        System.out.println(" ");
        System.out.println("Method 2¡G ");
        try {
            implement.Method2(8, 0);
        } 
        catch (ArithmeticException e) {
            System.out.println("Arithmetic exception in main¡G " + e.getMessage());
        }
    }
}

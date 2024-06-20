public class D1149488_HW2 {
	private int maxCapacity;
	private int[] stack;
	private int top;
	
	public D1149488_HW2() {
		// default capacity is 5
		this(5);
	}
	
	public D1149488_HW2(int capacity) {
		maxCapacity = capacity;
		stack = new int[maxCapacity];
		top = -1;
	}
	
	public boolean isFull() {
		return top == maxCapacity - 1;
	}
	
	public boolean isEmpty() {
		return top == -1;
	}
	
	public void push(int theElement) {
		if (isFull()) 
        {
            System.out.println("The Stack is FULL!!!");
        } 
        else 
        {
            top++;
            stack[top] = theElement;
        }
	}
	
	public void pop() {
		if (isEmpty()) 
        {
            System.out.println("The Stack is EMPTY!!!");
        } 
        else 
        {
            top--;
        }
	}
	
	public int top() {
		if (isEmpty()) 
        {
            System.out.println("The Stack is EMPTY!!!");
            return -1;
        } 
        else 
        {
            return stack[top];
        }
	}
	
	public void popall() {
		while (top() != -1) 
        {
            System.out.println(stack[top]);
            pop();
        }
	}
	
	public static void main(String args[]) {
		
		// the following codes are original testing code
		// make your modification to get them worked
		
		D1149488_HW2 MyStack = new D1149488_HW2();

		MyStack.push(1);
		MyStack.push(2);
		MyStack.push(3);
		System.out.println(MyStack.top());
		MyStack.pop();
		MyStack.push(4);
		MyStack.push(5);
		MyStack.push(6);
		MyStack.push(7);
		MyStack.push(8);
		MyStack.pop();
		MyStack.popall();
		MyStack.pop();
		// Final Output should be like:
		// 3
		// The Stack is FULL!!!
		// The Stack is FULL!!!
		// 5
		// 4
		// 2
		// 1
		// The Stack is EMPTY!!!
		// The Stack is EMPTY!!!
	}

	
	
		
}
class MyStack4 extends MyStack2 {
	
	public MyStack4(int capacity) {
		super(capacity);
	}
	
	public boolean isFull() {
		if(top == maxCapacity - 1) {
			System.out.println("Dynamic capacity enlargement is not implemented yet");
			return true;
		}
		return false;
	}
	
	public int size() {
		return top+1;
	}
}
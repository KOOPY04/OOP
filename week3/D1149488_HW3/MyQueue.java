class MyQueue extends MyStack2{
    MyStack2 stack1;
    MyStack2 stack2;

    int count = 0;
    public MyQueue(){
        this(5);
    }

    public MyQueue(int capacity) {
		maxCapacity = capacity;
		stack1 = new MyStack2(maxCapacity);
        stack2 = new MyStack2(maxCapacity);
	}

    public int size(){
        return stack1.top + 1;
    }

    public boolean isFull() {
		if (stack1.top == maxCapacity - 1) {
            System.out.println("The Queue is FULL!!!");
            return true;
        }
		else{
			return false;
		}
	}
	
	public boolean isEmpty() {
		if(stack1.top > 0) {
			return false;
		}
		System.out.println("The Queue is EMPTY!!!");
		return true;
	}

    public int addQ(int theElement) {
        if(!isFull())
        {
            stack1.push(theElement);
        }
        return 0;
	}

    public int deleteQ() {
		if (isEmpty()) 
        {
			return -1;
        } 
        while (stack1.top != -1) {
            stack2.push(stack1.pop());
        }
        int element = stack2.pop();

        while (stack2.top != -1) {
            stack1.push(stack2.pop());
        }

        return element;
	}

    public void deleteAll(){
        while(!isEmpty()){
            while(stack1.top != -1){
                stack2.push(stack1.pop());
            }
            while(stack2.top != -1){
                System.out.println(stack2.pop());
            } 
        }
        
    }
}
public class D1149488_HW2 {
	public static int value = 3;
	
	private int maxCapacity;
	private int[] queue;
	private int front;
	private int rear;
	
	public D1149488_HW2() {
		// default capacity is 5
		this(5);
	}
	
	public D1149488_HW2(int capacity) {
		maxCapacity = capacity;
		queue = new int[maxCapacity];
		front = -1;
		rear = -1;
	}
	
	public int size()
	{
		return rear - front;
	}

	public boolean isFull() {
		if (rear == maxCapacity - 1) {
            System.out.println("The Queue is FULL!!!");
            return true;
        }
		else{
			return false;
		}
	}
	
	public boolean isEmpty() {
		return rear == front;
	}
	
	public int addQ(int theElement) {
		if(!isFull()) {
			queue[++rear] = theElement;
		}
		return rear;
	}
	
	public int deleteQ() {
		if (isEmpty()) 
        {
            System.out.println("The Queue is EMPTY!!!");
			return -1;
        } 
        else 
        {
			front++;
			int store = queue[front];
			for(int i = 0; i < rear; i++)
			{
				queue[i] = queue[i + 1];
			}
			rear--;
			front--;
            return store;
        }
	}
	
	public int top() {
		if (isEmpty()) 
        {
            System.out.println("The Queue is EMPTY!!!");
            return -1;
        } 
        else 
        {
            return queue[front];
        }
	}
	
	public void deleteAll() {
		while (!isEmpty()) 
        {
			if(rear > front)
			{
				System.out.println(queue[++front]);
			}
        }
		if(isEmpty())
		{
			System.out.println("The Queue is EMPTY!!!");
			front = -1;
			rear = -1;
		}
	}

	public static void main(String args[]) {
		
		// the following codes are original testing code
		// make your modification to get them worked
		
		D1149488_HW2 queue = new D1149488_HW2(5);
		
		queue.addQ(1);
		queue.addQ(2);
		queue.addQ(3);
		System.out.println("size: "+queue.size());
		System.out.println("deleteQ: "+queue.deleteQ());
		queue.addQ(4);
		queue.addQ(5);
		queue.addQ(6);
		queue.addQ(7);
		queue.addQ(8);
		queue.deleteQ();
		queue.deleteAll(); // print all the items in Queue while deletion
		queue.deleteQ();
		// Final Output should be like:
		// size: 3
		// deleteQ: 1
		// The Queue is FULL!!!
		// The Queue is FULL!!!
		// 3
		// 4
		// 5
		// 6
		// The Queue is EMPTY!!!
		// The Queue is EMPTY!!!
	}
}
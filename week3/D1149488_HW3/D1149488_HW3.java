public class D1149488_HW3 extends MyQueue{
	public static int value = 3;
	
	public static void main(String args[]) {
		
		// the following codes are original testing code
		// make your modification to get them worked
		
		MyQueue queue = new MyQueue(5);
		
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
public class D1149488_HW3 {
	public static void main(String args[]) {
		MySearchable s1 = new SequenceSearch();
		// Searchable s2 = new BinarySearchTree();
		for(int i = 0; i < args.length; i++){
			s1.put(Integer.parseInt(args[i]));
		}
		if(s1.exist(1)){
			System.out.println("true");
		}
		else{
			System.out.println("false");
		}
	}
}
class SequenceSearch implements MySearchable {
	int array[];
	int index = 0;
	SequenceSearch() {
		array = new int[32];
	}
	
	public void put(int item) {
		array[index] = item;
		index++;
	}
	
	public boolean exist(int item) {
		for(int i = 1; i < index; i++){
			if(array[i] == array[0]){
				return true;
			}
		}
		return false;
	}
}
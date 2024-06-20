public class BinarySearchTree implements MySearchable {
    private int[] data;
    private int size;
    private int root;
    private int key;

    public BinarySearchTree() {
        data = new int[32];
        size = 0;  
    }

    // 插入一個元素
    public void put(int item) {
        root = 0;
        if(size == 0){
            key = item;
            size++;
        }
        else{
            while(true){
                if(item < data[root]){
                    if(root * 2 >= 32){
                        System.out.println("超出存儲大小限制");
                        break;
                    }
                    if(data[root * 2] == 0){
                        data[root * 2] = item;
                        break;
                    }
                    else{
                        root *= 2;
                    }
                }
                else if(item > data[root]){
                    if(root * 2 + 1 >= 32){
                        System.out.println("超出存儲大小限制");
                        break;
                    }
                    if(data[root * 2 + 1] == 0){
                        data[root * 2 + 1] = item;
                        break;
                    }
                    else{
                        root = root * 2 + 1;
                    }
                }
            }
        }
    }


    // 查詢元素是否存在
    public boolean exist(int item) {
        for(int i = 0; i <= 32 - 1; i++){
            if(key == data[i]){
                return true;
            }
        }
        return false;
    }

}

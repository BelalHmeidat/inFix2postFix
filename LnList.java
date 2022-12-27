public class LnList {
    private LnNode first;
    private LnNode last;
    private int size;

    public LnList() {
        first = last = null;
        size = 0;
    }

    public LnNode getFirst(){
        if (size <= 0){
            return null;
        }
        return first;


    

    
    }

    public LnNode getLast(){
        if (size <= 0){
            return null;
        }
        return last;

    }

    public int getSize(){
        return this.size;
    }

    public void addFirst(Object element){
        if (size == 0){
            this.first = this.last = new LnNode(element);
            this.size = 1;
            return;
        }
        LnNode node = new LnNode(element);
        node.next = this.first;
        this.size++;
        this.first = node;
        return;
    }

    public void addLast (Object elemnet){
//        if (this.size < 0){
//            System.out.println("NO");
//            return;
//        }
//        LnNode node = new LnNode(elemnet);
        if (this.size == 0){
            this.first = this.last = new LnNode(elemnet);
            this.size = 1;
            return;
        }
        // LnNode current = this.first;
        // for (int i = 0; i < size; i++){
        //     current = current.next;
        // }
        // current = node;
        // this.last = current;
        
        this.last.next = new LnNode(elemnet);
        this.last = this.last.next;
        this.size++;
        return;

    }
    public boolean removeFirst(){
        if (size <= 0){
            System.out.println("NO");
            return false;
        }
        first = first.next;
        if (size == 1){
            last = first;
        }
        size--;
        return true;
    }

    public boolean removeLast(){
        if (size <= 0 ){
            System.out.println("NO");
            return false;

        }
        LnNode current = new LnNode();
        current = first;
        for (int i = 0; i < size -1; i++){
            current = current.next;
        }
        if (size == 1){
            first = last = null;
        }
        current.next = null;
        size--;
        return true;
    }
    //Not used
    public void add (Object element, int index){
        if (size <= 0 || index >= size){
            System.out.println("NO");
            return;
        }
        if (index == 0){
            addFirst(element);
            return;
        }
        LnNode current = new LnNode();
        current = first;
        for (int i =0; i < size; i++){
            current = current.next;
            if (i == index){
                break;
            }
        }
        LnNode newNode = new LnNode();
        current.next = newNode;
        newNode.data = element;
        size++;
        return;

    }
    //Not used
    public void remove (int index){
        if (size <= 0 || index >= size){
            System.out.println("NO");
            return;
        }
        if (index == 0){
            removeFirst();
            // size--;
            return;
        }
        if (index == size - 1){
            removeLast();
            // size--;
            return;
        }
        LnNode current = new LnNode();
        current = first;
        for (int i = 0; i < size; i ++){
            current = current.next;
            if (i == index - 1){
                break;
            }
        }
        current.next = current.next.next;
        size--;
        return;

    }
    public static LnList appendList(LnList list, LnList list2){

        list.last.next = list2.first;
        list.last = list2.last;
        list.size += list2.size;
        return list;
    }

    private static void swap(Object value1, Object value2) {
        Object temp = value1;
        value1 = value2;
        value2 = temp;
    }

     public LnList copy(){
        LnList copy = new LnList();
        LnNode current = first;
        for (int i = 0; i < this.size; i++){
            copy.addLast(current.data);
            current = current.next;
        }
        return copy;
    }

    public void print(){
        LnNode current = first;
        for (int i = 0; i < size; i++){
            System.out.println(current.data);
            current = current.next;
        }
    }
}
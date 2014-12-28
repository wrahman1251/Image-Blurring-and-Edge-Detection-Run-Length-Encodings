public class DList {

    private DListNode head;
    private int size;

    public DList() {
        DListNode sentinel = new DListNode(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        head = sentinel;
        size = 0;
    }

    public DList(Object item) {
        DListNode sentinel = new DListNode(null);
        head = sentinel;
        DListNode new_node = new DListNode(item, sentinel, sentinel);
        sentinel.next = new_node;
        sentinel.prev = new_node;
        size = 1;
    }

    public void addToFront(Object item) {
        DListNode new_node = new DListNode(item);
        head.next.prev = new_node;
        new_node.next = head.next;
        new_node.prev = head;
        head.next = new_node;
        size++;
    }

    public void addToEnd(Object item) {
        head.prev.next = new DListNode(item);
        head.prev.next.prev = head.prev;
        head.prev.next.next = head;
        head.prev = head.prev.next;
        size++;
    }

    public int sizeOfDList() {
        return size;
    }

    public String toString() {
        String representation = "[";
        DListNode current = head;
        while (current.next != head) {
            representation += current.next.item.toString();
            if (current.next.next != head) {
                representation += ", ";
            }
            current = current.next;
        }
        representation += "]";
        return representation;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {

        //DList list1 = new DList();
        //DList list2 = new DList("hello");
        //list1.addToFront("are");
        //list1.addToFront("how");
        //list1.addToEnd("you?");
        //System.out.println(list2.toString());
        //System.out.println(list1.toString());
        //System.out.println(list1.size());
        //System.out.println(list2.size());

    }
}
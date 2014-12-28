public class DListNode {

    public Object item;
    public DListNode next;
    public DListNode prev;

    public DListNode(Object item) {
        this(item, null, null);
    }

    public DListNode(Object item, DListNode next, DListNode prev) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }

    public static void main(String[] args) {

    }
}
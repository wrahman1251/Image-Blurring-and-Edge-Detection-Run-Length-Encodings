public class Run {

    public int run_length;
    public int red;
    public int green;
    public int blue;
    public Run next;
    public Run prev;

    public Run(int run_length, int red, int green, int blue) {
        this.run_length = run_length;
        this.red = red;
        this.green = green;
        this.blue = blue;
        next = null;
        prev = null;
    }

    public Run(int run_length, int red, int green, int blue, Run next, Run prev) {
        this(run_length, red, green, blue);
        this.next = next;
        this.prev = prev;
    }

    public static void main(String[] args) {

    }
}
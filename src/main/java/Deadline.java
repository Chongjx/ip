public class Deadline extends Task {
    String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public void setDeadline(String by) {
        this.by = by;
    }

    public String getDeadline() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(By: " + by + ")";
    }
}

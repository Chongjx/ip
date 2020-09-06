package duke.task;

public class Deadline extends Task {
    String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.taskType = "[D]";
    }

    public void setDeadline(String by) {
        this.by = by;
    }

    public String getDeadline() {
        return by;
    }

    @Override
    public String toString() {
        return super.toString() + "(By:" + by + ")";
    }
}

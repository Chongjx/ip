public class Event extends Task {
    String dateTime;

    public Event(String description, String dateTime) {
        super(description);
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(At: " + dateTime + ")";
    }
}

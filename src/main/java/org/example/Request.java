package org.example;


import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Request {
    String body;
    Date date;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    Integer priority;
    public Request(String body, Date date, Integer priority) {
        this.body = body;
        this.date = date;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return dateFormat.format(date) + " - " + this.body;
    }

    public String toStringBirou() {
        return priority + " - " + dateFormat.format(date) + " - " + this.body;
    }

    public String toStringFinalizate() {
        return dateFormat.format(date) + " - " + this.body.split(" ")[1] + " " + this.body.split(" ")[2].split(",")[0];
    }

    public String toStringFinalizateJuridic() {
        return dateFormat.format(date) + " - " + this.body.split(",")[1].split(" ")[5] + " " + this.body.split(",")[1].split(" ")[6];
    }
}

class RequestComparator implements Comparator<Request> {
    @Override
    public int compare(Request o1, Request o2) {
        if (o1.date.compareTo(o2.date) > 0) {
            return 1;
        } else if (o1.date.compareTo(o2.date) < 0) {
            return -1;
        } else
            return o1.priority.compareTo(o2.priority);
    }
}

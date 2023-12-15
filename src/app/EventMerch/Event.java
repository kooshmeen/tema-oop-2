package app.EventMerch;

import fileio.input.DateInput;
import lombok.Getter;

import java.util.Date;

@Getter
public class Event {
    private final String name;
    private final DateInput date;
    private final String description;
    public Event(String name, DateInput date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
    public String getDayWithZero() {
        return date.getDay() < 10 ? "0" + date.getDay() : String.valueOf(date.getDay());
    }
    public String getMonthWithZero() {
        return date.getMonth() < 10 ? "0" + date.getMonth() : String.valueOf(date.getMonth());
    }
}

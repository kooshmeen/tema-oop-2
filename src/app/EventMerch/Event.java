package app.EventMerch;

import fileio.input.DateInput;
import lombok.Getter;

@Getter
public class Event {
    private final String name;
    private final DateInput date;
    private final String description;
    private static final int TEN = 10;
    public Event(final String name, final DateInput date, final String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
    /**
     * Returns the day.
     *
     * @return the day
     */
    public String getDayWithZero() {
        return date.getDay() < TEN ? "0" + date.getDay() : String.valueOf(date.getDay());
    }
    /**
     * Returns the month.
     *
     * @return the month
     */
    public String getMonthWithZero() {
        return date.getMonth() < TEN ? "0" + date.getMonth() : String.valueOf(date.getMonth());
    }
}

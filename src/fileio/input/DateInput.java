package fileio.input;

public class DateInput {
    private Integer day;
    private Integer month;
    private Integer year;
    public DateInput(final String date) {
        String[] dateSplit = date.split("-");
        this.day = Integer.parseInt(dateSplit[0]);
        this.month = Integer.parseInt(dateSplit[1]);
        this.year = Integer.parseInt(dateSplit[2]);
    }
    /**
     * Returns the day.
     *
     * @return the day
     */
    public Integer getDay() {
        return day;
    }
    /**
     * Sets the day.
     *
     * @param day the day to set
     */
    public void setDay(final Integer day) {
        this.day = day;
    }
    /**
     * Returns the month.
     *
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }
    /**
     * Sets the month.
     *
     * @param month the month to set
     */
    public void setMonth(final Integer month) {
        this.month = month;
    }
    /**
     * Returns the year.
     *
     * @return the year
     */
    public Integer getYear() {
        return year;
    }
    /**
     * Sets the year.
     *
     * @param year the year to set
     */
    public void setYear(final Integer year) {
        this.year = year;
    }
}

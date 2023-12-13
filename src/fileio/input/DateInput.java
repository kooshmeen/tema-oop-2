package fileio.input;

public class DateInput {
    private Integer day;
    private Integer month;
    private Integer year;
    public DateInput(String date) {
        String[] dateSplit = date.split("-");
        this.day = Integer.parseInt(dateSplit[0]);
        this.month = Integer.parseInt(dateSplit[1]);
        this.year = Integer.parseInt(dateSplit[2]);
    }
    public Integer getDay() {
        return day;
    }
    public void setDay(final Integer day) {
        this.day = day;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(final Integer month) {
        this.month = month;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(final Integer year) {
        this.year = year;
    }
}

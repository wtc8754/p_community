package Model;

/**
 * Basic class to represent a date
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class date {

    /**
     * numerical month of the given date
     */
    private int month;

    /**
     * day of the month
     */
    private int day;

    /**
     * four-digit year of the date
     */
    private int year;

    /**
     * make a new date
     *
     * @param month numerical month of the given date
     * @param day   day of the month
     * @param year  four-digit year of the date
     */
    public date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * @return the numerical month of the given date
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return day of the month
     */
    public int getDay() {
        return day;
    }

    /**
     * @return four-digit year of the date
     */
    public int getYear() {
        return year;
    }

    /**
     * Check the day, month and year of another date against this one
     *
     * @param o other date
     * @return true iff the month, day, and year are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof date) {
            date oth = (date) o;
            return ((this.month == oth.getMonth()) && (day == oth.getDay()) && (year == oth.getYear()));
        }
        return false;
    }

    /**
     * @return string representation of this date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}

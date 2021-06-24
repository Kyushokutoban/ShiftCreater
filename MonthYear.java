package shiftCreater;

import java.util.Calendar;

public class MonthYear
{
    private int month; // One of Calendar.JANUARY, ..., Calendar.DECEMBER
    private int year;  // The year, such as 2021

    private Calendar now;

    // Day of week the first day of the month lands on.
    // This is one of Calendar.SUNDAY, ..., Calendar.SATURDAY
    private int firstWeekDayOfMonth;

    public MonthYear()
    {
        now = Calendar.getInstance();
        month = now.get(Calendar.MONTH);
        year = now.get(Calendar.YEAR);
        computeFirstWeekDay();
    }

    public MonthYear(int theMonth, int theYear)
    {
        month = (theMonth < Calendar.JANUARY) ? Calendar.JANUARY : Math.min(theMonth, Calendar.DECEMBER);
        year = Math.max(theYear, 1);

        // compute the day of the week that the first day of the month lands on
        computeFirstWeekDay();
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    // Return the day of the week the first of the month lands on.
    public int getFirstWeekDayOfMonth()
    {
        return firstWeekDayOfMonth;
    }

    // Return the day of the week of the given day of the month.
    public int getDayOfWeek(int dayOfMonth)
    {
        // Compute the day of the week that the dayOfMonth
        //       parameter lands on.
        int whichDay = (dayOfMonth + getFirstWeekDayOfMonth() - 1) % (Calendar.SATURDAY - Calendar.SUNDAY + 1);
        if (whichDay == 0) return Calendar.SATURDAY;
        else return whichDay;
    }

    private static boolean isLeapYear(int year)
    {
        if (year%4==0 & year%100!=0 || year%400==0) return true;
        else return false;
    }

    public boolean isLeapYear()
    {
        return isLeapYear(year);
    }

    private static int numDaysInMonth(int theMonth, int theYear)
    {
        switch (theMonth)
        {
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return isLeapYear(theYear) ? 29 : 28;
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
            default:
                return 31;
        }
    }

    public int numDaysInMonth()
    {
        return numDaysInMonth(month, year);
    }

    private static int numDaysInYear(int theYear)
    {
        // We could calculate this by the number of days in
        // each month of the year, but this way is simpler
        // and more efficient.
        return isLeapYear(theYear) ? 366 : 365;
    }

    // Return the number of days in the current year.
    public int numDaysInYear()
    {
        return numDaysInYear(year);
    }

    // compute the day of the week the first day of the month lands on
    private void computeFirstWeekDay()
    {
        // We will compute the day of the week from January 1, 1970
        final int EPOCH_YEAR = 1970;
        long dayCount = Calendar.THURSDAY; // January 1, 1970 is on a Thursday

        if (year >= EPOCH_YEAR)
        {
            // add number of days in each year since 1970 to dayCount
            for (int y = EPOCH_YEAR; y < year; y++)
            {
                dayCount += numDaysInYear(y);
            }
        }
        else
        {
            // subtract number of days in each year since 1970 to dayCount
            for (int y = EPOCH_YEAR - 1; y >= year; y--)
            {
                dayCount -= numDaysInYear(y);
            }
        }

        // add number of days in each previous month of the year
        for (int i = Calendar.JANUARY; i < month; i++)
        {
            dayCount += numDaysInMonth(i, year);
        }

        // Mod dayCount by the number of days in a week. This is the
        // day of the week that the first day of the month lands on.
        firstWeekDayOfMonth = (int)modDayOfWeek(dayCount);
    }

    // Compute n mod m, handling negative numbers correctly.
    private static long mod(long n, long m)
    {
        long r = n % m;
        return (r < 0) ? m + r : r;
    }

    // Compute the representative day of week value of the
    // equivalence class that the value n is in. Here the
    // equivalence relation a ~ b is given by
    // a = b (modulo (number of days in week)).
    // In other words: compute the day of week the value n
    // corresponds to in the range [SUNDAY,SATURDAY].
    private static long modDayOfWeek(long n)
    {
        return mod(n - Calendar.SUNDAY, Calendar.SATURDAY - Calendar.SUNDAY + 1) + Calendar.SUNDAY;
    }

    // move to the next month
    public void nextMonth()
    {
        if (year == Integer.MAX_VALUE && month == Calendar.DECEMBER) return;

        int numDaysInOldMonth = numDaysInMonth();

        if (month == Calendar.DECEMBER)
        {
            year++;
            month = Calendar.JANUARY;
        }
        else
        {
            month++;
        }

        firstWeekDayOfMonth =
                (int)modDayOfWeek(firstWeekDayOfMonth + numDaysInOldMonth);
    }

    // move to previous month
    public void previousMonth()
    {
        if (year <= 1 && month == Calendar.JANUARY) return;

        if (month == Calendar.JANUARY)
        {
            year--;
            month = Calendar.DECEMBER;
        }
        else
        {
            month--;
        }

        firstWeekDayOfMonth =
                (int)modDayOfWeek(firstWeekDayOfMonth - numDaysInMonth());
    }

    // move to next year
    public void nextYear()
    {
        if (year == Integer.MAX_VALUE) return;

        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++)
        {
            nextMonth();
        }
    }

    // move to previous year
    public void previousYear()
    {
        if (year <= 1) return;

        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++)
        {
            previousMonth();
        }
    }
}

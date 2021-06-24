package shiftCreater;

public class Members
{
    private String name;
    private int startTime;
    private int endTime;
    private double howLong;

    Members(String theName)
    {
        this.name = theName;
    }

    public void setShift(int sTime, int eTime)
    {
        this.startTime = sTime;
        this.endTime = eTime;
        this.howLong = eTime - sTime;
    }

    public void setName(String theName)
    {
        this.name = theName;
    }

    public String getName() { return name; }

    public int getStartTime() { return startTime; }

    public int getEndTime() { return endTime; }

    public double getHowLong() { return howLong; }

    public int calculateSalary(int OOPerHour) { return (int) (howLong * OOPerHour); }
}

package shiftCreater;

public class ShiftData
{
    private int requiredNum;
    private boolean[] isFilled;
    private int actualNum;

    public ShiftData(int theRequiredNum)
    {
        this.requiredNum = theRequiredNum;
        isFilled = new boolean[requiredNum];
        actualNum = 0;
        for(int i = 0; i < requiredNum; i++) {
            isFilled[i] = false;
        }
    }

    public int getRequiredNum() { return requiredNum; }

    public void setRequiredNum(int theNum) { this.requiredNum = theNum; }

    public int getActualNum() { return actualNum; }

    public boolean[] getIsFilled() { return isFilled; }

    public boolean writeShift()
    {
        if (actualNum <= requiredNum) {
            for(int j = 0; j < isFilled.length; j++)
            {
                if(isFilled[j] == false) {
                    isFilled[j] = true;
                    actualNum++;
                    return true;
                }
            }
        }
        return false;
    }
}

package shiftCreater;

import java.util.ArrayList;

public class Shift
{
    private class shiftManager
    {
        private ArrayList<ShiftData> shiftData;
        private ArrayList<Members> members;

        shiftManager()
        {
            shiftData = new ArrayList<ShiftData>();
            members = new ArrayList<Members>();
        }

        public ArrayList<ShiftData> getShiftData() { return shiftData; }
        public ArrayList<Members> getMembers() { return members; }
    }

    private int numOfMembers = 0;

    private shiftManager shiftStruct = new shiftManager();

    public Shift()
    {
        for(int i = 0; i < 18; i++)
        {
            shiftStruct.shiftData.add(i, new ShiftData(3));
        }
    }

    public Shift(int[] requiredNumOfPeople)
    {
        for(int i = 0; i < requiredNumOfPeople.length; i++) {
            shiftStruct.shiftData.add(i, new ShiftData(requiredNumOfPeople[i]));
        }
    }

    public void addShiftData()
    {
        shiftStruct.shiftData.add(new ShiftData(3));
    }

    public void setNumOfRequiredWorkers(int theNum, int whatTime)
    {
        whatTime-=6;
        shiftStruct.shiftData.get(whatTime).setRequiredNum(theNum);
    }

    public int isMember(String theName)
    {
        for(int i = 0; i < shiftStruct.members.size(); i++) {
            if (shiftStruct.members.get(i).getName() == theName) {
                return i;
            }
        }
        return 0;
    }

    public void addMembers(String theName)
    {
        if (isMember(theName) == 0) {
            numOfMembers++;
            shiftStruct.members.add(new Members(theName));
        }
    }

    public void removeMembers(String theName)
    {
        if (isMember(theName) != 0) {
            shiftStruct.members.remove(theName);
        }
    }

    public void rename(String theName)
    {
        if (isMember(theName) != 0)
            shiftStruct.members.get(isMember(theName)).setName(theName);
    }

    public boolean submitShift(String theName, int startTime, int endTime)
    {
        startTime-=6;
        endTime-=6;
        if (startTime < 0 || 22 < startTime || endTime < 7 || endTime < 24)
            return false;
        else if (isMember(theName) == 0)
            return false;
        else {
            int time = endTime - startTime;
            int[] startEnd = {0, 0};
            for(int i = startTime; i < time; i++) {
                if (startEnd[0] == 0 && shiftStruct.shiftData.get(i).writeShift()) {
                    shiftStruct.shiftData.get(i).writeShift();
                    startEnd[0] = startTime + 6;
                }
                if(startEnd[0] != 0 && shiftStruct.shiftData.get(i).writeShift()) {
                    shiftStruct.shiftData.get(i).writeShift();
                    startEnd[1] = startTime + 6;
                }
            }

            shiftStruct.members.get(isMember(theName)).setShift(startEnd[0], startEnd[1]);
            return true;
        }
    }

}
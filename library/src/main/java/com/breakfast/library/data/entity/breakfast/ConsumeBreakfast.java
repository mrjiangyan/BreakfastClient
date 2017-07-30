package com.breakfast.library.data.entity.breakfast;

/**
 * Created by admin on 2017/7/30.
 */

public class ConsumeBreakfast {



    public int getbreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastTotalCount(int value) {
        breakfastCount = value;
    }

    public String getroomNumber() {
        return roomNumber;
    }

    public void setBreakfastUseCount(String value) {
        roomNumber = value;
    }

    private  int breakfastCount;

    private  String roomNumber;

}

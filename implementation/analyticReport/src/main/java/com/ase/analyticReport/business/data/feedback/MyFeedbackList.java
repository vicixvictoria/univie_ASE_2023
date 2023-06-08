package com.ase.analyticReport.business.data.feedback;

import java.util.ArrayList;
import java.util.Collection;

public class MyFeedbackList {

    private final Collection<MyFeedback> myFeedbacks;

    public MyFeedbackList() {
        myFeedbacks = new ArrayList<>();
    }

    public MyFeedbackList(Collection<MyFeedback> myFeedbacks) {

        if (myFeedbacks == null) {
            this.myFeedbacks = new ArrayList<>();
            return;
        }
        this.myFeedbacks = myFeedbacks;
    }

    public MyFeedbackList(MyFeedbackList myFeedbackList) {
        this.myFeedbacks = myFeedbackList.getFeedbacks();
    }

    public Collection<MyFeedback> getFeedbacks() {
        return this.myFeedbacks;
    }

    public void add(MyFeedback myFeedback) {
        myFeedbacks.add(myFeedback);
    }

    public void add(MyFeedbackList myFeedbackList) {
        for (MyFeedback elem : myFeedbackList.getFeedbacks()) {
            this.add(elem);
        }
    }

    public void add(Collection<MyFeedback> myFeedbackList) {
        for (MyFeedback elem : myFeedbackList) {
            this.add(elem);
        }
    }

    public int size() {
        return myFeedbacks.size();
    }

    public float getMeanRatingOverall() {
        float total = 0;
        for (MyFeedback myFeedback : this.myFeedbacks) {
            total += myFeedback.getRatingOverall();
        }
        return total / this.size();
    }
    public float getMeanFoodRating() {
        float total = 0;
        for (MyFeedback myFeedback : this.myFeedbacks) {
            total += myFeedback.getRatingFood();
        }
        return total / this.size();
    }

    public float getMeanLocationRating() {
        float total = 0;
        for (MyFeedback myFeedback : this.myFeedbacks) {
            total += myFeedback.getRatingLocation();
        }
        return total / this.size();
    }

}

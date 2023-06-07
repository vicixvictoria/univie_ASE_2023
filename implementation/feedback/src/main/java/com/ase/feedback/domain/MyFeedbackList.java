package com.ase.feedback.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyFeedbackList {

    private final List<MyFeedback> myFeedbacks;

    public MyFeedbackList() {
        myFeedbacks = new ArrayList<MyFeedback>();
    }

    public MyFeedbackList(List<MyFeedback> myFeedbacks) {
        this.myFeedbacks = myFeedbacks;
    }

    public MyFeedbackList(MyFeedbackList myFeedbackList) {
        this.myFeedbacks = myFeedbackList.getFeedbacks();
    }

    public List<MyFeedback> getFeedbacks() {
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
}

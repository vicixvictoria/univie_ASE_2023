package com.example.ase_project.feedback.model.data;

import java.util.ArrayList;
import java.util.Collection;

public class FeedbackList {
    private final Collection<Feedback> feedbacks;

    public FeedbackList() {
        feedbacks = new ArrayList<Feedback>();
    }

    public FeedbackList(Collection<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public FeedbackList(FeedbackList feedbackList) { this.feedbacks = feedbackList.get();}

    public Collection<Feedback> get() {
        return this.feedbacks;
    }

    public void add(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public void add (FeedbackList feedbackList) {
        for (Feedback elem: feedbackList.get()) {
            this.add(elem);
        }
    }

    public void add (Collection<Feedback> feedbackList) {
        for (Feedback elem: feedbackList) {
            this.add(elem);
        }
    }
}

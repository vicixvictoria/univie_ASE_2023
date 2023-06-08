package com.ase.analyticReport.integration;

import com.ase.analyticReport.business.data.event.EMyEventTypes;
import com.ase.analyticReport.business.data.event.MyEvent;
import com.ase.analyticReport.business.data.feedback.MyFeedback;
import com.ase.analyticReport.business.data.feedback.MyFeedbackList;
import com.ase.common.event.EEventType;
import com.ase.common.event.Event;
import com.ase.common.feedback.Feedback;
import com.ase.common.feedback.FeedbackList;
import java.util.ArrayList;

public class Converter {
    public MyEvent networkEventToMyEvent(Event event) {
        if (event == null) return null;
        return new MyEvent(event.eventID(), networkEventTypeToMyEventType(event.type()), event.capacity(), event.date(), event.description(),
                event.organizerID(), event.eventName());
    }

    private EMyEventTypes networkEventTypeToMyEventType(EEventType type) {
        return switch (type) {
            case ENTERTAINMENT -> EMyEventTypes.ENTERTAINMENT;
            case FOOD -> EMyEventTypes.FOOD;
            case HEALTH -> EMyEventTypes.HEALTH;
        };
    }

    public MyFeedback networkFeedbackToMyFeedback(Feedback feedback) {
        if (feedback == null) return null;
        return new MyFeedback(feedback.eventID(), feedback.userID(), feedback.ratingOverall(), feedback.ratingFood(), feedback.ratingLocation(), feedback.comment());
    }

    public MyFeedbackList networkFeedbackListToMyFeedbackList(FeedbackList feedbackList) {
        ArrayList<MyFeedback> list = new ArrayList<>();
        for (int i = 0; i < feedbackList.feedbacks().size(); i++) {
            list.add(networkFeedbackToMyFeedback(feedbackList.feedbacks().get(i)));
        }
        return new MyFeedbackList(list);
    }

}

package com.ase.feedback.integration;
import com.ase.common.feedback.Feedback;
import com.ase.common.feedback.FeedbackList;
import com.ase.feedback.domain.MyFeedback;
import com.ase.feedback.domain.MyFeedbackList;
import java.util.ArrayList;


public class Converter {

    public Feedback feedbackToNetworkFeedback(MyFeedback myFeedback) {
        return new Feedback(myFeedback.getFeedbackID(), myFeedback.getEventID(), myFeedback.getUserID(), myFeedback.getRatingOverall(), myFeedback.getRatingLocation(), myFeedback.getRatingFood(), myFeedback.getComment());
    }

    public FeedbackList feedbackListToNetworkFeedbackList(MyFeedbackList myFeedbackList) {
        ArrayList<Feedback> list = new ArrayList<>();
        for (int i = 0; i < myFeedbackList.getFeedbacks().size(); i++) {
            list.add(feedbackToNetworkFeedback(myFeedbackList.getFeedbacks().get(i)));
        }
        return new FeedbackList(list);
    }

    public MyFeedback networkFeedbackToMyFeedback(Feedback feedback) {
        return new MyFeedback(feedback.eventID(), feedback.userID(), feedback.ratingOverall(), feedback.ratingLocation(), feedback.ratingFood(), feedback.comment());
    }

    public MyFeedbackList networkFeedbackListToMyFeedbackList(FeedbackList feedbackList) {
        ArrayList<MyFeedback> list = new ArrayList<>();
        for (int i = 0; i < feedbackList.feedbacks().size(); i++) {
            list.add(networkFeedbackToMyFeedback(feedbackList.feedbacks().get(i)));
        }
        return new MyFeedbackList(list);
    }
}

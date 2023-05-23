package com.ase.feedback.network;
import com.ase.common.feedback.FeedbackMessage;
import com.ase.feedback.model.data.Feedback;


public class Converter {

    public FeedbackMessage getNetworkFeedback(Feedback feedback) {
        return new FeedbackMessage(feedback.getFeedbackID(), feedback.getEventID(), feedback.getUserID(), feedback.getRatingOverall(), feedback.getRatingLocation(), feedback.getRatingFood(), feedback.getComment());
    }

}

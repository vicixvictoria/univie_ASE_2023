package com.ase.common.feedback;

import java.io.Serializable;
import java.util.List;

public record FeedbackList(List<Feedback> feedbacks) implements Serializable {

}

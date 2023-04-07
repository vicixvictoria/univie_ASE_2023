package com.example.ase_project.Feedback.model.data;

public class FeedbackContent {
    private int rating;
    private String comment;
    private final int ratingLowerBound = 0;
    private final int ratingUpperBound = 5;

    public FeedbackContent(int rating, String comment){
        this.setRating(rating);
        this.setComment(comment);
    }

    public FeedbackContent(FeedbackContent feedbackContent) {
        if (feedbackContent == null) {
            throw new IllegalArgumentException("FeedbackContent can not be Null!");
        }
        this.setRating(feedbackContent.getRating());
        this.setComment(feedbackContent.getComment());
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    private void setRating(int rating){
        if (this.validateRating(rating)) {
            this.rating = rating;
            return;
        }
        String exceptionMessage = "rating value has to be between " + this.ratingLowerBound + " and " + this.ratingUpperBound;
        throw new IllegalArgumentException(exceptionMessage);
    }

    private void setComment(String comment) {
        if (this.validateComment(comment)) {
            this.comment = comment;
            return;
        }
        throw new IllegalArgumentException("Comment failed the validity check.");
    }

    public boolean validateRating(int rating) {
        return 0 <= rating && rating <= 5;
    }

    public boolean validateComment(String comment) {
        return true;
    }

    public String getDisplayData() {
        return "display data for feedback";
    }

    @Override
    public String toString() {
        return Integer.toString(this.rating) + ": " + this.comment;
    }

}

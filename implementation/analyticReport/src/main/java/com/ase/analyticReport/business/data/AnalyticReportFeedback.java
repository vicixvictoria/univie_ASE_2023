package com.ase.analyticReport.business.data;

public record AnalyticReportFeedback(String eventID, int numberOfFeedbacks, float ratingOverall, float ratingFood, float ratingLocation) {
}

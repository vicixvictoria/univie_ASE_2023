package com.ase.analyticReport;

import com.ase.analyticReport.business.AnalyticReportService;
import com.ase.analyticReport.business.data.AnalyticReportEvent;
import com.ase.analyticReport.business.data.AnalyticReportFeedback;
import com.ase.analyticReport.business.data.feedback.MyFeedback;
import com.ase.analyticReport.business.data.feedback.MyFeedbackList;
import com.ase.analyticReport.integration.RestAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AnalyticReportUnitTests {
    RestAccess restAccess;
    AnalyticReportService analyticReportService;

    @BeforeEach
    public void setup() {
        MyFeedbackList feedbackList = new MyFeedbackList();
        feedbackList.add(new MyFeedback("100", "101", 1,1,1, "test"));

        restAccess = Mockito.mock(RestAccess.class);
        Mockito.when(restAccess.getCapacity(any(String.class))).thenReturn(10);
        Mockito.when(restAccess.getFeedbackList(any(String.class))).thenReturn(feedbackList);
        Mockito.when(restAccess.getNumberOfAttending(any(String.class))).thenReturn(8);

        analyticReportService = new AnalyticReportService(restAccess);
    }
    @Test
    public void getAnalyticReportFeedback_validCall() {

        ResponseEntity<AnalyticReportFeedback> response = analyticReportService.getAnalyticReportFeedback("100");
        AnalyticReportFeedback report = response.getBody();
        int status = response.getStatusCode().value();
        Assertions.assertNotNull(report);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), HttpStatusCode.valueOf(status));

    }
    @Test
    public void getAnalyticReportFeedback_eventIdIsNull() {

        ResponseEntity<AnalyticReportFeedback> response = analyticReportService.getAnalyticReportFeedback(null);
        AnalyticReportFeedback report = response.getBody();
        int status = response.getStatusCode().value();
        Assertions.assertNull(report);
        Assertions.assertEquals(HttpStatusCode.valueOf(400), HttpStatusCode.valueOf(status));

    }
    @Test
    public void getAnalyticReportEvent_validCall() {

        ResponseEntity<AnalyticReportEvent> response = analyticReportService.getAnalyticReportEvent("100");
        AnalyticReportEvent report = response.getBody();
        int status = response.getStatusCode().value();
        Assertions.assertNotNull(report);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), HttpStatusCode.valueOf(status));
    }

    @Test
    public void getAnalyticReportEvent_eventIdIsNull() {

        ResponseEntity<AnalyticReportEvent> response = analyticReportService.getAnalyticReportEvent(null);
        AnalyticReportEvent report = response.getBody();
        int status = response.getStatusCode().value();
        Assertions.assertNull(report);
        Assertions.assertEquals(HttpStatusCode.valueOf(400), HttpStatusCode.valueOf(status));

    }

}

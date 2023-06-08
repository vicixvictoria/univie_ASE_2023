import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

import {AnalyticReportService} from "../../services/analyticReport.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Feedback} from "../../dtos/feedback";
import {EventService} from "../../services/event.service";
import {AlertService} from "../../services/alert.service";
import {AccountService} from "../../services/account.service";
import {FeedbackService} from "../../services/feedbackService";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {AnalyticReportEvent} from "../../dtos/analyticReportEvent";


@Component({
  selector: 'app-analyticReport',
  templateUrl: './analyticReportEvent.component.html'
})
export class AnalyticReportEventComponent implements OnInit {
  // @ts-ignore
  analyticForm: FormGroup;
  // @ts-ignore
  analytic: AnalyticReportEvent;
  displayedColumns: string[] = ['Capacity','NumberofAttending'];

  // @ts-ignore
  capacity:number;

  // @ts-ignore
  numberOf:number;

  submitted: boolean = false;
  // @ts-ignore
  eventID: string;

  // @ts-ignore
  userID: string;

  // @ts-ignore
  ratingOverall: number;
// @ts-ignore
  ratingFood: number;
// @ts-ignore
  ratingLocation: number;

  constructor(private eventService: EventService,
              private alertService: AlertService,
              private accountService: AccountService,
              private analyticService: AnalyticReportService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              @Inject(MAT_DIALOG_DATA)
              private data: any) {

    this.eventID = data.eventID;

    this.analyticForm = this.formBuilder.group({
      comment: [],
      ratingFood: [],
      ratingLocation: [],
      ratingOverall: [],
    });
  }


  ngOnInit(): void{
    this.loadAnalyticReport(this.eventID);

  }

  loadAnalyticReport(eventID:string){
    this.analyticService.getAnalyticReportEvent(eventID).subscribe({
      next: data => {
        console.log('received analytic report', data);
        this.analytic = data;

        console.log(this.analytic);
      },
      error: error => {
        console.warn(error);
      }
    });
    this.capacity = this.analytic.capacity;
    this.numberOf = this.analytic.numberOfAttending;
  }

  goBack() {
  }

}

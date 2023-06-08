import {Component, Inject, OnInit} from "@angular/core";
import {FormBuilder, FormGroup} from "@angular/forms";
import {EventType} from "../../gloabl/eventType";
import {EventService} from "../../services/event.service";
import {AlertService} from "../../services/alert.service";
import {AccountService} from "../../services/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {TaggingEvent} from "../../dtos/taggingEvent";
import {TaggingTypes} from "../../gloabl/taggingTypes";
import {Feedback} from "../../dtos/feedback";
import {FeedbackService} from "../../services/feedbackService";

@Component({
  selector: 'app-showFeedback',
  templateUrl: './showFeedback.component.html',
})
export class ShowFeedbackComponent implements OnInit {

  // @ts-ignore
  showFeedbackForm: FormGroup;
  // @ts-ignore
  feedbacks: Feedback[];
  displayedColumns: string[] = ['rating overall', 'rating food', 'rating location', 'comment'];

  submitted: boolean = false;

  created: boolean = false;
  private validated = false;
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
              private feedbackService: FeedbackService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              @Inject(MAT_DIALOG_DATA)
              private data: any) {

    this.eventID = data.eventID;

    this.showFeedbackForm = this.formBuilder.group({
      comment: [],
      ratingFood: [],
      ratingLocation: [],
      ratingOverall: [],
    });
  }


  goBack() {
  }

  ngOnInit(): void {
    this.loadAllFeedback(this.eventID);
  }

  public loadAllFeedback(eventID: string) {
    this.feedbackService.getAllFeedbacksByEventId(eventID).subscribe({
      next: data => {
        console.log('received feedbacks', data);
        this.feedbacks = data;

        console.log(this.feedbacks);
      },
      error: error => {
        console.warn(error);
      }
    });
  }

}

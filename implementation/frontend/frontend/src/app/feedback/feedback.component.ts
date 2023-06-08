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
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
})
export class FeedbackComponent implements OnInit {

  // @ts-ignore
  feedbackForm: FormGroup;
  submitted: boolean = false;

  created: boolean = false;
  private validated = false;
  // @ts-ignore
  eventID: string;

  // @ts-ignore
  userID: string;

  // @ts-ignore
  ratingOverall: number;

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

    this.feedbackForm = this.formBuilder.group({
      comment: [],
      ratingFood: [],
      ratingLocation: [],
      ratingOverall: [],
    });
  }


  submit() {
    this.validated = true;
    if (this.feedbackForm.invalid) {
      return;
    }
    this.submitted = true;

    /*if(this.feedbackForm.controls.ratingOverall.value == '1'){
      this.ratingOverall = 1
    }
    if(this.feedbackForm.controls.ratingOverall.value == '2'){
      this.ratingOverall = 2
    }
    if(this.feedbackForm.controls.ratingOverall.value == '3'){
      this.ratingOverall = 3
    }
    if(this.feedbackForm.controls.ratingOverall.value == '3'){
      this.ratingOverall = 3
    }

    if(this.feedbackForm.controls.ratingFoodl.value == '1'){
      this.ratingOverall = 1
    }*/

    // @ts-ignore
    this.userID = this.accountService.userValue?.id

    const feedback:Feedback = new Feedback('',this.eventID, this.userID,
      this.feedbackForm.controls.ratingOverall.value, this.feedbackForm.controls.ratingLocation.value,
      this.feedbackForm.controls.ratingFood.value, this.feedbackForm.controls.comment.value)
    console.log('Feedback with values: ', feedback);

    this.feedbackService.createFeedback(feedback).subscribe(a => {
        this.created = true;
        this.router.navigate(['/searchService']);
      }, error =>{
        console.warn("cannot be saved");
      }
    );
  }

  goBack() {
    //this.router.navigate(['../eventInventory']);
  }

  getValidatingClass(control: string): object {
    if (!this.feedbackForm.controls[control]) {
      console.warn('cant validate ' + control + ' because its not present.');
      return {'is-invalid': true};
    }
    return {
      'is-valid': this.validated && !this.feedbackForm.controls[control].errors,
      'is-invalid': this.validated && this.feedbackForm.controls[control].errors
    };
  }

  ngOnInit(): void {
  }

}

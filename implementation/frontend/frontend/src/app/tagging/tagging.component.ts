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

@Component({
  selector: 'app-tagging',
  templateUrl: './tagging.component.html',
})
export class TaggingComponent implements OnInit {

  // @ts-ignore
  eventForm: FormGroup;
  submitted: boolean = false;

  created: boolean = false;
  private validated = false;
  // @ts-ignore
  taggingType: TaggingTypes;
  eventUpdate: string;

  // @ts-ignore
  userID: string;

  constructor(private eventService: EventService,
              private alertService: AlertService,
              private accountService: AccountService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              @Inject(MAT_DIALOG_DATA)
              private data: any) {

    this.eventUpdate = data.eventID;

  this.eventForm = this.formBuilder.group({
    eventType: [],
  });
  }


  submit() {
    this.validated = true;
    if (this.eventForm.invalid) {
      return;
    }
    this.submitted = true;
    if (this.eventForm.controls.eventType.value == 'FOOD') {
      this.taggingType = TaggingTypes.FOOD
    }
    if (this.eventForm.controls.eventType.value == 'HEALTH') {
      this.taggingType = TaggingTypes.HEALTH
    }
    if (this.eventForm.controls.eventType.value == 'ENTERTAINMENT') {
      this.taggingType = TaggingTypes.ENTERTAINMENT
    }

    if (this.eventForm.controls.eventType.value == 'EDUCATION') {
      this.taggingType = TaggingTypes.EDUCATION
    }

    //TODO: Alex rest of your code --> call your service
  }

  goBack() {
    //this.router.navigate(['../eventInventory']);
  }

  ngOnInit(): void {
  }

}

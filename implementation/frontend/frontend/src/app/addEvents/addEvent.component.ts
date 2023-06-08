import {Component, OnInit} from '@angular/core';
import {EventService} from "../../services/event.service";
//import { MatTableModule } from '@angular/material';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Event} from "../../dtos/event";
import {formatDate, Location} from "@angular/common";
import {AlertService} from "../../services/alert.service";
import {EventType} from "../../gloabl/eventType";
import {User} from "../models/user";
import {AccountService} from "../../services/account.service";


@Component({
  selector: 'app-addEvent',
  templateUrl: './addEvent.component.html',
  styleUrls: ['./addEvent.component.scss']
})
export class AddEventComponent implements OnInit {

  eventForm: FormGroup;
  submitted: boolean = false;

  created: boolean = false;
  private validated = false;
  // @ts-ignore
  eventType_: EventType;

  // @ts-ignore
  userID: string;

  constructor(private eventService: EventService,
              private alertService: AlertService,
              private accountService: AccountService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location, ) {
    this.eventForm = this.formBuilder.group({
      eventName: ['', Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(255)])],
      date: [formatDate(new Date(), 'yyyy-MM-dd', 'en-US'), Validators.required],
      description: [null, Validators.compose([Validators.minLength(2), Validators.maxLength(255)])],
      capacity: [],
      eventType: [],
    });
  }

  ngOnInit() {
  }

  submit() {
    this.validated = true;
    if (this.eventForm.invalid) {
      return;
    }
    this.submitted = true;
    if(this.eventForm.controls.eventType.value == 'FOOD'){
      this.eventType_ = EventType.FOOD
    }
    if(this.eventForm.controls.eventType.value == 'HEALTH'){
      this.eventType_ = EventType.HEALTH
    }
    if(this.eventForm.controls.eventType.value == 'ENTERTAINMENT'){
      this.eventType_ = EventType.ENTERTAINMENT
    }

    // @ts-ignore
    this.userID = this.accountService.userValue?.id

    const event: Event = new Event('', this.eventForm.controls.eventName.value,
      this.eventForm.controls.date.value,
      this.eventForm.controls.description.value,
      this.eventForm.controls.capacity.value,
      this.eventType_,
      this.userID);
    console.log('Event with values: ', event);

    this.eventService.createEvent(event).subscribe(a => {
      this.created = true;
      this.router.navigate(['/eventInventory']);
    }, error =>{
    this.alertService.error("cannot be saved");
  }
);
}



  getValidatingClass(control: string): object {
    if (!this.eventForm.controls[control]) {
      console.warn('cant validate ' + control + ' because its not present.');
      return {'is-invalid': true};
    }
    return {
      'is-valid': this.validated && !this.eventForm.controls[control].errors,
      'is-invalid': this.validated && this.eventForm.controls[control].errors
    };
  }


  goBack() {
    //this.router.navigate(['../eventInventory']);
  }
}

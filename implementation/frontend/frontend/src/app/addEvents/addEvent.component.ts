import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
//import { MatTableModule } from '@angular/material';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Event} from "../../dtos/event";
import {formatDate} from "@angular/common";
import {AlertService} from "../../services/alert.service";
import { Location } from '@angular/common';


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

  constructor(private eventService: EventService,
              private alertService: AlertService,
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

    const event: Event = new Event('', this.eventForm.controls.eventName.value,
      this.eventForm.controls.date.value,
      this.eventForm.controls.description.value,
      this.eventForm.controls.capacity.value,
      this.eventForm.controls.eventType.value,
      "ABC");
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

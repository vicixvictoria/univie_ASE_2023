import {Component, Injectable, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {SearchServiceService} from "../../services/searchService.service";
import {BookmarkService} from "../../services/bookmark.service";
import {User} from "../models/user";
import {AccountService} from "../../services/account.service";
import {UpdateEventComponent} from "../updateEvents/updateEvent.component";
import {TaggingComponent} from "../tagging/tagging.component";
import {formatDate} from "@angular/common";


//import { MatTableModule } from '@angular/material';


@Component({
  selector: 'app-searchService',
  templateUrl: './searchService.component.html',
  styleUrls: ['./searchService.component.css'],
})
export class SearchServiceComponent implements OnInit {

  error = false;
  errorMessage = '';
  // @ts-ignore

  searchForm: FormGroup;
  private validated = false;

  event: Event | undefined;
  // @ts-ignore
  events: Event[];
  // @ts-ignore
  userID : string
  displayedColumns: string[] = ['name', 'date', 'description', 'capacity', 'eventType', 'action'];



  constructor(
    private readonly dialog: MatDialog,
    private dialogRef: MatDialogRef<SearchServiceComponent>,
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private searchService: SearchServiceService,
    private bookmarkService: BookmarkService,
    private accountService: AccountService,
    //private user: User,
  ) {

    // @ts-ignore
    this.event = new Event();

    this.searchForm  = this.formBuilder.group({
      eventName: ['', Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(255)])],
      capacity: [],
      date: [formatDate(new Date(), 'yyyy-MM-dd', 'en-US'), Validators.required],
      description: [],
    });
  }

  ngOnInit(): void {
    this.loadAllEvents();
    // @ts-ignore
    this.userID = this.getUserValue().id;
  }

  public getUserValue() {
    return this.accountService.userValue;
  }

  bookmark(event: Event){
    this.bookmarkService.bookmarkEvent(event, this.userID);
  }

  unbookmark(event: Event){
    this.bookmarkService.unbookmarkEvent(event, this.userID);
  }

  tag(event: Event){
    const dialog = this.dialog.open(TaggingComponent, {
      data: {
        eventID: event.eventID
      },
      width: '1500px'});
    dialog.afterClosed().subscribe(() => {
      this.loadAllEvents()
    });
  }

  register(event: Event){

  }

  feedback(event: Event){

  }


  public loadAllEvents() {
    this.searchService.getAllEvents().subscribe({
      next: data => {
        console.log('received events', data);
        this.events = data;

        console.log(this.events);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
  }

  public searchByName(){
    this.searchService.getEventsByName(this.searchForm.controls.eventName.value).subscribe({
      next: data => {
        console.log('received events', data);
        this.events = data;

        console.log(this.events);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
  }

  public searchByDate(){
    this.searchService.getEventsByDate(this.searchForm.controls.date.value).subscribe({
      next: data => {
        console.log('received events', data);
        this.events = data;

        console.log(this.events);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
  }

  public searchByCapacity(){
    this.searchService.getEventsByCapacity(this.searchForm.controls.capacity.value).subscribe({
      next: data => {
        console.log('received events', data);
        this.events = data;
        console.log(this.events);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
  }

  public searchByDes(){
    this.searchService.getEventsByDes(this.searchForm.controls.capacity.value).subscribe({
      next: data => {
        console.log('received events', data);
        this.events = data;
        console.log(this.events);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
  }

  getValidatingClass(control: string): object {
    if (!this.searchForm.controls[control]) {
      console.warn('cant validate ' + control + ' because its not present.');
      return {'is-invalid': true};
    }
    return {
      'is-valid': this.validated && !this.searchForm.controls[control].errors,
      'is-invalid': this.validated && this.searchForm.controls[control].errors
    };
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }


}

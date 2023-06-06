import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {FormBuilder, FormControl} from "@angular/forms";
import {SearchServiceService} from "../../services/searchService.service";
import {BookmarkService} from "../../services/bookmark.service";
import {User} from "../models/user";


//import { MatTableModule } from '@angular/material';


@Component({
  selector: 'app-searchService',
  templateUrl: './searchService.component.html',
  styleUrls: ['./searchService.component.css']
})
export class SearchServiceComponent implements OnInit {

  error = false;
  errorMessage = '';
  // @ts-ignore

  searchForm: FormGroup;

  event: Event | undefined;
  // @ts-ignore
  events: Event[];
  displayedColumns: string[] = ['name', 'date', 'description', 'capacity', 'eventType', 'action'];



  constructor(
    private readonly dialog: MatDialog,
    private dialogRef: MatDialogRef<SearchServiceComponent>,
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private searchService: SearchServiceService,
    private bookmarkService: BookmarkService,
    private user: User,
  ) {

    // @ts-ignore
    this.event = new Event();

    this.searchForm  = this.formBuilder.group({
      eventName: new FormControl(this.event.eventName, [
        /* Validators.required, Validators.minLength(1), Validators.maxLength(64)*/]),
      capacity: new FormControl(this.event.capacity, []),
      date: new FormControl(this.event.date, []),
      description: new FormControl(this.event.description, []),
    });
  }

  ngOnInit(): void {
    this.loadAllEvents();
  }

  bookmark(event: Event){
    this.bookmarkService.bookmarkEvent(event, this.user);
  }

  unbookmark(event: Event){
    this.bookmarkService.unbookmarkEvent(event, this.user);
  }


  register(event: Event){

  }

  feedback(event: Event){

  }


  public loadAllEvents() {
    this.eventService.getAllEvents().subscribe({
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
    this.eventService.getEventsByName(this.searchForm.controls.eventName.value).subscribe({
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
    this.eventService.getEventsByDate(this.searchForm.controls.date.value).subscribe({
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
    this.eventService.getEventsByCapacity(this.searchForm.controls.capacity.value).subscribe({
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
    this.eventService.getEventsByDes(this.searchForm.controls.capacity.value).subscribe({
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

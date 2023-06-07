import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {FormBuilder, FormControl} from "@angular/forms";
import {SearchServiceService} from "../../services/searchService.service";
import {BookmarkService} from "../../services/bookmark.service";
import {AttendanceService} from "../../services/attendance.service";
import {User} from "../models/user";
import {AttendeeEventList} from "../../dtos/attendeeEventList";


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

  attendeeEventList: string[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private dialogRef: MatDialogRef<SearchServiceComponent>,
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private searchService: SearchServiceService,
    private bookmarkService: BookmarkService,
    private attendanceService: AttendanceService,
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

    this.attendanceService = attendanceService;
  }

  ngOnInit(): void {
    this.loadAllEvents();
    this.loadAttendeeEventList(); // Load attendee's event list on initialization
  }

  bookmark(event: Event){
    this.bookmarkService.bookmarkEvent(event, this.user);
  }

  unbookmark(event: Event){
    this.bookmarkService.unbookmarkEvent(event, this.user);
  }

  /**
   * Register the current user for an event.
   *
   * @param event - The event to register for.
   */
  register(event: Event) {
    this.attendanceService.register(this.user, event).subscribe(() => {
      this.attendeeEventList.push(event.eventID);
    });
  }

  /**
   * Deregister the current user from an event.
   *
   * @param event - The event to deregister from.
   */
  deregister(event: Event) {
    this.attendanceService.deregister(this.user, event).subscribe(() => {
      const index = this.attendeeEventList.indexOf(event.eventID);
      if (index > -1) {
        this.attendeeEventList.splice(index, 1);
      }
    });
  }

  /**
   * Check if the current user is registered for an event.
   *
   * @param event - The event to check registration for.
   * @return true if the user is registered for the event, false otherwise.
   */
  isRegistered(event: Event): boolean {
    return this.attendeeEventList.includes(event.eventID);
  }

  /**
   * Load the list of event IDs for the current user from the server.
   */
  loadAttendeeEventList(): void {
    this.attendanceService.getAttendeeEventList(this.user).subscribe(
      (attendeeEventList: AttendeeEventList) => {
        this.attendeeEventList = attendeeEventList.eventIDs;
      },
      (error: any) => {
        this.defaultServiceErrorHandling(error);
      }
    );
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

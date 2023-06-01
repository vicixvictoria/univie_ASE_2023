import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {AddEventComponent} from "../addEvents/addEvent.component";
import {Router} from "@angular/router";
import {UpdateEventComponent} from "../updateEvents/updateEvent.component";
//import { MatTableModule } from '@angular/material';


@Component({
  selector: 'app-eventInventory',
  templateUrl: './eventInventory.component.html',
  styleUrls: ['./eventInventory.component.scss']
})
export class EventInventoryComponent implements OnInit {

  error = false;
  errorMessage = '';
  // @ts-ignore

  eventForm: FormGroup;

  // @ts-ignore
  event: Event;
  event1: Event | undefined;

  // @ts-ignore
  events: Event[];
  displayedColumns: string[] = ['name', 'date', 'description', 'capacity', 'eventType', 'action'];


  constructor(
    private router: Router,
    private readonly dialog: MatDialog,
    private dialogRef: MatDialogRef<EventInventoryComponent>,
    private eventService: EventService,
  ) {
  }

  ngOnInit(): void {
    this.loadAllEvents();
  }


  deleteEvent(eventID: string){
    if (confirm('Event ' + eventID + '"wirklich löschen?')) {
      // @ts-ignore
      this.eventService.deleteEvent(eventID).subscribe({
        next: () => {
          console.log("Deleting Event " + eventID)
          this.router.navigate(['eventInventory']);
        }
        ,
        error: error => {
          this.defaultServiceErrorHandling(error);
        }
      });
    }
  }

  updateEvent(event: Event){
    const dialog = this.dialog.open(UpdateEventComponent, {
      data: {
        eventID: event.eventID
      },
      width: '1500px'});
    dialog.afterClosed().subscribe(() => {
      this.loadAllEvents()
    });
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

  public loadAllEventsByOrganizerID(id: number) {
    this.eventService.getAllEventsByOrganizerID(id).subscribe({
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

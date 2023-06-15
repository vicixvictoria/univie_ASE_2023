import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {AddEventComponent} from "../addEvents/addEvent.component";
import {Router} from "@angular/router";
import {UpdateEventComponent} from "../updateEvents/updateEvent.component";
import {AccountService} from "../../services/account.service";
//import { MatTableModule } from '@angular/material';
import {AttendanceService} from "../../services/attendance.service";
import {AnalyticReportEventComponent} from "../analyticReport/analyticReportEvent.component";
import { forkJoin } from 'rxjs';
import { tap } from 'rxjs/operators';


interface EventWithAttendees extends Event{
  attendance?: number;
}

@Component({
  selector: 'app-eventInventory',
  templateUrl: './eventInventory.component.html',
  styleUrls: ['./eventInventory.component.scss']
})
export class EventInventoryComponent implements OnInit {

  error = false;
  errorMessage = '';
  // @ts-ignore

  // @ts-ignore
  event: Event;
  // @ts-ignore
  userID : string

  // @ts-ignore
  events: Event[];
  displayedColumns: string[] = ['name', 'date', 'description', 'capacity', 'eventType', 'attendance', 'action'];


  constructor(
    private router: Router,
    private readonly dialog: MatDialog,
    private dialogRef: MatDialogRef<EventInventoryComponent>,
    private eventService: EventService,
    private accountService: AccountService,
    private attendanceService: AttendanceService,

  ) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.userID = this.getuserValue().id
    // this.loadAllEvents();
    this.loadAllEventsByOrganizerID(this.userID);
    this.loadAttendance(this.userID);
  }

  public getuserValue() {
    return this.accountService.userValue;
  }

  deleteEvent(eventID: string){
    if (confirm('Event ' + eventID + 'wirklich löschen?')) {
      console.log
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

  analyticReport(eventID: string){
    const dialog = this.dialog.open(AnalyticReportEventComponent, {
      data: {
        eventID: eventID
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

  public loadAllEventsByOrganizerID(id: string) {
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

  public loadAttendance(id: string) {
    this.eventService.getAllEventsByOrganizerID(id).subscribe({
      next: data => {
        const attendanceObservables = data.map((event: EventWithAttendees) => {
          return this.attendanceService.getAttendance(event.eventID).pipe(
            tap(count => {
              event.attendance = count;
            })
          );
        });

        forkJoin(attendanceObservables).subscribe({
          next: () => {
            this.events = data;
          },
          error: error => {
            this.defaultServiceErrorHandling(error);
          }
        });
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

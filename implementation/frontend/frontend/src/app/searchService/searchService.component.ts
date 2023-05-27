import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EventService} from "../../services/event.service";
import {Event} from "../../dtos/event";
import {FormBuilder, FormControl} from "@angular/forms";
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
  ) {

    // @ts-ignore
    this.event = new Event();

    this.searchForm  = this.formBuilder.group({
      eventName: new FormControl(this.event.eventName, [
        /* Validators.required, Validators.minLength(1), Validators.maxLength(64)*/]),
    });
  }

  ngOnInit(): void {
  }

  bookmark(event: Event){

  }

  register(event: Event){

  }

  feedback(event: Event){

  }


}

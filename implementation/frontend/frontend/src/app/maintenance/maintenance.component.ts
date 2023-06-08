import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";

import {MaintenanceService} from "../../services/maintenance.service";
import {AlertService} from "../../services/alert.service";
import {Availability} from "../models/availability";


@Component({
  selector: 'app-maintenance',
  templateUrl: './maintenance.component.html'
})
export class MaintenanceComponent implements OnInit {
  displayText: String | undefined;
  availabilities: Availability[] | undefined;

  constructor(private maintenanceService: MaintenanceService) { }

  ngOnInit(): void{
    this.getAvailabilities();
  }

  goBack() {
    // TODO: insert routing here
  }
  public getAvailabilities() {
    this.maintenanceService.getAvailability().subscribe({
      next: data => {
        console.log('recieved availabilities', data);
        this.availabilities = data;
        console.log(this.availabilities);
      },
      error: error => {
        this.defaultServiceErrorHandling(error);
      }
    });
    console.log(this.displayText);

  }
  private defaultServiceErrorHandling(error: any) {
    console.log(error);
  }



}

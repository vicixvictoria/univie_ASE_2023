import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";

import {AnalyticReportService} from "../../services/analyticReport.service";


@Component({
  selector: 'app-analyticReport',
  templateUrl: './analyticReport.component.html'
})
export class AnalyticReportServiceComponent implements OnInit {

  constructor(private analyticReportService: AnalyticReportService) { }

  ngOnInit(): void{
    // check for user role
    // display buttons according to user role
    // implement button presses to display data
  }

  goBack() {
    // TODO: insert routing here
  }
}

import {Component, OnInit} from "@angular/core";
import { Location } from '@angular/common';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {CalendarService} from "../../services/calendar.service";
import {User} from "../models/user";
import {AccountService} from "../../services/account.service";
import {AttendanceService} from "../../services/attendance.service";


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
})
export class CalendarComponent implements OnInit {

  // @ts-ignore
  userID : String

  constructor(private accountService: AccountService, private httpClient: HttpClient, private calendarService: CalendarService) {

  }

  ngOnInit(): void {
    // @ts-ignore
    this.userID = this.getUserValue().id;
  }

  public getUserValue() {
    return this.accountService.userValue;
  }

  exportXML(){
    console.log("export XML")
    if (this.userID != null) {
      this.calendarService.exportXML(this.userID);
    }
  }

  exportJSON(){
    console.log("export JSON")
    if (this.userID != null) {
      this.calendarService.exportJSON(this.userID)
    }
  }

  exportICAL(){
    console.log("export ICal")
    if (this.userID != null) {
        this.calendarService.exportICAL(this.userID);
      }
  }

}

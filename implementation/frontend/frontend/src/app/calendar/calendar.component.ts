import {Component, OnInit} from "@angular/core";
import { Location } from '@angular/common';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {CalendarService} from "../../services/calendar.service";
import {User} from "../models/user";
import {AccountService} from "../../services/account.service";


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
})
export class CalendarComponent {
  user: User | null;

  constructor(private accountService: AccountService, private httpClient: HttpClient, private calendarService: CalendarService) {
    this.user = this.accountService.userValue;

  }

  exportXML(){
    if (this.user != null) {
      this.calendarService.exportXML(this.user);
    }
  }

  exportJSON(){
    if (this.user != null) {
      this.calendarService.exportJSON(this.user)
    }
  }

  exportICAL(){
      if (this.user != null) {
        this.calendarService.exportICAL(this.user);
      }
  }

}

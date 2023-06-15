import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
// @ts-ignore
import {AnalyticReportFeedback} from "../app/models/AnalyticReportFeedback";
import {User} from "../app/models/user";
import {Event} from "../dtos/event";
import {AccountService} from "./account.service";
import {AnalyticReportEvent} from "../dtos/analyticReportEvent";

const baseUri = 'http://localhost:8080/api/v1/analyticReport/';

@Injectable({
  providedIn: 'root'
})
export class AnalyticReportService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  // @ts-ignore
  event1: Observable<Event>;

  constructor(private httpClient: HttpClient, private accountService: AccountService) {
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public getuserValue() {
    return this.accountService.userValue;
  }

  private headers = new HttpHeaders({"Authorization": "Bearer " + this.getuserValue()?.jwt});

  getAnalyticReportEvent(eventID: String):Observable<AnalyticReportEvent> {
    return this.httpClient.get<AnalyticReportEvent>(`${baseUri}event/${eventID}`, {headers: this.headers});
  }

  getAnalyticReportFeedback(eventID: String):Observable<AnalyticReportFeedback> {
    return this.httpClient.get<AnalyticReportFeedback>(`${baseUri}feedback/${eventID}`, {headers: this.headers});
  }
}

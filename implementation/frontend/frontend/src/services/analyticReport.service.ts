import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
// @ts-ignore
import {environment} from "../environments/environment";
import {AnalyticReportEvent} from "../app/models/AnalyticReportEvent";
import {AnalyticReportFeedback} from "../app/models/AnalyticReportFeedback";


@Injectable({
  providedIn: 'root'
})
export class AnalyticReportService {

  // @ts-ignore
  constructor(private httpClient: HttpClient) { }

  getAnalyticReportEvent(eventID: String) {
    return this.httpClient.get<AnalyticReportEvent>(`${environment.apiUrl}/analyticReport/event`, eventID);
  }

  getAnalyticReportFeedback(eventID: String) {
    return this.httpClient.get<AnalyticReportFeedback>(`${environment.apiUrl}/analyticReport/feedback`, eventID);
  }
}

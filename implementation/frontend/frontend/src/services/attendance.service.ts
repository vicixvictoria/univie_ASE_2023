import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Event} from "../dtos/event";
import {User} from "../app/models/user";
import {AttendeeEventList} from "../dtos/attendeeEventList";

const baseUri = 'http://localhost:8080/api/v1/attendance';

/**
 * AttendanceService class to interact with the server's attendance endpoints.
 */
@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  constructor(private httpClient: HttpClient) { }

  /**
   * Register a user for an event.
   * @param {User} user - The user object.
   * @param {Event} event - The event object.
   * @returns {Observable<number>} The new attendance count for the event.
   */
  register(id: String, event: Event): Observable<number> {
    const url = `${baseUri}/register/${id}/${event.eventID}`;
    return this.httpClient.post<number>(url, {});
  }

  /**
   * Deregister a user for an event.
   * @param {User} user - The user object.
   * @param {Event} event - The event object.
   * @returns {Observable<number>} The new attendance count for the event.
   */
  deregister(id: String, event: Event): Observable<number> {
    const url = `${baseUri}/deregister/${id}/${event.eventID}`;
    return this.httpClient.post<number>(url, {});
  }

  /**
   * Fetches the list of events that the user is attending.
   * @param {User} user - The user object.
   * @returns {Observable<AttendeeEventList>} The list of events that the user is attending.
   */
  getAttendeeEventList(id: String): Observable<AttendeeEventList> {
    const url = `${baseUri}/attendeeEventList/${id}`;
    return this.httpClient.get<AttendeeEventList>(url);
  }

  /**
   * Fetches the number of attendees for a given event.
   * @param {string} eventID - The event id.
   * @returns {Observable<number>} The number of attendees for the event.
   */
  getAttendance(eventID: string): Observable<number> {
    const url = `${baseUri}/attendance/${eventID}`;
    return this.httpClient.get<number>(url);
  }
}

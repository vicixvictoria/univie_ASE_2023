import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
// @ts-ignore
import {environment} from "../environments/environment";
import {Event} from "../dtos/event";

const baseUri = environment + '/events';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  // @ts-ignore
  event1: Observable <Event>;
  constructor(private httpClient: HttpClient) { }

  createEvent(event: Event): Observable<Event> {
    console.log('Add event', event);
    return this.httpClient.post<Event>(baseUri, event);
    //return this.event;
  }

  updateEvent(event: Event): Observable<Event>{
    console.log('Update event', event);
    return this.httpClient.put<Event>(baseUri, event);
  }




}

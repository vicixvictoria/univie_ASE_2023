import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
// @ts-ignore
import {Event} from "../dtos/event";
import {environment} from "../environments/environment.prod";
//import {environment} from "../environments/environment";

const baseUri = 'http://localhost:8080/api/v1/events';

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

  deleteEvent(id: string): Observable<Event>{
    return this.httpClient.delete<Event>(baseUri + '/' + id);
  }

  getAllEvents(): Observable<Event[]> {
    console.log('get all Events from one organizer');
    return this.httpClient.get<Event[]>(baseUri);
  }

  getEventsByName(name: string): Observable<Event[]> {
    console.log('get all Events with this name' + name);
    return this.httpClient.get<Event[]>(baseUri+ '/' + name);
  }

  getEventsByDate(date: Date): Observable<Event[]> {
    console.log('get all Events with this name' + date);
    return this.httpClient.get<Event[]>(baseUri + '/' + date);
  }

  getEventsByCapacity(capacity: number): Observable<Event[]> {
    console.log('get all Events with this name' + capacity);
    return this.httpClient.get<Event[]>(baseUri + '/' + capacity);
  }

  getEventsByDes(des: string): Observable<Event[]> {
    console.log('get all Events with this desc' + des);
    return this.httpClient.get<Event[]>(baseUri + '/' + des);
  }

  getAllEventsByOrganizerID(id:string): Observable<Event[]> {
    console.log('get all Events from one organizer');
    return this.httpClient.get<Event[]>(baseUri+ '/organizer' + id);

  }




}

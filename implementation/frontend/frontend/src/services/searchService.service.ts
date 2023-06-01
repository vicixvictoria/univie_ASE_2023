import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
// @ts-ignore
import {Event} from "../dtos/event";
import {environment} from "../environments/environment.prod";
//import {environment} from "../environments/environment";

const baseUri = 'http://localhost:8080/api/v1/searchService';

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  // @ts-ignore
  event1: Observable <Event>;
  constructor(private httpClient: HttpClient) { }


  getAllEvents(): Observable<Event[]> {
    console.log('get all Events');
    return this.httpClient.get<Event[]>(baseUri);
  }

  getEventsByName(name: string): Observable<Event[]> {
    console.log('get all Events with this name' + name);
    return this.httpClient.get<Event[]>(baseUri + '/' + name);
  }

  getEventsByDate(date: Date): Observable<Event[]> {
    console.log('get all Events with this name' + date);
    return this.httpClient.get<Event[]>(baseUri + '/' + date);
  }

  getEventsByCapacity(capacity: number): Observable<Event[]> {
    console.log('get all Events with this name' + capacity);
    return this.httpClient.get<Event[]>(baseUri + '/' + capacity);
  }

  getAllEventsByOrganizerID(id:number): Observable<Event[]> {
    console.log('get all Events from one organizer');
    return this.httpClient.get<Event[]>(baseUri+ '/organizer' + id);

  }




}

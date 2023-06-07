import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
// @ts-ignore
import {Event} from "../dtos/event";
import {environment} from "../environments/environment.prod";
import {User} from "../app/models/user";
import {AccountService} from "./account.service";
//import {environment} from "../environments/environment";

const baseUri = 'http://localhost:8080/api/v1/events';


@Injectable({
  providedIn: 'root'
})
export class EventService {

  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  // @ts-ignore
  event1: Observable <Event>;
  constructor(private httpClient: HttpClient, private accountService: AccountService){
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public getuserValue() {
    return this.accountService.userValue;
  }

  private headers = new HttpHeaders({"Authorization":"Bearer " + this.getuserValue()?.jwt});

  createEvent(event: Event): Observable<Event> {
    console.log('Add event', event);
    return this.httpClient.post<Event>(baseUri, event, {headers: this.headers});
    //return this.event;
  }

  updateEvent(event: Event): Observable<Event>{
    console.log('Update event', event);
    return this.httpClient.put<Event>(baseUri, event, {headers: this.headers});
  }

  deleteEvent(id: string): Observable<Event>{
    console.log('Delete event with id:', id);
    return this.httpClient.delete<Event>(baseUri + '/' + id, {headers: this.headers});
  }

  getAllEvents(): Observable<Event[]> {
    console.log('get all Events');
    return this.httpClient.get<Event[]>(baseUri, {headers: this.headers});
  }

  getAllEventsByOrganizerID(organizerID: String): Observable<Event[]> {
    console.log('get all Events by organizerID');
    return this.httpClient.get<Event[]>(baseUri + '/organizer/' + organizerID, {headers: this.headers});
  }

  getEventsByName(name: string): Observable<Event[]> {
    console.log('get all Events with this name' + name);
    return this.httpClient.get<Event[]>(baseUri+ '/name' + name, {headers: this.headers});
  }

  getEventsByDate(date: Date): Observable<Event[]> {
    console.log('get all Events with this name' + date);
    return this.httpClient.get<Event[]>(baseUri + '/date' + date, {headers: this.headers});
  }

  getEventsByCapacity(capacity: number): Observable<Event[]> {
    console.log('get all Events with this name' + capacity);
    return this.httpClient.get<Event[]>(baseUri + '/capacity' + capacity, {headers: this.headers});
  }

  getEventsByDes(des: string): Observable<Event[]> {
    console.log('get all Events with this desc' + des);
    return this.httpClient.get<Event[]>(baseUri + '/description' + des, {headers: this.headers});
  }

  /*
  getAllEventsByOrganizerID(id:string): Observable<Event[]> {
    console.log('get all Events from one organizer with id' + id);
    return this.httpClient.get<Event[]>(baseUri+ '/organizer' + id, {headers: this.headers});

  }*/




}

import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
// @ts-ignore
import {Event} from "../dtos/event";
import {User} from "../app/models/user";
import {AccountService} from "./account.service";


const baseUri = 'http://localhost:8080/api/v1/searchService';

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  constructor(private httpClient: HttpClient, private accountService: AccountService){
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public getUserValue() {
    return this.accountService.userValue;
  }

  private headers = new HttpHeaders({"Authorization":"Bearer " + this.getUserValue()?.jwt});


  getAllEvents(): Observable<Event[]> {
    console.log('get all Events');
    return this.httpClient.get<Event[]>(baseUri, {headers: this.headers});
  }

  getEventsByName(name: string): Observable<Event[]> {
    console.log('get all Events with this name' + name);
    return this.httpClient.get<Event[]>(baseUri + '/eventName/' + name, {headers: this.headers});
  }

  getEventsByDate(date: Date): Observable<Event[]> {
    console.log('get all Events with this date' + date);
    return this.httpClient.get<Event[]>(baseUri + '/date/' + date, {headers: this.headers});
  }

  getEventsByCapacity(capacity: number): Observable<Event[]> {
    console.log('get all Events with this capacity' + capacity);
    return this.httpClient.get<Event[]>(baseUri + '/capacity/' + capacity, {headers: this.headers});
  }

  getEventsByDes(des: string): Observable<Event[]> {
    console.log('get all Events with this desc' + des);
    return this.httpClient.get<Event[]>(baseUri + '/des/' + des, {headers: this.headers});
  }

  getAllEventsByOrganizerID(id:number): Observable<Event[]> {
    console.log('get all Events from one organizer');
    return this.httpClient.get<Event[]>(baseUri+ '/organizer/' + id, {headers: this.headers});

  }




}

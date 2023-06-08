
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
// @ts-ignore
import {environment} from "../environments/environment.prod";
import {Availability} from "../app/models/availability";
import {User} from "../app/models/user";
import {Event} from "../dtos/event";
import {AccountService} from "./account.service";
const baseUri = 'http://localhost:8080/api/v1/maintenance/availability';


@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {

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

  getAvailability(): Observable<Availability[]>{
    return this.httpClient.get<Availability[]>(baseUri, {headers: this.headers});
  }
}


import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
// @ts-ignore
import {environment} from "../environments/environment.prod";
import {Availability} from "../app/models/availability";
const baseUri = 'http://localhost:8080/api/v1/maintenance/availability';


@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {

  // @ts-ignore
  constructor(private httpClient: HttpClient) { }

  getAvailability(): Observable<Availability[]>{
    return this.httpClient.get<Availability[]>(baseUri, {});
  }
}

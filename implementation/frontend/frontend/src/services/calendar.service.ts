import {Injectable} from "@angular/core";
import {User} from "../app/models/user";
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { map } from "rxjs/operators";
import {BehaviorSubject, Observable} from "rxjs";
import {AccountService} from "./account.service";



const baseUri = "http://localhost:8080/api/v1/calendar/"

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;
  constructor(private httpClient: HttpClient, private accountService: AccountService) {
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public getuserValue() {
    return this.accountService.userValue;
  }

  private headers = new HttpHeaders({"Authorization":"Bearer " + this.getuserValue()?.jwt});


  exportXML(userId: String){
    const url = `${baseUri}export/${userId}/XML`;
    console.log("Request calendar with:", url)
    return this.httpClient.get(url, { headers: this.headers, responseType: 'text' }).subscribe((response: string) => {
      const element = document.createElement('a');
      element.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(response);
      element.download = 'calendar.xml';
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
  }

  exportJSON(userId: String){
    const url = `${baseUri}export/${userId}/JSON`;
    console.log("Request calendar with:", url)
    return this.httpClient.get(url, { headers: this.headers, responseType: 'text' }).subscribe((response: string) => {
      const element = document.createElement('a');
      element.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(response);
      element.download = 'calendar.json';
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
  }

  exportICAL(userId: String){
    const url = `${baseUri}export/${userId}/ICal`;
    console.log("Request calendar with:", url);

    return this.httpClient.get(url, {headers: this.headers, responseType: 'text' }).subscribe((response: string) => {
      const element = document.createElement('a');
      element.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(response);
      element.download = 'calendar.ics';
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
  }
}

import {Injectable} from "@angular/core";
import {User} from "../app/models/user";
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import { map } from "rxjs/operators";



const baseUri = environment + '/calendar/';
//const baseUri = "http://localhost:8080/api/v1/calendar/"

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private httpClient: HttpClient) {
  }


  exportXML(user: User){
    const url = `${baseUri}export/${user.id}/XML`;
    console.log("Request calendar with:", url)
    return this.httpClient.get(url, { responseType: 'text' }).subscribe((response: string) => {
      const element = document.createElement('a');
      element.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(response);
      element.download = 'calendar.xml';
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
  }

  exportJSON(user: User){
    const url = `${baseUri}export/${user.id}/JSON`;
    console.log("Request calendar with:", url)
    return this.httpClient.get(url, { responseType: 'text' }).subscribe((response: string) => {
      const element = document.createElement('a');
      element.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(response);
      element.download = 'calendar.json';
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
  }

  exportICAL(user: User){
    const url = `${baseUri}export/${user.id}/ICal`;
    console.log("Request calendar with:", url);

    return this.httpClient.get(url, { responseType: 'text' }).subscribe((response: string) => {
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

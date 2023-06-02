import {Injectable} from "@angular/core";
import {User} from "../app/models/user";
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import { map } from "rxjs/operators";


const baseUri = environment + '/calendar/';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private httpClient: HttpClient) {
  }


  exportXML(user: User){
    const url = `${baseUri}/${user.id}/XML`;
    return this.httpClient.get(url).pipe(
      map((response: any) => {
        return response;
      })
    );
  }

  exportJSON(user: User){
    const url = `${baseUri}/${user.id}/JSON`;
    return this.httpClient.get(url).pipe(
      map((response: any) => {
        return response;
      })
    );
  }

  exportICAL(user: User){
    const url = `${baseUri}/${user.id}/ICal`;
    return this.httpClient.get(url).pipe(
      map((response: any) => {
        return response;
      })
    );
  }
}

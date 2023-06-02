import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Event} from "../dtos/event";
import {environment} from "../environments/environment";
import {User} from "../app/models/user";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";


const baseUri = environment + '/bookmark/';


@Injectable({
  providedIn: 'root'
})
export class BookmarkService {


  constructor(private httpClient: HttpClient) {
  }


  bookmarkEvent(event: Event, user: User) {
    console.log('Add BookmarkEvent')
    return this.httpClient.post(baseUri + 'add/' + event.id + '/' + user.id, null).subscribe(response => {
      return response;
    });
  }

  unbookmarkEvent(event: Event, user: User) {
    console.log('Request all bookmarked Events for User')
    this.httpClient.delete(baseUri + 'unbookmarkEvent/' + event.id + '/' + user.id).subscribe(response => {
      console.log(response);
    });
  }

  getBookmarkedEventIds(user: User): Observable<string[]> {
    console.log('Request all bookmarked Events for User');
    const url = baseUri + 'bookmarkedEventIds/' + user.id;

    return this.httpClient.get<string[]>(url).pipe(
      map((response: string[]) => {
        return response;
      })
    );
  }

}

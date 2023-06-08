import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Event} from "../dtos/event";
import {User} from "../app/models/user";
import {map} from "rxjs/operators";
import {BehaviorSubject, Observable} from "rxjs";
import {AccountService} from "./account.service";


const baseUri = 'http://localhost:8080/api/v1/bookmark/';



@Injectable({
  providedIn: 'root'
})
export class BookmarkService {
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

  bookmarkEvent(event: Event, id: String) {
    const postUri = baseUri + 'add/' + event.eventID + '/' + id
    console.log('Add BookmarkEvent with', postUri)
    return this.httpClient.post(postUri, null,{headers: this.headers}).subscribe(response => {
      return response;
   });
  } 

  unbookmarkEvent(event: Event, id: String) {
    const deleteUri = baseUri + 'unbookmarkEvent/'+ event.eventID+'/'+id
    console.log('Unbookmark all bookmarked Events for User', deleteUri)
    this.httpClient.delete(deleteUri, {headers: this.headers}).subscribe(response => {
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

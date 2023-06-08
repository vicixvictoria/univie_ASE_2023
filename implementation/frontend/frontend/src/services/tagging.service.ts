import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Event} from "../dtos/event";
import {environment} from "../environments/environment";
import {User} from "../app/models/user";
import {TaggingEvent} from "../dtos/taggingEvent";
import {TaggingTypes} from "../gloabl/taggingTypes";
import {BehaviorSubject, Observable} from "rxjs";
import {AccountService} from "./account.service";


const baseUri = "http://localhost:8080/api/v1/tag/";


@Injectable({
  providedIn: 'root'
})
export class TaggingService {

  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;
  constructor(private httpClient: HttpClient, private accountService: AccountService) {
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  private headers = new HttpHeaders({"Authorization":"Bearer " + this.getuserValue()?.jwt});

  public getuserValue() {
    return this.accountService.userValue;
  }

  tagEvent(eventID: string, userID: string, tag: TaggingTypes) {
    console.log('tag Event with')
    const tag_uri = baseUri + 'add/' + eventID + '/' + userID+'/'+tag
    console.log(tag_uri)
    return this.httpClient.post(tag_uri, null, {headers: this.headers}).subscribe(response => {
      return response;
    });
  }

  untagEvent(eventID: string, userID: string, tag: TaggingTypes) {
    console.log('remove tag from Event')
    this.httpClient.delete(baseUri + 'removeEvent/' + eventID + '/' + userID, {headers: this.headers}).subscribe(response => {
      return response;
    });

  }

  getTagsForUserAndEvent(user: User, event: Event) {
    console.log('Request all tagging Events for User')
    const url = baseUri + 'event/' + event.eventID+ '/'+user.id;
    return this.httpClient.get<TaggingEvent>(url);
  }

}

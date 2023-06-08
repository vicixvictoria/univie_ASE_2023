import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../app/models/user";
import {Event} from "../dtos/event";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AccountService} from "./account.service";
import {Feedback} from "../dtos/feedback";

const baseUri = 'http://localhost:8080/api/v1/feedback/';


@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  // @ts-ignore
  event1: Observable<Event>;

  constructor(private httpClient: HttpClient, private accountService: AccountService) {
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public getuserValue() {
    return this.accountService.userValue;
  }

  private headers = new HttpHeaders({"Authorization": "Bearer " + this.getuserValue()?.jwt});

  createFeedback(feedback: Feedback): Observable<Feedback> {
    console.log('Add feedack', feedback);
    return this.httpClient.post<Feedback>(baseUri, feedback, {headers: this.headers});
  }

  getAllFeedbacksByEventId(eventId: String): Observable<Feedback[]> {
    console.log('Fetching feedbacks for event: ' + eventId);
    return this.httpClient.get<Feedback[]>(baseUri + 'event/' + eventId, {headers: this.headers});

}

}

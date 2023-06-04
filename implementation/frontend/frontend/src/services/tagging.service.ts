import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Event} from "../dtos/event";
import {environment} from "../environments/environment";
import {User} from "../app/models/user";
import {EventType} from "../gloabl/eventType";
import {TaggingEvent} from "../dtos/taggingEvent";


const baseUri = environment + '/tag/';


@Injectable({
  providedIn: 'root'
})
export class TaggingService {


  constructor(private httpClient: HttpClient) {
  }


  tagEvent(event: Event, user: User, tag: EventType) {
    console.log('tag Event with')
    return this.httpClient.post(baseUri + 'add/' + event.eventID + '/' + user.id, null).subscribe(response => {
      return response;
    });
  }

  untagEvent(event: Event, user: User, tag: EventType) {
    console.log('remove tag from Event')
    this.httpClient.put<TaggingEvent>(baseUri + 'removeTag/' + event.eventID + '/' + user.id+'/'+tag, null).subscribe(response => {
      return response;
    });

  }

  getTagsForUserAndEvent(user: User, event: Event) {
    console.log('Request all tagging Events for User')
    const url = baseUri + 'event/' + event.eventID+ '/'+user.id;
    return this.httpClient.get<TaggingEvent>(url);
  }

}

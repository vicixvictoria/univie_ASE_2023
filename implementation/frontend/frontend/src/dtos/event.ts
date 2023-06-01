import {EventType} from "../gloabl/eventType";

export class Event {
  constructor(
    public eventID: string,
    public eventName: string,
    public date: Date,
    public description: string,
    public capacity: number,
    public type: EventType,
    public organizerID: string,
  )
  {
  }
}

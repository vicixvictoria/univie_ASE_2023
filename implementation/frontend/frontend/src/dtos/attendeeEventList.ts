/**
 * Class representing a list of event IDs that a particular user is attending.
 */
export class AttendeeEventList {
  userID: string;
  eventIDs: string[];

  constructor(userID: string, eventIDs: string[]) {
    this.userID = userID;
    this.eventIDs = eventIDs;
  }
}

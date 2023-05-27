export class Event {
  constructor(
    public id: string,
    public eventName: string,
    public date: Date,
    public description: string,
    public capacity: number,
    public eventType: string,
    public organizerID: string,
  )
  {
  }
}

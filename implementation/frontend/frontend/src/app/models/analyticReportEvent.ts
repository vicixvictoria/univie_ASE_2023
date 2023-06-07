export class AnalyticReportEvent {
  constructor(
    public eventID: string,
    public numberOfAttending: bigint
  ) { }
}

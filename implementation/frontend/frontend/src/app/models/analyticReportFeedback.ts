export class AnalyticReportFeedback {
  constructor(
    public eventID: string,
    public meanRatingFood: number,
    public meanRatingLocation: number,
    public meanOverallRating: number,
    public numberOfComments: bigint,

  ) { }
}


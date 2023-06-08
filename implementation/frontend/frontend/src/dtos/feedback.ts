export class Feedback{

  constructor(
    public feedbackID: string,
    public eventID: string,
    public userID: string,
    public ratingOverall: number,
    public ratingLocation: number,
    public ratingFood: number,
    public comment: string

  )
  {}
}

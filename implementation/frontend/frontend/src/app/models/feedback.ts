export class Feedback {
  constructor(
    public ratingOverall: bigint,
    public ratingFood: bigint,
    public ratingLocation: bigint,
    public comment: string,
  ) { }
}

import {UserType} from "../../gloabl/userType";

export class User {
  id?: string;
  username?: string;
  password?: string;
  token?: string
  role?: UserType;
}

import { User } from "./user";
import { Mode } from "../shared/mode";

export interface Session {
  host: string;
  code: string;
  active: boolean;
  mode: Mode;
  players: User[]
}

import { Answer } from "./answer";

export interface Question {
  content: string;
  category: string;
  points: number;
  image: string | null;
  imageName: string | null;
  answers: Answer[];
}

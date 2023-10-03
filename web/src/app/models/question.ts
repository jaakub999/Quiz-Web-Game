import { Answer } from "./answer";

export interface Question {
  content: string;
  category: string;
  points: number;
  image: File | null;
  answers: Answer[];
}

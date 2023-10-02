import { Question } from "./question";

export interface QuestionSet {
  name: string;
  publicAccess: boolean;
  keyId: string;
  questions: Question[];
}

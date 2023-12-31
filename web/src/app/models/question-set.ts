import { Question } from "./question";

export interface QuestionSet {
  name: string;
  publicAccess: boolean;
  keyId: string | null;
  questions: Question[];
}

import { Injectable } from '@angular/core';
import { API_BASE_URL, HEADER, JWT_TOKEN_KEY } from "../config/constants.config";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { QuestionSet } from "../models/question-set";

@Injectable({
  providedIn: 'root'
})
export class QuestionSetService {

  private readonly baseUrl = `${API_BASE_URL}/question-set`;

  constructor(private http: HttpClient) {}

  createQuestionSet(questionSet: QuestionSet): Observable<QuestionSet> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.post<QuestionSet>(`${this.baseUrl}/create`, questionSet, { headers });
  }

  getUserQuestionSets(): Observable<QuestionSet[]> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.get<QuestionSet[]>(`${this.baseUrl}/user-sets`, { headers });
  }
}

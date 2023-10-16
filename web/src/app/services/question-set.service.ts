import { Injectable } from '@angular/core';
import { API_BASE_URL, HEADER, JWT_TOKEN_KEY } from "../config/constants.config";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { QuestionSet } from "../models/question-set";
import {QuestionSetResponse} from "../models/question-set-response";

@Injectable({
  providedIn: 'root'
})
export class QuestionSetService {

  private readonly baseUrl = `${API_BASE_URL}/question-set`;

  constructor(private http: HttpClient) {}

  createQuestionSet(questionSet: QuestionSet): Observable<any> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.post(`${this.baseUrl}/create`, questionSet, { headers });
  }

  getUserQuestionSets(): Observable<QuestionSetResponse[]> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.get<QuestionSetResponse[]>(`${this.baseUrl}/user-sets`, { headers });
  }

  getQuestionSet(keyId: string): Observable<QuestionSet> {
    return this.http.get<QuestionSet>(`${this.baseUrl}/${keyId}`)
  }

  deleteQuestionSet(keyId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${keyId}`);
  }

  editQuestionSet(questionSet: QuestionSet): Observable<any> {
    return this.http.put(`${this.baseUrl}/edit`, questionSet);
  }
}

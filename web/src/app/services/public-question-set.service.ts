import { Injectable } from '@angular/core';
import { API_BASE_URL } from "../config/constants.config";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { PublicQuestionSet } from "../models/public-question-set";

@Injectable({
  providedIn: 'root'
})
export class PublicQuestionSetService {

  private readonly baseUrl = `${API_BASE_URL}/public-question-sets`;

  constructor(private http: HttpClient) {}

  getPublicQuestionSets(): Observable<PublicQuestionSet[]> {
    return this.http.get<PublicQuestionSet[]>(this.baseUrl);
  }
}

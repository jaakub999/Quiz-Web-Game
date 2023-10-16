import { Injectable } from '@angular/core';
import { API_BASE_URL, HEADER, JWT_TOKEN_KEY } from "../config/constants.config";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Mode } from "../shared/mode";
import { SessionResponse } from "../models/session-response";
import { CodeResponse } from "../models/code-response";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private readonly baseUrl = `${API_BASE_URL}/session`;

  constructor(private http: HttpClient) {}

  createSession(mode: Mode): Observable<CodeResponse> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.post<CodeResponse>(`${this.baseUrl}/create`, mode, { headers })
  }

  getSession(code: string): Observable<SessionResponse> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.get<SessionResponse>(`${this.baseUrl}/${code}`, { headers });
  }

  joinSession(code: string): Observable<any> {
    const token = localStorage.getItem(JWT_TOKEN_KEY)!;
    const headers = new HttpHeaders().set(HEADER, token);
    return this.http.put(`${this.baseUrl}/join`, code, { headers })
  }
}

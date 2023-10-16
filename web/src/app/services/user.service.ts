import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/user";
import { ChangePasswordRequest } from "../models/change-password-password";
import { API_BASE_URL } from "../config/constants.config";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly baseUrl = `${API_BASE_URL}/users`;

  constructor(private http: HttpClient) {}

  register(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  changeForgottenPassword(request: ChangePasswordRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/change-forgotten-password`, request);
  }
}

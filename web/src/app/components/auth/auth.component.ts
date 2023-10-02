import { Component } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import { LoginResponse } from "../../models/login-response";
import { RouteUrl } from "../../shared/route-url";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {
  form = {
    username: '',
    password: '',
    keepLogged: false
  };

  showForgotPassword = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSignIn(): void {
    const encodedPassword = encodeURIComponent(this.form.password);
    this.authService.logIn(this.form.username, encodedPassword, this.form.keepLogged).subscribe(
      (response: LoginResponse) => {
        this.authService.setToken(response.token);
        this.router.navigateByUrl(RouteUrl.HOME);
      });
  }

  onRegister(): void {
    this.router.navigateByUrl(RouteUrl.REGISTER);
  }

  onPasswordChange(): void {
    this.showForgotPassword = true;
  }

  getEvent(event: boolean): void {
    this.showForgotPassword = event;
  }

  areCredentialsValid(): boolean {
    return this.form.username.trim() !== '' && this.form.password.trim() !== '';
  }
}

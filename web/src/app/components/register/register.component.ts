import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../../services/user.service";
import { RouteUrl } from "../../shared/route-url";
import { EmailType } from "../../shared/email-type";
import { catchError } from "rxjs";
import {User} from "../../models/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  username = '';
  password = '';
  confirmPassword = '';
  email = '';
  showEmail = false;
  emailType = EmailType.REGISTER;

  constructor(
      private router: Router,
      private userService: UserService
  ) {}

  onSubmit() {
    if (this.isFormValid()) {
      if (this.password !== this.confirmPassword) {
        window.alert('Passwords do not match');
        return;
      } else {
        const user: User = {
          username: this.username,
          password: this.password,
          email: this.email,
          verified: false
        };

        this.userService.register(user).pipe(
            catchError((error): any => {
              window.alert('An unexpected error occurred');
              return;
            })
        ).subscribe(() => {
          this.showEmail = true;
        });
      }
    }
  }

  cancel() {
    this.router.navigateByUrl(RouteUrl.AUTH)
  }

  isFormValid(): boolean {
    return (
        this.username.trim() !== '' &&
        this.password.trim() !== '' &&
        this.confirmPassword.trim() !== '' &&
        this.email.trim() !== ''
    );
  }
}

import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../../services/user.service";
import { RouteUrl } from "../../shared/route-url";
import { EmailType } from "../../shared/email-type";
import { catchError } from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  form = {
    username: '',
    password: '',
    confirmPassword: '',
    email: ''
  };

  showEmail = false;
  emailType = EmailType.REGISTER;

  constructor(
      private router: Router,
      private userService: UserService
  ) {}

  onSubmit() {
    if (this.isFormValid()) {
      if (this.form.password !== this.form.confirmPassword) {
        window.alert('Passwords do not match');
        return;
      } else {
        this.userService.register(this.form.username, this.form.password, this.form.email).pipe(
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
        this.form.username.trim() !== '' &&
        this.form.password.trim() !== '' &&
        this.form.confirmPassword.trim() !== '' &&
        this.form.email.trim() !== ''
    );
  }
}

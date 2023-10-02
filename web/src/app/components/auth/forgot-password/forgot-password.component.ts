import { Component, EventEmitter, Output } from '@angular/core';
import { EmailService } from "../../../services/email.service";
import { EmailType } from "../../../shared/email-type";
import { catchError } from "rxjs";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html'
})
export class ForgotPasswordComponent {

  @Output() event: EventEmitter<boolean> = new EventEmitter<boolean>();

  email = '';

  showEmail = false;
  emailType = EmailType.PASSWORD;

  constructor(
    private emailService: EmailService
  ) {}

  onSubmit(): void {
    if (this.isEmailValid) {
      this.emailService.sendPasswordEmail(this.email).pipe(
        catchError((error): any => {
          window.alert('An unexpected error occurred');
          return
        })
      ).subscribe(() => {
        this.showEmail = true;
      });
    }
  }

  cancel(): void {
    this.event.emit(false);
  }

  get isEmailValid(): boolean {
    return this.email.trim() !== '';
  }
}

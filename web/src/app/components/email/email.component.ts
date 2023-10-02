import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { catchError } from "rxjs";
import { EmailType } from "../../shared/email-type";
import { EmailService } from "../../services/email.service";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  @Input() email!: string;
  @Input() type!: EmailType;
  @Input() show!: boolean;

  constructor(
    private route: ActivatedRoute,
    private emailService: EmailService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
  }

  resendEmail() {
    if (this.type === EmailType.REGISTER) {
      this.emailService.resendRegisterEmail(this.email).pipe(
        catchError((error): any => {
          window.alert('An unexpected error occurred');
          return;
        })
      ).subscribe(() => {
        window.confirm('Email has been resent');
      });
    }

    else if (this.type === EmailType.PASSWORD) {
      this.emailService.sendPasswordEmail(this.email).pipe(
        catchError((error): any => {
          window.alert('An unexpected error occurred');
          return;
        })
      ).subscribe(() => {
        window.confirm('Email has been resent');
      });
    }
  }
}

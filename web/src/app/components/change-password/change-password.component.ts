import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../../services/user.service";
import { RouteUrl } from "../../shared/route-url";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  password = '';
  confirmPassword = '';
  token!: string;
  changed = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token')!;
  }

  onSubmit() {
    if (this.isFormValid()) {
      const request = {
        token: this.token,
        newPassword: this.password,
        confirmNewPassword: this.confirmPassword
      }

      this.userService.changeForgottenPassword(request).subscribe(
        () => {
          this.changed = true;
          setTimeout(() => {
            this.cancel();
          }, 5000);
        });
    }
  }

  cancel() {
    this.router.navigateByUrl(RouteUrl.AUTH)
  }

  isFormValid(): boolean {
    return this.password.trim() !== '' && this.confirmPassword.trim() !== '';
  }
}

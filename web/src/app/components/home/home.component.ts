import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { FormBuilder } from "@angular/forms";
import { RouteUrl } from "../../shared/route-url";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  showCode = false;
  code = '';

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {}

  hostGame(): void {

  }

  insertCode(): void {
    this.showCode = true;
  }

  goToSets(): void {
    this.router.navigateByUrl(RouteUrl.EXPLORER)
  }

  joinGame(): void {

  }

  cancel(): void {
    this.showCode = false;
  }
}

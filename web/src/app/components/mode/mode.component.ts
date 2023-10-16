import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { Mode } from "../../shared/mode";
import { RouteUrl } from "../../shared/route-url";
import { SessionService } from "../../services/session.service";
import { CodeResponse } from "../../models/code-response";

@Component({
  selector: 'app-mode',
  templateUrl: './mode.component.html',
  styleUrls: ['./mode.component.css']
})
export class ModeComponent {

  protected readonly Mode = Mode;
  mode: Mode = Mode.NONE;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  setMode(x: number): void {
    switch (x) {
      case 0:
        this.mode = Mode.NONE;
        break;
      case 1:
        this.mode = Mode.CLASSIC;
        break;
      case 2:
        this.mode = Mode.BIDDING;
        break;
    }
  }

  createLobby(): void {
    this.sessionService.createSession(this.mode).subscribe(
      (response: CodeResponse) => {
        this.router.navigate([`${RouteUrl.LOBBY}/${response.code}`]);
    });
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.HOME);
  }
}

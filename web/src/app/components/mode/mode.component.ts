import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {Mode} from "../../shared/mode";

@Component({
  selector: 'app-mode',
  templateUrl: './mode.component.html',
  styleUrls: ['./mode.component.css']
})
export class ModeComponent implements OnInit {

  mode: Mode = Mode.NONE;
  classicDesc!: string;

  constructor(
    private translate: TranslateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.classicDesc = this.translate.instant('CLASSIC_DESC');
  }

  setMode(x: number): void {
    switch (x) {
      case 0:
        this.mode = Mode.CLASSIC;
        break;
      case 1:
        this.mode = Mode.BIDDING;
        break;
      default:
        this.mode = Mode.NONE;
        break;
    }
  }

  protected readonly Mode = Mode;
}

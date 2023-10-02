import { Component, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { LanguageService } from "./services/language.service";
import { WebSocketService } from "./services/web-socket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  constructor(
    private translate: TranslateService,
    private languageService: LanguageService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    const defaultLanguage = this.languageService.getDefaultLanguage();
    this.translate.setDefaultLang(defaultLanguage);
    this.webSocketService.connect();
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }

  switchLanguage(language: string): void {
    this.translate.use(language);
    this.languageService.setDefaultLanguage(language);
  }
}

import { Injectable } from '@angular/core';
import { LAN_TOKEN } from "../config/constants.config";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  constructor() {}

  getDefaultLanguage(): string {
    return localStorage.getItem(LAN_TOKEN) || 'en';
  }

  setDefaultLanguage(language: string): void {
    localStorage.setItem(LAN_TOKEN, language);
  }
}

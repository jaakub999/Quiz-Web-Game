import { Component, OnInit } from '@angular/core';
import { RouteUrl } from "../../shared/route-url";
import { Router } from "@angular/router";

@Component({
  selector: 'app-question-set-creator',
  templateUrl: './question-set-creator.component.html',
  styleUrls: ['./question-set-creator.component.css']
})
export class QuestionSetCreatorComponent implements OnInit {

  sliderValue = 1;
  file = null;

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {
    const slider = document.querySelector('.slider') as HTMLInputElement;
    slider.addEventListener('input', () => {
      this.sliderValue = +slider.value;
    });
  }

  onFileSelected(event: any): void {
    this.file = event.target.files[0];

    if (!this.file) {
      return;
    }
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.EXPLORER);
  }
}

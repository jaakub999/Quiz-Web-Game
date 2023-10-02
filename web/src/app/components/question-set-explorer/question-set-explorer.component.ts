import { Component, OnInit } from '@angular/core';
import { RouteUrl } from "../../shared/route-url";
import { Router } from "@angular/router";
import { QuestionSetService } from "../../services/question-set.service";
import { QuestionSet } from "../../models/question-set";

@Component({
  selector: 'app-question-set-explorer',
  templateUrl: './question-set-explorer.component.html',
  styleUrls: ['./question-set-explorer.component.css']
})
export class QuestionSetExplorerComponent implements OnInit {

  private questionSets!: QuestionSet[];

  constructor(
    private questionSetService: QuestionSetService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.questionSetService.getUserQuestionSets().subscribe(
      (data: QuestionSet[]) => {
        this.questionSets = data;
      });
  }

  goToCreator(): void {
    this.router.navigateByUrl(RouteUrl.CREATOR);
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.HOME);
  }
}

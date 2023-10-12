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

  questionSets: QuestionSet[] = [];
  selectedRow!: number;

  constructor(
    private questionSetService: QuestionSetService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchQuestionSets();
  }

  onRowClicked(row: number): void {
    this.selectedRow = row;
  }

  isRowSelected(): boolean {
    return this.selectedRow !== null && this.selectedRow !== undefined;
  }

  goToCreator(): void {
    this.router.navigate([`${RouteUrl.CREATOR}/new`]);
  }

  delete(): void {
    const keyId = this.questionSets[this.selectedRow].keyId;

    if (keyId) {
      this.questionSetService.deleteQuestionSet(keyId).subscribe(() => {
        this.fetchQuestionSets();
      });
    }
  }

  edit(): void {
    const keyId = this.questionSets[this.selectedRow].keyId;
    this.router.navigate([`${RouteUrl.CREATOR}/${keyId}`]);
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.HOME);
  }

  private fetchQuestionSets(): void {
    this.questionSetService.getUserQuestionSets().subscribe(
      (data: QuestionSet[]) => {
        this.questionSets = data;
      });
  }
}

import { Component, Input } from '@angular/core';
import { PublicQuestionSet } from "../../../models/public-question-set";
import { QuestionSetResponse } from "../../../models/question-set-response";

@Component({
  selector: 'app-lobby-sets-table',
  templateUrl: './lobby-sets-table.component.html',
  styleUrls: ['./lobby-sets-table.component.css']
})
export class LobbySetsTableComponent {

  @Input() publicQuestionSets!: PublicQuestionSet[];
  @Input() userQuestionSets!: QuestionSetResponse[];

  activeTab: string = 'public'

  changeTab(tab: string): void {
    this.activeTab = tab;
  }
}

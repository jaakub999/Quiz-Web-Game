import { Component, OnInit } from '@angular/core';
import { RouteUrl } from "../../shared/route-url";
import { ActivatedRoute, Router } from "@angular/router";
import { QuestionSet } from "../../models/question-set";
import { Question } from "../../models/question";
import { Answer } from "../../models/answer";
import { QuestionSetCreatorPage } from "./question-set-creator-page";
import { QuestionSetService } from "../../services/question-set.service";
import { EMPTY, map, switchMap } from "rxjs";

@Component({
  selector: 'app-question-set-creator',
  templateUrl: './question-set-creator.component.html',
  styleUrls: ['./question-set-creator.component.css']
})
export class QuestionSetCreatorComponent implements OnInit {

  index = 0;
  save = false;
  access = 'public';
  questionSetName = '';
  page = new QuestionSetCreatorPage();
  questions: Question[] = [];
  keyId!: string | null;

  constructor(
    private questionSetService: QuestionSetService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const slider = document.querySelector('.slider') as HTMLInputElement;

    if (slider) {
      slider.addEventListener('input', () => {
        this.page.points = +slider.value;
      });
    }

    this.route.paramMap.pipe(
      map(params => params.get('key_id')),
      switchMap(keyId => {
        if (keyId !== null && keyId !== "new") {
          this.keyId = keyId;
          return this.questionSetService.getQuestionSet(keyId);
        } else {
          return EMPTY;
        }
      })
    ).subscribe(
      (questionSet: QuestionSet) => {
        if (questionSet) {
          this.questions = questionSet.questions;
          this.questionSetName = questionSet.name;
          this.index = 0;

          if (!questionSet.publicAccess) {
            this.access = 'private';
          }

          this.page.prepare(this.questions[this.index]);
        }
      }
    );
  }

  addOrOverwriteQuestion(overwrite: boolean): void {
    const correct = this.getCorrectAnswers();
    const answers = this.createAnswers(correct);
    const question = this.createQuestion(answers);

    if (overwrite) {
      this.questions[this.index] = question;
    }

    else {
      this.questions.push(question);
    }

    this.page.prepare();
    this.index = this.questions.length;
  }

  switchQuestion(value: number): void {
    this.index = this.index + value;
    this.page.prepare(this.questions[this.index]);
  }

  deleteQuestion(): void {
    if (this.index < this.questions.length) {
      this.questions.splice(this.index, 1);
    }

    if (this.index === this.questions.length) {
      this.page.prepare();
    } else {
      this.page.prepare(this.questions[this.index]);
    }
  }

  reset(): void {
    this.questions.splice(0, this.questions.length);
    this.index = 0;
    this.page.prepare();
  }

  async onFileSelected(event: any): Promise<void> {
    const selectedFile = event.target.files[0];

    if (!selectedFile) {
      return;
    }

    const result = await this.validateFileFormat(selectedFile);

    if (!result.ok) {
      return;
    }

    this.page.file = await this.fileToBase64(selectedFile);
    this.page.fileName = selectedFile.name;
  }

  removeFile(): void {
    this.page.file = null;
    this.page.fileName = null;
  }

  getFileName(): string {
    if (this.page.fileName) {
      const name = this.page.fileName;
      const extension = name.split('.')[1];
      const limit = 13;
      return name.length > limit ? name.substring(0, limit) + '...' + extension : name;
    }

    return '';
  }

  goToSave(): void {
    this.save = true;
  }

  cancel(): void {
    this.save = false;
  }

  saveQuestionSet(): void {
    const questionSet: QuestionSet = {
      name: this.questionSetName,
      publicAccess: this.access === 'public',
      keyId: null,
      questions: this.questions
    };

    if (this.keyId) {
      questionSet.keyId = this.keyId;
      this.questionSetService.editQuestionSet(questionSet).subscribe(() => {
        this.goBack();
      });
    }

    else {
      this.questionSetService.createQuestionSet(questionSet).subscribe(() => {
        this.goBack();
      });
    }
  }

  isNameValid(): void {
    this.questionSetName.trim() === '';
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.EXPLORER);
  }

  isFormValid(): boolean {
    return (
        this.page.inputA.trim() !== '' &&
        this.page.inputB.trim() !== '' &&
        this.page.inputC.trim() !== '' &&
        this.page.inputD.trim() !== '' &&
        this.page.content.trim() !== '' &&
        this.page.category.trim() !== ''
    );
  }

  private async validateFileFormat(file: File): Promise<{ ok: boolean; error?: string }> {
    const allowedFormats = ['image/jpeg', 'image/gif', 'image/png'];

    if (!allowedFormats.includes(file.type)) {
      return { ok: false, error: "Unsupported media type" };
    }

    return { ok: true };
  }

  private async fileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      reader.onload = (event) => {
        if (event.target && event.target.result) {
          const base64String: string = event.target.result as string;
          resolve(base64String);
        } else {
          reject(new Error("Failed to read file as Base64"));
        }
      };

      reader.onerror = (error) => {
        reject(error);
      };

      reader.readAsDataURL(file);
    });
  }

  private getCorrectAnswers(): boolean[] {
    const correct: boolean[] = Array(4).fill(false);

    switch (this.page.selectedOption) {
      case 'a': {
        correct[0] = true;
        break;
      }
      case 'b': {
        correct[1] = true;
        break;
      }
      case 'c': {
        correct[2] = true;
        break;
      }
      case 'd': {
        correct[3] = true;
        break;
      }
    }

    return correct;
  }

  private createAnswers(correct: boolean[]): Answer[] {
    return [
      { content: this.page.inputA, correct: correct[0] },
      { content: this.page.inputB, correct: correct[1] },
      { content: this.page.inputC, correct: correct[2] },
      { content: this.page.inputD, correct: correct[3] }
    ];
  }

  private createQuestion(answers: Answer[]): Question {
    return {
      content: this.page.content,
      category: this.page.category,
      points: this.page.points,
      image: this.page.file,
      imageName: this.page.fileName,
      answers: answers
    };
  }
}

import { Component, OnInit } from '@angular/core';
import { RouteUrl } from "../../shared/route-url";
import { Router } from "@angular/router";
import { QuestionSet } from "../../models/question-set";
import { Question } from "../../models/question";
import { Answer } from "../../models/answer";

@Component({
  selector: 'app-question-set-creator',
  templateUrl: './question-set-creator.component.html',
  styleUrls: ['./question-set-creator.component.css']
})
export class QuestionSetCreatorComponent implements OnInit {

  index = 0;
  inputA!: string;
  inputB!: string;
  inputC!: string;
  inputD!: string;
  content!: string;
  category!: string;
  selectedOption!: string;
  sliderValue!: number;
  file!: File | null;
  questions: Question[] = [];
  questionSet!: QuestionSet;

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {
    const slider = document.querySelector('.slider') as HTMLInputElement;
    slider.addEventListener('input', () => {
      this.sliderValue = + slider.value;
    });

    this.prepareForm();
  }

  addQuestion(overwrite: boolean): void {
    const correct = this.getCorrectAnswers();
    const answers = this.createAnswers(correct);
    const question = this.createQuestion(answers);

    if (overwrite) {
      this.questions[this.index] = question;
    }

    else {
      this.questions.push(question);
    }

    this.prepareForm();
    this.index = this.questions.length;
  }

  switchQuestion(value: number): void {
    this.index = this.index + value;
    this.prepareForm(this.questions[this.index]);
  }

  deleteQuestion(): void {
    if (this.index < this.questions.length) {
      this.questions.splice(this.index, 1);
    }

    if (this.index === this.questions.length) {
      this.prepareForm();
    } else {
      this.prepareForm(this.questions[this.index]);
    }
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

    this.file = selectedFile;
  }

  removeFile(): void {
    this.file = null;
  }

  getFileName(): string {
    if (this.file) {
      const name = this.file.name;
      const extension = this.file.type.split('/')[1];
      const limit = 13;
      return name.length > limit ? name.substring(0, limit) + '...' + extension : name;
    }

    return '';
  }

  goBack(): void {
    this.router.navigateByUrl(RouteUrl.EXPLORER);
  }

  isFormValid(): boolean {
    return (
        this.inputA.trim() !== '' &&
        this.inputB.trim() !== '' &&
        this.inputC.trim() !== '' &&
        this.inputD.trim() !== '' &&
        this.content.trim() !== '' &&
        this.category.trim() !== ''
    );
  }

  private async validateFileFormat(file: File): Promise<{ ok: boolean; error?: string }> {
    const allowedFormats = ['image/jpeg', 'image/gif', 'image/png'];

    if (!allowedFormats.includes(file.type)) {
      return { ok: false, error: "Unsupported media type" };
    }

    return { ok: true };
  }

  private getCorrectAnswers(): boolean[] {
    const correct: boolean[] = Array(4).fill(false);

    switch (this.selectedOption) {
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
      { content: this.inputA, correct: correct[0] },
      { content: this.inputB, correct: correct[1] },
      { content: this.inputC, correct: correct[2] },
      { content: this.inputD, correct: correct[3] }
    ];
  }

  private createQuestion(answers: Answer[]): Question {
    return {
      content: this.content,
      category: this.category,
      points: this.sliderValue,
      image: this.file,
      answers: answers
    };
  }

  private prepareForm(options?: Partial<Question>): void {
    const defaultData: Question = {
      content: '',
      category: '',
      points: 1,
      image: null,
      answers: [
        { content: '', correct: true },
        { content: '', correct: false },
        { content: '', correct: false },
        { content: '', correct: false }
      ]
    };

    const data: Question = { ...defaultData, ...options };
    this.setValues(data);
  }

  private setValues(data: Question): void {
    this.inputA = data.answers[0].content;
    this.inputB = data.answers[1].content;
    this.inputC = data.answers[2].content;
    this.inputD = data.answers[3].content;
    this.content = data.content;
    this.category = data.category;
    this.file = data.image;
    this.sliderValue = data.points;
    this.selectedOption = this.findCorrect(data.answers.findIndex(answer => answer.correct));
  }

  private findCorrect(correctIndex: number): string {
    switch (correctIndex) {
      case 0:
        return 'a';
      case 1:
        return 'b';
      case 2:
        return 'c';
      case 3:
        return 'd';
    }

    return 'a';
  }
}

import { Question } from "../../models/question";

export class CreatorPage {
  inputA: string;
  inputB: string;
  inputC: string;
  inputD: string;
  content: string;
  category: string;
  selectedOption: string;
  points: number;
  file: string | null;
  fileName: string | null;

  constructor() {
    this.inputA = '';
    this.inputB = '';
    this.inputC = '';
    this.inputD = '';
    this.content = '';
    this.category = '';
    this.selectedOption = 'a';
    this.points = 1;
    this.file = null;
    this.fileName = null;
  }

  prepare(options?: Partial<Question>): void {
    const defaultData: Question = {
      content: '',
      category: '',
      points: 1,
      image: null,
      imageName: null,
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
    this.fileName = data.imageName;
    this.points = data.points;
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

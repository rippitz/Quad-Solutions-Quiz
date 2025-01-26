import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {NgForOf, NgIf, NgStyle} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {FormsModule} from '@angular/forms';

interface Question {
  id: string;
  category: string;
  type: string;
  difficulty: string;
  question: string;
  answers: string[];
}

interface AnswerCheck {
  id: string;
  selectedAnswerIndex: number;
}

interface Result {
  id: string;
  selectedAnswerIndex: number;
  correctAnswerIndex: number;
  correct: boolean;
}

interface CheckAnswersResponse {
  results: Result[];
}

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [HttpClientModule, NgIf, NgForOf, MatButton, MatCard, MatCardTitle, MatCardHeader, MatCardContent, MatRadioGroup, MatRadioButton, MatSnackBarModule, FormsModule, NgStyle], // Only HttpClientModule here
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent {
  questions: Question[] | undefined;
  selectedAnswers: number[] = [];
  results: Result[] | undefined;
  score: number = 0;
  showResults: boolean = false;

  constructor(private router: Router, private http: HttpClient, private snackBar: MatSnackBar) {
    this.questions = this.router.getCurrentNavigation()?.extras.state?.['questions'] as Question[];
    if (this.questions) {
      this.selectedAnswers = new Array(this.questions.length).fill(null);
    }
  }

  submitAnswers() {
    if (!this.questions) {
      return;
    }

    if (this.selectedAnswers.some(answer => answer === null || answer === undefined)) {
      this.snackBar.open('Please answer all questions before submitting.', 'OK', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
      return;
    }

    const answersToCheck: AnswerCheck[] = this.questions.map((question, index) => ({
      id: question.id,
      selectedAnswerIndex: this.selectedAnswers[index]
    }));

    this.http.post<CheckAnswersResponse>('http://quiz-backend:8080/api/check-answers', { questions: answersToCheck }).subscribe(response => {
      this.results = response.results;
      this.score = this.results.filter(result => result.correct).length * 10;
      this.showResults = true;

      window.scrollTo(0, 0);
    });
  }

  returnToStart() {
    this.router.navigate(['']);
  }
}

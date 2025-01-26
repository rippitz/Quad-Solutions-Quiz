import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs'; // Import Observable

interface Question {
  id: string;
  category: string;
  type: string;
  difficulty: string;
  question: string;
  answers: string[];
}

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, HttpClientModule],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {
  questions$: Observable<Question[]> | undefined;

  constructor(private router: Router, private http: HttpClient) { }

  startQuiz() {
    this.questions$ = this.http.get<Question[]>('http://localhost:8080/api/questions');

    this.questions$.subscribe(questions => {
      this.router.navigate(['/quiz'], { state: { questions: questions } });
    });
  }
}

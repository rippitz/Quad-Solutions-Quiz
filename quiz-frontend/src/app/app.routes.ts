import { Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { QuizComponent } from './quiz/quiz.component';

export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'quiz', component: QuizComponent },
];

import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatToolbarModule],
  templateUrl: './app.component.html', // Use templateUrl
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'quiz-app';

  constructor(private router: Router) {}

  navigateToLanding() {
    this.router.navigate(['/']);
  }
}

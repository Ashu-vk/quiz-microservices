import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
  showProfile = false;
  @Input() title: string = 'Quiz App';

  // Example user data; replace with real user input from a service
  user = {
    name: 'Jane Doe',
    avatarUrl: 'assets/avatar-default.png',
    status: 'Online',
    totalQuizzes: 12,
    passedQuizzes: 9,
    createdQuizzes: 5,
    isAdmin: false,
  };

  toggleProfile() {
    this.showProfile = !this.showProfile;
  }
}

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  imports: [CommonModule]
})
export class HomeComponent {
  quizzes = [
    { id: 1, title: 'General Knowledge', description: 'A mixed set of general knowledge questions to challenge your mind.', questions: 10, time: '10m' },
    { id: 2, title: 'Science & Nature', description: 'Test your science intuition and discover fun facts.', questions: 12, time: '12m' },
    { id: 3, title: 'History', description: 'Dive into historical events and trivia.', questions: 8, time: '8m' },
  ];

  featured = this.quizzes[0];

  startQuiz(id: number) {
    // TODO: integrate with router/service; placeholder for now
    window.alert('Starting quiz: ' + id);
  }

  browseCategories() {
    window.alert('Browse categories (not yet implemented)');
  }

  openPractice() {
    window.alert('Opening practice mode (not yet implemented)');
  }

  trackById(index: number, item: any) {
    return item.id;
  }
}

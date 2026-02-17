import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuizService } from '../service/quiz-service';
import { Quiz } from '../../shared/models/quiz.model';

@Component({
  selector: 'app-quiz-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './quiz-list.html',
  styleUrls: ['./quiz-list.css'],
})
export class QuizList implements OnInit {
  quizzes: Quiz[] = [];
  page = 0;
  size = 10;
  total = 0;
  loading = false;

  constructor(private quizService: QuizService) {}

  ngOnInit(): void {
    this.load();
  }

  load(page = this.page) {
    if (page < 0) return;
    this.loading = true;
    this.quizService.getQuizzes(page, this.size).subscribe(res => {
      this.quizzes = res.items;
      this.total = res.total;
      this.page = res.page;
      this.loading = false;
    }, () => (this.loading = false));
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.total / this.size));
  }
}

import { Injectable } from '@angular/core';
import { Observable, map, catchError, of } from 'rxjs';
import { ApiService } from '../../shared/services/api.service';
import { Quiz, Paginated } from '../../shared/models/quiz.model';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  constructor(private api: ApiService) {}

  readonly apiUrl = '/quiz-service'
  /**
   * Fetch paginated quizzes from backend.
   * Attempts to support common Spring-style pagination (`content`, `totalElements`, `number`, `size`),
   * or a plain array response.
   */
  getQuizzes(page = 0, size = 10): Observable<Paginated<Quiz>> {
    const params = { page, size };

    return this.api.getAll<Paginated<Quiz>>(
      `${this.apiUrl}/quiz`,
      params
    ).pipe(
      map(res => ({
        items: res.items.map(it => this.mapToQuiz(it)),
        total: res.total,
        page: res.page,
        size: res.size
      })),
      catchError(() =>
        of({ items: [], total: 0, page, size })
      )
    );
  }

  private mapToQuiz(raw: any): Quiz {
    return {
      id: raw.id ?? raw._id ?? raw.uuid,
      name: raw.name ?? raw.title ?? '',
      type: raw.type ?? raw.quizType,
      category: raw.category ?? raw.cat,
      questionCount: raw.questionCount ?? raw.questionCount ?? raw.questions?.length ?? 0,
    } as Quiz;
  }
   quizzes: Quiz[] = [
  { id: 1, name: 'Java Basics', type: 'MCQ', category: 'Programming', questionCount: 10 },
  { id: 2, name: 'Spring Boot Fundamentals', type: 'MCQ', category: 'Backend', questionCount: 15 },
  { id: 3, name: 'Angular Core Concepts', type: 'MCQ', category: 'Frontend', questionCount: 12 },
  { id: 4, name: 'JavaScript Advanced', type: 'MCQ', category: 'Programming', questionCount: 20 },
  { id: 5, name: 'HTML & CSS Basics', type: 'MCQ', category: 'Frontend', questionCount: 8 },
  { id: 6, name: 'Microservices Architecture', type: 'Theory', category: 'Backend', questionCount: 18 },
  { id: 7, name: 'Docker Essentials', type: 'MCQ', category: 'DevOps', questionCount: 14 },
  { id: 8, name: 'Kubernetes Intro', type: 'MCQ', category: 'DevOps', questionCount: 16 },
  { id: 9, name: 'Data Structures', type: 'MCQ', category: 'Programming', questionCount: 25 },
  { id: 10, name: 'Algorithms Basics', type: 'MCQ', category: 'Programming', questionCount: 22 },
  { id: 11, name: 'SQL Mastery', type: 'MCQ', category: 'Database', questionCount: 15 },
  { id: 12, name: 'MongoDB Fundamentals', type: 'MCQ', category: 'Database', questionCount: 13 },
  { id: 13, name: 'System Design Basics', type: 'Theory', category: 'Architecture', questionCount: 10 },
  { id: 14, name: 'REST API Design', type: 'MCQ', category: 'Backend', questionCount: 17 },
  { id: 15, name: 'JWT & Security', type: 'MCQ', category: 'Security', questionCount: 12 },
  { id: 16, name: 'CI/CD Concepts', type: 'Theory', category: 'DevOps', questionCount: 9 },
  { id: 17, name: 'AWS Basics', type: 'MCQ', category: 'Cloud', questionCount: 14 },
  { id: 18, name: 'TypeScript Deep Dive', type: 'MCQ', category: 'Frontend', questionCount: 19 },
  { id: 19, name: 'RxJS Essentials', type: 'MCQ', category: 'Frontend', questionCount: 11 },
  { id: 20, name: 'Design Patterns', type: 'Theory', category: 'Programming', questionCount: 20 }
];

}

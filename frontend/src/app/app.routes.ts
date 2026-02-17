import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'quiz',
        loadComponent: () => import('./quiz/quiz-list/quiz-list').then(m => m.QuizList),
    },
];

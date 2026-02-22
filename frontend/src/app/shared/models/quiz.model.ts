export interface Quiz {
  id: number | string;
  name: string;
  type?: string;
  category?: string;
  questionCount?: number;
}

export interface Paginated<T> {
  items: T[];
  total: number;
  page: number;
  size: number;
}

export interface User{
id: number
name?: string
email?: string
role?: string
createdAt?: string // ISO-8601
updatedAt?: string
} 

export interface Question {

id?: number
quiz?: Quiz
text?: string
options?: string[]
correctAnswer?: string
explanation?: string
type?: string
points?: number
timeLimitInSeconds?: number
isActive?: boolean
difficulty?: string
category?: string
createdAt?: string
updatedAt?: string
}

export interface SubmissionAnswer {
  id?: number
submission?: Submission;
question?: Question
givenAnswer?: string
correct?: boolean
pointsAwarded?: number
}

export interface Submission {
  id?: number
quiz?: Quiz
user?: User
startTime?: string
endTime?: string
score?: number
totalPoints?: number
status?: string
answers?: SubmissionAnswer[]
}
export interface Result {
  id?: number
user?: User
quiz?: Quiz
score?: number
totalQuestions?: number
correctAnswers?: number
attemptedAt?: string
}
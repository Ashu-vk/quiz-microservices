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

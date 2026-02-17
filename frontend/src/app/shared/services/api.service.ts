import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { ApiError } from '../api-error';

@Injectable({ providedIn: 'root' })
export class ApiService {
  readonly baseUrl = 'http://localhost:8089';

  constructor(private http: HttpClient) {}

  private handleError(err: any) {
    const status = err?.status;
    let message = 'An unknown error occurred';
    if (err?.error?.message) message = err.error.message;
    else if (err?.message) message = err.message;
    const apiError = new ApiError(message, status, err?.error ?? err);
    return throwError(() => apiError);
  }

  private fullUrl(path: string) {
    if (!path) return this.baseUrl;
    return path.startsWith('http') ? path : `${this.baseUrl}${path.startsWith('/') ? '' : '/'}${path}`;
  }

  getAll<T>(path: string, params?: Record<string, string | number | boolean>): Observable<T> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach(k => {
        const v = params[k];
        if (v !== undefined && v !== null) httpParams = httpParams.set(k, String(v));
      });
    }
    return this.http.get<T>(this.fullUrl(path), { params: httpParams }).pipe(catchError(e => this.handleError(e)));
  }

  getById<T>(path: string, id: string | number): Observable<T> {
    const url = `${path.replace(/\/$/, '')}/${id}`;
    return this.http.get<T>(this.fullUrl(url)).pipe(catchError(e => this.handleError(e)));
  }

  create<T>(path: string, body: any): Observable<T> {
    return this.http.post<T>(this.fullUrl(path), body).pipe(catchError(e => this.handleError(e)));
  }

  update<T>(path: string, id: string | number, body: any): Observable<T> {
    const url = `${path.replace(/\/$/, '')}/${id}`;
    return this.http.put<T>(this.fullUrl(url), body).pipe(catchError(e => this.handleError(e)));
  }

  delete<T>(path: string, id: string | number): Observable<T> {
    const url = `${path.replace(/\/$/, '')}/${id}`;
    return this.http.delete<T>(this.fullUrl(url)).pipe(catchError(e => this.handleError(e)));
  }
}

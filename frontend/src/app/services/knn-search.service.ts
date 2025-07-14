import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface KnnSearchRequest {
  query: string;
  k: number;
}

@Injectable({ providedIn: 'root' })
export class KnnSearchService {
  private baseUrl = '/api/search';

  constructor(private http: HttpClient) {}

  knnSearch(request: KnnSearchRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/knn`, request);
  }
}

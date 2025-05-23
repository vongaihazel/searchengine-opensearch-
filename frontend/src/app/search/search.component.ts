import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../environments/environment';

import { SearchInputComponent } from './search-input/search-input.component';
import { SearchHistoryComponent } from './search-history/search-history.component';
import { SearchResultsComponent } from './search-results/search-results.component';

export interface User {
  id: number;
  username: string;
}

@Component({
  selector: 'app-search',
  standalone: true,
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    SearchInputComponent,
    SearchHistoryComponent,
    SearchResultsComponent
  ],
})
export class SearchComponent implements OnInit {
  userId: number = 1;
  searchHistory: string[] = [];
  results: User[] = [];
  isHistoryVisible: boolean = false;
  isHistoryEmpty: boolean = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getSearchHistory();
  }

  search(query: string): void {
    this.http.post<any>(`${environment.apiUrl}/query`, { userId: this.userId, query })
      .subscribe({
        next: (response) => {
          this.results = response;
          this.getSearchHistory();
        },
        error: (error) => {
          console.error('Error during search', error);
        }
      });
  }

  getSearchHistory(): void {
    this.http.get<string[]>(`${environment.apiUrl}/history/${this.userId}`)
      .subscribe({
        next: (history) => {
          this.searchHistory = history;
          this.isHistoryEmpty = history.length === 0;
        },
        error: (error) => {
          console.error('Error fetching search history', error);
        }
      });
  }

  useHistory(historyItem: string): void {
    this.search(historyItem);
    this.isHistoryVisible = false;
  }

  toggleHistory(): void {
    this.isHistoryVisible = !this.isHistoryVisible;
  }
}

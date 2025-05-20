import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';

// Define the User interface
export interface User {
  id: number;
  username: string;
}

@Component({
  selector: 'app-search',
  standalone: true,
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class SearchComponent implements OnInit {
  query: string = '';
  userId: number = 1;
  searchHistory: string[] = [];
  results: User[] = [];
  isHistoryVisible: boolean = false;
  isHistoryEmpty: boolean = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getSearchHistory();
  }

  search(): void {
    this.http.post<any>(`${environment.apiUrl}/query`, { userId: this.userId, query: this.query })
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
    this.query = historyItem;
    this.search();
    this.isHistoryVisible = false;
  }

  toggleHistory(): void {
    this.isHistoryVisible = !this.isHistoryVisible;
  }
}

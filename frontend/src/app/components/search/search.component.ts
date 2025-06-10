import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { Observable, of } from 'rxjs';
import { SearchInputComponent } from './search-input/search-input.component';
import { SearchHistoryComponent } from './search-history/search-history.component';
import { SearchResultsComponent } from './search-results/search-results.component';

export interface Article {
  id: string;
  title: string;
  content: string;
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
  results: Article[] = [];
  isHistoryVisible: boolean = false;
  isHistoryEmpty: boolean = false;
  userName$: Observable<string> = of('Guest');

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getSearchHistory();
  }

  isLoading = false;

  search(query: string): void {
    this.isLoading = true;
    this.isHistoryVisible = false;
    this.http.post<any>(`${environment.apiUrl}/query`, { userId: this.userId, query })
      .subscribe({
        next: (response) => {
          console.log('Full search response:', response); // For debugging
          this.results = response.openSearchResults || [];
          this.getSearchHistory();
           this.isLoading = false;
        },
        error: (error) => {
          console.error('Error during search', error);
          this.isLoading = false;
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
    if (this.isHistoryVisible) {
      this.getSearchHistory(); // Refresh history each time dropdown opens
    }
  }

}

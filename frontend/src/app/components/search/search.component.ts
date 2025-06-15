import { Component, OnInit, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
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
  isLoading = false;
  userName$: Observable<string> = of('Guest');

  currentSearchTerm: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.getSearchHistory();
  }

  search(query: string): void {
    this.currentSearchTerm = query;

    this.isLoading = true;
    this.isHistoryVisible = false;

    // If query is '*', send empty string to get all results
    const actualQuery = query.trim() === '*' ? '' : query;

    this.http.post<any>(`${environment.apiUrl}/query`, { userId: this.userId, query: actualQuery })
      .subscribe({
        next: (response) => {
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
          const seen = new Set<string>();
          this.searchHistory = history.reverse().filter(item => {
            if (seen.has(item)) return false;
            seen.add(item);
            return true;
          });
          this.isHistoryEmpty = this.searchHistory.length === 0;
        },
        error: (error) => {
          console.error('Error fetching search history', error);
        }
      });
  }

  toggleHistory(): void {
    this.isHistoryVisible = !this.isHistoryVisible;
    if (this.isHistoryVisible) {
      this.getSearchHistory();
    }
  }

  useHistory(query: string): void {
    this.search(query);
    this.isHistoryVisible = false;
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    const clickedInside = target.closest('.history-button') || target.closest('.history-dropdown');
    if (!clickedInside) {
      this.isHistoryVisible = false;
    }
  }
}

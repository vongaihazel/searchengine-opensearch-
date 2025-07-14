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
import { FilterSidebarComponent } from './filter-sidebar/filter-sidebar.component';
import { KnnSearchService, KnnSearchRequest } from '../../services/knn-search.service';

export interface Article {
  id: string;
  title: string;
  content: string;
  author?: string;
  category?: string;
  publish_date?: string;
  tags?: string[];
  views?: number;
  rating?: number;
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
    SearchResultsComponent,
    FilterSidebarComponent
  ],
})
export class SearchComponent implements OnInit {
  userId: number = 1; // keep if your backend needs userId for history
  searchHistory: string[] = [];
  results: Article[] = [];
  filteredResults: Article[] = [];
  isSidebarVisible = false;
  isSidebarOpen = false;
  isAISidebarVisible = false;
  authors: string[] = [];
  categories: string[] = [];
  isHistoryVisible: boolean = false;
  isHistoryEmpty: boolean = false;
  isLoading = false;

  userName$: Observable<string> = of('Guest');

  currentSearchTerm: string = '';

  authorFilter?: string;
  categoryFilter?: string;
  minRatingFilter?: number;

  aiPrompt: string = '';
  aiResponse: string | null = null;

   toggleSidebar() {
      this.isSidebarVisible = !this.isSidebarVisible;
      this.isSidebarOpen = !this.isSidebarOpen;
    }

    closeSidebar() {
      this.isSidebarOpen = false;
    }

   applyFilters(filters: { author?: string; category?: string; minRating?: number }) {
     console.log('SearchComponent applyFilters called with', filters);

     this.authorFilter = filters.author;
     this.categoryFilter = filters.category;
     this.minRatingFilter = filters.minRating;

     this.search(this.currentSearchTerm);
   }


    onSearchResultsReceived(results: Article[]) {
      this.results = results;
      this.filteredResults = []; // reset any filters
      this.authors = [...new Set(results.map(r => r.author).filter((a): a is string => !!a))];
    }


  constructor(private http: HttpClient, private router: Router, private knnSearchService: KnnSearchService) {}

  ngOnInit(): void {
    this.getSearchHistory();
    this.loadAuthors();
    this.loadCategories();
  }

  knnSearch(query: string): void {
      this.currentSearchTerm = query;
      this.isLoading = true;
      this.isHistoryVisible = false;

      if (!query.trim()) {
        this.results = [];
        this.isLoading = false;
        return;
      }

      const request: KnnSearchRequest = {
        query: query,
        k: 10,  // number of nearest neighbors to fetch
      };

      this.knnSearchService.knnSearch(request).subscribe({
        next: (response) => {
          // Backend returns SearchResponse as JSON string; parse or adjust as needed
          // Example assuming response is JSON string:
          let body;
          try {
            body = typeof response === 'string' ? JSON.parse(response) : response;
          } catch {
            body = response;
          }

          // Extract articles from OpenSearch hits
          // This depends on your backend response format - adapt accordingly
          const hits = body.hits?.hits || [];
          this.results = hits.map((hit: any) => {
            const source = hit._source || {};
            return {
              id: hit._id || '',
              title: source.title || '',
              content: source.content || '',
              author: source.author,
              category: source.category,
              publish_date: source.publish_date,
              tags: source.tags,
              views: source.views,
              rating: source.rating
            } as Article;
          });

          this.filteredResults = this.results;
          this.authors = [...new Set(this.results.map(r => r.author).filter((a): a is string => !!a))];
          this.categories = [...new Set(this.results.map(r => r.category).filter((c): c is string => !!c))];
          this.getSearchHistory();
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error during semantic search', error);
          this.isLoading = false;
        }
      });
    }

  search(query: string): void {
    this.currentSearchTerm = query;

    this.knnSearch(query);

    this.isLoading = true;
    this.isHistoryVisible = false;

    const actualQuery = query.trim() === '*' ? '' : query;

    const payload = {
      query: actualQuery || '',
      author: this.authorFilter,
      category: this.categoryFilter,
      minRating: this.minRatingFilter
    };

    console.log('Sending payload:', JSON.stringify(payload, null, 2));

    this.http.post<{ articles: Article[]; totalHits: number }>(
      `/api/articles/search`,
      payload
    ).subscribe({
      next: (response) => {
        this.results = response.articles || [];
        this.authors = [...new Set(this.results.map(r => r.author).filter((a): a is string => !!a))];
        this.categories = [...new Set(this.results.map(r => r.category).filter((c): c is string => !!c))];
        this.filteredResults = this.results;
        this.getSearchHistory();
        this.isLoading = false;

      },
      error: (error) => {
        console.error('Error during search', error);
        this.isLoading = false;
      }
    });

  }




  generateAICompletion(): void {
    if (!this.aiPrompt.trim()) {
      alert('Please enter a prompt!');
      return;
    }

    const payload = { prompt: this.aiPrompt };

    this.http.post<{ choices: { message: { content: string } }[] }>(
      `/api/ai/complete`,
      payload
    ).subscribe({
      next: (response) => {
        this.aiResponse = response.choices?.[0]?.message?.content || 'No response received.';
      },
      error: (error) => {
        console.error('Error generating AI completion:', error);
        this.aiResponse = 'Error generating AI completion.';
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

  toggleAISidebar() {
    this.isAISidebarVisible = !this.isAISidebarVisible;

    // Optional: Close the filter sidebar if both shouldn't be open at once
    if (this.isAISidebarVisible) {
      this.isSidebarVisible = false;
    }
  }


  useHistory(query: string): void {
    this.search(query);
    this.isHistoryVisible = false;
  }

  clearFilters(): void {
    this.authorFilter = undefined;
    this.categoryFilter = undefined;
    this.minRatingFilter = undefined;
    this.search(this.currentSearchTerm);
  }


  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent): void {
    const target = event.target as HTMLElement;

    // History dropdown
    const clickedInsideHistory = target.closest('.history-button') || target.closest('.history-dropdown');

    // Filter sidebar
    const clickedInsideSidebar = target.closest('.sidebar') || target.closest('.filter-button');

    // AI sidebar
    const clickedInsideAISidebar = target.closest('.ai-sidebar') || target.closest('.ai-button');

    if (!clickedInsideHistory) {
      this.isHistoryVisible = false;
    }

    if (!clickedInsideSidebar && !clickedInsideAISidebar) {
      this.isSidebarVisible = false;
      this.isSidebarOpen = false;
      this.isAISidebarVisible = false;
    }
  }

  loadAuthors(): void {
    this.http.get<string[]>(`${environment.apiUrl}/api/articles/authors`).subscribe({
      next: (authors) => {
        this.authors = authors;
      },
      error: (err) => {
        console.error('Failed to load authors', err);
      }
    });
  }

  loadCategories(): void {
    this.http.get<string[]>(`${environment.apiUrl}/api/articles/categories`).subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (err) => {
        console.error('Failed to load categories', err);
      }
    });
  }


}

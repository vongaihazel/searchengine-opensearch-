import { Component, HostListener } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule, AsyncPipe } from '@angular/common';
import { UserService } from './services/user.service';
import { SearchHistoryComponent } from './components/search/search-history/search-history.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, SearchHistoryComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'searchengine';
  userName$;
  isHistoryVisible = false;
  searchHistory: string[] = [];

  constructor(
    private userService: UserService,
    private http: HttpClient,
    private router: Router
    ) {
    this.userName$ = this.userService.userName$;
    this.getSearchHistory(); // optional: load history initially
  }

  toggleHistory() {
    this.isHistoryVisible = !this.isHistoryVisible;
    if (this.isHistoryVisible) {
      this.getSearchHistory();
    }
  }

  getSearchHistory(): void {
    const userId = 1;
     this.http.get<string[]>(`http://localhost:8080/search/history/${userId}`).subscribe({
      next: (history) => {
        const seen = new Set<string>();
        this.searchHistory = history.reverse().filter(item => {
          if (seen.has(item)) return false;
          seen.add(item);
          return true;
        });
      },
      error: (err) => {
        console.error('Error fetching history:', err);
      }
    });
  }

  useHistory(query: string) {
    this.isHistoryVisible = false;
    this.router.navigate(['/search'], { queryParams: { q: query } });
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

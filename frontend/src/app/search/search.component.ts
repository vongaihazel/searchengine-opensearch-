import { Component, OnInit } from '@angular/core'; // Import necessary Angular core modules
import { HttpClient } from '@angular/common/http'; // Import HttpClient for HTTP requests
import { FormsModule } from '@angular/forms'; // Import FormsModule for template-driven forms
import { CommonModule } from '@angular/common'; // Import CommonModule for common directives
import { environment } from 'src/environments/environment'; // Import environment variables

// Define the User interface
export interface User {
  id: number; // User ID
  username: string; // Username
}

@Component({
  selector: 'app-search', // Component selector
  standalone: true, // Standalone component
  templateUrl: './search.component.html', // Template URL
  styleUrls: ['./search.component.scss'], // Stylesheet URLs
  imports: [CommonModule, FormsModule], // Modules to import
})
export class SearchComponent implements OnInit {
  query: string = ''; // Search query
  userId: number = 1; // Default user ID
  searchHistory: string[] = []; // Array to store search history
  results: User[] = []; // Array to store search results
  isHistoryVisible: boolean = false; // Flag for history visibility
  isHistoryEmpty: boolean = false; // Flag for empty history

  constructor(private http: HttpClient) {} // Inject HttpClient

  ngOnInit(): void {
    this.getSearchHistory(); // Fetch search history on initialization
  }

  search(): void {
    // Make a POST request to the search API
    this.http.post<any>(`${environment.apiUrl}/query`, { userId: this.userId, query: this.query })
      .subscribe({
        next: (response) => {
          this.results = response; // Store the search results
          this.getSearchHistory(); // Update search history
        },
        error: (error) => {
          console.error('Error during search', error); // Log any errors
        }
      });
  }

  getSearchHistory(): void {
    // Make a GET request to fetch search history
    this.http.get<string[]>(`${environment.apiUrl}/history/${this.userId}`)
      .subscribe({
        next: (history) => {
          this.searchHistory = history; // Store the fetched history
          this.isHistoryEmpty = history.length === 0; // Check if history is empty
        },
        error: (error) => {
          console.error('Error fetching search history', error); // Log any errors
        }
      });
  }

  useHistory(historyItem: string): void {
    this.query = historyItem; // Set the query to the selected history item
    this.search(); // Perform search
    this.isHistoryVisible = false; // Hide history dropdown
  }

  toggleHistory(): void {
    this.isHistoryVisible = !this.isHistoryVisible; // Toggle visibility of search history
  }
}

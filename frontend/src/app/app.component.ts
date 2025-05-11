import { Component } from '@angular/core'; // Import Component decorator
import { RouterOutlet, RouterModule, Routes } from '@angular/router'; // Import routing modules
import { SearchComponent } from './search/search.component'; // Import the SearchComponent
import { CommonModule } from '@angular/common'; // Import CommonModule for common directives

// Define the application routes
const routes: Routes = [
  { path: '', redirectTo: '/search', pathMatch: 'full' }, // Redirect empty path to '/search'
  { path: 'search', component: SearchComponent }, // Route to SearchComponent
  { path: '**', redirectTo: '/search' } // Wildcard route to redirect any unknown path
];

@Component({
  selector: 'app-root', // Component selector
  standalone: true, // Standalone component
  templateUrl: './app.component.html', // Template URL
  styleUrls: ['./app.component.scss'], // Stylesheet URLs
  imports: [RouterOutlet, CommonModule, RouterModule] // Import necessary modules
})
export class AppComponent {
  title = 'searchengine'; // Application title
}

// Export router configuration for use in the module
export const appConfig = {
  providers: [RouterModule.forRoot(routes)] // Configure the router with defined routes
};

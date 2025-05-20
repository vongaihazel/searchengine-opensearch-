import { Routes } from '@angular/router'; // Import Routes type for defining routes
import { SearchComponent } from './search/search.component'; // Import the SearchComponent

// Define application routes
export const routes: Routes = [
  { path: '', component: SearchComponent }, // Default route that loads the SearchComponent
];

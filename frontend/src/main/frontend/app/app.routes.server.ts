import { RenderMode, ServerRoute } from '@angular/ssr'; // Import necessary types for server routing

// Define server routes
export const serverRoutes: ServerRoute[] = [
  {
    path: '**', // Wildcard route to match all paths
    renderMode: RenderMode.Prerender // Set render mode to prerender for improved performance
  }
];

import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core'; // Import core Angular functionalities
import { provideRouter } from '@angular/router'; // Import router provider

import { routes } from './app.routes'; // Import application routes
import { provideClientHydration, withEventReplay } from '@angular/platform-browser'; // Import client hydration utilities
import { provideHttpClient, withFetch } from '@angular/common/http'; // Import HTTP client provider

// Define application configuration
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Enable zone change detection with event coalescing
    provideRouter(routes), // Provide routing with defined application routes
    provideHttpClient(withFetch()), // Configure HTTP client with fetch support
    provideClientHydration(withEventReplay()) // Enable client hydration with event replay
  ]
};

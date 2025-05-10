import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';

// Combine the existing appConfig with the new HTTP client provider
const config = {
  ...appConfig,
  providers: [
    ...appConfig.providers,
    provideHttpClient(withFetch()) // Enable fetch API
  ]
};

// Bootstrap the application with the combined configuration
bootstrapApplication(AppComponent, config)
  .catch(err => console.error('Bootstrap error:', err));

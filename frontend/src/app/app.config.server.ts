import { mergeApplicationConfig, ApplicationConfig } from '@angular/core'; // Import necessary Angular core functionalities
import { provideServerRendering } from '@angular/platform-server'; // Import server rendering provider
import { provideServerRouting } from '@angular/ssr'; // Import server routing provider
import { appConfig } from './app.config'; // Import application configuration
import { serverRoutes } from './app.routes.server'; // Import server routes
import { provideClientHydration } from '@angular/platform-browser'; // Import client hydration provider

// Define server configuration
const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(), // Enable server-side rendering
    provideServerRouting(serverRoutes), // Provide server routing with defined routes
    provideClientHydration() // Enable client hydration
  ]
};

// Merge the application config with server config
export const config = mergeApplicationConfig(appConfig, serverConfig);

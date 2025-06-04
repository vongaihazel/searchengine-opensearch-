import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'search', pathMatch: 'full' },

  {
    path: 'search',
    loadComponent: () =>
      import('./components/search/search.component').then(m => m.SearchComponent),
  },
  {
    path: 'create-user',
    loadComponent: () =>
      import('./components/user-create/user-create.component').then(m => m.UserCreateComponent),
  },
  {
    path: 'sign-in',
    loadComponent: () =>
      import('./components/sign-in/sign-in.component').then(m => m.SignInComponent),
  },

  { path: '**', redirectTo: 'search' }
];

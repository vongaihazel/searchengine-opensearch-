import { Routes } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { UserCreateComponent } from './user-create/user-create.component';

export const routes: Routes = [
  { path: '', redirectTo: 'search', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: 'create-user', component: UserCreateComponent },
  { path: '**', redirectTo: 'search' }
];

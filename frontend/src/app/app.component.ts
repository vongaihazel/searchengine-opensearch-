import { Component } from '@angular/core';
import { RouterOutlet, RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { CommonModule } from '@angular/common';


const routes: Routes = [
  { path: '', redirectTo: '/search', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: '**', redirectTo: '/search' }
];


@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [RouterOutlet, CommonModule, RouterModule]
})
export class AppComponent {
  title = 'searchengine';
}
export const appConfig = {
  providers: [RouterModule.forRoot(routes)]
};


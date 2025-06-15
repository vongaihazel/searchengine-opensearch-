import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule, AsyncPipe } from '@angular/common';
import { UserService } from './services/user.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'searchengine';
  userName$;

  constructor(
    private userService: UserService,
    private http: HttpClient,
    ) {
    this.userName$ = this.userService.userName$;
  }

  setUserName(name: string) {
      this.userService.setUserName(name);
    }
  }






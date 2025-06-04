import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {
  username = '';

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
  ) {}

  signIn() {
    this.http.get<any[]>(`/api/users`)
      .subscribe({
        next: (users) => {
          const user = users.find(u => u.username === this.username);
          if (user) {
            this.userService.setUserName(user.username);
            this.router.navigate(['/']);
          } else {
            alert('User not found');
          }
        },
        error: (err) => {
          alert('Error signing in: ' + err.message);
        }
      });
  }
}

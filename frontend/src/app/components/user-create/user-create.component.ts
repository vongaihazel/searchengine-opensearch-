import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-user-create',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent {
  user = { username: '', email: '' };

  constructor(private http: HttpClient) {}

  createUser() {
    this.http.post('/api/users', this.user).subscribe({
      next: () => alert('User created successfully!'),
      error: (err) => alert('Failed to create user: ' + err.message),
    });
  }
}

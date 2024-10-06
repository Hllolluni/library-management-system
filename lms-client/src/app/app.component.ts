import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'lms-client';

  isLoggedIn(): boolean {
    const token = localStorage.getItem('access_token');
    console.log("Navbar"+ !!token)
    return !!token;
  }
}

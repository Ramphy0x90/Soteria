import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.sass']
})
export class LogInComponent implements OnInit {
  loginForm: User = {userName: null, password: null};
  loggedIn = false;
  invalidCredentials: boolean = false;
  errorMessage = '';

  formSubmitted: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    if(this.userService.isLoggedIn()) {
      this.router.navigate(['app']);
    }
  }

  onSubmit(): void {
    let user: User = this.loginForm;

    this.userService.logIn(user).subscribe({
      next: (data) => {
        console.log(data);
        this.loggedIn = true;
        this.invalidCredentials = false;

        this.userService.saveLogInUser(user, data.accessToken);
        this.router.navigate(['app']);
      },
      error: (err) => {
        this.invalidCredentials = true;
      }
    });
  }

}

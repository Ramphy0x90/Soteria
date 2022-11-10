import { Component, OnInit } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

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

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    let user: User = this.loginForm;

    this.userService.logIn(user).subscribe({
      next: (data) => {
        console.log(data);
        this.loggedIn = true;
        this.invalidCredentials = false;
      },
      error: (err) => {
        this.invalidCredentials = true;
      }
    });
  }

}

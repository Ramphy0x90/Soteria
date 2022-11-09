import { Component, OnInit } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.sass']
})
export class LogInComponent implements OnInit {
  loginForm: User = {userName: null, password: null};
  isSuccessful = false;
  invalidCredentials: boolean = false;
  errorMessage = '';

  formSubmitted: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { userName, password } = this.loginForm;

    /*this.authService.register(username, email, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );*/
  }

}

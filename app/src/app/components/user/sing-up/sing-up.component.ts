import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-singup',
  templateUrl: './sing-up.component.html',
  styleUrls: ['./sing-up.component.sass']
})
export class SingUpComponent implements OnInit {
  signupForm: User = {userName: null, password: null};
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
    let user: User = this.signupForm;

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

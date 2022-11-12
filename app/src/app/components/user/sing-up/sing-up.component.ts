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
  hasError: boolean = false;
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

    this.userService.signIn(user).subscribe({
      next: (data) => {
        this.hasError = false;
        this.router.navigate(['login']);
      },
      error: (err) => {
        this.hasError = true;
        this.errorMessage = err.error;
      }
    });
  }
}

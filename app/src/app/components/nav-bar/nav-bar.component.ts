import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.sass']
})
export class NavBarComponent implements OnInit {
  userLogged: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.userService.userStatus.subscribe((userLogged) => {
      this.userLogged = userLogged;
    });
  }

  ngOnInit(): void {
    this.userLogged = this.userService.isLoggedIn();
  }

  logOut() {
    this.userService.logOut();
    this.router.navigate(['home']);
  }
}

import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  userLogged: boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userLogged = this.userService.isLoggedIn()
  }

}

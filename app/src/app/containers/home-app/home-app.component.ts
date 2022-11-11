import { Component, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';

@Component({
  selector: 'app-home-app',
  templateUrl: './home-app.component.html',
  styleUrls: ['./home-app.component.sass']
})
export class HomeAppComponent implements OnInit {
  credentials: Credential[] = [
    {id: null, entity: 1, userName: 'Ramphy0x90', password: 'password'},
    {id: null, entity: 2, userName: '_ramphy_', password: 'password1'}
  ];

  constructor() { }

  ngOnInit(): void {
  }

}

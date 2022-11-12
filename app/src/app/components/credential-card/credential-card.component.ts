import { Component, Input, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';

@Component({
  selector: 'app-credential-card',
  templateUrl: './credential-card.component.html',
  styleUrls: ['./credential-card.component.sass']
})
export class CredentialCardComponent implements OnInit {
  @Input() credential!: Credential;
  @Input() active: boolean = false;
  icon!: string;

  constructor() { }

  ngOnInit(): void {
  }

}

import { Component, Input, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';

@Component({
  selector: 'app-credential-form',
  templateUrl: './credential-form.component.html',
  styleUrls: ['./credential-form.component.sass']
})
export class CredentialFormComponent implements OnInit {
  @Input() credential!: Credential;
  // 0 -> Read | 1 -> New | 2 -> Edit
  @Input() mode: number = 0;

  passwordVisible: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  passwordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }
}

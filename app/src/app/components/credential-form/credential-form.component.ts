import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Credential } from 'src/app/models/credential';
import { CredentialForm } from 'src/app/models/credential-form';
import { Entity } from 'src/app/models/entity';
import { EntityService } from 'src/app/services/entity.service';

@Component({
  selector: 'app-credential-form',
  templateUrl: './credential-form.component.html',
  styleUrls: ['./credential-form.component.sass']
})
export class CredentialFormComponent implements OnInit {
  // 0 -> Read | 1 -> New | 2 -> Edit
  @Input() mode: number = 0;
  @Input() credential!: Credential;
  @Output() credentialForm = new EventEmitter<CredentialForm>();

  passwordVisible: boolean = false;
  invalidCredentials: boolean = false;
  formSubmitted: boolean = false;
  errorMessage = '';
  entities!: Entity[];

  constructor(private entityService: EntityService) { }

  ngOnInit(): void {
    this.entityService.getEntities().subscribe({
      next: (data) => {
        this.entities = data;
      }
    });
  }

  passwordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  onSubmit(form: NgForm): void {
    this.credentialForm.emit({form: form, data: this.credential});
  }
}

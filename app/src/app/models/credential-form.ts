import { NgForm } from "@angular/forms";
import { Credential } from "./credential";


export interface CredentialForm {
  form: NgForm;
  data: Credential;
}

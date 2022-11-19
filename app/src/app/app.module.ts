import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './containers/home/home.component';
import { HomeAppComponent } from './containers/home-app/home-app.component';
import { LoginComponent } from './containers/login/login.component';
import { SignupComponent } from './containers/signup/signup.component';
import { LogInComponent } from './components/user/log-in/log-in.component';
import { SingUpComponent } from './components/user/sing-up/sing-up.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CredentialFormComponent } from './components/credential-form/credential-form.component';
import { CredentialCardComponent } from './components/credential-card/credential-card.component';
import { PasswordPipe } from './pipes/password.pipe';
import { UsernamePipe } from './pipes/username.pipe';
import { PasswordStrengthPipe } from './pipes/password-strength.pipe';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HomeAppComponent,
    LoginComponent,
    SignupComponent,
    LogInComponent,
    SingUpComponent,
    NavBarComponent,
    CredentialFormComponent,
    CredentialCardComponent,
    PasswordPipe,
    UsernamePipe,
    PasswordStrengthPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 2500,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

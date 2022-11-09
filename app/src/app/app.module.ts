import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './containers/home/home.component';
import { HomeAppComponent } from './containers/home-app/home-app.component';
import { LoginComponent } from './containers/login/login.component';
import { SignupComponent } from './containers/signup/signup.component';
import { LogInComponent } from './components/user/log-in/log-in.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HomeAppComponent,
    LoginComponent,
    SignupComponent,
    LogInComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

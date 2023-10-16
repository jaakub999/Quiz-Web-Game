import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HomeComponent } from './components/home/home.component';
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { RegisterComponent } from './components/register/register.component';
import { EmailComponent } from './components/email/email.component';
import { MenuComponent } from './components/menu/menu.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ForgotPasswordComponent } from './components/auth/forgot-password/forgot-password.component';
import { WebSocketService } from "./services/web-socket.service";
import { CreatorComponent } from './components/creator/creator.component';
import { ExplorerComponent } from './components/explorer/explorer.component';
import { ModeComponent } from './components/mode/mode.component';
import { LobbyComponent } from './components/lobby/lobby.component';
import { LobbyPlayerTableComponent } from './components/lobby/lobby-player-table/lobby-player-table.component';
import { LobbySetsTableComponent } from './components/lobby/lobby-sets-table/lobby-sets-table.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/translations/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    HomeComponent,
    RegisterComponent,
    EmailComponent,
    MenuComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    CreatorComponent,
    ExplorerComponent,
    ModeComponent,
    LobbyComponent,
    LobbyPlayerTableComponent,
    LobbySetsTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    })
  ],
  providers: [WebSocketService],
  bootstrap: [AppComponent]
})
export class AppModule {}

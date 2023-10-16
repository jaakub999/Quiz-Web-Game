import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteUrl } from "./shared/route-url";
import { AuthGuard } from "./guards/auth.guard";
import { HomeComponent } from "./components/home/home.component";
import { AuthComponent } from "./components/auth/auth.component";
import { RegisterComponent } from "./components/register/register.component";
import { ChangePasswordComponent } from "./components/change-password/change-password.component";
import { ExplorerComponent } from "./components/explorer/explorer.component";
import { CreatorComponent } from "./components/creator/creator.component";
import { ModeComponent } from "./components/mode/mode.component";
import { LobbyComponent } from "./components/lobby/lobby.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: RouteUrl.HOME,
    pathMatch: 'full'
  },
  {
    path: RouteUrl.HOME,
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RouteUrl.AUTH,
    component: AuthComponent
  },
  {
    path: RouteUrl.REGISTER,
    component: RegisterComponent
  },
  {
    path: RouteUrl.PASSWORD_TOKEN,
    component: ChangePasswordComponent
  },
  {
    path: RouteUrl.EXPLORER,
    component: ExplorerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RouteUrl.CREATOR + '/:key_id',
    component: CreatorComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RouteUrl.MODE,
    component: ModeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RouteUrl.LOBBY + '/:code',
    component: LobbyComponent,
    canActivate: [AuthGuard]
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

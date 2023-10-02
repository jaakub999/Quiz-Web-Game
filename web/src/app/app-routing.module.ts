import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteUrl } from "./shared/route-url";
import { AuthGuard } from "./guards/auth.guard";
import { HomeComponent } from "./components/home/home.component";
import { AuthComponent } from "./components/auth/auth.component";
import { RegisterComponent } from "./components/register/register.component";
import { ChangePasswordComponent } from "./components/change-password/change-password.component";
import { QuestionSetExplorerComponent } from "./components/question-set-explorer/question-set-explorer.component";
import { QuestionSetCreatorComponent } from "./components/question-set-creator/question-set-creator.component";

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
    component: QuestionSetExplorerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RouteUrl.CREATOR,
    component: QuestionSetCreatorComponent,
    canActivate: [AuthGuard]
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { RouteUrl } from "../shared/route-url";

@Injectable({
  providedIn: "root"
})
export class AuthGuard {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.authService.isLoggedIn()) {
      return true;
    }

    this.router.navigate([RouteUrl.AUTH], {
      queryParams: { returnUrl: state.url }
    });

    return false;
  }
}

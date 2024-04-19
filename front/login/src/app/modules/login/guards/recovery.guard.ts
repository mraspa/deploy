import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { RecoveryService } from "../../auth/services/recovery.service";
import { inject } from "@angular/core";




export const canActivateRecovery: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ) => {
    const router = inject(Router);

    return inject(RecoveryService).RecoveryActived() ? true : router.navigate(["/recovery"]);
  };
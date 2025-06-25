import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { provideToastr } from "ngx-toastr";
import { provideHttpClient } from '@angular/common/http';

import { routes } from './app.routes';
import { AuthInterceptor } from "@shared/guards/auth.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideToastr({
      timeOut: 3000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }),
    importProvidersFrom(BrowserAnimationsModule, AuthInterceptor),
    provideHttpClient(),
    provideRouter(routes)
  ]
};

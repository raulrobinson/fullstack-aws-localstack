import { Component, inject, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { ToastComponent } from "../../components/toast/toast.component";
import { ToastrService } from "ngx-toastr";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    ToastComponent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  captchaInput: string = '';
  captchaSession: string = '';
  captchaSvg: string = '';

  toastMessage = '';
  toastType: 'success' | 'error' = 'success';
  toastVisible = false;

  public readonly router = inject(Router);
  public readonly toast = inject(ToastrService);
  public readonly http = inject(HttpClient);
  public readonly sanitizer = inject(DomSanitizer);

  private readonly CAPTCHA_ENDPOINT = 'https://captcha-svc.vercel.app/api/v2/captcha';

  ngOnInit() {
    this.loadCaptcha();
  }

  loadCaptcha() {
    this.http.get<any>(this.CAPTCHA_ENDPOINT, {
      withCredentials: true
    }).subscribe({
      next: (response) => {
        this.captchaSvg = response.svg;
        this.captchaSession = response.sessionId || response.text;
      },
      error: () => this.toast.error('Error al cargar el CAPTCHA')
    });
  }

  get sanitizedSvg(): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(this.captchaSvg);
  }

  async login() {
    /*const isCaptchaValid = await this.validateCaptcha();
    if (!isCaptchaValid) {
      this.toast.error('CAPTCHA inválido ❌');
      this.loadCaptcha(); // recargar
      return;
    }*/

    if (this.username === 'admin' && this.password === 'admin123') {
      localStorage.setItem('auth', 'true');
      this.toast.success('Inicio de sesión exitoso ✅');
      setTimeout(() => this.router.navigate(['/']), 1000);
    } else {
      this.toast.error('Credenciales inválidas ❌');
    }
  }

  async validateCaptcha(): Promise<boolean> {
    try {
      await firstValueFrom(this.http.post(this.CAPTCHA_ENDPOINT, {
        captchaInput: this.captchaInput,
        captchaSession: this.captchaSession
      }, {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials: true
      }));
      return true;
    } catch {
      return false;
    }
  }

  getYear() {
    return new Date().getFullYear();
  }
}

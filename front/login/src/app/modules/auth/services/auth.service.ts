import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../interfaces/login-response.interface';
import { environment } from 'src/environments/environment';
import { JwtService } from './jwt.service';

interface User{
    email: string,
    password: string
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    private readonly apiUrl = environment.apiGateaway + "/v1/auth"

    private http = inject(HttpClient);
    private jwtService = inject(JwtService);

    login(user: User):Observable<LoginResponse>{
        return this.http.post<LoginResponse>(`${this.apiUrl}/login`, user)
                                .pipe(
                                    tap(res => this.jwtService.setToken(res.jwt))
                                )
    }
  
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpClientService } from './http-client.service';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Authority } from '../model/account';


export class JwtOutDto {
  public jwttoken: string;
  public authorities: Authority[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {



  constructor(
    private httpClient: HttpClient,
    private clientService: HttpClientService
  ) { }

  authenticate(username, password) {
    const url = environment.apiHost + 'authenticate';
    console.log('url:' + url);
    return this.httpClient.post<JwtOutDto>(url, { username, password }).pipe(
      map(
        userData => {
          this.setToken(userData.jwttoken);
          this.setUserInfo(username, userData.authorities);
          console.log('username:' + username);
          return userData;
        }
      )

    );
  }

  public setUserInfo(username: string, ats: Authority[]) {
    const auths = JSON.stringify(ats);
    localStorage.setItem('username', username);
    localStorage.setItem('auths', auths);
  }


  public setToken(tk: string) {
    const tokenStr = 'Bearer ' + tk;
    localStorage.setItem('token', tokenStr);
  }

  public getUserName(): string {
    return localStorage.getItem('username');
  }

  signup(username, password, email, refCode) {
    return this.httpClient.post<any>(environment.apiHost + '/register', { username, password, email, refCode, tobeEndUser: true });
  }

  isUserLoggedIn() {
    const user = localStorage.getItem('username');
    // console.log(!(user === null));
    return !(user === null);
  }

  public hasAuthority(a: Authority): boolean {
    return this.listAuthorities().includes(a);
  }

  public listAuthorities(): Authority[] {
    return this.isUserLoggedIn() ? JSON.parse(localStorage.getItem('auths')) : [];
  }

  public logOut():void {
    localStorage.removeItem('username');
    localStorage.removeItem('auths');
  }

}

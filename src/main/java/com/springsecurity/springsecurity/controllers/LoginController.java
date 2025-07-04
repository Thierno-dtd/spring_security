package com.springsecurity.springsecurity.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {

    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private LoginController(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    @GetMapping("/user")
    public String getUser() {
         return "user";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "user";
    }

    @GetMapping("/")
    public String getGithub(Principal user, @AuthenticationPrincipal OidcUser oidcUser) {
        StringBuffer userinfo = new StringBuffer();
        if(user instanceof UsernamePasswordAuthenticationToken) userinfo.append(getUserUsernamePasswordLoginInfo(user));
        else if(user instanceof OAuth2AuthenticationToken) userinfo.append(getOauth2LoginInfo(user, oidcUser));
        return userinfo.toString();
    }

    private StringBuffer getUserUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        if(token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome " + u.getUsername());
        }else usernameInfo.append("NA");
        return usernameInfo;
    }

    private StringBuffer getOauth2LoginInfo(Principal user, OidcUser oidcUser) {
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;
        OAuth2AuthorizedClient authClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(), token.getName());
        if(token.isAuthenticated()){
            Map<String, Object> userAttributes = ((DefaultOAuth2User) token.getPrincipal()).getAttributes();
            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, "+userAttributes.get("name") + "<br><br>");
            protectedInfo.append("e-mail : "+userAttributes.get("email") + "<br><br>");
            protectedInfo.append("Access token : " + userToken);

            if(oidcUser != null) {
                OidcIdToken idToken = oidcUser.getIdToken();
                if(idToken != null) {
                    protectedInfo.append("idToken value: " + idToken.getTokenValue()+"<br><br>");
                    protectedInfo.append("Token mapped values <br><br>");
                    Map<String, Object> claims = idToken.getClaims();
                    for (String key : claims.keySet()) {
                        protectedInfo.append("  " + key + ": " + claims.get(key)+"<br>");
                    }
                }
            }
        }else protectedInfo.append("NA");

        return protectedInfo;
    }
}

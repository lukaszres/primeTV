package com.lkre.services.cookieService;

import com.lkre.web.common.cookies.CookieExpiration;
import com.lkre.web.common.cookies.Cookies;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler {

    public static void setCookie(Cookies cookieName, String value, CookieExpiration expiration) {
        Cookie newCookie = new Cookie(cookieName.getValue(), value);
        newCookie.setMaxAge(expiration.getSeconds());
        newCookie.setPath("/");
        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addCookie(newCookie);
    }

    public static Cookie getCookie(Cookies cookieName) {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0)
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equalsIgnoreCase(cookieName.getValue()))
                    return userCookie;
            }
        return null;
    }
}
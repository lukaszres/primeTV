package com.lkre.services.cookieService;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler {

    public static void setCookie(String name, String value, int expiry) {
        Cookie newCookie = new Cookie(name, value);
        newCookie.setMaxAge(expiry);
        newCookie.setPath("/");
        HttpServletResponse response =
                (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addCookie(newCookie);
    }

    public static Cookie getCookie(String name) {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0)
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equalsIgnoreCase(name))
                    return userCookie;
            }
        return null;
    }
}
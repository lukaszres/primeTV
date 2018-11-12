package com.lkre.services.cookieService;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
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

}
package com.lkre.web.common.cookies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Cookies {
    GENRES_COOKIES("userSelectedGenres"),
    ACCEPTED_COOKIES("acceptedCookies");

    @Getter
    private String value;
}

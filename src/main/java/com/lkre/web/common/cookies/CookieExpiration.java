package com.lkre.web.common.cookies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CookieExpiration {
    MINUTE(60),
    HOUR(60 * 60),
    DAY(60 * 60 * 24),
    WEEK(60 * 60 * 24 * 7),
    MONTH(60 * 60 * 24 * 7 * 4);
    @Getter
    private int seconds;
}

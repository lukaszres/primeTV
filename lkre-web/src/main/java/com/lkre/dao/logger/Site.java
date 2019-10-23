package com.lkre.dao.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Site {
    INDEX("index.xhtml"),
    STATS("stats/stats.xhtml");
    @Getter
    private String value;
}

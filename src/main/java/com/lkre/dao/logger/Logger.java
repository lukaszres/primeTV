package com.lkre.dao.logger;

public interface Logger {
    void log(Site site, Activity activity, String details);

    int count();

    int count(Site site, Period period);
}

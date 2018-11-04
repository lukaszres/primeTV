package com.lkre.dao.logger;

import com.lkre.models.Log;

import java.util.List;

public interface Logger {
    void log(Site site, Activity activity, String details);

    List<Log> getLogs();
}

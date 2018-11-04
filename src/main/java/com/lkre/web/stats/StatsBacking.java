package com.lkre.web.stats;

import com.lkre.dao.logger.Activity;
import com.lkre.dao.logger.Logger;
import com.lkre.dao.logger.LoggerImpl;
import com.lkre.dao.logger.Site;
import com.lkre.models.Log;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class StatsBacking {
    private Logger logger = new LoggerImpl();
    @Getter
    @Setter
    private List<Log> logs;

    @PostConstruct
    public void init() {
        logger.log(Site.STATS, Activity.OPEN_SITE, null);
        logs = logger.getLogs();
    }
}

package com.lkre.web.stats;

import com.lkre.dao.logger.Activity;
import com.lkre.dao.logger.Logger;
import com.lkre.dao.logger.Period;
import com.lkre.dao.logger.Site;
import com.lkre.models.Log;
import com.lkre.models.SiteVisit;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.*;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class StatsBacking {
    @ManagedProperty(value = "#{logger}")
    private Logger logger = new Logger();
    private List<Log> logs;
    private List<Site> sites;
    private Site selectedSite;
    private Map<Site, SiteVisit> sitesVisits;

    @PostConstruct
    public void init() {
        logger.log(Site.STATS, Activity.OPEN_SITE, null);
        sites = new ArrayList<>(Arrays.asList(Site.values()));
        createSitesVisits();
    }

    private void createSitesVisits() {
        sitesVisits = new HashMap<>();
        sites.forEach(site -> {
            int today = logger.count(site, Period.TODAY);
            int week = logger.count(site, Period.WEEK);
            int month = logger.count(site, Period.MONTH);
            sitesVisits.put(site, new SiteVisit(today, week, month));
        });
    }

    public int totalVisits(Period period) {
        int visits = 0;
        for (Map.Entry<Site, SiteVisit> entry : sitesVisits.entrySet()) {
            switch (period) {
                case TODAY:
                    visits += entry.getValue().getToday();
                    break;
                case WEEK:
                    visits += entry.getValue().getWeek();
                    break;
                case MONTH:
                    visits += entry.getValue().getMonth();
                    break;
            }
        }
        return visits;
    }
}

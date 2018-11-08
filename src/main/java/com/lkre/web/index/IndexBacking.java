package com.lkre.web.index;

import com.lkre.dao.logger.Activity;
import com.lkre.dao.logger.Logger;
import com.lkre.dao.logger.Site;
import com.lkre.services.htmlService.HtmlService;
import com.lkre.services.htmlService.HtmlServiceFactory;
import com.lkre.services.htmlService.Seance;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class IndexBacking {
    private List<Seance> seances;
    private String seancesString;
    private List<String> genres = new ArrayList<>();
    private List<String> selectedGenres = new ArrayList<>();
    //    @ManagedProperty(value = "#{urlHtmlService}")
    private HtmlService htmlService = HtmlServiceFactory.createService();
    @ManagedProperty(value = "#{logger}")
    private Logger logger = new Logger();

    @PostConstruct
    public void init() {
        logger.log(Site.INDEX, Activity.OPEN_SITE, null);
    }

    public void downloadSeances(ActionEvent e) {

        String details = e.getComponent()
                .getId();
        logger.log(Site.INDEX, Activity.PUSH_BUTTON, details);

        seances = htmlService.main();
        StringBuilder stringBuilder = new StringBuilder();
        seances.forEach(seance -> stringBuilder.append(seance.toString())
                .append("\n")
        );
        seancesString = stringBuilder.toString();
        createGenres();
    }

    private void createGenres() {
        seances.forEach(seance -> {
            String genre = seance.getGenre();
            if (!genres.contains(genre)) {
                genres.add(genre);
            }
        });
    }
}
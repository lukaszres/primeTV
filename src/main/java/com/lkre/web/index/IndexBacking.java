package com.lkre.web.index;

import com.lkre.dao.logger.Activity;
import com.lkre.dao.logger.Logger;
import com.lkre.dao.logger.Site;
import com.lkre.services.cookieService.CookieHandler;
import com.lkre.services.htmlService.HtmlService;
import com.lkre.services.htmlService.HtmlServiceFactory;
import com.lkre.services.htmlService.Seance;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lkre.web.common.cookies.CookieExpiration.MONTH;
import static com.lkre.web.common.cookies.CookieExpiration.WEEK;
import static com.lkre.web.common.cookies.Cookies.ACCEPTED_COOKIES;
import static com.lkre.web.common.cookies.Cookies.GENRES_COOKIES;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class IndexBacking {
    private List<Seance> seances;
    private List<Seance> selectedSeances = new ArrayList<>();
    private String seancesString;
    private List<String> genres = new ArrayList<>();
    private List<String> selectedGenres = new ArrayList<>();
    private HtmlService htmlService = HtmlServiceFactory.createService();
    @ManagedProperty(value = "#{logger}")
    private Logger logger = new Logger();
    private boolean isCookiesAccepted;

    @PostConstruct
    public void init() {
        logger.log(Site.INDEX, Activity.OPEN_SITE, null);
        Cookie acceptedCookie = CookieHandler.getCookie(ACCEPTED_COOKIES);
        isCookiesAccepted = false;
        if (acceptedCookie != null) {
            isCookiesAccepted = acceptedCookie.getValue().equalsIgnoreCase("true");
        }
    }

    public void downloadSeances(ActionEvent e) {
        String details = e.getComponent()
                .getId();
        logger.log(Site.INDEX, Activity.PUSH_BUTTON, details);
        seances = htmlService.downloadSeances();
        createSeancesString();
        createGenres();
        importSelectedGenres();
        updateTextArea();
    }

    private void createSeancesString() {
        StringBuilder stringBuilder = new StringBuilder();
        selectedSeances.forEach(seance -> stringBuilder.append(seance.toString())
                .append("\n")
        );
        seancesString = stringBuilder.toString();
    }

    private void createGenres() {
        seances.forEach(seance -> {
            String genre = seance.getGenre();
            if (!genres.contains(genre)) {
                genres.add(genre);
            }
        });
        Collections.sort(genres);
    }

    private void createSelectedSeances() {
        selectedSeances.clear();
        seances.forEach(seance -> selectedGenres.forEach(genre -> {
            if (seance.getGenre()
                    .equals(genre))
                selectedSeances.add(seance);
        }));
    }

    public void onSelectGenresChanged() {
        CookieHandler.setCookie(GENRES_COOKIES, selectedGenresString(), MONTH);
        updateTextArea();
    }

    private void updateTextArea() {
        createSelectedSeances();
        createSeancesString();
        RequestContext.getCurrentInstance().update("form_textArea");
    }

    private String selectedGenresString() {
        StringBuilder stringBuilder = new StringBuilder();
        selectedGenres.forEach(genre -> stringBuilder.append(prepareGenreString(genre)));
        String s = stringBuilder.toString();
        int length = s.length();
        if (length > 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    private String prepareGenreString(String genre) {
        if (genre != null && genre.length() > 0)
            return genre.replace(" ", "_").replace(";", "_:_") + "|";
        else
            return "";
    }

    private void importSelectedGenres() {
        Cookie cookie = CookieHandler.getCookie(GENRES_COOKIES);
        selectedGenres = prepareGenreList(cookie);
    }

    private List<String> prepareGenreList(Cookie cookie) {
        if (cookie != null) {
            String selectedGenresString = cookie.getValue();
            String[] split = selectedGenresString.split("\\|");
            List<String> selectedGenresList = new ArrayList<>();
            for (String genre : split) {
                String replace = genre.replace("_:_", ";").replace("_", " ");
                selectedGenresList.add(replace);
            }
            return selectedGenresList;
        } else
            return null;
    }

    public void onAcceptCookie(ActionEvent e) {

        isCookiesAccepted = true;
        CookieHandler.setCookie(ACCEPTED_COOKIES, "true", WEEK);
    }
}
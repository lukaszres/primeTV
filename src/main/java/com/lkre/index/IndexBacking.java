package com.lkre.index;

import com.lkre.services.htmlService.HtmlService;
import com.lkre.services.htmlService.Seance;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import java.util.List;

@Getter
@Setter
@ManagedBean
public class IndexBacking {

    private List<Seance> seances;
    private String seancesString;
    @ManagedProperty(value = "#{htmlService}")
    private HtmlService htmlService;

    public void downloadSeances(ActionEvent actionEvent) {
        seances = htmlService.main();
        StringBuilder stringBuilder = new StringBuilder();
        seances.forEach(seance -> stringBuilder.append(seance.toString()).append("\n")
        );
        seancesString = stringBuilder.toString();
    }
}
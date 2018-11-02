package com.lkre.index;

import com.lkre.services.htmlService.HtmlService;
import com.lkre.services.htmlService.Seance;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import java.util.List;

@Getter
@Setter
@ManagedBean
public class IndexBacking {

    private List<Seance> seances;
    private String firstName = "Johny";
    private String lastName = "Mnemonic";

    public void downloadSeances(ActionEvent actionEvent) {
        seances = HtmlService.main();
    }

    public String showGreeting() {
        return "Hello " + firstName + " " + lastName + "!";
    }
}
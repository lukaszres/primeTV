package pl.lkre.program.generator;

import pl.lkre.program.generator.model.Seance;

import java.util.List;

public interface HtmlService {
    List<Seance> downloadSeances();
}

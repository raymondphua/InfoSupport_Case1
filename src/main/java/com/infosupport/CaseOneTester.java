package com.infosupport;

import com.infosupport.Database.CursusRepository;
import com.infosupport.domain.*;

import java.time.LocalDate;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class CaseOneTester {

    public static void main(String[] args) {
        //CursistRepository.getAllBedrijven();
        //CursusRepository.getAllCursussen();
        //CursistRepository.createCursist(new Particulier(0, "Raymond", "Teststraatje", "Rotterdam", 27));

        CursusRepository.getAllCursussen();

        Cursus cursusTest = new Cursus("factuur", "factuur", 3);
        cursusTest.addStartDatumToCursus(LocalDate.of(2016, 10, 10));

        //CursusRepository.createCursus(cursusTest);

        //inschrijving factuur
        Cursist bedrijf = new Bedrijf(1, "Info Support", "Teststraat", "Testwoonplaats");

        Cursist offerte = new Bedrijf(2, 1);

        Cursist particulier1 = new Particulier(3, "Raymond", "Straat Raymond", "Woonplaats Raymond", 2);

        Cursist particulier2 = new Particulier(4, "Factuur x", "Straat x", "Woonplaats x", 2);

        Cursist particulier3 = new Particulier(5, "Factuur y", "Straat y", "Woonplaats y", 2);

        ((Bedrijf)offerte).addCursist(particulier1);
        ((Bedrijf)offerte).addCursist(particulier2);
        ((Bedrijf)offerte).addCursist(particulier3);

        ((Bedrijf)bedrijf).addCursist(offerte);

        Cursist alleenStaandeParticulier = new Particulier(6, "Alleen staand", "alleen staand", "alleen staand", 0);

        Cursus cursus = new Cursus("JAVA", "Java Programmeren", 4);
        cursus.addStartDatumToCursus(LocalDate.of(2016, 10, 02));

        Inschrijving inschrijving = new Inschrijving();
        inschrijving.addCursus(cursus, LocalDate.of(2016, 10, 10));
        inschrijving.addCursisten(cursus, bedrijf);
        inschrijving.addCursisten(cursus, alleenStaandeParticulier);

        Factuur factuur = new Factuur();
        factuur.addInschrijving(inschrijving);
        factuur.printInfoOfWeek(41);

        factuur.printInfoOfCursist(alleenStaandeParticulier);
    }
}

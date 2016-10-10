package com.infosupport.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class Factuur {

    private int id;
    private List<Cursus> cursusList;
    private List<Cursist> cursistList;

    public Factuur() {
        cursusList = new ArrayList<>();
        cursistList = new ArrayList<>();
    }

    public List<Cursist> getCursisten() {
        return cursistList;
    }

    public List<Cursus> getCursussen() {
        return cursusList;
    }

    public void printInfo() {
        cursusList.stream().forEach(c -> {
            System.out.println(c.getTitel());
            c.getCursists().stream().forEach(cursist -> {
                if (cursist instanceof Particulier) {
                    System.out.println("Particulier: " + cursist.getNaam());
                    System.out.println("Factuur adres: " + cursist.getAdres() + " " + cursist.woonplaats);
                }
            });
        });
    }
}

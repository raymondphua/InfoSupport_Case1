package com.infosupport.domain;

import com.infosupport.exceptions.IllegalStartDateException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Raymond Phua on 9-10-2016.
 */
@Data
@NoArgsConstructor
public class Cursus {

    private int code;
    private String titel;
    private int duurtijd;
    private List<LocalDateTime> startDatums;
    private List<Cursist> cursists;

    //TODO: hashmap<weekNr, List<cursist>> of hashmap<Week, List<Cursist>> week = weeknr + datum

    public Cursus(int code, String titel, int duurtijd) {
        this.code = code;
        this.titel = titel;
        this.duurtijd = duurtijd;
        startDatums = new ArrayList<>();
        cursists = new ArrayList<>();
    }

    public void addCursistToCursus(Cursist cursist) {
        cursists.add(cursist);
    }

    public void addStartDatumToCursus(LocalDateTime startDatum) {
        startDatums.add(startDatum);
    }

    public boolean getStartDatumFromWeek(int week) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        LocalDateTime dateFromWeekFound = startDatums.stream().filter(s -> (s.get(woy) - 1) == week).findFirst().orElse(null);

        if (dateFromWeekFound != null) {
            return true;
        }

        return false;
    }
}

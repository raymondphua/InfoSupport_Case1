package com.infosupport.domain;

import com.infosupport.exceptions.IllegalStartDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Created by Raymond Phua on 9-10-2016.
 */
public class CursusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Cursus cursus;

    @Before
    public void init() {
        cursus = new Cursus(1, "Java8", 4);
    }

    @Test
    public void add_Cursist_To_Cursus_Should_Increase_Size_By_One() {

        int sizeBefore = cursus.getCursists().size();
        cursus.addCursistToCursus(new Bedrijf(1, 1));

        int sizeAfter = cursus.getCursists().size();

        assertThat(sizeAfter, is(sizeBefore + 1));
    }

    @Test
    public void add_Particulier_To_Cursus_Should_Not_Fail() {
        Particulier particulier = new Particulier(1, "naam", "adres", "woonplaats", 0);
        cursus.addCursistToCursus(particulier);

        Cursist cursist = cursus.getCursists().get(0);

        assertThat(cursist, is(particulier));
    }

    @Test
    public void add_Start_Datum_To_Cursus() {
        cursus.addStartDatumToCursus(LocalDateTime.of(2016, 11, 01, 0, 0));

        LocalDateTime localDateTime = cursus.getStartDatums().get(0);

        assertThat(localDateTime, is(LocalDateTime.of(2016, 11, 01, 0, 0)));
    }

    @Test
    public void given_Week_Number_Should_Give_Correct_Date() {
        LocalDateTime dateFromWeek41 = LocalDateTime.of(2016, 10, 10, 0, 0);
        cursus.addStartDatumToCursus(dateFromWeek41);

        boolean exists = cursus.getStartDatumFromWeek(41);

        assertTrue(exists);
    }
//    @Test
//    public void invalidStartDateShouldThrowException() throws IllegalStartDateException {
//        cursus.setStartDatum(LocalDateTime.of(2016,10,02,0,0));
//        thrown.expect(IllegalStartDateException.class);
//        thrown.expectCause(is(IllegalStartDateException.class));
//        cursus.validate();
//    }
}
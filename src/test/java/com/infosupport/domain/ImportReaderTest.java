package com.infosupport.domain;

import com.infosupport.exceptions.ImportException;
import com.infosupport.exceptions.IncorrectFormatException;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by Raymond Phua on 11-10-2016.
 */
public class ImportReaderTest {

    private ImportReader reader;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        reader = new ImportReader();
    }

    @Test
    public void incorrect_Format() throws ImportException {

        String file = incorrectFormat();

        thrown.expect(ImportException.class);
        thrown.expectMessage("Incorrect format");
        thrown.expectCause(is(IncorrectFormatException.class));
        List<Cursus> cursus = reader.importCursus(file);
    }

    @Test
    public void correct_Format() throws ImportException {

        String file = getFile();

        List<Cursus> cursus = reader.importCursus(file);

        assertThat(cursus.get(0), is(not(nullValue())));
    }

    @Test
    public void incorrect_Duur() throws ImportException {
        String file = incorrectDuur();

        thrown.expect(ImportException.class);
        thrown.expectMessage("Incorrect format");
        thrown.expectCause(is(IncorrectFormatException.class));
        List<Cursus> cursus = reader.importCursus(file);
    }

    @Test
    public void incorrect_Date() throws ImportException {
        String file = incorrectDate();

        thrown.expect(ImportException.class);
        thrown.expectMessage("Incorrect format");
        thrown.expectCause(is(IncorrectFormatException.class));
        List<Cursus> cursus = reader.importCursus(file);
    }

    @Test
    public void incorrect_Amount_Of_Properties() throws ImportException {
        String file = incorrectAmountOfProperties();

        thrown.expect(ImportException.class);
        thrown.expectMessage("Incorrect format");
        thrown.expectCause(is(IncorrectFormatException.class));
        List<Cursus> cursus = reader.importCursus(file);
    }

    @Test
    public void import_Single_Cursus_From_File() throws ImportException {

        String file = getFile();

        List<Cursus> cursus = reader.importCursus(file);

        Cursus expectedCursus = new Cursus("CNETIN", "C# Programmeren", 5);
        expectedCursus.addStartDatumToCursus(LocalDate.of(2013, 10, 14));

        assertThat(cursus.get(0), is(expectedCursus));
    }

    @Test
    public void import_Multiple_Cursussen_From_File() throws ImportException {

        String file = getMultipleCursussen();

        List<Cursus> cursus = reader.importCursus(file);

        List<Cursus> expected = new ArrayList<>();
        Cursus a = new Cursus("CNETIN", "C# Programmeren", 5);
        a.addStartDatumToCursus(LocalDate.of(2013, 10, 14));

        Cursus b = new Cursus("ADCSB", "Advanced C#", 2);
        b.addStartDatumToCursus(LocalDate.of(2013, 10, 21));

        expected.add(a);
        expected.add(b);

        assertThat(cursus, is(expected));
    }

    @Test
    public void import_Duplicate_Cursussen_From_File_Should_Not_Add() throws ImportException{

        String file = getDuplicate();

        List<Cursus> cursus = reader.importCursus(file);

        assertThat(cursus.size(), is(1));
    }

    private String getMultipleCursussen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");
        sb.append("\n");
        sb.append("Titel: Advanced C#");
        sb.append("\n");
        sb.append("Cursuscode: ADCSB");
        sb.append("\n");
        sb.append("Duur: 2 dagen");
        sb.append("\n");
        sb.append("Startdatum: 21/10/2013");

        return sb.toString();
    }

    private String getDuplicate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");
        sb.append("\n");
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");

        return sb.toString();
    }

    private String getFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");

        return sb.toString();
    }

    private String incorrectFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");

        return sb.toString();
    }

    private String incorrectDuur() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5");
        sb.append("\n");
        sb.append("Startdatum: 14/10/2013");
        sb.append("\n");

        return sb.toString();
    }

    private String incorrectDate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");
        sb.append("\n");
        sb.append("Cursuscode: CNETIN");
        sb.append("\n");
        sb.append("Duur: 5 dagen");
        sb.append("\n");
        sb.append("Startdatum: 14-10-2013");
        sb.append("\n");

        return sb.toString();
    }

    private String incorrectAmountOfProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: C# Programmeren");

        return sb.toString();
    }
}
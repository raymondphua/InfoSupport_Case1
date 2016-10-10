package com.infosupport.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class FactuurTest {

    private Factuur factuur;

    @Before
    public void init() {
        factuur = new Factuur();
    }

    @Test
    public void factuur_Has_Cursisten() {
        List<Cursist> cursisten = factuur.getCursisten();
        cursisten.add(new Particulier(1, "Raymond", "Test straat 1", "Rotterdam", 1));
        assertThat(cursisten.size(), is(not(0)));
    }
}
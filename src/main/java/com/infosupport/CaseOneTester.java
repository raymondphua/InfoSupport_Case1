package com.infosupport;

import com.infosupport.Database.CursistRepository;
import com.infosupport.domain.Particulier;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class CaseOneTester {

    public static void main(String[] args) {
        CursistRepository.getAllBedrijven();

        //CursistRepository.createCursist(new Particulier(0, "Raymond", "Teststraatje", "Rotterdam", 27));
    }
}

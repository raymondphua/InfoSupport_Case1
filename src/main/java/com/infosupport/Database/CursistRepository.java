package com.infosupport.Database;

import com.infosupport.domain.Bedrijf;
import com.infosupport.domain.Cursist;
import com.infosupport.domain.Particulier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class CursistRepository extends Database {

    public static List<Bedrijf> getAllBedrijven() {
        getConnection();

        List<Bedrijf> bedrijven = new ArrayList<>();
        try {
            List<Cursist> allCursisten = new ArrayList<>();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "SELECT * FROM CURSISTS";

            System.out.println("\n Executing query: " + query);
            ResultSet rset = stmt.executeQuery(query);

            while(rset.next()) {
                allCursisten.add(mapToCursist(rset));
            }

            List<Cursist> fixedRelationsCursists = fixRelations(allCursisten);

            bedrijven = (List<Bedrijf>)(List<?>) fixedRelationsCursists;
            stmt.close();
            //return products;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bedrijven;
    }

    public static void createCursist(Cursist cursist) {
        getConnection();

        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int offerte = parseBoolToInt(cursist.isOfferte());
            String query = "";

            if (cursist.getParentId() == 0) {
                query = "INSERT INTO CURSISTS (NAAM, ADRES, WOONPLAATS, OFFERTE) " +
                        "VALUES ('" + cursist.getNaam() + "', '" + cursist.getAdres() + "', '" + cursist.getWoonplaats() + "'" +
                        ", '" + offerte + "')";
            } else {
                query = "INSERT INTO CURSISTS (NAAM, ADRES, WOONPLAATS, PARENT, OFFERTE) " +
                        "VALUES ('" + cursist.getNaam() + "', '" + cursist.getAdres() + "', '" + cursist.getWoonplaats() + "'" +
                        ", '" + cursist.getParentId() + "', '" + offerte + "')";
            }

            System.out.println("\n Executing query: " + query);
            ResultSet rset = stmt.executeQuery(query);

            //return products;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static Cursist mapToCursist(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("id");
        String naam = resultSet.getString("naam");
        String adres = resultSet.getString("adres");
        String woonplaats = resultSet.getString("woonplaats");
        int parentId = resultSet.getInt("parent");
        Cursist cursist;

        if (resultSet.getBoolean("OFFERTE")){
            cursist = new Bedrijf(id, parentId);
        } else if (resultSet.getInt("parent") == 0) {
            cursist = new Bedrijf(id, naam, adres, woonplaats);
        } else {
            cursist = new Particulier(id, naam, adres, woonplaats, parentId);
        }

        return cursist;
    }

    private static List<Cursist> fixRelations(List<Cursist> cursists) {

        List<Cursist> fixedCursists = new ArrayList<>();

        List<Particulier> particulieren = (List<Particulier>)(List<?>) cursists.stream().filter(c -> c instanceof Particulier).collect(Collectors.toList());

        List<Bedrijf> bedrijven = (List<Bedrijf>)(List<?>) cursists.stream().filter(c -> c instanceof Bedrijf && !c.isOfferte()).collect(Collectors.toList());

        List<Bedrijf> offertes = (List<Bedrijf>)(List<?>) cursists.stream().filter(b -> b.isOfferte()).collect(Collectors.toList());

        //List<Bedrijf> parentChildOfferteParticulier = offertes.stream().filter(o -> particulieren.stream().filter(p -> o.getId() == p.getParentId()).count() > 0).collect(Collectors.toList());

        //for each offerte, add the correct particulier to it
        for (Bedrijf o : offertes) {
            for (Particulier p : particulieren) {
                if (o.getId() == p.getParentId()) {
                    o.addCursist(p);
                }
            }
        }

        //for each bedrijf add the correct offerte
        bedrijven.stream().forEach(b -> offertes.stream().forEach(o -> {
            if (b.getId() == o.getParentId()) {
                b.addCursist(o);
            }
        }));

        fixedCursists = (List<Cursist>)(List<?>) bedrijven;
        //TODO: IN STREAMS
//        List<Bedrijf> parentChildOfferteParticulier = offertes.stream().flatMap(o -> particulieren.stream().map(p -> {
//            if (o.getId() == p.getParentId()) {
//                o.addCursist(p);
//            }
//
//            return o;
//        })).collect(Collectors.toList());
        //).filter(o -> particulieren.stream().filter(p -> o.getId() == p.getParentId()).count() > 0).collect(Collectors.toList());

        return fixedCursists;
    }

    private static int parseBoolToInt(boolean bool) {
        if (bool) {
            return 1;
        }

        return 0;
    }
}

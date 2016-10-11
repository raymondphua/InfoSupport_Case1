package com.infosupport.Database;

import com.infosupport.domain.Cursus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Raymond Phua on 10-10-2016.
 */
public class CursusRepository extends Database {

    public static List<Cursus> getAllCursussen() {
        getConnection();

        List<Cursus> cursussen = new ArrayList<>();
        try {
            String query = "SELECT * FROM CURSUS c JOIN START_DATUMS sd ON c.CODE = sd.CURSUS_FK";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            System.out.println("\n Executing query: " + query);
            ResultSet rset = preparedStatement.executeQuery();

            cursussen = mapToCursusList(rset);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cursussen;
    }

    public static void createCursus(Cursus cursus) {
        getConnection();

        try {
            String query = "INSERT INTO CURSUS (CODE, TITEL, DUURTIJD) VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, cursus.getCode());
            preparedStatement.setString(2, cursus.getTitel());
            preparedStatement.setInt(3, cursus.getDuurtijd());

            System.out.println("Executing query: " + query);
            preparedStatement.executeQuery();

            for (LocalDate localDate : cursus.getStartDatums()) {
                Date date = Date.valueOf(localDate);

                String queryDatum = "INSERT INTO START_DATUMS (START_DATUM, CURSUS_FK) VALUES(?, ?)";

                PreparedStatement preparedStatement1 = conn.prepareStatement(queryDatum);
                preparedStatement1.setDate(1, date);
                preparedStatement1.setString(2, cursus.getCode());

                System.out.println("Executing query: " + queryDatum);
                preparedStatement1.executeQuery();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static Cursus mapToCursus(ResultSet rset, Cursus cursus) throws SQLException {

        if (cursus != null) {
            LocalDate date = rset.getDate("start_datum").toLocalDate();
            cursus.addStartDatumToCursus(date);
        } else {
            String code = rset.getString("code");
            String titel = rset.getString("titel");
            int duurtijd = rset.getInt("duurtijd");
            LocalDate date = rset.getDate("start_datum").toLocalDate();

            cursus = new Cursus(code, titel, duurtijd);
            cursus.addStartDatumToCursus(date);
        }

        return cursus;
    }

    private static List<Cursus> mapToCursusList(ResultSet rset) throws SQLException {
        Map<String, Cursus> foundCursus = new HashMap<>();

        while (rset.next()) {
            Cursus existingCursus = foundCursus.get(rset.getString("code"));

            Cursus cursus = mapToCursus(rset, existingCursus);

            foundCursus.put(rset.getString("code"), cursus);
        }

        return foundCursus.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}

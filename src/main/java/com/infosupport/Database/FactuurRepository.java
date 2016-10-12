package com.infosupport.Database;

import com.infosupport.domain.Cursist;
import com.infosupport.domain.Cursus;
import com.infosupport.domain.Factuur;
import com.infosupport.domain.Inschrijving;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raymond Phua on 11-10-2016.
 */
@NoArgsConstructor
public class FactuurRepository extends Database{

    public List<Factuur> getAllFacturen() {
        getConnection();

        List<Factuur> facturen = new ArrayList<>();
        try {
            String query = "SELECT * FROM FACTUUR f JOIN INSCHRIJVING i ON f.INSCHRIJVINGID = i.ID " +
                    "JOIN CURSUS c ON i.CURSUSID = c.CODE JOIN CURSISTS cs ON i.CURSISTID = cs.ID";


            PreparedStatement preparedStatement = conn.prepareStatement(query);
            System.out.println("\n Executing query: " + query);
            ResultSet rset = preparedStatement.executeQuery();

            while(rset.next()) {
                facturen.add(mapper.mapToFactuur(rset, facturen));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return facturen;
    }

    public void createFactuur(Factuur factuur) {
        getConnection();

        try {
            for (Inschrijving inschrijving : factuur.getInschrijvingen()) {

                String query = "INSERT INTO FACTUUR (FACTUURID, INSCHRIJVINGID) VALUES(?, ?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                preparedStatement.setInt(1, factuur.getId());
                preparedStatement.setInt(2, inschrijving.getId());

                System.out.println("Executing query: " + query);
                preparedStatement.executeQuery();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}

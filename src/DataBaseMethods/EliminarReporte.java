package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class EliminarReporte {

    public static String obtenerFechaPorFolio(Connection connection, String folio) {
        String query = "SELECT fecha FROM reporteoperadores WHERE folio = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, folio);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Date fechaSql = resultSet.getDate("fecha");
                if (fechaSql != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(fechaSql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean eliminarReportePorFolio(Connection connection, String folio) {
        String query = "DELETE FROM reporteoperadores WHERE folio = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, folio);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

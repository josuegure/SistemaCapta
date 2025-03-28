package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import javafx.scene.control.Alert;

public class InsertarReporte {

    public void insertarReporte(String folio, String empleado, String tipoAtendido, String contacto, String genero, String calle, String colonia, String referencia, String clasificacion, String grupoAtencion, String diaSemana, Date fecha, String especificaciones, String resultados) {

    String sql = "INSERT INTO reporteoperadores (folio, Numero_operador, tipo_atendido, contacto, genero, calle, colonia, referencia, clasificacion, grupo_atencion, dia_semana, fecha, especificaciones, resultados) VALUES (?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, folio); // Folio
            stmt.setString(2, empleado); // Tipo atendido
            stmt.setString(3, tipoAtendido); // Tipo atendido
            stmt.setString(4, contacto); // Contacto
            stmt.setString(5, genero); // Género
            stmt.setString(6, calle); // Calle
            stmt.setString(7, colonia); // Colonia
            stmt.setString(8, referencia); // Referencia
            stmt.setString(9, clasificacion); // Clasificación
            stmt.setString(10, grupoAtencion); // Grupo de atención
            stmt.setString(11, diaSemana); // Día de la semana
            stmt.setDate(12, fecha); // Fecha (corregido)
            stmt.setString(13, especificaciones); // Especificaciones
            stmt.setString(14, resultados); // Resultados

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro exitoso", "El reporte ha sido registrado correctamente.");
            } else {
                functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "No se pudo registrar el reporte.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
            functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de base de datos", "Hubo un problema al registrar el reporte, verifica el folio o el número de operador" /*+ e.getMessage()*/);
        }   
    }  
    
        public void insertarReporteCompleto(
     int folio, String numeroParamedico, String estado, String delegacion, 
        String asignacion, String motivo, Date fecha, String diaSemana, 
        String calle, String colonia, String municipio, String lugarOcurrencia, 
        String horaLlamada, String horaDespacho, String horaArribo, String horaDisponible,
        // Datos de vehículo
        String numMoto, String operador, String prestadorServicio,
        // Datos del paciente
        String nombrePaciente, String genero, String domicilio, String coloniaPaciente, 
        String municipioPaciente, String telefono, String ocupacion, 
        String derechoHabiente, String edad,
        //Causa traumatica
        String  agenteCausal, String especifique, String origenProbable, String especifiqueCausa, String primeraVez, String subsecuente
        ) {

        // Query para insertar en reporteparamedicos
        String sqlReporte = "INSERT INTO reporteparamedicos (folio, numero_paramedico, estado, delegacion, asignacion, "
                + "motivo, fecha, diasemana, calle, colonia, municipio, lugarocurrencia, horallamada, horadespacho, horaarribo, horadisponible) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Query para insertar en vehiculo
        String sqlVehiculo = "INSERT INTO vehiculo (Num_moto, operador, prestador_servicio, folio) VALUES (?, ?, ?, ?)";

        // Query para insertar en datos_paciente
        String sqlPaciente = "INSERT INTO datos_paciente (nombre, genero, domicilio, colonia, municipio, telefono, ocupacion, derecho_habiente, edad, folio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sqlAgenteCausal = "INSERT INTO causa_traumatica (agente_causal, especifique, origen_probable, "
                   + "especifique_origen, primera_vez, subsecuente, folio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.connect()) {
            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar en reporteparamedicos
            try (PreparedStatement stmtReporte = conn.prepareStatement(sqlReporte)) {
                stmtReporte.setInt(1, folio);
                stmtReporte.setString(2, numeroParamedico);
                stmtReporte.setString(3, estado);
                stmtReporte.setString(4, delegacion);
                stmtReporte.setString(5, asignacion);
                stmtReporte.setString(6, motivo);
                stmtReporte.setDate(7, fecha);
                stmtReporte.setString(8, diaSemana);
                stmtReporte.setString(9, calle);
                stmtReporte.setString(10, colonia);
                stmtReporte.setString(11, municipio);
                stmtReporte.setString(12, lugarOcurrencia);
                stmtReporte.setString(13, horaLlamada);
                stmtReporte.setString(14, horaDespacho);
                stmtReporte.setString(15, horaArribo);
                stmtReporte.setString(16, horaDisponible);
                stmtReporte.executeUpdate();
            }

            // Insertar en vehiculo
            try (PreparedStatement stmtVehiculo = conn.prepareStatement(sqlVehiculo)) {
                stmtVehiculo.setString(1, numMoto);
                stmtVehiculo.setString(2, operador);
                stmtVehiculo.setString(3, prestadorServicio);
                stmtVehiculo.setInt(4, folio);
                stmtVehiculo.executeUpdate();
            }

            // Insertar en datos_paciente
            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setString(1, nombrePaciente);
                stmtPaciente.setString(2, genero);
                stmtPaciente.setString(3, domicilio);
                stmtPaciente.setString(4, coloniaPaciente);
                stmtPaciente.setString(5, municipioPaciente);
                stmtPaciente.setString(6, telefono);
                stmtPaciente.setString(7, ocupacion);
                stmtPaciente.setString(8, derechoHabiente);
                stmtPaciente.setString(9, edad);
                stmtPaciente.setInt(10, folio);
                stmtPaciente.executeUpdate();
            }
            // Insertar casusa traumatica
            try (PreparedStatement stmtAgenteCausal = conn.prepareStatement(sqlAgenteCausal)) {
            stmtAgenteCausal.setString(1, agenteCausal);
            stmtAgenteCausal.setString(2, especifique);
            stmtAgenteCausal.setString(3, origenProbable);
            stmtAgenteCausal.setString(4, especifiqueCausa);
            stmtAgenteCausal.setString(5, primeraVez);
            stmtAgenteCausal.setString(6, subsecuente);
            stmtAgenteCausal.setInt(7, folio);
            stmtAgenteCausal.executeUpdate();
            }
            
            conn.commit(); // Confirmar transacción

          /*  functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro exitoso",
                    "El reporte se ha sido registrados correctamente.");*/

        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());

            functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de base de datos",
                    "Hubo un problema al registrar el reporte, verifica los datos ingresados.");
        }
    }
        public void insertarEvaluacion(
    int folio, String nivelConsciencia, String viaAerea, String reflejoDeglucion, 
    String observacion, String auscultacion, String presenciaPulsos, String piel, 
    String calidad, String caracteristicas, String hemitorax, String sitio, 
    String tipoAlergia, String atendidoPrimerCorrespondiente, String medicamentosIngeridos, 
    String cirugiasPrevias, String condicionPaciente, String prioridad,
     String controlHemorragia, String viasVenosas,String sitioAplicacion, String tipoSoluciones, String manejo) {

    String sqlEvaluacion = "INSERT INTO evaluacion (nivel_consciencia, via_aerea, reflejo_deglucion, observacion, auscultacion, "
                         + "presencia_pulsos, piel, calidad, caracteristicas, hemitorax, sitio, tipo_alergia, "
                         + "atendido_primer_correspondiente, medicamentos_ingeridos, cirugias_previas, condicion_paciente, "
                         + "prioridad, folio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
String sqlTratamiento = "INSERT INTO tratamiento (control_hemorragia, vias_venosas, sitio_aplicacion, "
                           + "tipo_soluciones, manejo, folio) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = ConnectionDB.connect();
         PreparedStatement stmtEvaluacion = conn.prepareStatement(sqlEvaluacion)) {

        stmtEvaluacion.setString(1, nivelConsciencia);
        stmtEvaluacion.setString(2, viaAerea);
        stmtEvaluacion.setString(3, reflejoDeglucion);
        stmtEvaluacion.setString(4, observacion);
        stmtEvaluacion.setString(5, auscultacion);
        stmtEvaluacion.setString(6, presenciaPulsos);
        stmtEvaluacion.setString(7, piel);
        stmtEvaluacion.setString(8, calidad);
        stmtEvaluacion.setString(9, caracteristicas);
        stmtEvaluacion.setString(10, hemitorax);
        stmtEvaluacion.setString(11, sitio);
        stmtEvaluacion.setString(12, tipoAlergia);
        stmtEvaluacion.setString(13, atendidoPrimerCorrespondiente);
        stmtEvaluacion.setString(14, medicamentosIngeridos);
        stmtEvaluacion.setString(15, cirugiasPrevias);
        stmtEvaluacion.setString(16, condicionPaciente);
        stmtEvaluacion.setString(17, prioridad);
        stmtEvaluacion.setInt(18, folio);

        stmtEvaluacion.executeUpdate();
        

      /*  functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro exitoso",
                "La evaluación ha sido registrada correctamente.");*/
  try (
         PreparedStatement stmtTratamiento = conn.prepareStatement(sqlTratamiento)) {

        stmtTratamiento.setString(1, controlHemorragia);
        stmtTratamiento.setString(2, viasVenosas);
        stmtTratamiento.setString(3, sitioAplicacion);
        stmtTratamiento.setString(4, tipoSoluciones);
        stmtTratamiento.setString(5, manejo);
        stmtTratamiento.setInt(6, folio);

        stmtTratamiento.executeUpdate();
       
        
    }
    }
  
    
    catch (SQLException e) {
        System.out.println("Error al insertar evaluación: " + e.getMessage());

        functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de base de datos",
                "Hubo un problema al registrar la evaluación, verifica los datos ingresados.");
    }
}
   public boolean existeFolio(int folio) {
    String query = "SELECT COUNT(*) FROM reporteparamedicos WHERE folio = ?";
    try (Connection conn = ConnectionDB.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, folio);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar folio: " + e.getMessage());
    }
    return false;
}
}


    
    /*public void insertarReporteParamedicos(int folio, String numeroParamedico, String estado, String delegacion, 
                                       String asignacion, String motivo, Date fecha, String diaSemana, 
                                       String calle, String colonia, String municipio, String lugarOcurrencia, 
                                       String horaLlamada, String horaDespacho, String horaArribo, String horaDisponible) {

    String sql = "INSERT INTO reporteparamedicos (folio, numero_paramedico, estado, delegacion, asignacion, " +
                 "motivo, fecha, diasemana, calle, colonia, municipio, lugarocurrencia, horallamada, horadespacho, horaarribo, horadisponible) " +
                 "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = ConnectionDB.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, folio); // Folio (int, clave primaria)
        stmt.setString(2, numeroParamedico); // Número del paramédico
        stmt.setString(3, estado); // Estado
        stmt.setString(4, delegacion); // Delegación
        stmt.setString(5, asignacion); // Asignación
        stmt.setString(6, motivo); // Motivo
        stmt.setDate(7, fecha); 
        stmt.setString(8, diaSemana); // Día de la semana
        stmt.setString(9, calle); // Calle
        stmt.setString(10, colonia); // Colonia
        stmt.setString(11, municipio); // Municipio
        stmt.setString(12, lugarOcurrencia); // Lugar de ocurrencia
        stmt.setString(13, horaLlamada); // Hora de la llamada
        stmt.setString(14, horaDespacho); // Hora de la llamada
        stmt.setString(15, horaArribo); // Hora de la llamada
        stmt.setString(16, horaDisponible); // Hora de la llamada
        int filasInsertadas = stmt.executeUpdate();
        if (filasInsertadas > 0) {
            functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro exitoso", 
                "El reporte ha sido registrado correctamente.");
        } else {
            functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", 
                "No se pudo registrar el reporte.");
        }

    } catch (SQLException e) {
        System.out.println("Error al insertar datos: " + e.getMessage());
        functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error de base de datos", 
            "Hubo un problema al registrar el reporte, verifica el folio o los datos ingresados.");
    }
}*/
  


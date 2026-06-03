package edu.uptc.vista;

import edu.uptc.controlador.*;
import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import edu.uptc.servicio.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase principal del sistema de gestión de contratos públicos (SECOP II).
 * Contiene toda la interacción con el usuario mediante JOptionPane.
 * Es la única capa del sistema que usa interfaces gráficas.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Application {

    private static UsuarioServicio usuarioServicio     = new UsuarioServicio();
    private static ContratoServicio contratoServicio   = new ContratoServicio();
    private static ReporteServicio reporteServicio     = new ReporteServicio();

    private static UsuarioControlador usuarioControlador;
    private static ContratoControlador contratoControlador;
    private static ReporteControlador reporteControlador;
    private static SeguridadControlador seguridadControlador;

    /**
     * Punto de entrada. Inicializa datos de prueba y lanza el menú principal.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        cargarDatosDePrueba();

        SeguridadServicio seguridadServicio = new SeguridadServicio(usuarioServicio.getUsuarios());
        usuarioControlador     = new UsuarioControlador(usuarioServicio);
        contratoControlador    = new ContratoControlador(contratoServicio, reporteServicio);
        reporteControlador     = new ReporteControlador(reporteServicio);
        seguridadControlador   = new SeguridadControlador(seguridadServicio);

        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Iniciar Sesión", "Salir"};
            int op = JOptionPane.showOptionDialog(null,
                    "Bienvenido al Sistema de Contratos Públicos\nSECOP II - UPTC",
                    "Sistema de Contratos", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (op != 0) { continuar = false; continue; }

            // --- LOGIN: la vista captura datos y los pasa al controlador ---
            Usuario usuario = null;
            int intentos = 0;
            while (intentos < 3 && usuario == null) {
                String correo = JOptionPane.showInputDialog(null, "Correo electrónico:", "Iniciar Sesión", JOptionPane.PLAIN_MESSAGE);
                if (correo == null) break;
                String clave = JOptionPane.showInputDialog(null, "Contraseña:", "Iniciar Sesión", JOptionPane.PLAIN_MESSAGE);
                if (clave == null) break;

                usuario = seguridadControlador.iniciarSesion(correo.trim(), clave.trim());
                if (usuario == null) {
                    intentos++;
                    int restantes = 3 - intentos;
                    if (restantes > 0)
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intentos restantes: " + restantes, "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Acceso bloqueado.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (usuario == null) continue;

            JOptionPane.showMessageDialog(null, "Bienvenido/a, " + usuario.getNombre(), "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);

            switch (seguridadControlador.obtenerRol(usuario)) {
                case "ADMINISTRADOR": menuAdministrador(); break;
                case "CONTRATANTE":   menuContratante((Contratante) usuario); break;
                case "CONTRATISTA":   menuContratista(); break;
                default: JOptionPane.showMessageDialog(null, "Rol no reconocido.");
            }
        }
        JOptionPane.showMessageDialog(null, "Hasta pronto.", "SECOP II", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== MENÚ ADMINISTRADOR =====================

    /**
     * Menú del administrador: gestión completa de usuarios.
     */
    private static void menuAdministrador() {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Crear Usuario", "Consultar Usuario", "Actualizar Usuario", "Eliminar Usuario", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Administrador", "Administrador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0: vistaCrearUsuario(); break;
                case 1: vistaConsultarUsuario(); break;
                case 2: vistaActualizarUsuario(); break;
                case 3: vistaEliminarUsuario(); break;
                default: activo = false;
            }
        }
    }

    /** Captura datos y llama al controlador para crear un usuario. */
    private static void vistaCrearUsuario() {
        String[] tipos = {"Contratante", "Contratista"};
        int tipoIdx = JOptionPane.showOptionDialog(null, "¿Qué tipo de usuario desea crear?", "Crear Usuario",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipoIdx < 0) return;

        // Datos comunes
        String[] opcTP = {"NATURAL", "JURIDICA"};
        int tpIdx = JOptionPane.showOptionDialog(null, "Tipo de persona:", "Crear Usuario",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTP, opcTP[0]);
        if (tpIdx < 0) return;

        String[] opcTD = {"CC", "TI", "CE", "PASAPORTE", "NIT"};
        int tdIdx = JOptionPane.showOptionDialog(null, "Tipo de documento:", "Crear Usuario",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTD, opcTD[0]);
        if (tdIdx < 0) return;

        // Validar documento único antes de continuar
        String numDoc;
        while (true) {
            numDoc = JOptionPane.showInputDialog("Número de documento:");
            if (numDoc == null) return;
            if (numDoc.isBlank()) {
                JOptionPane.showMessageDialog(null, "El número de documento no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (usuarioControlador.existeDocumento(numDoc)) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un usuario con el documento " + numDoc + ".\nIngrese un número de documento diferente.",
                        "Documento Duplicado", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        String nombre  = JOptionPane.showInputDialog("Nombre completo:");
        if (nombre == null || nombre.isBlank()) return;
        String correo  = JOptionPane.showInputDialog("Correo electrónico:");
        if (correo == null || correo.isBlank()) return;
        String clave   = JOptionPane.showInputDialog("Contraseña:");
        if (clave == null || clave.isBlank()) return;
        String tel     = JOptionPane.showInputDialog("Teléfono:");
        if (tel == null || tel.isBlank()) return;
        String dir     = JOptionPane.showInputDialog("Dirección:");
        if (dir == null || dir.isBlank()) return;
        String ciudad  = JOptionPane.showInputDialog("Ciudad:");
        if (ciudad == null || ciudad.isBlank()) return;

        TipoPersona tp  = TipoPersona.valueOf(opcTP[tpIdx]);
        TipoDocumento td = TipoDocumento.valueOf(opcTD[tdIdx]);

        if (tipoIdx == 0) {
            String sector  = JOptionPane.showInputDialog("Sector de la entidad:");
            if (sector == null || sector.isBlank()) return;
            String[] opcNivel = {"MUNICIPAL", "DEPARTAMENTAL", "NACIONAL"};
            int nivelIdx = JOptionPane.showOptionDialog(null, "Nivel de la entidad:", "Crear Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcNivel, opcNivel[0]);
            if (nivelIdx < 0) return;
            String codEntidad = JOptionPane.showInputDialog("Código único de entidad:");
            if (codEntidad == null || codEntidad.isBlank()) return;

            String resultado = usuarioControlador.crearContratante(tp, td, numDoc, nombre, correo, clave,
                    tel, dir, ciudad, sector, NivelEntidad.valueOf(opcNivel[nivelIdx]), codEntidad);
            JOptionPane.showMessageDialog(null, resultado);
        } else {
            int entPub = JOptionPane.showConfirmDialog(null, "¿Es entidad pública?", "Crear Contratista", JOptionPane.YES_NO_OPTION);
            if (entPub < 0) return;
            String area = JOptionPane.showInputDialog("Función principal del área de desempeño:");
            if (area == null || area.isBlank()) return;

            String resultado = usuarioControlador.crearContratista(tp, td, numDoc, nombre, correo, clave,
                    tel, dir, ciudad, entPub == JOptionPane.YES_OPTION, area);
            JOptionPane.showMessageDialog(null, resultado);
        }
    }

    /** Captura el documento y muestra la información del usuario. */
    private static void vistaConsultarUsuario() {
        String doc = JOptionPane.showInputDialog("Número de documento a consultar:");
        if (doc == null || doc.isBlank()) return;
        Usuario u = usuarioControlador.consultarUsuario(doc);
        if (u != null)
            JOptionPane.showMessageDialog(null, u.toString(), "Información del Usuario", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** Captura datos nuevos y actualiza el usuario. */
    private static void vistaActualizarUsuario() {
        String doc = JOptionPane.showInputDialog("Número de documento del usuario a actualizar:");
        if (doc == null || doc.isBlank()) return;
        Usuario u = usuarioControlador.consultarUsuario(doc);
        if (u == null) { JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE); return; }

        String[] opcTP = {"NATURAL", "JURIDICA"};
        int tpIdx = JOptionPane.showOptionDialog(null, "Nuevo tipo de persona:", "Actualizar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcTP, u.getTipoPersona().name());
        if (tpIdx < 0) return;
        String correo = JOptionPane.showInputDialog("Nuevo correo:", u.getCorreo());
        if (correo == null) return;
        String clave = JOptionPane.showInputDialog("Nueva contraseña:", u.getContraseña());
        if (clave == null) return;
        String dir = JOptionPane.showInputDialog("Nueva dirección:", u.getDirecccion());
        if (dir == null) return;
        String ciudad = JOptionPane.showInputDialog("Nueva ciudad:", u.getCiudad());
        if (ciudad == null) return;

        String resultado = usuarioControlador.actualizarUsuario(doc, TipoPersona.valueOf(opcTP[tpIdx]), correo, clave, dir, ciudad);
        JOptionPane.showMessageDialog(null, resultado);
    }

    /** Captura el documento y elimina el usuario. */
    private static void vistaEliminarUsuario() {
        String doc = JOptionPane.showInputDialog("Número de documento del usuario a eliminar:");
        if (doc == null || doc.isBlank()) return;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Confirma la eliminación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        JOptionPane.showMessageDialog(null, usuarioControlador.eliminarUsuario(doc));
    }

    // ===================== MENÚ CONTRATANTE =====================

    /**
     * Menú del contratante: gestión de contratos.
     *
     * @param contratante El contratante autenticado.
     */
    private static void menuContratante(Contratante contratante) {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Crear Contrato", "Consultar Contrato", "Actualizar Contrato",
                    "Eliminar Contrato", "Ver Reportes de Interventoría", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Contratante", "Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0: vistaCrearContrato(contratante); break;
                case 1: vistaConsultarContrato(); break;
                case 2: vistaActualizarContrato(); break;
                case 3: vistaEliminarContrato(); break;
                case 4: vistaVerTodosLosReportes(); break;
                default: activo = false;
            }
        }
    }

    /** Captura todos los datos del contrato y llama al controlador según el tipo. */
    private static void vistaCrearContrato(Contratante contratante) {
        // Buscar contratista
        String docContratista = JOptionPane.showInputDialog("Documento del contratista asignado:");
        if (docContratista == null || docContratista.isBlank()) return;
        Usuario u = usuarioControlador.consultarUsuario(docContratista);
        if (!(u instanceof Contratista)) {
            JOptionPane.showMessageDialog(null, "Contratista no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Contratista contratista = (Contratista) u;

        // Validar ID único antes de continuar
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("ID del contrato:");
            if (id == null) return;
            if (id.isBlank()) {
                JOptionPane.showMessageDialog(null, "El ID no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (contratoControlador.existeIdContrato(id)) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un contrato con el ID " + id + ".\nIngrese un ID diferente.",
                        "ID Duplicado", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        String objeto = JOptionPane.showInputDialog("Objeto del contrato:");
        if (objeto == null || objeto.isBlank()) return;
        String valorStr = JOptionPane.showInputDialog("Valor total del contrato ($):");
        if (valorStr == null || valorStr.isBlank()) return;
        String plazoStr = JOptionPane.showInputDialog("Plazo de ejecución (meses):");
        if (plazoStr == null || plazoStr.isBlank()) return;

        double valorContrato;
        int plazoContrato;
        try {
            valorContrato = Double.parseDouble(valorStr);
            plazoContrato = Integer.parseInt(plazoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor o plazo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tipo de contrato
        String[] tiposContrato = {"Prestación de Servicios", "Compraventa", "Obra Pública"};
        int tipoIdx = JOptionPane.showOptionDialog(null, "Tipo de contrato:", "Crear Contrato",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tiposContrato, tiposContrato[0]);
        if (tipoIdx < 0) return;

        String resultado;
        if (tipoIdx == 0) {
            String perfil = JOptionPane.showInputDialog("Perfil requerido:");
            if (perfil == null || perfil.isBlank()) return;
            String honStr = JOptionPane.showInputDialog("Honorario mensual ($):");
            if (honStr == null || honStr.isBlank()) return;
            try {
                double honorario = Double.parseDouble(honStr);
                resultado = contratoControlador.crearContratoPrestacionServicios(
                        id, objeto, valorContrato, plazoContrato,
                        contratista, contratante, perfil, honorario, plazoContrato);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Honorario inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (tipoIdx == 1) {
            String item   = JOptionPane.showInputDialog("Ítem o bien a adquirir:");
            if (item == null || item.isBlank()) return;
            String marca  = JOptionPane.showInputDialog("Marca:");
            if (marca == null) return;
            String modelo = JOptionPane.showInputDialog("Modelo:");
            if (modelo == null) return;
            String serie  = JOptionPane.showInputDialog("Serie:");
            if (serie == null) return;
            String vuStr  = JOptionPane.showInputDialog("Valor unitario ($):");
            if (vuStr == null || vuStr.isBlank()) return;
            String cantStr = JOptionPane.showInputDialog("Cantidad:");
            if (cantStr == null || cantStr.isBlank()) return;
            try {
                double vu  = Double.parseDouble(vuStr);
                int    cant = Integer.parseInt(cantStr);
                resultado = contratoControlador.crearContratoCompraventa(
                        id, objeto, valorContrato, plazoContrato,
                        contratista, contratante, item, marca, modelo, serie, vu, cant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            String ubicacion = JOptionPane.showInputDialog("Ubicación de la obra:");
            if (ubicacion == null || ubicacion.isBlank()) return;
            String areaStr = JOptionPane.showInputDialog("Área de intervención (m²):");
            if (areaStr == null || areaStr.isBlank()) return;
            try {
                double area = Double.parseDouble(areaStr);
                resultado = contratoControlador.crearContratoObrasPublicas(
                        id, objeto, valorContrato, plazoContrato,
                        contratista, contratante, ubicacion, area);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Área inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, resultado);
    }

    /** Captura el ID y muestra la información del contrato. */
    private static void vistaConsultarContrato() {
        String id = JOptionPane.showInputDialog("ID del contrato a consultar:");
        if (id == null || id.isBlank()) return;
        Contrato c = contratoControlador.consultarContrato(id);
        if (c != null)
            JOptionPane.showMessageDialog(null, c.toString(), "Contrato", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Contrato no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** Captura los nuevos datos y actualiza el contrato. */
    private static void vistaActualizarContrato() {
        String id = JOptionPane.showInputDialog("ID del contrato a actualizar:");
        if (id == null || id.isBlank()) return;
        String valorStr = JOptionPane.showInputDialog("Nuevo valor del contrato ($):");
        if (valorStr == null || valorStr.isBlank()) return;
        String plazoStr = JOptionPane.showInputDialog("Nuevo plazo (meses):");
        if (plazoStr == null || plazoStr.isBlank()) return;
        try {
            double valor = Double.parseDouble(valorStr);
            int plazo    = Integer.parseInt(plazoStr);
            JOptionPane.showMessageDialog(null, contratoControlador.actualizarContrato(id, valor, plazo));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Captura el ID y elimina el contrato. */
    private static void vistaEliminarContrato() {
        String id = JOptionPane.showInputDialog("ID del contrato a eliminar:");
        if (id == null || id.isBlank()) return;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Confirma la eliminación del contrato " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        JOptionPane.showMessageDialog(null, contratoControlador.eliminarContrato(id));
    }

    // ===================== MENÚ CONTRATISTA =====================

    /** Menú del contratista: cambio de estado y consulta de reportes. */
    private static void menuContratista() {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Cambiar Estado del Contrato", "Ver Reportes por Contrato",
                    "Ver Todos los Reportes", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Contratista", "Contratista",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0: vistaCambiarEstado(); break;
                case 1: vistaVerReportesPorContrato(); break;
                case 2: vistaVerTodosLosReportes(); break;
                default: activo = false;
            }
        }
    }

    /** Captura el ID, estado e informe, y llama al controlador para cambiar el estado. */
    private static void vistaCambiarEstado() {
        String id = JOptionPane.showInputDialog("ID del contrato:");
        if (id == null || id.isBlank()) return;
        Contrato contrato = contratoControlador.consultarContrato(id);
        if (contrato == null) {
            JOptionPane.showMessageDialog(null, "Contrato no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Estado actual: " + contrato.getEstado());

        String[] estados = {"PUBLICADO", "LICITACION", "ADJUDICACION", "EJECUCION", "FINALIZADO"};
        int estadoIdx = JOptionPane.showOptionDialog(null, "Seleccione el nuevo estado:", "Cambiar Estado",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, estados, contrato.getEstado().name());
        if (estadoIdx < 0) return;

        String informe = JOptionPane.showInputDialog("Informe que justifica el cambio de estado:");
        if (informe == null || informe.isBlank()) {
            JOptionPane.showMessageDialog(null, "El informe es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null,
                contratoControlador.cambiarEstadoContrato(id, EstadoContrato.valueOf(estados[estadoIdx]), informe));
    }

    /** Muestra todos los reportes registrados. */
    private static void vistaVerTodosLosReportes() {
        ArrayList<ReporteInventoria> reportes = reporteControlador.consultarTodos();
        if (reportes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reportes registrados.", "Reportes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("=== Reportes de Interventoría ===\n\n");
        for (ReporteInventoria r : reportes) {
            sb.append("Contrato: ").append(r.getContrato() != null ? r.getContrato().getId() : "N/A").append("\n");
            sb.append("Fecha/Hora: ").append(r.getFechaHora()).append("\n");
            sb.append("Informe: ").append(r.getInforme()).append("\n");
            sb.append("Estado actual: ").append(r.getContrato() != null ? r.getContrato().getEstado() : "N/A").append("\n");
            sb.append("-----------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Reportes de Interventoría", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Muestra los reportes de un contrato específico. */
    private static void vistaVerReportesPorContrato() {
        String id = JOptionPane.showInputDialog("ID del contrato:");
        if (id == null || id.isBlank()) return;
        ArrayList<ReporteInventoria> reportes = reporteControlador.consultarPorContrato(id);
        if (reportes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron reportes para el contrato: " + id, "Reportes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("=== Reportes del Contrato " + id + " ===\n\n");
        for (ReporteInventoria r : reportes) {
            sb.append("Fecha/Hora: ").append(r.getFechaHora()).append("\n");
            sb.append("Informe: ").append(r.getInforme()).append("\n");
            sb.append("Estado: ").append(r.getContrato() != null ? r.getContrato().getEstado() : "N/A").append("\n");
            sb.append("-----------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Reportes", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== DATOS DE PRUEBA =====================

    /**
     * Carga usuarios de prueba en el sistema para facilitar las pruebas.
     */
    private static void cargarDatosDePrueba() {
        Administrador admin = new Administrador(TipoPersona.NATURAL, TipoDocumento.CC,
                "1000000000", "Administrador SECOP", "admin@secop.gov.co",
                "admin123", "3001234567", "Calle 1 # 1-1", "Tunja");
        usuarioServicio.crearUsuario(admin);

        Contratante contratante = new Contratante(TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja", "contratante@tunja.gov.co",
                "contra123", "6087412580", "Calle 19 # 10-10", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");
        usuarioServicio.crearUsuario(contratante);

        Contratista contratista = new Contratista(TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez", "contratista@gmail.com",
                "contrat123", "3109876543", "Carrera 5 # 20-30", "Tunja",
                false, "Ingeniería Civil");
        usuarioServicio.crearUsuario(contratista);
    }
}

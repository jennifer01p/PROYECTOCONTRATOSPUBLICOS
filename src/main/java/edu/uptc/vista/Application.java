package edu.uptc.vista;

import edu.uptc.controlador.*;
import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import edu.uptc.servicio.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase principal del sistema de gestión de contratos públicos (SECOP II).
 * Punto de entrada de la aplicación; gestiona los menús según el rol del usuario.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Application {

    /** Servicio compartido de usuarios (lista única en memoria). */
    private static UsuarioServicio usuarioServicio = new UsuarioServicio();

    /** Servicio de contratos. */
    private static ContratoServicio contratoServicio = new ContratoServicio();

    /** Servicio de reportes de interventoría. */
    private static ReporteServicio reporteServicio = new ReporteServicio();

    /**
     * Método principal. Inicializa datos de prueba y lanza el menú de autenticación.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        cargarDatosDePrueba();

        SeguridadServicio seguridadServicio = new SeguridadServicio(usuarioServicio.getUsuarios());
        SeguridadControlador seguridadControlador = new SeguridadControlador(seguridadServicio);
        UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioServicio);
        ContratoControlador contratoControlador = new ContratoControlador(contratoServicio, reporteServicio);
        ReporteControlador reporteControlador = new ReporteControlador(reporteServicio);

        boolean continuar = true;
        while (continuar) {
            String[] opcionesInicio = {"Iniciar Sesión", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null,
                    "Bienvenido al Sistema de Contratos Públicos\nSECOP II - UPTC",
                    "Sistema de Contratos", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, opcionesInicio, opcionesInicio[0]);

            if (opcion != 0) { continuar = false; continue; }

            Usuario usuario = seguridadControlador.iniciarSesion();
            if (usuario == null) continue;

            String rol = seguridadControlador.obtenerRol(usuario);

            switch (rol) {
                case "ADMINISTRADOR":
                    menuAdministrador(usuarioControlador);
                    break;
                case "CONTRATANTE":
                    menuContratante(contratoControlador, reporteControlador, (Contratante) usuario, usuarioServicio);
                    break;
                case "CONTRATISTA":
                    menuContratista(contratoControlador, reporteControlador);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Rol no reconocido.");
            }
        }

        JOptionPane.showMessageDialog(null, "Hasta pronto.", "SECOP II", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra el menú del administrador: crear, consultar, actualizar y eliminar usuarios.
     *
     * @param uc Controlador de usuarios.
     */
    private static void menuAdministrador(UsuarioControlador uc) {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Crear Usuario", "Consultar Usuario", "Actualizar Usuario", "Eliminar Usuario", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Administrador", "Administrador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0: uc.crearUsuario(); break;
                case 1: uc.consultarUsuario(); break;
                case 2: uc.actualizarUsuario(); break;
                case 3: uc.eliminarUsuario(); break;
                default: activo = false;
            }
        }
    }

    /**
     * Muestra el menú del contratante: gestión de contratos y consulta de reportes.
     *
     * @param cc              Controlador de contratos.
     * @param rc              Controlador de reportes.
     * @param contratante     El contratante autenticado.
     * @param usuarioServicio Servicio de usuarios para seleccionar contratista.
     */
    private static void menuContratante(ContratoControlador cc, ReporteControlador rc,
                                        Contratante contratante, UsuarioServicio usuarioServicio) {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Crear Contrato", "Consultar Contrato", "Actualizar Contrato",
                    "Eliminar Contrato", "Ver Reportes de Interventoría", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Contratante", "Contratante",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0:
                    String docContratista = JOptionPane.showInputDialog("Ingrese el documento del contratista asignado:");
                    if (docContratista == null || docContratista.isBlank()) break;
                    Usuario u = usuarioServicio.buscarUsuario(docContratista);
                    if (u instanceof Contratista) {
                        cc.crearContrato(contratante, (Contratista) u);
                    } else {
                        JOptionPane.showMessageDialog(null, "Contratista no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 1: cc.consultarContrato(); break;
                case 2: cc.actualizarContrato(); break;
                case 3: cc.eliminarContrato(); break;
                case 4: rc.consultarTodosLosReportes(); break;
                default: activo = false;
            }
        }
    }

    /**
     * Muestra el menú del contratista: cambio de estado y consulta de reportes.
     *
     * @param cc Controlador de contratos.
     * @param rc Controlador de reportes.
     */
    private static void menuContratista(ContratoControlador cc, ReporteControlador rc) {
        boolean activo = true;
        while (activo) {
            String[] opciones = {"Cambiar Estado del Contrato", "Consultar Reportes por Contrato",
                    "Ver Todos los Reportes", "Cerrar Sesión"};
            int op = JOptionPane.showOptionDialog(null, "Menú Contratista", "Contratista",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (op) {
                case 0: cc.cambiarEstadoContrato(); break;
                case 1: rc.consultarReportesPorContrato(); break;
                case 2: rc.consultarTodosLosReportes(); break;
                default: activo = false;
            }
        }
    }

    /**
     * Carga datos iniciales de prueba: un administrador, un contratante y un contratista.
     */
    private static void cargarDatosDePrueba() {
        // Administrador por defecto
        Administrador admin = new Administrador(TipoPersona.NATURAL, TipoDocumento.CC,
                "1000000000", "Administrador SECOP", "admin@secop.gov.co",
                "admin123", "3001234567", "Calle 1 # 1-1", "Tunja");
        usuarioServicio.crearUsuario(admin);

        // Contratante de prueba
        Contratante contratante = new Contratante(TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja", "contratante@tunja.gov.co",
                "contra123", "6087412580", "Calle 19 # 10-10", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");
        usuarioServicio.crearUsuario(contratante);

        // Contratista de prueba
        Contratista contratista = new Contratista(TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez", "contratista@gmail.com",
                "contrat123", "3109876543", "Carrera 5 # 20-30", "Tunja",
                false, "Ingeniería Civil");
        usuarioServicio.crearUsuario(contratista);
    }
}

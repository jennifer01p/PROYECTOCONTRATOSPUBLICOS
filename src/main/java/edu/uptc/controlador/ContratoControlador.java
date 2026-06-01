package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import edu.uptc.servicio.ContratoServicio;
import edu.uptc.servicio.ReporteServicio;

import javax.swing.*;
import java.time.LocalDate;

/**
 * Controlador que gestiona las operaciones sobre contratos públicos
 * mediante cuadros de diálogo JOptionPane.
 * El contratante puede crear, consultar, actualizar y eliminar contratos.
 * El contratista puede cambiar el estado de un contrato.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoControlador {

    /** Servicio de contratos. */
    private ContratoServicio contratoServicio;

    /** Servicio de reportes de interventoría. */
    private ReporteServicio reporteServicio;

    /**
     * Constructor del controlador.
     *
     * @param contratoServicio Servicio de gestión de contratos.
     * @param reporteServicio  Servicio de reportes de interventoría.
     */
    public ContratoControlador(ContratoServicio contratoServicio, ReporteServicio reporteServicio) {
        this.contratoServicio = contratoServicio;
        this.reporteServicio = reporteServicio;
    }

    /**
     * Solicita los datos base de un contrato (comunes a todos los tipos).
     *
     * @param contratante El contratante que crea el contrato.
     * @param contratista El contratista asignado.
     * @return Arreglo con id, objeto, valorContrato, plazoContrato.
     */
    private String[] pedirDatosBase() {
        String id = JOptionPane.showInputDialog("ID del contrato:");
        if (id == null || id.isBlank()) return null;
        String objeto = JOptionPane.showInputDialog("Objeto del contrato:");
        if (objeto == null || objeto.isBlank()) return null;
        String valor = JOptionPane.showInputDialog("Valor del contrato ($):");
        if (valor == null || valor.isBlank()) return null;
        String plazo = JOptionPane.showInputDialog("Plazo de ejecución (meses):");
        if (plazo == null || plazo.isBlank()) return null;
        return new String[]{id, objeto, valor, plazo};
    }

    /**
     * Permite al contratante crear un contrato (prestación de servicios,
     * compraventa u obra pública) ingresando los datos por JOptionPane.
     *
     * @param contratante El contratante que crea el contrato.
     * @param contratista El contratista asignado al contrato.
     */
    public void crearContrato(Contratante contratante, Contratista contratista) {
        String[] tiposContrato = {"Prestación de Servicios", "Compraventa", "Obra Pública"};
        int tipoIdx = JOptionPane.showOptionDialog(null, "Seleccione el tipo de contrato:", "Crear Contrato",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tiposContrato, tiposContrato[0]);
        if (tipoIdx < 0) return;

        String[] base = pedirDatosBase();
        if (base == null) { JOptionPane.showMessageDialog(null, "Creación cancelada."); return; }

        double valorContrato;
        int plazoContrato;
        try {
            valorContrato = Double.parseDouble(base[2]);
            plazoContrato = Integer.parseInt(base[3]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor o plazo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contrato contrato = null;

        if (tipoIdx == 0) {
            // Prestación de Servicios
            String perfil = JOptionPane.showInputDialog("Perfil requerido:");
            if (perfil == null || perfil.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }
            String honorarioStr = JOptionPane.showInputDialog("Honorario mensual ($):");
            if (honorarioStr == null || honorarioStr.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }
            double honorario;
            try { honorario = Double.parseDouble(honorarioStr); }
            catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Honorario inválido.", "Error", JOptionPane.ERROR_MESSAGE); return; }

            contrato = new ContratoPrestacionServicios(LocalDate.now(), base[0], base[1], valorContrato,
                    plazoContrato, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                    contratista, contratante, perfil, honorario, plazoContrato);

        } else if (tipoIdx == 1) {
            // Compraventa
            String item = JOptionPane.showInputDialog("Ítem o bien a adquirir:");
            if (item == null || item.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }
            String marca = JOptionPane.showInputDialog("Marca:");
            if (marca == null) return;
            String modelo = JOptionPane.showInputDialog("Modelo:");
            if (modelo == null) return;
            String serie = JOptionPane.showInputDialog("Serie:");
            if (serie == null) return;
            String vuStr = JOptionPane.showInputDialog("Valor unitario ($):");
            if (vuStr == null || vuStr.isBlank()) return;
            String cantStr = JOptionPane.showInputDialog("Cantidad:");
            if (cantStr == null || cantStr.isBlank()) return;
            double vu;
            int cant;
            try { vu = Double.parseDouble(vuStr); cant = Integer.parseInt(cantStr); }
            catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE); return; }

            contrato = new ContratoCompraventa(LocalDate.now(), base[0], base[1], valorContrato,
                    plazoContrato, EstadoContrato.PUBLICADO, TipoContrato.COMPRAVENTA,
                    contratista, contratante, item, marca, modelo, serie, vu, cant);

        } else {
            // Obra Pública
            String ubicacion = JOptionPane.showInputDialog("Ubicación de la obra (dirección):");
            if (ubicacion == null || ubicacion.isBlank()) { JOptionPane.showMessageDialog(null, "Cancelado."); return; }
            String areaStr = JOptionPane.showInputDialog("Área de intervención (m²):");
            if (areaStr == null || areaStr.isBlank()) return;
            double area;
            try { area = Double.parseDouble(areaStr); }
            catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Área inválida.", "Error", JOptionPane.ERROR_MESSAGE); return; }

            contrato = new ContratoObrasPublicas(LocalDate.now(), base[0], base[1], valorContrato,
                    plazoContrato, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                    contratista, contratante, ubicacion, area);
        }

        if (!contratoServicio.validarContrato(contrato)) {
            JOptionPane.showMessageDialog(null,
                    "El contrato no es válido. Verifique que los valores calculados coincidan con el valor total del contrato.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contratoServicio.crearContrato(contrato);
        JOptionPane.showMessageDialog(null, "Contrato creado exitosamente con ID: " + contrato.getId());
    }

    /**
     * Consulta un contrato por su ID y muestra la información al usuario.
     */
    public void consultarContrato() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del contrato a consultar:");
        if (id == null || id.isBlank()) return;
        Contrato c = contratoServicio.consultarContrato(id);
        if (c != null) {
            JOptionPane.showMessageDialog(null, c.toString(), "Contrato", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Contrato no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el valor y el plazo de un contrato existente.
     */
    public void actualizarContrato() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del contrato a actualizar:");
        if (id == null || id.isBlank()) return;
        String valorStr = JOptionPane.showInputDialog("Nuevo valor del contrato ($):");
        if (valorStr == null || valorStr.isBlank()) return;
        String plazoStr = JOptionPane.showInputDialog("Nuevo plazo (meses):");
        if (plazoStr == null || plazoStr.isBlank()) return;
        try {
            double valor = Double.parseDouble(valorStr);
            int plazo = Integer.parseInt(plazoStr);
            String resultado = contratoServicio.actualizarContrato(id, valor, plazo);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un contrato del sistema por su ID.
     */
    public void eliminarContrato() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del contrato a eliminar:");
        if (id == null || id.isBlank()) return;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Confirma la eliminación del contrato " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        String resultado = contratoServicio.eliminar(id);
        JOptionPane.showMessageDialog(null, resultado);
    }

    /**
     * Permite al contratista cambiar el estado de un contrato
     * y genera automáticamente un reporte de interventoría.
     */
    public void cambiarEstadoContrato() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del contrato:");
        if (id == null || id.isBlank()) return;
        Contrato contrato = contratoServicio.consultarContrato(id);
        if (contrato == null) {
            JOptionPane.showMessageDialog(null, "Contrato no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Estado actual: " + contrato.getEstado());
        String[] estados = {"PUBLICADO", "LICITACION", "ADJUDICACION", "EJECUCION", "FINALIZADO"};
        int estadoIdx = JOptionPane.showOptionDialog(null, "Seleccione el nuevo estado:", "Cambiar Estado",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, estados, contrato.getEstado().name());
        if (estadoIdx < 0) return;
        String informe = JOptionPane.showInputDialog("Ingrese el informe que justifica el cambio de estado:");
        if (informe == null || informe.isBlank()) { JOptionPane.showMessageDialog(null, "Debe ingresar un informe.", "Error", JOptionPane.ERROR_MESSAGE); return; }

        String resultado = contratoServicio.cambiarEstadoContrato(id, EstadoContrato.valueOf(estados[estadoIdx]));
        reporteServicio.crearReporte(contrato, informe);
        JOptionPane.showMessageDialog(null, resultado + "\nReporte de interventoría generado.");
    }

    /**
     * Retorna el servicio de contratos.
     * @return Instancia de {@link ContratoServicio}.
     */
    public ContratoServicio getContratoServicio() {
        return contratoServicio;
    }
}

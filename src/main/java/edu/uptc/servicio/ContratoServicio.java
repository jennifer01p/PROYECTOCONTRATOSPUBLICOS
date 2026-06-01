package edu.uptc.servicio;

import edu.uptc.dominio.Contrato;
import edu.uptc.enums.EstadoContrato;

import java.util.ArrayList;

/**
 * Servicio encargado de toda la lógica de negocio relacionada con contratos.
 * Permite crear, consultar, actualizar, eliminar y cambiar el estado de contratos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoServicio {

    /** Lista de contratos registrados en el sistema. */
    private ArrayList<Contrato> contratos;

    /**
     * Constructor que inicializa la lista de contratos.
     */
    public ContratoServicio() {
        this.contratos = new ArrayList<>();
    }

    /**
     * Registra un contrato directamente sin validación.
     *
     * @param contrato El contrato a registrar.
     */
    public void crearContrato(Contrato contrato) {
        contratos.add(contrato);
    }

    /**
     * Valida y registra un contrato. Si el contrato no cumple las reglas
     * de negocio propias de su tipo, no se guarda y retorna un mensaje de error.
     *
     * @param contrato El contrato a validar y registrar.
     * @return Mensaje indicando si se creó correctamente o el motivo del fallo.
     */
    public String crearContratoValidado(Contrato contrato) {
        if (!contrato.validarContrato()) {
            return "El contrato no es válido. Verifique que los valores calculados "
                    + "coincidan con el valor total del contrato.";
        }
        contratos.add(contrato);
        return "Contrato creado exitosamente con ID: " + contrato.getId();
    }

    /**
     * Busca y retorna un contrato por su ID.
     *
     * @param id ID del contrato a buscar.
     * @return El contrato encontrado, o {@code null} si no existe.
     */
    public Contrato consultarContrato(String id) {
        for (Contrato contratoAux : contratos) {
            if (id.equals(contratoAux.getId())) {
                return contratoAux;
            }
        }
        return null;
    }

    /**
     * Actualiza el valor y el plazo de un contrato existente.
     *
     * @param id            ID del contrato.
     * @param valorContrato Nuevo valor del contrato.
     * @param plazoContrato Nuevo plazo en meses.
     * @return Mensaje de resultado de la operación.
     */
    public String actualizarContrato(String id, double valorContrato, int plazoContrato) {
        Contrato contrato = consultarContrato(id);
        if (contrato != null) {
            contrato.setValorContrato(valorContrato);
            contrato.setPlazoContrato(plazoContrato);
            return "Información del contrato actualizada correctamente.";
        }
        return "Contrato no encontrado con ID: " + id;
    }

    /**
     * Elimina un contrato del sistema por su ID.
     *
     * @param id ID del contrato a eliminar.
     * @return Mensaje de resultado de la operación.
     */
    public String eliminar(String id) {
        Contrato contrato = consultarContrato(id);
        if (contrato != null) {
            contratos.remove(contrato);
            return "Contrato eliminado correctamente.";
        }
        return "No se encontró el contrato con ID: " + id;
    }

    /**
     * Valida un contrato usando las reglas propias de su tipo.
     *
     * @param contrato El contrato a validar.
     * @return {@code true} si el contrato es válido, {@code false} en caso contrario.
     */
    public boolean validarContrato(Contrato contrato) {
        return contrato.validarContrato();
    }

    /**
     * Cambia el estado de un contrato y genera automáticamente el reporte
     * de interventoría correspondiente.
     *
     * @param id              ID del contrato.
     * @param estado          Nuevo estado del contrato.
     * @param informe         Informe que justifica el cambio de estado.
     * @param reporteServicio Servicio de reportes para registrar el cambio.
     * @return Mensaje de resultado de la operación.
     */
    public String cambiarEstadoConReporte(String id, EstadoContrato estado,
                                           String informe, ReporteServicio reporteServicio) {
        Contrato contrato = consultarContrato(id);
        if (contrato == null) {
            return "El contrato con ID " + id + " no fue encontrado.";
        }
        contrato.setEstado(estado);
        reporteServicio.crearReporte(contrato, informe);
        return "Estado del contrato actualizado a " + estado
                + ". Reporte de interventoría generado.";
    }

    /**
     * Cambia el estado de un contrato sin generar reporte (uso interno / pruebas).
     *
     * @param id     ID del contrato.
     * @param estado Nuevo estado.
     * @return Mensaje de resultado.
     */
    public String cambiarEstadoContrato(String id, EstadoContrato estado) {
        Contrato contrato = consultarContrato(id);
        if (contrato != null) {
            contrato.setEstado(estado);
            return "Estado del contrato actualizado a " + estado;
        }
        return "El contrato con la id " + id + " no se encontró.";
    }
}

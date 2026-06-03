package edu.uptc.servicio;

import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.Usuario;
import edu.uptc.enums.NivelEntidad;
import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link UsuarioServicio}.
 * Verifica el correcto funcionamiento de la gestión de usuarios, incluyendo
 * la creación y verificación de existencia de documentos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
class UsuarioServicioTest {

    /** Instancia del servicio de usuarios a probar. */
    private UsuarioServicio usuarioServicio;
    /** Contratante de prueba utilizado en los tests. */
    private Contratante contratante;
    /** Contratista de prueba utilizado en los tests. */
    private Contratista contratista;


    /**
     * Configuración inicial para cada prueba.
     * Se inicializa la instancia de {@link UsuarioServicio} y los objetos de contratante y contratista de prueba.
     */
    @BeforeEach
    void setUp() {
        usuarioServicio = new UsuarioServicio();

        contratante = new Contratante(
                TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja",
                "contratante@tunja.gov.co", "clave123",
                "6087412580", "Calle 19", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");

        contratista = new Contratista(
                TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez",
                "carlos@gmail.com", "clave456",
                "3109876543", "Carrera 5", "Tunja",
                false, "Ingeniería Civil");
    }

    /**
     * Prueba el método {@link UsuarioServicio#existeDocumento(String)}.
     * Verifica que el método retorne {@code true} cuando un documento ya ha sido registrado.
     */
    @Test
    void ExisteDocumentoTest() {

        usuarioServicio.crearUsuario(contratista);

        assertTrue(usuarioServicio.existeDocumento("1234567890"), "El documento 1234567890 fue registrado, debe retornar true");
    }


    /**
     * Prueba el método {@link UsuarioServicio#crearUsuario(Usuario)} para un caso exitoso.
     * Verifica que el mensaje de retorno sea el esperado y que el usuario pueda ser encontrado posteriormente.
     */
    @Test
    void CrearUsuarioExitosoTest() {
        String resultado = usuarioServicio.crearUsuario(contratista);

        assertEquals("Usuario registrado exitosamente.", resultado, "Debe retornar el mensaje de registro exitoso");

        assertNotNull(usuarioServicio.buscarUsuario("1234567890"), "Después de crearUsuario, buscarUsuario no debe retornar null");
    }

}

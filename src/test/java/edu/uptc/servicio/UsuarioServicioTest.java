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

class UsuarioServicioTest {

    private UsuarioServicio usuarioServicio;
    private Contratante contratante;
    private Contratista contratista;


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

    @Test
    void ExisteDocumentoTest() {

        usuarioServicio.crearUsuario(contratista);

        assertTrue(usuarioServicio.existeDocumento("1234567890"), "El documento 1234567890 fue registrado, debe retornar true");
    }


    @Test
    void CrearUsuarioExitosoTest() {
        String resultado = usuarioServicio.crearUsuario(contratista);

        assertEquals("Usuario registrado exitosamente.", resultado, "Debe retornar el mensaje de registro exitoso");

        assertNotNull(usuarioServicio.buscarUsuario("1234567890"), "Después de crearUsuario, buscarUsuario no debe retornar null");
    }

}
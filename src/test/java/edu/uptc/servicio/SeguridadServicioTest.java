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
 * Clase de pruebas unitarias para {@link SeguridadServicio}.
 * Verifica el correcto funcionamiento de la autenticación de usuarios.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
class SeguridadServicioTest {

    /** Instancia del servicio de usuarios para gestionar los usuarios de prueba. */
    private UsuarioServicio usuarioServicio;
    /** Instancia del servicio de seguridad a probar. */
    private SeguridadServicio seguridadServicio;
    /** Contratante de prueba utilizado en los tests. */
    private Contratante contratante;
    /** Contratista de prueba utilizado en los tests. */
    private Contratista contratista;

    /**
     * Configuración inicial para cada prueba.
     * Se inicializan las instancias de los servicios y los objetos de contratante y contratista de prueba.
     * Se registran estos usuarios en el {@link UsuarioServicio} y se inicializa {@link SeguridadServicio}
     * con la lista de usuarios.
     */
    @BeforeEach
    void setUp() {
        usuarioServicio  = new UsuarioServicio();

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

        usuarioServicio.crearUsuario(contratante);
        usuarioServicio.crearUsuario(contratista);

        // SeguridadServicio recibe la misma lista que tiene UsuarioServicio
        seguridadServicio = new SeguridadServicio(usuarioServicio.getUsuarios());
    }

    /**
     * Prueba el método {@link SeguridadServicio#iniciarSesion(String, String)} con credenciales correctas.
     * Verifica que el inicio de sesión sea exitoso y retorne el usuario esperado.
     */
    @Test
    void IniciarSesionExitosoTest() {

        Usuario resultado = seguridadServicio.iniciarSesion("carlos@gmail.com", "clave456");

        assertNotNull(resultado, "Con credenciales correctas no debe retornar null");

        assertEquals("Carlos Pérez", resultado.getNombre(), "El nombre del usuario autenticado debe ser Carlos Pérez");
    }

    /**
     * Prueba el método {@link SeguridadServicio#iniciarSesion(String, String)} con credenciales incorrectas.
     * Verifica que el inicio de sesión falle y retorne {@code null}.
     */
    @Test
    void IniciarSesionAmbosIncorrectosTest() {
        assertNull(seguridadServicio.iniciarSesion("mal@mail.com", "claveMAL"), "Con correo y contraseña incorrectos debe retornar null");
    }

}

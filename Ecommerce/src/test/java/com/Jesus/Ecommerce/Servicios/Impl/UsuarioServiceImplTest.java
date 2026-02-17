package com.Jesus.Ecommerce.Servicios.Impl;


import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseDTO;
import com.Jesus.Ecommerce.DTOs.Usuario.UsuarioResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Mappers.UsuarioMapper;
import com.Jesus.Ecommerce.Modelos.Usuario;
import com.Jesus.Ecommerce.Repositorios.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks

    private UsuarioServiceImpl usuarioService;
    Integer id=1;
    UsuarioRegistroDTO dto = new UsuarioRegistroDTO("Usuario Prueba","Contraseña","Usuario@gmail.com","Usuario Apellido","1234567890","ADMIN");
    Usuario usuario = new Usuario(1,"Usuario Prueba","Contraseña1","Usuario@gmail.com","Usuario Apellido","1234567890",LocalDateTime.now(),LocalDateTime.now(),"ADMIN");
    UsuarioResponseDTO responseDTO= new UsuarioResponseDTO(1,"Usuario Prueba","Usuario@gmail.com","Usuario apellido","123456789","ADMIN");

    Usuario usuari2 = new Usuario(1,"Usuario Prueba2","Contraseña1","Usuario@gmail.com","Usuario Apellido","1234567890",LocalDateTime.now(),LocalDateTime.now(),"ADMIN");
    UsuarioResponseSimpleDTO responseDTO2= new UsuarioResponseSimpleDTO(1,"Usuario Prueba");

    Usuario usuario3 = new Usuario(1,"Usuario Prueba3","Contraseña1","Usuario@gmail.com","Usuario Apellido","1234567890",LocalDateTime.now(),LocalDateTime.now(),"ADMIN");
    UsuarioResponseSimpleDTO responseDTO3= new UsuarioResponseSimpleDTO(1,"Usuario Prueba");

    List<UsuarioResponseSimpleDTO> ListaUsuarioResponseDTOSResultado=new ArrayList<>();


    List<Usuario> listaUsuario=new ArrayList<>();
    List<UsuarioResponseSimpleDTO> ListaUsuarioResponseDTOS=new ArrayList<>();



    @Nested
    @DisplayName("Pruebas para registrarUsuario()")
    class PruebasRegistrarUsuario{



        @Test
        @DisplayName("Exito: Devuelve UsuarioResponseDTO")
        void registrarUsuario() {
            when(usuarioMapper.toEntity(dto)).thenReturn(usuario);

            when(usuarioRepository.save(usuario)).thenReturn(usuario);
            when(passwordEncoder.encode("Contraseña")).thenReturn("claveEncriptada123");
            when(usuarioMapper.toDto(usuario)).thenReturn(responseDTO);
            //Act
            UsuarioResponseDTO Resultado= usuarioService.registrarUsuario(dto);
            //
            assertNotNull(Resultado);
            assertEquals("Usuario Prueba",Resultado.nombreUsuario());


            verify(passwordEncoder).encode("Contraseña");
        }
    }

    @Nested
    @DisplayName("Pruebas para modificarUsuario()")
    class PruebasModificarUsuario{
        @Test
        @DisplayName("Exito:devuelve UsuarioResponseDTO")
        void modificarUsuario() {

            //
            when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
            when(passwordEncoder.encode("Contraseña")).thenReturn("claveEncriptada123");
            when(usuarioMapper.toDto(usuario)).thenReturn(responseDTO);

            when(usuarioRepository.save(usuario)).thenReturn(usuario);
            //Act
            UsuarioResponseDTO Resultado = usuarioService.modificarUsuario(id,dto);
            //

            assertNotNull(Resultado);
            assertEquals("Usuario Prueba",Resultado.nombreUsuario());
            verify(passwordEncoder).encode("Contraseña");
            verify(usuarioRepository).save(usuario);
            //assertThrows(UsuarioNoEncontradoExepcion.class, ()-> usuarioService.modificarUsuario(id,dto));
        }
        @Test
        @DisplayName("fallo:id inexistente")
        void modificarUsuarioFalloId() {

            when(usuarioRepository.findById(2)).thenReturn(Optional.empty());
            //
            assertThrows(UsuarioNoEncontradoExepcion.class,
                    ()-> usuarioService.modificarUsuario(2,dto));

            verify(usuarioRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerUsuarioPorId()")
    class PruebasObtenerUsuarioPorId{

        @Test
        @DisplayName("Exito: obtener usuario")
        void obtenerUsuarioPorId() {

             when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
             when(usuarioMapper.toDto(usuario)).thenReturn(responseDTO);

             UsuarioResponseDTO Resultado = usuarioService.obtenerUsuarioPorId(id);

             assertNotNull(Resultado);
             assertEquals("Usuario Prueba",Resultado.nombreUsuario());

        }

        @Test
        @DisplayName("Fallo: Id inexistente")
        void obtenerUsuarioPorIdInexistente() {

            when(usuarioRepository.findById(2)).thenReturn(Optional.empty());
            assertThrows(UsuarioNoEncontradoExepcion.class,
                ()-> usuarioService.obtenerUsuarioPorId(2));

            verify(usuarioRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para listarUsuarios()")
    class PruebasListarUsuarios{

        @Test
        @DisplayName("Exito:Lista de SimpleResponseDTO")
        void listarUsuarios() {

            listaUsuario.add(usuari2);
            listaUsuario.add(usuario3);

            ListaUsuarioResponseDTOS.add(responseDTO2);
            ListaUsuarioResponseDTOS.add(responseDTO3);


            ListaUsuarioResponseDTOSResultado.add(responseDTO2);
            ListaUsuarioResponseDTOSResultado.add(responseDTO3);

            when(usuarioRepository.findAll()).thenReturn(listaUsuario);
            when(usuarioMapper.toSimpleDtoList(listaUsuario)).thenReturn(ListaUsuarioResponseDTOS);

            List<UsuarioResponseSimpleDTO> Resultado =usuarioService.listarUsuarios();

           assertNotNull(Resultado);
           assertEquals(2,Resultado.size());
           assertEquals(responseDTO2.nombreUsuario(),Resultado.get(0).nombreUsuario());
           assertEquals(responseDTO3.nombreUsuario(),Resultado.get(1).nombreUsuario());

            verify(usuarioRepository).findAll();
            verify(usuarioMapper).toSimpleDtoList(listaUsuario);
        }

        @Test
        @DisplayName("Exito:Lista vacia")
        void listarUsuariosVacia() {

            when(usuarioRepository.findAll()).thenReturn(listaUsuario);
            when(usuarioMapper.toSimpleDtoList(listaUsuario)).thenReturn(ListaUsuarioResponseDTOS);

            List<UsuarioResponseSimpleDTO> Resultado =usuarioService.listarUsuarios();

            assertNotNull(Resultado);
            assertTrue(Resultado.isEmpty());
        }
    }

    @Nested
    @DisplayName("Pruebas para eliminarUsuario()")
    class PruebasEliminarUsuario{

        @Test
        @DisplayName("Exito:Eliminado Correctamente")
        void eliminarUsuario() {


            when(usuarioRepository.existsById(id)).thenReturn(true);

            usuarioService.eliminarUsuario(id);

            verify(usuarioRepository).deleteById(id);
        }
        @Test
        @DisplayName("Fallo:Id inexistente")
        void eliminarUsuarioInexistente() {

            Integer idFalso=100;

            when(usuarioRepository.existsById(idFalso)).thenReturn(false);

            UsuarioNoEncontradoExepcion excepcion = assertThrows(UsuarioNoEncontradoExepcion.class, () -> {
                usuarioService.eliminarUsuario(idFalso);
            });

            assertEquals("Usuario no encontrado con id: " + idFalso, excepcion.getMessage());

            verify(usuarioRepository, never()).deleteById(any());

        }


    }
}
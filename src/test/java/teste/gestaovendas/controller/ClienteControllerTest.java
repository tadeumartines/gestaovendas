package teste.gestaovendas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import teste.gestaovendas.model.Cliente;
import teste.gestaovendas.service.ClienteService;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteService.listarTodos()).thenReturn(clientes);

        List<Cliente> result = clienteController.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    public void testBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().getId().equals(1L));
    }

    @Test
    public void testBuscarPorIdNotFound() {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.buscarPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSalvar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteController.salvar(cliente);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testAtualizar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteService.atualizar(anyLong(), any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.atualizar(1L, cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().getId().equals(1L));
    }

    @Test
    public void testAtualizarNotFound() {
        when(clienteService.atualizar(anyLong(), any(Cliente.class))).thenThrow(new RuntimeException("Cliente não encontrado"));

        ResponseEntity<Cliente> response = clienteController.atualizar(1L, new Cliente());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletarPorId() {
        doNothing().when(clienteService).deletarPorId(1L);

        ResponseEntity<Void> response = clienteController.deletarPorId(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

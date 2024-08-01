package teste.gestaovendas.service;

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
import teste.gestaovendas.model.Cliente;
import teste.gestaovendas.repository.ClienteRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    public void testBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testSalvar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.salvar(cliente);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testDeletarPorId() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deletarPorId(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAtualizar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome Atualizado");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Nome Atualizado");

        Cliente result = clienteService.atualizar(1L, clienteAtualizado);

        assertEquals("Nome Atualizado", result.getNome());
    }
}

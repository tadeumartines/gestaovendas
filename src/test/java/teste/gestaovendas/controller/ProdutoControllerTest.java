package teste.gestaovendas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
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
import teste.gestaovendas.model.Produto;
import teste.gestaovendas.service.ProdutoService;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto());
        when(produtoService.listarTodos()).thenReturn(produtos);

        List<Produto> result = produtoController.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    public void testBuscarPorId() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.buscaPorId(1L)).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> response = produtoController.buscaPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().getId().equals(1L));
    }

    @Test
    public void testBuscarPorIdNotFound() {
        when(produtoService.buscaPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = produtoController.buscaPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSalvar() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.salvar(any(Produto.class))).thenReturn(produto);

        Produto result = produtoController.salvar(produto);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testAtualizar() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.atualizar(anyLong(), any(Produto.class))).thenReturn(produto);

        ResponseEntity<Produto> response = produtoController.atualizar(1L, produto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().getId().equals(1L));
    }

    @Test
    public void testAtualizarNotFound() {
        when(produtoService.atualizar(anyLong(), any(Produto.class))).thenThrow(new RuntimeException("Produto não encontrado"));

        ResponseEntity<Produto> response = produtoController.atualizar(1L, new Produto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletarPorId() {
        doNothing().when(produtoService).deletarPorId(1L);

        ResponseEntity<Void> response = produtoController.deletarPorId(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

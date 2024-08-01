package teste.gestaovendas.service;

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
import teste.gestaovendas.model.Produto;
import teste.gestaovendas.repository.ProdutoRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto());
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    public void testBuscarPorId() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> result = produtoService.buscaPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testSalvar() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.salvar(produto);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testDeletarPorId() {
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletarPorId(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAtualizar() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setDescricao("Descrição Atualizada");
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setDescricao("Descrição Atualizada");

        Produto result = produtoService.atualizar(1L, produtoAtualizado);

        assertEquals("Descrição Atualizada", result.getDescricao());
    }
}

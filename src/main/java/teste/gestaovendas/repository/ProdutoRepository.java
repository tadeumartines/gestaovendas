package teste.gestaovendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.gestaovendas.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

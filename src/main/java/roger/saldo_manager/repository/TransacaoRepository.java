package roger.saldo_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roger.saldo_manager.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
    
}

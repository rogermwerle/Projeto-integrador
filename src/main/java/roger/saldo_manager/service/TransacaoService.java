package roger.saldo_manager.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roger.saldo_manager.model.Transacao;
import roger.saldo_manager.model.Usuario;
import roger.saldo_manager.repository.TransacaoRepository;
import roger.saldo_manager.repository.UsuarioRepository;

@Service
public class TransacaoService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    @Transactional
    public Transacao transferir(Long remetenteId, Long destinatarioId, double valor, String descricao) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new IllegalArgumentException("Remetente com ID " + remetenteId + " não encontrado."));
        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new IllegalArgumentException("Destinatário com ID " + destinatarioId + " não encontrado."));

        if (remetente.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para a transferência.");
        }

        remetente.setSaldo(remetente.getSaldo() - valor);
        destinatario.setSaldo(destinatario.getSaldo() + valor);

        usuarioRepository.saveAndFlush(remetente);
        usuarioRepository.saveAndFlush(destinatario);

        Transacao transacao = new Transacao();
        transacao.setRemetente(remetente);
        transacao.setDestinatario(destinatario);
        transacao.setValor(valor);
        transacao.setDescricao(descricao != null && !descricao.isEmpty() ? descricao : "Transferência realizada");
        transacao.setDataHora(LocalDateTime.now());
        transacao.setTipo("TRANSFERENCIA");
        return transacaoRepository.save(transacao);
    }

    public void excluir(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação com ID " + id + " não encontrada."));
        transacaoRepository.delete(transacao);
    }

    public List<Transacao> listar() {
        return transacaoRepository.findAll();
    }

}

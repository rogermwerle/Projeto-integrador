package roger.saldo_manager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roger.saldo_manager.model.Usuario;
import roger.saldo_manager.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario pegarID(long id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

    public Usuario cadastrar(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void excluir(Long id) {
        Usuario usuario = pegarID(id);
        usuarioRepository.deleteById(usuario.getId());
    }

    public Usuario depositar(Long id, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        double novoSaldo = usuario.getSaldo() + valor;
        usuario.setSaldo(novoSaldo);
        return usuarioRepository.save(usuario);
    }

    public Usuario sacar(Long id, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));
        
        if (usuario.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
        double novoSaldo = usuario.getSaldo() - valor;
        usuario.setSaldo(novoSaldo);
        return usuarioRepository.save(usuario);
    }

    public Usuario alterarNome(Long id, Usuario usuario, double valor) {
        Usuario usuarioAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        usuarioAtualizar.setNome(usuario.getNome());
        return usuarioRepository.save(usuarioAtualizar);
    }
}

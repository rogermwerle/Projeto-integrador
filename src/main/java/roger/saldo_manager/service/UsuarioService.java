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

    public Usuario depositar(Long id, Usuario usuario, double valor) {
        Usuario usuarioAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        double novoSaldo = usuario.getSaldo() + valor;

        usuarioAtualizar.setSaldo(novoSaldo);
        return usuarioRepository.save(usuarioAtualizar);
    }

    public Usuario sacar(Long id, Usuario usuario, double valor) {
        Usuario usuarioAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        double novoSaldo = usuario.getSaldo() - valor;

        usuarioAtualizar.setSaldo(novoSaldo);
        return usuarioRepository.save(usuarioAtualizar);
    }

    public Usuario alterarNome(Long id, Usuario usuario, double valor) {
        Usuario usuarioAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        usuarioAtualizar.setNome(usuario.getNome());
        return usuarioRepository.save(usuarioAtualizar);
    }
}
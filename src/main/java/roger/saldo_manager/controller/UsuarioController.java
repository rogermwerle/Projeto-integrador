package roger.saldo_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roger.saldo_manager.model.Usuario;
import roger.saldo_manager.service.UsuarioService;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> pegarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.pegarID(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> listar() {
        List<Usuario> listarUsuarios = usuarioService.listar();
        return new ResponseEntity<>(listarUsuarios, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/depositar/{id}")
    public ResponseEntity<Usuario> depositar(@PathVariable Long id, @RequestParam double valor) {
        if (valor <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Usuario usuarioAtualizado = usuarioService.depositar(id, valor);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/sacar/{id}")
    public ResponseEntity<Usuario> sacar(@PathVariable Long id, @RequestParam double valor) {
        if (valor <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Usuario usuarioAtualizado = usuarioService.sacar(id, valor);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/alterar-nome/{id}")
    public ResponseEntity<Usuario> alterarNome(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.alterarNome(id, usuario, 0);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}

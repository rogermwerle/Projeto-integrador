package roger.saldo_manager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roger.saldo_manager.model.Transacao;
import roger.saldo_manager.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> excluirTransacao(@PathVariable Long id) {
        transacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List> listar() {
        List<Transacao> listarUsuarios = transacaoService.listar();
        return new ResponseEntity<>(listarUsuarios, HttpStatus.OK);
    }

    @PostMapping("/transferir")
    public ResponseEntity<Transacao> transferir(
            @RequestParam Long remetenteId,
            @RequestParam Long destinatarioId,
            @RequestParam double valor,
            @RequestParam(required = false) String descricao) {
        Transacao transacao = transacaoService.transferir(remetenteId, destinatarioId, valor, descricao);
        return ResponseEntity.ok(transacao);
    }
}

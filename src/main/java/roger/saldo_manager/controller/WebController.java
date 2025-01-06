package roger.saldo_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String index2() {
        return "index";
    }

    @GetMapping("/addusuario")
    public String addusuario() {
        return "adicionar-usuario";
    }

    @GetMapping("usuarios")
    public String usuarios() {
        return "gerenciar-usuarios";
    }

    @GetMapping("/saldo")
    public String usuarioSaldo() {
        return "saldo";
    }

    @GetMapping("/transacao")
    public String transacao() {
        return "transacao";
    }
}

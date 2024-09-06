package felipe.proj.ecom.apresentacao.controller;

import felipe.proj.ecom.dominio.entidades.Venda;
import felipe.proj.ecom.aplicacao.service.RelatorioService;
import felipe.proj.ecom.aplicacao.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        return ResponseEntity.ok(vendaService.getAllVendas());
    }

    @PostMapping
    public ResponseEntity<Venda> addVenda(@RequestBody Venda venda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.addVenda(venda));
    }

    @GetMapping("/relatorio/semana")
    public ResponseEntity<List<Venda>> getRelatorioPorSemanaAtual() {
        return ResponseEntity.ok(relatorioService.getRelatorioPorSemanaAtual());
    }

    @GetMapping("/relatorio/mes")
    public ResponseEntity<List<Venda>> getRelatorioPorMesAtual() {
        return ResponseEntity.ok(relatorioService.getRelatorioPorMesAtual());
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<Venda>> getRelatorioPorData(@RequestParam("inicio") LocalDateTime inicio, @RequestParam("fim") LocalDateTime fim) {
        return ResponseEntity.ok(relatorioService.getRelatorioPorData(inicio, fim));
    }
}

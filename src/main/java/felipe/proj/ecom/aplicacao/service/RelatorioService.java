package felipe.proj.ecom.aplicacao.service;

import felipe.proj.ecom.dominio.entidades.Venda;
import felipe.proj.ecom.infraestrutura.repositorio.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private VendaRepository vendaRepository;

    public List<Venda> getRelatorioPorData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return vendaRepository.findAllBydatavendaBetween(dataInicio, dataFim);
    }

    public List<Venda> getRelatorioPorMesAtual() {
        LocalDateTime inicioDoMes = LocalDateTime.now().withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime fimDoMes = inicioDoMes.plusMonths(1).minusNanos(1);
        return getRelatorioPorData(inicioDoMes, fimDoMes);
    }

    public List<Venda> getRelatorioPorSemanaAtual() {
        LocalDateTime inicioDaSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));;
        LocalDateTime fimDaSemana = inicioDaSemana.plusWeeks(1).minusNanos(1);
        return getRelatorioPorData(inicioDaSemana, fimDaSemana);
    }
}

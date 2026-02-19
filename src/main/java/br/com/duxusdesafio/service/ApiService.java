package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.model.ComposicaoTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiService {

    /**
     * Vai retornar um Time, com a composição do time daquela data
     */
	public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
		// Utilizei Java Streams para filtrar a lista e encontrar o primeiro time correspondente à data informada.
        return todosOsTimes.stream()
                .filter(time -> time.getData().equals(data))
                .findFirst()
                .orElse(null);
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
	public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // Essa função percorre os times do período e encontra a pessoa que mais foi escalada.
        List<Time> filtrados = filtrarPorData(dataInicial, dataFinal, todosOsTimes);
        return filtrados.stream()
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(ComposicaoTime::getIntegrante)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // Analisa o cargo de cada um para dizer qual deles apareceu mais vezes.
        List<Time> filtrados = filtrarPorData(dataInicial, dataFinal, todosOsTimes);
        return filtrados.stream()
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // Serve para descobrir qual franquia teve mais representantes nos times escolhidos.
        List<Time> filtrados = filtrarPorData(dataInicial, dataFinal, todosOsTimes);
        return filtrados.stream()
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFranquia())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }
    
    /**
     * Filtra a lista de times com base em um intervalo de datas.
     * Criei este método para não precisar repetir a lógica de filtrar datas em todas as outras funções do projeto.
     * Ele garante que a gente só olhe para os times que estão dentro do período que o usuário escolheu.
     */
    private List<Time> filtrarPorData(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        return todosOsTimes.stream()
                .filter(time -> {
                    LocalDate data = time.getData();
                    // Verifica se o time está entre o dia inicial e o dia final selecionados.
                    boolean dpsInicio = (dataInicial == null) || !data.isBefore(dataInicial);
                    boolean antesFim = (dataFinal == null) || !data.isAfter(dataFinal);
                    return dpsInicio && antesFim;
                })
                .collect(Collectors.toList());
    }

}

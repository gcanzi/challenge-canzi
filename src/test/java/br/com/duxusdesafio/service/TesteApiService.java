package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.Integrante;

import br.com.duxusdesafio.model.Time;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class TesteApiService {

    private final static LocalDate data1993 = LocalDate.of(1993,1, 1);
    private final static LocalDate data1995 = LocalDate.of(1995,1, 1);

    @Spy
    private ApiService apiService;


    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @DataProvider
    public static Object[][] testTimeDaDataParams() {

        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();

        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        Time timeChicagoBullsDe1995 = dadosParaTesteApiService.getTimeChicagoBullsDe1995();
        Time timeDetroidPistonsDe1993 = dadosParaTesteApiService.getTimeDetroidPistonsDe1993();

        return new Object[][]{
                {
                        data1995,
                        todosOsTimes,
                        timeChicagoBullsDe1995
                },
                {
                        data1993,
                        todosOsTimes,
                        timeDetroidPistonsDe1993
                }
        };
    }

    @Test
    @UseDataProvider("testTimeDaDataParams")
    public void testTimeDaData(LocalDate data, List<Time> todosOsTimes, Time esperado) {

        Time timeRetornado = apiService.timeDaData(data, todosOsTimes);

        assertEquals(esperado, timeRetornado);
    }



    @DataProvider
    public static Object[][] testIntegranteMaisUsadoParams() {

        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();

        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        dadosParaTesteApiService.getDenis_rodman()
                }
        };
    }


    @Test
    @UseDataProvider("testIntegranteMaisUsadoParams")
    public void testIntegranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Integrante esperado) {

        Integrante integranteRetornado = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);

        assertEquals(esperado, integranteRetornado);
    }



    @DataProvider
    public static Object[][] testTimeMaisComumParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        List<String> integrantesEsperados = Arrays.asList(
                dadosParaTesteApiService.getDenis_rodman().getNome(),
                dadosParaTesteApiService.getMichael_jordan().getNome(),
                dadosParaTesteApiService.getScottie_pippen().getNome()
        );
        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        integrantesEsperados
                }
        };
    }

    @Test
    @UseDataProvider("testTimeMaisComumParams")
    public void testIntegrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, List<String> esperado) {

        List<String> nomeDosIntegrantesDoTimeMaisComum = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal, todosOsTimes);

        if(nomeDosIntegrantesDoTimeMaisComum != null){
            nomeDosIntegrantesDoTimeMaisComum.sort(Comparator.naturalOrder());
        }

        assertEquals(esperado, nomeDosIntegrantesDoTimeMaisComum);
    }



    @DataProvider
    public static Object[][] testFuncaoMaisComumParams() {

        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        "ala"
                }
        };
    }

    @Test
    @UseDataProvider("testFuncaoMaisComumParams")
    public void testFuncaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {

        String funcaoMaisComum = apiService.funcaoMaisComum(dataInicial, dataFinal, todosOsTimes);

        assertEquals(esperado, funcaoMaisComum);
    }

    @DataProvider
    public static Object[][] testFranquiaMaisFamosaParams() {
        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        dadosParaTesteApiService.getFranquiaNBA()
                }
        };
    }

    @Test
    @UseDataProvider("testFranquiaMaisFamosaParams")
    public void testFranquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {

        String franquiaMaisFamosa = apiService.franquiaMaisFamosa(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, franquiaMaisFamosa);
    }

    @DataProvider
    public static Object[][] testContagemPorFranquiaParams() {

        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put(dadosParaTesteApiService.getFranquiaNBA(), 2L);

        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        esperado
                }
        };
    }

    @Test
    @UseDataProvider("testContagemPorFranquiaParams")
    public void testContagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {

        Map<String, Long> contagemPorFranquia = apiService.contagemPorFranquia(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFranquia);
    }



    @DataProvider
    public static Object[][] testContagemPorFuncaoParams() {

        DadosParaTesteApiService dadosParaTesteApiService = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dadosParaTesteApiService.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put("ala", 2L);
        esperado.put("ala-pivô", 1L);

        return new Object[][]{
                {
                        data1993,
                        data1995,
                        todosOsTimes,
                        esperado
                }
        };
    }

    @Test
    @UseDataProvider("testContagemPorFuncaoParams")
    public void testContagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {

        Map<String, Long> contagemPorFuncao = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFuncao);
    }
    
    
    @Test
    public void testTimeDaDataComListaNula() {
        // Testa se a sua blindagem funciona quando alguém passa "null" no lugar da lista
        Time resultado = apiService.timeDaData(LocalDate.now(), null);
        assertNull("Deveria retornar null em vez de dar erro quando a lista de times for nula", resultado);
    }

    @Test
    public void testTimeDaDataComListaVazia() {
        // Testa a blindagem para listas vazias
        Time resultado = apiService.timeDaData(LocalDate.now(), new ArrayList<>());
        assertNull("Deveria retornar null quando a lista de times for vazia", resultado);
    }

    @Test
    public void testEstatisticasComListaNula() {
        // Testa se o integranteMaisUsado devolve null graciosamente se não houver lista
        Object resultado = apiService.integranteMaisUsado(LocalDate.now(), LocalDate.now(), null);
        assertNull("Deveria retornar null para estatísticas quando não houver times para analisar", resultado);
    }

    @Test
    public void testEstatisticasComListaVazia() {
        // Testa se os mapas retornam vazios em vez de quebrar a aplicação
        Map<String, Long> resultado = apiService.contagemPorFranquia(LocalDate.now(), LocalDate.now(), Collections.emptyList());
        assertTrue("O mapa deve retornar vazio (sem estourar erro) se a lista de times for vazia", resultado.isEmpty());
    }

    @Test
    public void testFiltroComDatasNulasEListaNula() {
        // Testa o pior cenário possível: datas nulas e lista nula ao mesmo tempo
        Map<String, Long> resultado = apiService.contagemPorFuncao(null, null, null);
        assertTrue("Deve retornar mapa vazio ao passar lista nula, mesmo com datas nulas", resultado.isEmpty());
    }
    
    @Test
    public void testFiltroComDatasInvertidas() {
        // Testa um erro comum de usuário: colocar a data de início depois da data de fim.
        // O sistema deve entender que não há times nesse período impossível e retornar null.
        DadosParaTesteApiService mock = new DadosParaTesteApiService();
        LocalDate dataInicial = LocalDate.of(1995, 1, 1);
        LocalDate dataFinal = LocalDate.of(1993, 1, 1);
        
        String resultado = apiService.funcaoMaisComum(dataInicial, dataFinal, mock.getTodosOsTimes());
        assertNull("Deveria retornar null ao pesquisar com um período de datas invertido", resultado);
    }

    @Test
    public void testFiltroParaPeriodoSemJogos() {
        // Testa se o sistema lida bem quando o usuário pesquisa um período válido, mas que não tem nenhum time cadastrado.
        DadosParaTesteApiService mock = new DadosParaTesteApiService();
        LocalDate dataInicial = LocalDate.of(2000, 1, 1);
        LocalDate dataFinal = LocalDate.of(2010, 1, 1);
        
        Map<String, Long> resultado = apiService.contagemPorFranquia(dataInicial, dataFinal, mock.getTodosOsTimes());
        assertTrue("O mapa deve retornar vazio se não houver times no período pesquisado", resultado.isEmpty());
    }
}

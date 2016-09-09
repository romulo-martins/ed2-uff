/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojoparticoes;

import org.junit.Test;
import utils.Arquivos;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author vanessa
 */
public class GeracaoParticoesTest {

    GeracaoParticoes instance = null;
    List<Cliente> entrada = null;
    List<Cliente> oraculoSaida = null;
    List<Cliente> saida = null;
    List<String> nomeArquivosSaida = null;
    private static final String NOME_ARQUIVO_ENTRADA = "entrada.dat";

    public GeracaoParticoesTest() {
    }

    @Before
    public void setUp() {
        instance = new GeracaoParticoes();
        entrada = new ArrayList<Cliente>();
        oraculoSaida = new ArrayList<Cliente>();
        nomeArquivosSaida = new ArrayList<String>();
    }

    @After
    public void tearDown() {
        //deleta os arquivos da rodada 
        for (int i = 0; i < nomeArquivosSaida.size(); i++) {
            Arquivos.deletaArquivo(nomeArquivosSaida.get(i));
        }
    }

    
    /*********************************************************************
     * *******************************************************************
     * TESTES RELACIONADOS A GERAÇÃO DE PARTIÇÃO POR CLASSIFICACAO INTERNA
     * *******************************************************************     
     *********************************************************************/
    
    /** 
     * Testa o caso de arquivo vazio
     * Como resultado, deve ser criado um arquivo p1.dat, também vazio
     */
    //@Test
    public void teste1ClassificacaoInterna() throws FileNotFoundException, Exception {
        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);
        nomeArquivosSaida.add("p1.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros já ordenados
     */
    //@Test
    public void teste2ClassificacaoInterna() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(5, "Maria"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros desordenados
     */
    //@Test
    public void teste3ClassificacaoInterna() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

    }

    /**
     * Testa o caso de arquivo com 6 registros desordenados
     */
    //@Test
    public void teste4ClassificacaoInterna() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(10, "Mirtes"));
        entrada.add(new Cliente(8, "Vanessa"));
        entrada.add(new Cliente(7, "Bruna"));
        entrada.add(new Cliente(2, "Marcos"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(2, "Marcos"));
        oraculoSaida.add(new Cliente(5, "Maria"));
        oraculoSaida.add(new Cliente(7, "Bruna"));
        oraculoSaida.add(new Cliente(8, "Vanessa"));
        oraculoSaida.add(new Cliente(10, "Mirtes"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 9 registros desordenados
     */
    //@Test
    public void teste5ClassificacaoInterna() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(10, "Mirtes"));
        entrada.add(new Cliente(20, "Mariana"));
        entrada.add(new Cliente(3, "Matheus"));
        entrada.add(new Cliente(87, "Jonas"));
        entrada.add(new Cliente(8, "Vanessa"));
        entrada.add(new Cliente(7, "Bruna"));
        entrada.add(new Cliente(2, "Marcos"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        nomeArquivosSaida.add("p1.dat");
        nomeArquivosSaida.add("p2.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        oraculoSaida.add(new Cliente(1, "João"));        
        oraculoSaida.add(new Cliente(3, "Matheus"));
        oraculoSaida.add(new Cliente(5, "Maria"));        
        oraculoSaida.add(new Cliente(10, "Mirtes"));
        oraculoSaida.add(new Cliente(20, "Mariana"));
        oraculoSaida.add(new Cliente(87, "Jonas"));
        
        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(2, "Marcos"));
        oraculoSaida.add(new Cliente(7, "Bruna"));
        oraculoSaida.add(new Cliente(8, "Vanessa"));        
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(1));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }
    
    /**
     * Testa o caso de arquivo com 20 registros desordenados
     */
    //@Test
    public void teste6ClassificacaoInterna() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(10, "Mirtes"));
        entrada.add(new Cliente(20, "Mariana"));
        entrada.add(new Cliente(3, "Matheus"));
        entrada.add(new Cliente(87, "Jonas"));
        
        entrada.add(new Cliente(8, "Vanessa"));
        entrada.add(new Cliente(7, "Bruna"));
        entrada.add(new Cliente(2, "Marcos"));
        entrada.add(new Cliente(0, "Julia"));
        entrada.add(new Cliente(9, "Ana Maria"));
        entrada.add(new Cliente(81, "Bianca"));
        
        entrada.add(new Cliente(60, "Hugo"));
        entrada.add(new Cliente(47, "Martim"));
        entrada.add(new Cliente(23, "Clarissa"));
        entrada.add(new Cliente(22, "Lucila"));
        entrada.add(new Cliente(35, "Marceu"));
        entrada.add(new Cliente(48, "Tatiana"));
        
        entrada.add(new Cliente(90, "Catarina"));
        entrada.add(new Cliente(85, "Leonardo"));
        

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        nomeArquivosSaida.add("p1.dat");
        nomeArquivosSaida.add("p2.dat");
        nomeArquivosSaida.add("p3.dat");
        nomeArquivosSaida.add("p4.dat");

        instance.executaClassificacaoInterna(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);
        
        oraculoSaida.add(new Cliente(1, "João"));                        
        oraculoSaida.add(new Cliente(3, "Matheus"));
        oraculoSaida.add(new Cliente(5, "Maria"));        
        oraculoSaida.add(new Cliente(10, "Mirtes"));
        oraculoSaida.add(new Cliente(20, "Mariana"));
        oraculoSaida.add(new Cliente(87, "Jonas"));                               

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(0, "Julia"));
        oraculoSaida.add(new Cliente(2, "Marcos"));
        oraculoSaida.add(new Cliente(7, "Bruna"));
        oraculoSaida.add(new Cliente(8, "Vanessa"));
        oraculoSaida.add(new Cliente(9, "Ana Maria"));
        oraculoSaida.add(new Cliente(81, "Bianca"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(1));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(22, "Lucila"));
        oraculoSaida.add(new Cliente(23, "Clarissa"));
        oraculoSaida.add(new Cliente(35, "Marceu"));
        oraculoSaida.add(new Cliente(47, "Martim"));
        oraculoSaida.add(new Cliente(48, "Tatiana"));
        oraculoSaida.add(new Cliente(60, "Hugo"));        
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(2));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(85, "Leonardo"));        
        oraculoSaida.add(new Cliente(90, "Catarina"));   
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(3));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());                
    }
    
    /*********************************************************************
     * *******************************************************************
     * TESTES RELACIONADOS A GERAÇÃO DE PARTIÇÃO POR SELECAO COM SUBSTITUICAO
     * *******************************************************************     
     *********************************************************************/ 
    
    
    /** 
     * Testa o caso de arquivo vazio
     * Como resultado, deve ser criado um arquivo p1.dat, também vazio
     */
    @Test
    public void teste1SelecaoComSubstituicao() throws FileNotFoundException, Exception {
        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);
        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoComSubstituicao(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros já ordenados
     */
    @Test
    public void teste2SelecaoComSubstituicao() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(5, "Maria"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoComSubstituicao(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros desordenados
     */
    @Test
    public void teste3SelecaoComSubstituicao() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoComSubstituicao(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

    }

    /**
     * Testa o caso de arquivo com 6 registros desordenados
     */
    @Test
    public void teste4SelecaoComSubstituicao() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(10, "Mirtes"));
        entrada.add(new Cliente(8, "Vanessa"));
        entrada.add(new Cliente(7, "Bruna"));
        entrada.add(new Cliente(2, "Marcos"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(2, "Marcos"));
        oraculoSaida.add(new Cliente(5, "Maria"));
        oraculoSaida.add(new Cliente(7, "Bruna"));
        oraculoSaida.add(new Cliente(8, "Vanessa"));
        oraculoSaida.add(new Cliente(10, "Mirtes"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoComSubstituicao(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o exemplo visto em aula
     */
    @Test
    public void teste5SelecaoComSubstituicao() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(29, "Maria"));
        entrada.add(new Cliente(14, "João"));
        entrada.add(new Cliente(76, "Mirtes"));
        entrada.add(new Cliente(75, "Mariana"));
        entrada.add(new Cliente(59, "Matheus"));
        entrada.add(new Cliente(6, "Jonas"));
        entrada.add(new Cliente(7, "Vanessa"));
        entrada.add(new Cliente(74, "Karla"));
        entrada.add(new Cliente(48, "Tatiana"));
        entrada.add(new Cliente(46, "Larissa"));
        entrada.add(new Cliente(10, "Marcela"));
        entrada.add(new Cliente(18, "Bruna"));
        entrada.add(new Cliente(56, "Catarina"));
        entrada.add(new Cliente(20, "Leonel"));
        entrada.add(new Cliente(26, "Leo"));
        entrada.add(new Cliente(4, "Yasmin"));
        entrada.add(new Cliente(21, "Ana"));
        entrada.add(new Cliente(65, "Yoko"));
        entrada.add(new Cliente(22, "Maurício"));
        entrada.add(new Cliente(49, "José"));
        entrada.add(new Cliente(11, "Alice"));
        entrada.add(new Cliente(16, "JC"));
        entrada.add(new Cliente(8, "TJ"));
        entrada.add(new Cliente(15, "Maira"));
        entrada.add(new Cliente(5, "Viviane"));
        entrada.add(new Cliente(19, "Fernanda"));
        entrada.add(new Cliente(50, "Daniel"));
        entrada.add(new Cliente(55, "Diego"));
        entrada.add(new Cliente(25, "Harry Potter"));
        entrada.add(new Cliente(66, "James Bond"));
        entrada.add(new Cliente(57, "Clark Kent"));
        entrada.add(new Cliente(77, "Lois Lane"));
        entrada.add(new Cliente(12, "Íris"));
        entrada.add(new Cliente(30, "Rosa"));
        entrada.add(new Cliente(17, "Helô"));
        entrada.add(new Cliente(9, "Joel"));
        entrada.add(new Cliente(54, "Carlos"));
        entrada.add(new Cliente(78, "Alex"));
        entrada.add(new Cliente(43, "Adriel"));
        entrada.add(new Cliente(38, "Ana Paula"));
        entrada.add(new Cliente(51, "Bia"));
        entrada.add(new Cliente(32, "Milton"));
        entrada.add(new Cliente(58, "Xande"));
        entrada.add(new Cliente(13, "Fausto"));
        entrada.add(new Cliente(73, "Sidney"));
        entrada.add(new Cliente(79, "Igor"));
        entrada.add(new Cliente(27, "Alexandre"));
        entrada.add(new Cliente(1, "Aline"));
        entrada.add(new Cliente(3, "Andrea"));
        entrada.add(new Cliente(60, "Murilo"));
        entrada.add(new Cliente(36, "Rafael"));
        entrada.add(new Cliente(47, "Ricardo"));
        entrada.add(new Cliente(31, "Regiane"));
        entrada.add(new Cliente(80, "Fábio"));
        
        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        nomeArquivosSaida.add("p1.dat");
        nomeArquivosSaida.add("p2.dat");
        nomeArquivosSaida.add("p3.dat");
        nomeArquivosSaida.add("p4.dat");
        nomeArquivosSaida.add("p5.dat");

        instance.executaSelecaoComSubstituicao(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6);

        oraculoSaida.add(new Cliente(6, "Jonas"));
        oraculoSaida.add(new Cliente(7, "Vanessa"));
        oraculoSaida.add(new Cliente(14, "João"));
        oraculoSaida.add(new Cliente(29, "Maria"));
        oraculoSaida.add(new Cliente(46, "Larissa"));
        oraculoSaida.add(new Cliente(48, "Tatiana"));
        oraculoSaida.add(new Cliente(59, "Matheus"));
        oraculoSaida.add(new Cliente(74, "Karla"));        
        oraculoSaida.add(new Cliente(75, "Mariana"));
        oraculoSaida.add(new Cliente(76, "Mirtes"));
        
        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(4, "Yasmin"));
        oraculoSaida.add(new Cliente(10, "Marcela"));
        oraculoSaida.add(new Cliente(18, "Bruna"));        
        oraculoSaida.add(new Cliente(20, "Leonel"));
        oraculoSaida.add(new Cliente(21, "Ana"));
        oraculoSaida.add(new Cliente(22, "Maurício"));
        oraculoSaida.add(new Cliente(26, "Leo"));
        oraculoSaida.add(new Cliente(49, "José"));
        oraculoSaida.add(new Cliente(56, "Catarina"));
        oraculoSaida.add(new Cliente(65, "Yoko"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(1));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(5, "Viviane"));
        oraculoSaida.add(new Cliente(8, "TJ"));
        oraculoSaida.add(new Cliente(11, "Alice"));
        oraculoSaida.add(new Cliente(15, "Maira"));
        oraculoSaida.add(new Cliente(16, "JC"));
        oraculoSaida.add(new Cliente(19, "Fernanda"));
        oraculoSaida.add(new Cliente(25, "Harry Potter"));
        oraculoSaida.add(new Cliente(50, "Daniel"));
        oraculoSaida.add(new Cliente(55, "Diego"));
        oraculoSaida.add(new Cliente(57, "Clark Kent"));
        oraculoSaida.add(new Cliente(66, "James Bond"));
        oraculoSaida.add(new Cliente(77, "Lois Lane"));
        oraculoSaida.add(new Cliente(78, "Alex"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(2));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(9, "Joel"));
        oraculoSaida.add(new Cliente(12, "Íris"));        
        oraculoSaida.add(new Cliente(17, "Helô"));
        oraculoSaida.add(new Cliente(30, "Rosa"));
        oraculoSaida.add(new Cliente(32, "Milton"));
        oraculoSaida.add(new Cliente(38, "Ana Paula"));
        oraculoSaida.add(new Cliente(43, "Adriel"));
        oraculoSaida.add(new Cliente(51, "Bia"));
        oraculoSaida.add(new Cliente(54, "Carlos"));
        oraculoSaida.add(new Cliente(58, "Xande"));        
        oraculoSaida.add(new Cliente(73, "Sidney"));
        oraculoSaida.add(new Cliente(79, "Igor"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(3));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(1, "Aline"));
        oraculoSaida.add(new Cliente(3, "Andrea"));
        oraculoSaida.add(new Cliente(13, "Fausto"));
        oraculoSaida.add(new Cliente(27, "Alexandre"));
        oraculoSaida.add(new Cliente(31, "Regiane"));
        oraculoSaida.add(new Cliente(36, "Rafael"));
        oraculoSaida.add(new Cliente(47, "Ricardo"));
        oraculoSaida.add(new Cliente(60, "Murilo"));
        oraculoSaida.add(new Cliente(80, "Fábio"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(4));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }
    
    /*********************************************************************
     * *******************************************************************
     * TESTES RELACIONADOS A GERAÇÃO DE PARTIÇÃO POR SELECAO NATURAL
     * *******************************************************************     
     *********************************************************************/ 
    
    
    /** 
     * Testa o caso de arquivo vazio
     * Como resultado, deve ser criado um arquivo p1.dat, também vazio
     */
    //@Test
    public void teste1SelecaoNatural() throws FileNotFoundException, Exception {
        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);
        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoNatural(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros já ordenados
     */
    //@Test
    public void teste2SelecaoNatural() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(5, "Maria"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoNatural(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de arquivo com 2 registros desordenados
     */
    //@Test
    public void teste3SelecaoNatural() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(5, "Maria"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoNatural(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

    }

    /**
     * Testa o caso de arquivo com 6 registros desordenados
     */
    //@Test
    public void teste4SelecaoNatural() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(5, "Maria"));
        entrada.add(new Cliente(1, "João"));
        entrada.add(new Cliente(10, "Mirtes"));
        entrada.add(new Cliente(8, "Vanessa"));
        entrada.add(new Cliente(7, "Bruna"));
        entrada.add(new Cliente(2, "Marcos"));

        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        oraculoSaida.add(new Cliente(1, "João"));
        oraculoSaida.add(new Cliente(2, "Marcos"));
        oraculoSaida.add(new Cliente(5, "Maria"));
        oraculoSaida.add(new Cliente(7, "Bruna"));
        oraculoSaida.add(new Cliente(8, "Vanessa"));
        oraculoSaida.add(new Cliente(10, "Mirtes"));

        nomeArquivosSaida.add("p1.dat");

        instance.executaSelecaoNatural(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6, 6);

        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o exemplo visto em aula
     */
    //@Test
    public void teste5SelecaoNatural() throws FileNotFoundException, Exception {
        entrada.add(new Cliente(29, "Maria"));
        entrada.add(new Cliente(14, "João"));
        entrada.add(new Cliente(76, "Mirtes"));
        entrada.add(new Cliente(75, "Mariana"));
        entrada.add(new Cliente(59, "Matheus"));
        entrada.add(new Cliente(6, "Jonas"));
        entrada.add(new Cliente(7, "Vanessa"));
        entrada.add(new Cliente(74, "Karla"));
        entrada.add(new Cliente(48, "Tatiana"));
        entrada.add(new Cliente(46, "Larissa"));
        entrada.add(new Cliente(10, "Marcela"));
        entrada.add(new Cliente(18, "Bruna"));
        entrada.add(new Cliente(56, "Catarina"));
        entrada.add(new Cliente(20, "Leonel"));
        entrada.add(new Cliente(26, "Leo"));
        entrada.add(new Cliente(4, "Yasmin"));
        entrada.add(new Cliente(21, "Ana"));
        entrada.add(new Cliente(65, "Yoko"));
        entrada.add(new Cliente(22, "Maurício"));
        entrada.add(new Cliente(49, "José"));
        entrada.add(new Cliente(11, "Alice"));
        entrada.add(new Cliente(16, "JC"));
        entrada.add(new Cliente(8, "TJ"));
        entrada.add(new Cliente(15, "Maira"));
        entrada.add(new Cliente(5, "Viviane"));
        entrada.add(new Cliente(19, "Fernanda"));
        entrada.add(new Cliente(50, "Daniel"));
        entrada.add(new Cliente(55, "Diego"));
        entrada.add(new Cliente(25, "Harry Potter"));
        entrada.add(new Cliente(66, "James Bond"));
        entrada.add(new Cliente(57, "Clark Kent"));
        entrada.add(new Cliente(77, "Lois Lane"));
        entrada.add(new Cliente(12, "Íris"));
        entrada.add(new Cliente(30, "Rosa"));
        entrada.add(new Cliente(17, "Helô"));
        entrada.add(new Cliente(9, "Joel"));
        entrada.add(new Cliente(54, "Carlos"));
        entrada.add(new Cliente(78, "Alex"));
        entrada.add(new Cliente(43, "Adriel"));
        entrada.add(new Cliente(38, "Ana Paula"));
        entrada.add(new Cliente(51, "Bia"));
        entrada.add(new Cliente(32, "Milton"));
        entrada.add(new Cliente(58, "Xande"));
        entrada.add(new Cliente(13, "Fausto"));
        entrada.add(new Cliente(73, "Sidney"));
        entrada.add(new Cliente(79, "Igor"));
        entrada.add(new Cliente(27, "Alexandre"));
        entrada.add(new Cliente(1, "Aline"));
        entrada.add(new Cliente(3, "Andrea"));
        entrada.add(new Cliente(60, "Murilo"));
        entrada.add(new Cliente(36, "Rafael"));
        entrada.add(new Cliente(47, "Ricardo"));
        entrada.add(new Cliente(31, "Regiane"));
        entrada.add(new Cliente(80, "Fábio"));
        
        Arquivos.salva(NOME_ARQUIVO_ENTRADA, entrada);

        nomeArquivosSaida.add("p1.dat");
        nomeArquivosSaida.add("p2.dat");
        nomeArquivosSaida.add("p3.dat");
        nomeArquivosSaida.add("p4.dat");
        nomeArquivosSaida.add("p5.dat");

        instance.executaSelecaoNatural(NOME_ARQUIVO_ENTRADA, nomeArquivosSaida, 6, 6);

        oraculoSaida.add(new Cliente(6, "Jonas"));
        oraculoSaida.add(new Cliente(7, "Vanessa"));
        oraculoSaida.add(new Cliente(14, "João"));
        oraculoSaida.add(new Cliente(29, "Maria"));
        oraculoSaida.add(new Cliente(46, "Larissa"));
        oraculoSaida.add(new Cliente(48, "Tatiana"));
        oraculoSaida.add(new Cliente(56, "Catarina"));
        oraculoSaida.add(new Cliente(59, "Matheus"));
        oraculoSaida.add(new Cliente(74, "Karla"));        
        oraculoSaida.add(new Cliente(75, "Mariana"));
        oraculoSaida.add(new Cliente(76, "Mirtes"));
        
        saida = Arquivos.leCliente(nomeArquivosSaida.get(0));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());

        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(4, "Yasmin"));
        oraculoSaida.add(new Cliente(10, "Marcela"));
        oraculoSaida.add(new Cliente(18, "Bruna"));        
        oraculoSaida.add(new Cliente(20, "Leonel"));
        oraculoSaida.add(new Cliente(21, "Ana"));
        oraculoSaida.add(new Cliente(22, "Maurício"));
        oraculoSaida.add(new Cliente(26, "Leo"));
        oraculoSaida.add(new Cliente(49, "José"));        
        oraculoSaida.add(new Cliente(65, "Yoko"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(1));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(5, "Viviane"));
        oraculoSaida.add(new Cliente(8, "TJ"));
        oraculoSaida.add(new Cliente(11, "Alice"));
        oraculoSaida.add(new Cliente(15, "Maira"));
        oraculoSaida.add(new Cliente(16, "JC"));
        oraculoSaida.add(new Cliente(19, "Fernanda"));
        oraculoSaida.add(new Cliente(25, "Harry Potter"));
        oraculoSaida.add(new Cliente(30, "Rosa"));
        oraculoSaida.add(new Cliente(50, "Daniel"));
        oraculoSaida.add(new Cliente(54, "Carlos"));
        oraculoSaida.add(new Cliente(55, "Diego"));
        oraculoSaida.add(new Cliente(57, "Clark Kent"));
        oraculoSaida.add(new Cliente(66, "James Bond"));
        oraculoSaida.add(new Cliente(77, "Lois Lane"));
        oraculoSaida.add(new Cliente(78, "Alex"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(2));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(9, "Joel"));
        oraculoSaida.add(new Cliente(12, "Íris"));        
        oraculoSaida.add(new Cliente(17, "Helô"));        
        oraculoSaida.add(new Cliente(32, "Milton"));
        oraculoSaida.add(new Cliente(38, "Ana Paula"));
        oraculoSaida.add(new Cliente(43, "Adriel"));
        oraculoSaida.add(new Cliente(47, "Ricardo"));
        oraculoSaida.add(new Cliente(51, "Bia"));        
        oraculoSaida.add(new Cliente(58, "Xande"));  
        oraculoSaida.add(new Cliente(60, "Murilo"));
        oraculoSaida.add(new Cliente(73, "Sidney"));
        oraculoSaida.add(new Cliente(79, "Igor"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(3));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        
        oraculoSaida.clear();
        oraculoSaida.add(new Cliente(1, "Aline"));
        oraculoSaida.add(new Cliente(3, "Andrea"));
        oraculoSaida.add(new Cliente(13, "Fausto"));
        oraculoSaida.add(new Cliente(27, "Alexandre"));
        oraculoSaida.add(new Cliente(31, "Regiane"));
        oraculoSaida.add(new Cliente(36, "Rafael"));      
        oraculoSaida.add(new Cliente(80, "Fábio"));
        
        saida.clear();
        saida = Arquivos.leCliente(nomeArquivosSaida.get(4));
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }
    
        
    
}

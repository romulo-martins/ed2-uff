/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojohash;

import org.junit.Test;
import utils.Arquivos;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Romulo
 */
public class EncadeamentoInteriorTest {

    EncadeamentoInterior instance = null;
    List<Cliente> tabHash = null;
    List<Cliente> tabHashSaida = null;

    
    private static final String NOME_ARQUIVO_HASH = "tabHash.dat";
    
    public EncadeamentoInteriorTest() {
    }
    
    @Before
    public void setUp() {
        instance = new EncadeamentoInterior();
        tabHash = new ArrayList<Cliente>();
        tabHashSaida = new ArrayList<Cliente>();

        //deleta o arquivo da rodada anterior
        Arquivos.deletaArquivo(NOME_ARQUIVO_HASH);
    }
    
    /**
     * Testa o caso de tabela hash com compartimentos vazios
     */
    @Test
    public void testaCriaTabelaVazia() throws FileNotFoundException, Exception {
        //Estamos usando tabela hash de tamanho 7
        //Adicionar 7 compartimentos que não apontam para ninguem (prox = -1)
        for (int i = 0; i <7; i++) {
            tabHash.add(new Cliente(-1, "          ",i, Cliente.LIBERADO));
        }        

        instance.criaHash(NOME_ARQUIVO_HASH, 7);
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);      
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa busca de chave que é encontrada na primeira tentativa
     */
    @Test
    public void testaBuscaChave1Tentativa() throws FileNotFoundException, Exception {
        for (int i = 0; i <7; i++) {
            if(i==1)
                tabHash.add(new Cliente(50,  "Carlos    ",i, Cliente.OCUPADO));
            else
                tabHash.add(new Cliente(-1, "          ",i, Cliente.LIBERADO));
        }
        
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        
        Result result = instance.busca(50,NOME_ARQUIVO_HASH);
        //endereço retornado deve ser igual a 1
        assertEquals(1, result.end);
        // achou deve ser 1
        assertEquals(1, result.a);
    }

    /**
     * Testa busca de chave que existia mas foi removida
     */
    @Test
    public void testaBuscaChaveRemovida() throws FileNotFoundException, Exception {
        for (int i = 0; i <7; i++) {
            if(i==1)
                tabHash.add(new Cliente(50,  "Carlos    ",i, Cliente.LIBERADO));
            else
                tabHash.add(new Cliente(-1, "          ",i, Cliente.LIBERADO));
        }
        
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        
        Result result = instance.busca(50,NOME_ARQUIVO_HASH);
        //endereço retornado deve ser igual a 1
        assertEquals(1, result.end);
        // achou deve ser 2
        assertEquals(2, result.a);
    }
    /**
     * Testa busca de chave que é encontrada na segunda tentativa
     */
    @Test
    public void testaBuscaChave2Tentativa() throws FileNotFoundException, Exception {
        
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);

        Result r = instance.busca(10,NOME_ARQUIVO_HASH);
        //endereço retornado deve ser igual a 4
        assertEquals(4, r.end);
        assertEquals(1, r.a);
    }
    
    /**
     * Testa busca de chave inexistente
     */
    @Test
    public void testaBuscaChaveInexistente() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.LIBERADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        
        Result r = instance.busca(17,NOME_ARQUIVO_HASH);
        //endereço retornado deve ser igual a 3, pois ele achou um LIBERADO durante a busca
        //a deve ser igual a 2, pois chave não foi encontrada na tabela
        assertEquals(3, r.end);
        assertEquals(2, r.a);
    }

    /**
     * Testa busca de chave que havia sido removida, mas foi reinserida mais adiante no arquivo
     */
    @Test
    public void testaBuscaChaveReinserida() throws FileNotFoundException, Exception { 
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 6, Cliente.LIBERADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Mauricio  ", 6, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        
        Result r = instance.busca(10,NOME_ARQUIVO_HASH);
        //endereço retornado deve ser igual a 6
        //a deve ser igual a 1, pois chave foi encontrada na tabela
        assertEquals(6, r.end);
        assertEquals(1, r.a);
    }

    /**
     * Testa inserção de cliente - existe compartimento vazio na tabela hash para receber o registro
     */
    @Test
    public void testaInsere1Tentativa() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(-1,  "          ", 0, Cliente.LIBERADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
              
        int end = instance.insere(49,"Joao      ", NOME_ARQUIVO_HASH);
        assertEquals(0, end);
        
        tabHash.clear();
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
            
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);        
                  
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção de registro com chave que já existe
     */
    @Test
    public void testaInsereChaveExistente() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);

        int end = instance.insere(49, "Jorge     ", NOME_ARQUIVO_HASH);
        assertEquals(-1, end);
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);        
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção no final da lista encadeada
     */
    @Test
    public void testaInsereFinalLista() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
              
        int end = instance.insere(87,"Mariana   ", NOME_ARQUIVO_HASH);
        assertEquals(6, end);
        
        tabHash.clear();
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 6, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(87,  "Mariana   ", 6, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
            
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);        
                  
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção no de registro em espaco vazio deixado por registro excluído
     */
    @Test
    public void testaInsereEspacoVazio() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.LIBERADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        
        int end = instance.insere(87, "Mariana   ", NOME_ARQUIVO_HASH);
        assertEquals(3, end);

        tabHash.clear();
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(87,  "Mariana   ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }
    
    /**
     * Testa exclusão de chave não existente
     */
    @Test
    public void testaExclusaoChaveInexistente() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);

        int end = instance.exclui(87, NOME_ARQUIVO_HASH);
        assertEquals(-1, end);
        
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa exclusão do primeiro no da lista encadeada 
     */
    @Test
    public void testaExclusaoPrimeiroNo() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);

        int end = instance.exclui(59, NOME_ARQUIVO_HASH);
        assertEquals(3, end);

        tabHash.clear();
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.LIBERADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa exclusão do ultimo no da lista encadeada de um determinado compartimento
     */
    @Test
    public void testaExclusaoUltimoNo() throws FileNotFoundException, Exception {
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);

        int end = instance.exclui(10, NOME_ARQUIVO_HASH);
        assertEquals(4, end);

        tabHash.clear();
        tabHash.add(new Cliente(49,  "Joao      ", 0, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 1, Cliente.LIBERADO));
        tabHash.add(new Cliente(51,  "Ana       ", 2, Cliente.OCUPADO));
        tabHash.add(new Cliente(59,  "Maria     ", 4, Cliente.OCUPADO));
        tabHash.add(new Cliente(10,  "Janio     ", 4, Cliente.LIBERADO));
        tabHash.add(new Cliente(103, "Pedro     ", 5, Cliente.OCUPADO));
        tabHash.add(new Cliente(-1,  "          ", 6, Cliente.LIBERADO));       
        
        tabHashSaida = Arquivos.leClientes(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }
  

}
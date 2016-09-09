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
 * @author vanessa
 */
public class EncadeamentoExteriorTest {

    EncadeamentoExterior instance = null;
    List<CompartimentoHash> tabHash = null;
    List<CompartimentoHash> tabHashSaida = null;

    List<Cliente> tabCliente = null;
    List<Cliente> tabClienteSaida = null;
    
    private static final String NOME_ARQUIVO_HASH = "tabHash.dat";
    private static final String NOME_ARQUIVO_DADOS = "clientes.dat";

    public EncadeamentoExteriorTest() {
    }
    
    @Before
    public void setUp() {
        instance = new EncadeamentoExterior();
        tabCliente = new ArrayList<Cliente>();
        tabClienteSaida = new ArrayList<Cliente>();
        tabHash = new ArrayList<CompartimentoHash>();
        tabHashSaida = new ArrayList<CompartimentoHash>();

        //deleta o arquivo da rodada anterior
        Arquivos.deletaArquivo(NOME_ARQUIVO_DADOS);
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
            tabHash.add(new CompartimentoHash(-1));
        }        

        instance.criaHash(NOME_ARQUIVO_HASH, 7);
        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);      
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa busca de chave que é encontrada na primeira tentativa
     */
    @Test
    public void testaBuscaChave1Tentativa() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(50,  "Carlos    ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);
        
        int end = instance.busca(50,NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        //endereço retornado deve ser igual a 0
        assertEquals(0, end);
    }

    /**
     * Testa busca de chave que existia mas foi removida
     */
    @Test
    public void testaBuscaChaveRemovida() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(50,  "Carlos    ", -1, Cliente.LIBERADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);
        
        int end = instance.busca(50,NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        //endereço retornado deve ser igual a -1
        assertEquals(-1, end);
    }
    /**
     * Testa busca de chave que é encontrada na segunda tentativa
     */
    @Test
    public void testaBuscaChave2Tentativa() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(10,  "Janio     ", 5, Cliente.OCUPADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.busca(10,NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        //endereço retornado deve ser igual a 3
        assertEquals(3, end);
    }
    
    /**
     * Testa busca de chave inexistente
     */
    @Test
    public void testaBuscaChaveInexistente() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.OCUPADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);
        
        int end = instance.busca(10,NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        //endereço retornado deve ser igual a -1
        assertEquals(-1, end);
    }

    /**
     * Testa busca de chave que havia sido removida, mas foi reinserida mais adiante no arquivo
     */
    @Test
    public void testaBuscaChaveReinserida() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ",  3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(10,  "Janio     ",  5, Cliente.LIBERADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ",  6, Cliente.OCUPADO));
        tabCliente.add(new Cliente(10,  "Janio S.  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.busca(10,NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        //endereço retornado deve ser igual a 3
        assertEquals(6, end);
    }

    /**
     * Testa inserção de cliente - existe compartimento vazio na tabela hash para receber o registro
     */
    @Test
    public void testaInsere1Tentativa() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);
       
        int end = instance.insere(50,"Mariana   ", NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS, 1);
        assertEquals(1, end);
        
        tabCliente.add(new Cliente(50, "Mariana   ", -1, Cliente.OCUPADO));        
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);        
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());

        tabHash.remove(1);
        tabHash.add(1, new CompartimentoHash(1));        
        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);        
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção de registro com chave que já existe
     */
    @Test
    public void testaInsereChaveExistente() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.insere(49,"Jorge     ", NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS, 1);
        assertEquals(-1, end);
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);        
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());
        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);        
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção no final da lista encadeada
     */
    @Test
    public void testaInsereFinalLista() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.OCUPADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.insere(10,"Ana       ", NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS, 6);
        assertEquals(6, end);

        tabCliente.remove(5);
        tabCliente.add(new Cliente(87, "Mauricio  ", 6, Cliente.OCUPADO));
        tabCliente.add(new Cliente(10, "Ana       ", -1, Cliente.OCUPADO));
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());
        
        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa inserção no de registro em espaco vazio deixado por registro excluído
     */
    @Test
    public void testaInsereEspacoVazio() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.LIBERADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.insere(10, "Ana       ", NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS, 6);
        assertEquals(3, end);

        tabCliente.remove(3);
        tabCliente.add(3, new Cliente(10, "Ana       ", 5, Cliente.OCUPADO));
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());

        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }
    
    /**
     * Testa exclusão de chave não existente
     */
    @Test
    public void testaExclusaoChaveInexistente() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.LIBERADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.exclui(10, NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        assertEquals(-1, end);
        
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());

        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa exclusão do primeiro no da lista encadeada de um determinado compartimento
     */
    @Test
    public void testaExclusaoPrimeiroNo() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.LIBERADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.exclui(59, NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        assertEquals(1, end);

        tabCliente.remove(1);
        tabCliente.add(1, new Cliente(59,  "Maria     ", 3, Cliente.LIBERADO));
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());

        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

    /**
     * Testa exclusão do ultimo no da lista encadeada de um determinado compartimento
     */
    @Test
    public void testaExclusaoUltimoNo() throws FileNotFoundException, Exception {
        tabHash.add(new CompartimentoHash(0));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(4));
        tabHash.add(new CompartimentoHash(1));
        tabHash.add(new CompartimentoHash(-1));
        tabHash.add(new CompartimentoHash(2));
        tabHash.add(new CompartimentoHash(-1));
        Arquivos.salva(NOME_ARQUIVO_HASH, tabHash);
        tabCliente.add(new Cliente(49,  "Joao      ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(59,  "Maria     ", 3, Cliente.OCUPADO));
        tabCliente.add(new Cliente(103, "Pedro     ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(3,   "Janio     ", 5, Cliente.LIBERADO));
        tabCliente.add(new Cliente(51,  "Ana       ", -1, Cliente.OCUPADO));
        tabCliente.add(new Cliente(87,  "Mauricio  ", -1, Cliente.OCUPADO));
        Arquivos.salva(NOME_ARQUIVO_DADOS, tabCliente);

        int end = instance.exclui(87, NOME_ARQUIVO_HASH, NOME_ARQUIVO_DADOS);
        assertEquals(5, end);

        tabCliente.remove(5);
        tabCliente.add(5, new Cliente(87,  "Mauricio  ", -1, Cliente.LIBERADO));
        tabClienteSaida = Arquivos.leClientes(NOME_ARQUIVO_DADOS);
        assertArrayEquals(tabCliente.toArray(), tabClienteSaida.toArray());

        tabHashSaida = Arquivos.leCompartimentos(NOME_ARQUIVO_HASH);
        assertArrayEquals(tabHash.toArray(), tabHashSaida.toArray());
    }

}
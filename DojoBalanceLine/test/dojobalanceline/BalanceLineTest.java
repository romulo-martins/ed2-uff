/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojobalanceline;

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
public class BalanceLineTest {

    BalanceLine instance = null;
    List<Cliente> mestre = null;
    List<Transacao> transacoes = null;
    List<Cliente> oraculoSaida = null;
    List<Transacao> oraculoErros = null;
    List<Cliente> saida = null;
    List<Transacao> erros = null;

    private static final String NOME_ARQUIVO_MESTRE = "mestre.dat";
    private static final String NOME_ARQUIVO_TRANSACOES = "transacoes.dat";
    private static final String NOME_ARQUIVO_SAIDA = "saida.dat";
    private static final String NOME_ARQUIVO_ERROS = "erros.dat";

    public BalanceLineTest() {
    }

    @Before
    public void setUp() {
        instance = new BalanceLine();
        mestre = new ArrayList<Cliente>();
        transacoes = new ArrayList<Transacao>();
        oraculoSaida = new ArrayList<Cliente>();
        oraculoErros = new ArrayList<Transacao>();
        //deleta o arquivo da rodada anterior
        Arquivos.deletaArquivo(NOME_ARQUIVO_SAIDA);
        Arquivos.deletaArquivo(NOME_ARQUIVO_ERROS);
    }

    @After
    public void tearDown() {
    }

    /** 
     * Testa o caso de arquivo Mestre e Arquivo de Transação vazios
     */
    @Test
    public void testaArquivosVazios() throws FileNotFoundException, Exception {
        
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

    /**
     * Testa o caso de arquivo Mestre com registros e arquivo transações vazio
     */
    @Test
    public void testaTransacoesVazio() throws FileNotFoundException, Exception {
        mestre.add(new Cliente(1, "João", "12/04/2000"));
        mestre.add(new Cliente(5, "Maria", "14/06/1999"));
        mestre.add(new Cliente(7, "Pedro", "23/07/1992"));
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);
        
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

    /**
     * Testa o caso de arquivo Mestre vazio e arquivo transações com inserções
     */
    @Test
    public void testaMestreVazio() throws FileNotFoundException, Exception {
        
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoInsercao(1, "João", "12/04/2000"));
        transacoes.add(new TransacaoInsercao(5, "Maria", "14/06/1999"));
        transacoes.add(new TransacaoInsercao(7, "Pedro", "23/07/1992"));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

    /**
     * Testa o caso de arquivo Mestre vazio e arquivo transações com inserções e modificações
     * As modificações devem acarretar erro, já que os registros a serem modificados não existem
     */
    
    
    @Test
    public void testaMestreVazioComErros() throws FileNotFoundException, Exception {

        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoInsercao(1, "João", "12/04/2000"));
        transacoes.add(new TransacaoInsercao(5, "Maria", "14/06/1999"));
        transacoes.add(new TransacaoModificacao(6, "nome", "Marta"));
        transacoes.add(new TransacaoInsercao(7, "Pedro", "23/07/1992"));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        oraculoErros.add(new TransacaoModificacao(6, "nome", "Marta"));
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

    /**
     * Testa o caso de arquivo Mestre com registros e arquivo transações com inserções
     * Não há erros neste caso
     */
    @Test
    public void testaInsercoes() throws FileNotFoundException, Exception {

        mestre.add(new Cliente(3, "Ana", "21/03/2000"));
        mestre.add(new Cliente(4, "Miriam", "25/07/1998"));
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoInsercao(1, "João", "12/04/2000"));
        transacoes.add(new TransacaoInsercao(5, "Maria", "14/06/1999"));
        transacoes.add(new TransacaoInsercao(7, "Pedro", "23/07/1992"));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(3, "Ana", "21/03/2000"));
        oraculoSaida.add(new Cliente(4, "Miriam", "25/07/1998"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

     /**
     * Testa o caso de arquivo Mestre com registros e arquivo transações com exclusoes
     * Não há erros neste caso
     */
    @Test
    public void testaExclusoes() throws FileNotFoundException, Exception {

        mestre.add(new Cliente(1, "João", "12/04/2000"));
        mestre.add(new Cliente(3, "Ana", "21/03/2000"));
        mestre.add(new Cliente(4, "Miriam", "25/07/1998"));
        mestre.add(new Cliente(5, "Maria", "14/06/1999"));
        mestre.add(new Cliente(7, "Pedro", "23/07/1992"));
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoExclusao(3));
        transacoes.add(new TransacaoExclusao(5));        
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(4, "Miriam", "25/07/1998"));        
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }
    /**
     * Testa o caso de arquivo Mestre e arquivo transações com inserções, exclusões e modificações
     * Não há erros neste caso
     */
    @Test
    public void testaCompleto() throws FileNotFoundException, Exception {

        mestre.add(new Cliente(1, "João", "12/04/2000"));
        mestre.add(new Cliente(5, "Maria", "14/06/1999"));
        mestre.add(new Cliente(7, "Pedro", "23/07/1992"));
        mestre.add(new Cliente(10, "Jonas", "21/01/1975"));
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoInsercao(3, "Rodrigo", "25/05/1980"));
        transacoes.add(new TransacaoModificacao(5, "nome", "Marta"));
        transacoes.add(new TransacaoExclusao(7));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(3, "Rodrigo", "25/05/1980"));
        oraculoSaida.add(new Cliente(5, "Marta", "14/06/1999"));
        oraculoSaida.add(new Cliente(10, "Jonas", "21/01/1975"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }

    /**
     * Testa o caso de arquivo Mestre e arquivo transações com inserções, exclusões e modificações
     * Erros são gerados
     */
    @Test
    public void testaCompletoComErros() throws FileNotFoundException, Exception {

        mestre.add(new Cliente(1, "João", "12/04/2000"));
        mestre.add(new Cliente(5, "Maria", "14/06/1999"));
        mestre.add(new Cliente(7, "Pedro", "23/07/1992"));
        mestre.add(new Cliente(10, "Jonas", "21/01/1975"));
        mestre.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        Arquivos.salva(NOME_ARQUIVO_MESTRE, mestre);

        transacoes.add(new TransacaoInsercao(2, "Rodrigo", "25/05/1980"));
        transacoes.add(new TransacaoModificacao(6, "nome", "Marta"));
        transacoes.add(new TransacaoExclusao(7));
        transacoes.add(new TransacaoInsercao(10, "Jonas", "21/01/1975"));
        transacoes.add(new TransacaoExclusao(11));
        transacoes.add(new TransacaoExclusao(Integer.MAX_VALUE));

        Arquivos.salva(NOME_ARQUIVO_TRANSACOES, transacoes);

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(2, "Rodrigo", "25/05/1980"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(10, "Jonas", "21/01/1975"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "    ", "          "));
        
        oraculoErros.add(new TransacaoModificacao(6, "nome", "Marta"));
        oraculoErros.add(new TransacaoInsercao(10, "Jonas", "21/01/1975"));
        oraculoErros.add(new TransacaoExclusao(11));
        oraculoErros.add(new TransacaoExclusao(Integer.MAX_VALUE));
        
        instance.executa(NOME_ARQUIVO_MESTRE, NOME_ARQUIVO_TRANSACOES, NOME_ARQUIVO_SAIDA, NOME_ARQUIVO_ERROS);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        erros = Arquivos.leTransacao(NOME_ARQUIVO_ERROS);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
        assertArrayEquals(oraculoErros.toArray(), erros.toArray());
    }
}

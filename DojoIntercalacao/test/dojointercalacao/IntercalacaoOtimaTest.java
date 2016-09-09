/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojointercalacao;

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
public class IntercalacaoOtimaTest {

    IntercalacaoOtima instance = null;
    List<Cliente> particao = null;
    List<Cliente> oraculoSaida = null;
    List<Cliente> saida = null;
    List<String> nomeParticoes = null;
   
    private static final String NOME_ARQUIVO_SAIDA = "saida.dat";

    public IntercalacaoOtimaTest() {
    }
    
    @Before
    public void setUp() {
        instance = new IntercalacaoOtima();
        particao = new ArrayList<Cliente>();
        oraculoSaida = new ArrayList<Cliente>();
        nomeParticoes = new ArrayList<String>();

        //deleta o arquivo da rodada anterior
        Arquivos.deletaArquivo(NOME_ARQUIVO_SAIDA);
    }
    
    /**
     * Testa o caso de 1 partição de entrada, vazia
     */
    @Test
    public void testaArquivoVazio() throws FileNotFoundException, Exception {
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));
        Arquivos.salva("particao1.dat", particao);
        nomeParticoes.add("particao1.dat");        

        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);   
        
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());        
    }

    /**
     * Testa o caso de 1 partição de entrada, com 3 registros
     */
    @Test
    public void testaUmaParticao() throws FileNotFoundException, Exception {
        particao.add(new Cliente(1, "João", "12/04/2000"));
        particao.add(new Cliente(5, "Maria", "14/06/1999"));
        particao.add(new Cliente(7, "Pedro", "23/07/1992"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao1.dat", particao);
        nomeParticoes.add("particao1.dat");

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);
        
        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());       
    }

    /**
     * Testa o caso de 3 arquivos de partição, 1 com 3 registros, 2 vazios
     * A primeira e a terceira partição são vazias, e a segunda contém registros
     */
    @Test
    public void testaUmaParticaoCheiaDuasVazias() throws FileNotFoundException, Exception {
        particao.add(new Cliente(1, "João", "12/04/2000"));
        particao.add(new Cliente(5, "Maria", "14/06/1999"));
        particao.add(new Cliente(7, "Pedro", "23/07/1992"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao2.dat", particao);

        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao1.dat", particao);
        Arquivos.salva("particao3.dat", particao);

        nomeParticoes.add("particao1.dat");
        nomeParticoes.add("particao2.dat");
        nomeParticoes.add("particao3.dat");

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));        
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de 2 arquivos de partição, 1 com 3 registros, 1 com 1 registro
     */
    @Test
    public void testaDuasParticoes() throws FileNotFoundException, Exception {
        particao.add(new Cliente(1, "João", "12/04/2000"));
        particao.add(new Cliente(5, "Maria", "14/06/1999"));
        particao.add(new Cliente(7, "Pedro", "23/07/1992"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao1.dat", particao);
        
        particao = new ArrayList<Cliente>();

        particao.add(new Cliente(3, "Ana", "12/12/1960"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));
        Arquivos.salva("particao2.dat", particao);
        
        nomeParticoes.add("particao1.dat");
        nomeParticoes.add("particao2.dat");

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(3, "Ana", "12/12/1960"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de 3 arquivos de partição, os 3 com registros
     */
    @Test
    public void testaTresParticoes() throws FileNotFoundException, Exception {
        particao.add(new Cliente(1, "João", "12/04/2000"));
        particao.add(new Cliente(5, "Maria", "14/06/1999"));
        particao.add(new Cliente(7, "Pedro", "23/07/1992"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao1.dat", particao);
        
        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(2, "Jose", "02/04/2001"));
        particao.add(new Cliente(4, "Mariana", "15/09/1997"));
        particao.add(new Cliente(10, "Cintia", "01/01/1991"));
        particao.add(new Cliente(11, "Camila", "09/12/1987"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao2.dat", particao);

        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(3, "Marcos", "09/04/1986"));
        particao.add(new Cliente(6, "Alice", "12/06/1999"));
        particao.add(new Cliente(12, "Marcela", "19/04/1991"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao3.dat", particao);
        nomeParticoes.add("particao1.dat");
        nomeParticoes.add("particao2.dat");
        nomeParticoes.add("particao3.dat");

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(2, "Jose", "02/04/2001"));
        oraculoSaida.add(new Cliente(3, "Marcos", "09/04/1986"));
        oraculoSaida.add(new Cliente(4, "Mariana", "15/09/1997"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(6, "Alice", "12/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(10, "Cintia", "01/01/1991"));
        oraculoSaida.add(new Cliente(11, "Camila", "09/12/1987"));                
        oraculoSaida.add(new Cliente(12, "Marcela", "19/04/1991"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de 4 arquivos de partição, os 4 com registros
     */
    @Test
    public void testaQuatroParticoes() throws FileNotFoundException, Exception {
        particao.add(new Cliente(1, "João", "12/04/2000"));
        particao.add(new Cliente(5, "Maria", "14/06/1999"));
        particao.add(new Cliente(7, "Pedro", "23/07/1992"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao1.dat", particao);

        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(2, "Jose", "02/04/2001"));
        particao.add(new Cliente(10, "Cintia", "01/01/1991"));
        particao.add(new Cliente(11, "Camila", "09/12/1987"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao2.dat", particao);

        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(3, "Marcos", "09/04/1986"));
        particao.add(new Cliente(6, "Alice", "12/06/1999"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao3.dat", particao);

        particao = new ArrayList<Cliente>();
        particao.add(new Cliente(4, "Mariana", "15/09/1997"));
        particao.add(new Cliente(12, "Marcela", "19/04/1991"));
        particao.add(new Cliente(Integer.MAX_VALUE, "", ""));

        Arquivos.salva("particao4.dat", particao);

        nomeParticoes.add("particao1.dat");
        nomeParticoes.add("particao2.dat");
        nomeParticoes.add("particao3.dat");
        nomeParticoes.add("particao4.dat");

        oraculoSaida.add(new Cliente(1, "João", "12/04/2000"));
        oraculoSaida.add(new Cliente(2, "Jose", "02/04/2001"));
        oraculoSaida.add(new Cliente(3, "Marcos", "09/04/1986"));
        oraculoSaida.add(new Cliente(4, "Mariana", "15/09/1997"));
        oraculoSaida.add(new Cliente(5, "Maria", "14/06/1999"));
        oraculoSaida.add(new Cliente(6, "Alice", "12/06/1999"));
        oraculoSaida.add(new Cliente(7, "Pedro", "23/07/1992"));
        oraculoSaida.add(new Cliente(10, "Cintia", "01/01/1991"));
        oraculoSaida.add(new Cliente(11, "Camila", "09/12/1987"));
        oraculoSaida.add(new Cliente(12, "Marcela", "19/04/1991"));
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));

        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);

        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de 10 arquivos de partição, os 10 com registros
     */
    @Test
    public void testaDezParticoes() throws FileNotFoundException, Exception {

        for (int i = 1; i <= 10;i++) {
            particao = new ArrayList<Cliente>();
            particao.add(new Cliente(i*10+1,"Ana","01/01/2000"));
            particao.add(new Cliente(i*10+3,"Ana","01/01/2000"));
            if ((i % 2) == 0) {
                particao.add(new Cliente(i*10+5,"Ana","01/01/2000"));
                particao.add(new Cliente(i*10+7,"Ana","01/01/2000"));
            }
            if ((i % 7) == 0) {
                particao.add(new Cliente(i*10+9,"Ana","01/01/2000"));
            }
            particao.add(new Cliente(Integer.MAX_VALUE, "", ""));
            Arquivos.salva("particao" + i + ".dat", particao);

            nomeParticoes.add("particao" + i + ".dat");

            oraculoSaida.add(new Cliente(i*10+1,"Ana","01/01/2000"));
            oraculoSaida.add(new Cliente(i*10+3,"Ana","01/01/2000"));
            if ((i % 2) == 0) {
                oraculoSaida.add(new Cliente(i*10+5,"Ana","01/01/2000"));
                oraculoSaida.add(new Cliente(i*10+7,"Ana","01/01/2000"));
            }
            if ((i % 7) == 0) {
                oraculoSaida.add(new Cliente(i*10+9,"Ana","01/01/2000"));
            }            
        }
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));
        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);        
        
        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }

    /**
     * Testa o caso de 100 arquivos de partição, os 100 com registros
     */
    @Test
    public void testaCemParticoes() throws FileNotFoundException, Exception {
        
        for (int i = 1; i <= 100;i++) {
            particao = new ArrayList<Cliente>();
            particao.add(new Cliente(i*10+1,"Ana","01/01/2000"));
            particao.add(new Cliente(i*10+3,"Ana","01/01/2000"));            
            if ((i % 2) == 0) {
                particao.add(new Cliente(i*10+5,"Ana","01/01/2000"));
                particao.add(new Cliente(i*10+7,"Ana","01/01/2000"));    
            }
            if ((i % 10) == 0) {
                particao.add(new Cliente(i*10+9,"Ana","01/01/2000"));
            }
            particao.add(new Cliente(Integer.MAX_VALUE, "", ""));
            Arquivos.salva("particao" + i + ".dat", particao);

            nomeParticoes.add("particao" + i + ".dat");

            oraculoSaida.add(new Cliente(i*10+1,"Ana","01/01/2000"));
            oraculoSaida.add(new Cliente(i*10+3,"Ana","01/01/2000"));
            if ((i % 2) == 0) {
                oraculoSaida.add(new Cliente(i*10+5,"Ana","01/01/2000"));
                oraculoSaida.add(new Cliente(i*10+7,"Ana","01/01/2000"));
            }
            if ((i % 10) == 0) {
                oraculoSaida.add(new Cliente(i*10+9,"Ana","01/01/2000"));
            }
        }
        oraculoSaida.add(new Cliente(Integer.MAX_VALUE, "", ""));
        instance.executa(nomeParticoes, NOME_ARQUIVO_SAIDA);
        
        saida = Arquivos.leCliente(NOME_ARQUIVO_SAIDA);

        assertArrayEquals(oraculoSaida.toArray(), saida.toArray());
    }
}
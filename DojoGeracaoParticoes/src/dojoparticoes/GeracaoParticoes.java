package dojoparticoes;

import java.io.*;
import java.util.*;

public class GeracaoParticoes {
    /**
     * Executa o algoritmo de geração de partições por Classificação Interna
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaClassificacaoInterna(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {
        
            //TODO: Inserir aqui o código do algoritmo de geração de partições

    }
    
    
    /**
     * Executa o algoritmo de geração de partições por Seleção com Substituição 
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaSelecaoComSubstituicao(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {
        
        DataInputStream in = null;
        DataOutputStream out = null;
        
        Cliente[] cliente = new Cliente[M];
        
        boolean[] congelado = new boolean[M];
        boolean fimDeArquivo = false;
        
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivoEntrada)));
            int posMax = 0;
            try {
                do {
                    cliente[posMax++] = Cliente.le(in);
                } while(posMax < M);
            } catch(EOFException e) {  // fim de arquivo
                // a cada leitura do vetor posMax é acresentado de mais 1, 
                //no ultimo caso não podemos acrescentar 1 porque desbalanceia o vetor então iremos decrementar
                posMax--;
            }
            // se posMax = 0, isto significa que o arquivo está vazio, então teremos um arquivo de saida vazio
            if(posMax == 0) {
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(0))));
                out.close();
                fimDeArquivo = true;
            }
            
            int posSaida = 0;
            
            while(!fimDeArquivo) {
                descongelar(congelado, posMax);
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(posSaida++))));

                int posMenorChave = buscarPosicaoMenorChave(cliente, congelado, posMax);
                while(posMenorChave != -1) {
                    int chaveAnterior = cliente[posMenorChave].codCliente;
                    cliente[posMenorChave].salva(out);
                    try {
                        cliente[posMenorChave] = Cliente.le(in);                    
                    } catch(EOFException e) {
                        congelado[posMenorChave] = true;
                        fimDeArquivo = true;
                    }
                    if((!congelado[posMenorChave]) && (chaveAnterior > cliente[posMenorChave].codCliente)) {
                        congelado[posMenorChave] = true;

                    }
                    posMenorChave = buscarPosicaoMenorChave(cliente, congelado, posMax);
                }
                out.close();
            }
            
        } catch(IOException e) {
            // erro de entrada e saida de arquivo
        } finally {
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        }            

    }
    
    /**
     * Executa o algoritmo de geração de partições por Seleção Natural 
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     * @param n tamanho do reservatório
     */
    public void executaSelecaoNatural(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M, int n) throws Exception {
        
            //TODO: Inserir aqui o código do algoritmo de geração de partições

    }

    private int buscarPosicaoMenorChave(Cliente[] cliente, boolean[] congelado, int posMax) {
        int menor = Integer.MAX_VALUE;
        int posicao = -1;
        for(int i = 0; i < posMax; i++) {
            if((!congelado[i]) && (menor > cliente[i].codCliente)) {
                menor = cliente[i].codCliente;
                posicao = i;                
            }
        }
        return posicao;
    }

    private void descongelar(boolean[] congelado, int posMax) {
        for(int i = 0; i < posMax; i++) {
            congelado[i] = false;
        }
    }
    
}
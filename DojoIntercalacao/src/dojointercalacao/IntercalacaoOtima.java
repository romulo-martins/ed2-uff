/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojointercalacao;

import java.io.*;
import java.util.List;

/**
 * Executa o algoritmo Intercalação Ótima
 *
 * @author Romulo
 */
public class IntercalacaoOtima {
    
    public static int MAX_MEMO = 3;

    public void executa(List<String> nomeParticoes, String nomeArquivoSaida) throws Exception {
        
        DataOutputStream out = null;
        DataInputStream[] in = new DataInputStream[MAX_MEMO];
        Cliente[] cliente = new Cliente[MAX_MEMO];
        boolean[] congelado = new boolean[MAX_MEMO];
        
        int cont = 0;
        
        while(nomeParticoes.size() > 1) {
            int tamanho;
            if(MAX_MEMO < nomeParticoes.size()) {
                tamanho = MAX_MEMO;
            }
            else {
                tamanho = nomeParticoes.size();
            }
            
            descongelar(congelado, tamanho);
            
            for(int i = 0; i < tamanho; i++) {
                try {
                    in[i] = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeParticoes.remove(0))));
                    cliente[i] = Cliente.le(in[i]);
                    if(cliente[i].codCliente == Integer.MAX_VALUE) {
                        congelado[i] = true;
                    }                    
                } catch(EOFException e) {
                    congelado[i] = true;
                }
            }
            String nomeSaida = "saida" + (++cont) + ".dat";
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeSaida)));
            
            int menor = buscarPosicaoDaMenorChave(cliente, congelado, tamanho);
            while(menor != -1) {
                cliente[menor].salva(out);
                try {
                    cliente[menor] = Cliente.le(in[menor]);
                    if(cliente[menor].codCliente == Integer.MAX_VALUE) {
                        congelado[menor] = true;
                    }                    
                } catch(EOFException e) {
                    congelado[menor] = true;
                }
                menor = buscarPosicaoDaMenorChave(cliente, congelado, tamanho);
            }
            new Cliente(Integer.MAX_VALUE, "","").salva(out);
            out.close();            
            nomeParticoes.add(nomeSaida);            
            fecharArquivos(in, tamanho);
            
        }

        File f = new File(nomeParticoes.get(0));
        f.renameTo(new File(nomeArquivoSaida));

    }

    private int buscarPosicaoDaMenorChave(Cliente[] cliente, boolean[] congelado, int tamanho) {
        int menorChave = Integer.MAX_VALUE;
        int menorPosicao = -1;
        for(int i = 0; i < tamanho; i++) {
            if((cliente[i].codCliente < menorChave) && (!congelado[i])) {
                menorChave = cliente[i].codCliente;
                menorPosicao = i;
            }
        }
        return menorPosicao;
    }

    private void fecharArquivos(DataInputStream[] in, int tamanho) throws IOException {
        for(int i = 0; i < tamanho; i++) {
            if(in[i] != null) {
                in[i].close();
            }
        }
    }

    private void descongelar(boolean[] congelado, int tamanho) {
        for(int i = 0; i < tamanho; i++) {
            congelado[i] = false;
        }
    }
    
}

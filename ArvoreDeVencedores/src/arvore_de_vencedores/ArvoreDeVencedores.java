/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arvore_de_vencedores;

import java.io.*;
import java.util.*;

/**
 *
 * @author Romulo
 */
public class ArvoreDeVencedores {

    private class No {
        int vencedor;
        int codVencedor;
        No esquerdo;
        No direito;
        
        public No(int vencedor, int codVencedor, No esquerdo, No direito) {
            this.vencedor = vencedor;
            this.codVencedor = codVencedor;
            this.esquerdo = esquerdo;
            this.direito = direito; 
        }
    }    
    
    public void executar(List<String> nomeArquivos, String arquivoSaida) throws IOException {
        int tamanho = nomeArquivos.size();
        DataInputStream[] in = new DataInputStream[tamanho];
        DataOutputStream out = null;
        
        Queue<No> nos = new LinkedList<No>();
        try {
            abrirArquivos(in, nomeArquivos);  
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(arquivoSaida)));
            
            Cliente[] cliente = new Cliente[tamanho];
            
            for(int i = 0; i < tamanho; i++) {
                cliente[i] = Cliente.le(in[i]);
                if(cliente[i].codCliente != Integer.MAX_VALUE) {
                    nos.add(new No(cliente[i].codCliente, i, null, null));                    
                }
            }           
            
            while(nos.size() > 1) {
                No esquerdo = nos.poll();
                No direito = nos.poll();
                if(esquerdo.vencedor < direito.vencedor) {
                    No vencedor = new No(esquerdo.vencedor, esquerdo.codVencedor, esquerdo, direito);
                    nos.add(vencedor);
                }
                else {
                    No vencedor = new No(direito.vencedor, direito.codVencedor, esquerdo, direito);
                    nos.add(vencedor);
                }
            }
            No raiz = nos.poll();
            Stack<No> pilha = new Stack<No>();
            
            while(raiz != null) {               
                //System.out.println(raiz.vencedor);
                cliente[raiz.codVencedor].salva(out);
                
                while(raiz.esquerdo != null && raiz.direito != null) {
                    if(raiz.vencedor == raiz.esquerdo.vencedor) {
                        pilha.add(raiz.direito);
                        raiz = raiz.esquerdo;
                    }
                    else {
                        pilha.add(raiz.esquerdo);
                        raiz = raiz.direito;
                    }
                }
                int indice = raiz.codVencedor;
                cliente[indice] = Cliente.le(in[indice]);
                
                if(cliente[indice].codCliente != Integer.MAX_VALUE) {
                    raiz = new No(cliente[indice].codCliente, indice, null, null);
                }
                else {
                    if(!pilha.empty()) {
                       raiz = pilha.pop(); 
                    }
                    else {
                       raiz = null;
                    }                    
                }

                while(!pilha.empty()) {
                    No no  = pilha.pop();
                    if(raiz.vencedor < no.vencedor) {
                        raiz = new No(raiz.vencedor, raiz.codVencedor, raiz, no);
                    }
                    else {
                        raiz = new No(no.vencedor, no.codVencedor, no, raiz);
                    }
                }
            }
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            
        } catch(IOException e) {
            //
        } finally {
            fecharArquivos(in);
            if(out != null) {
                out.close();
            }
        }   
    }
    
    private static void fecharArquivos(DataInputStream[] in) throws IOException {
        for(int i = 0; i < in.length; i++) {
            if(in[i] != null) {
                in[i].close();
            }
        }
    }

    private static void abrirArquivos(DataInputStream[] in, List<String> lista) throws IOException {
        for(int i = 0; i < in.length; i++) {
            in[i] = new DataInputStream(new BufferedInputStream(new FileInputStream(lista.get(i))));
        }
    }    
    
    // main de teste 
    public static void main(String[] args) throws IOException {
        List<String> listaDeNomes = new ArrayList<String>();
        
        for(int i = 1; i <= 7; i++) {
            listaDeNomes.add("part"+i+".dat");            
        }
        
        new ArvoreDeVencedores().executar(listaDeNomes,"saida.dat");    
        
    }
    
    
}

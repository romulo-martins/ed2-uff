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
public class LerArquivosParaTeste {
    
    public static void main(String[] args) throws IOException {
        
        // faz uma lista com os nomes dos arquivos com os dados de clientes
        List<String> listaDeEntrada = new ArrayList<String>();        
        for(int i = 1; i <= 7; i++) {
            listaDeEntrada.add("part"+i+".dat");            
        }
        
        // salva em uma lista o arquivo de saida 
        List<String> saida = new ArrayList<String>();
        saida.add("saida.dat");
        
        lerArquivosDat(listaDeEntrada);
        
        System.out.println("");
        
        lerArquivosDat(saida);       
        
    }

    private static void lerArquivosDat(List<String> listaDeNomes) throws IOException {
        if(listaDeNomes.isEmpty()) return;
        
        int tamanho = listaDeNomes.size();
        DataInputStream[] in = new DataInputStream[tamanho];
        Cliente cliente;
        
        try {
            for(int i = 0; i < tamanho; i++) {
                in[i] = new DataInputStream(new BufferedInputStream(new FileInputStream(listaDeNomes.get(i))));                    
            }

            for(int i = 0; i < tamanho; i++) {
                System.out.println("\nArquivo " + listaDeNomes.get(i) +": ");
                do {
                    cliente = Cliente.le(in[i]);
                    if(cliente.codCliente != Integer.MAX_VALUE) {
                        System.out.println(cliente);
                    }
                } while(cliente.codCliente != Integer.MAX_VALUE);

            }
        } catch (IOException e){    
            System.out.println(e.getMessage());
        } finally {
            for (DataInputStream in1 : in) {
                if (in1 != null) {
                    in1.close();
                }
            }
        }
        
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojohash;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author vanessa
 */
public class CompartimentoHash implements Entidade{

    public static int tamanhoRegistro = 4;
    
    public int prox;

    /*
     * Construtor do Compartimento
     */
    public CompartimentoHash(int prox) {
        this.prox = prox;
    }

    /**
     * Salva um Compartimento no arquivo out, na posição atual do cursor
     * @param out aquivo onde os dados serão gravados
     */
    @Override
    public void salva(RandomAccessFile out) throws IOException {
        out.writeInt(prox);        
    }

    /**
     * Lê um Compartimento do arquivo in na posição atual do cursor
     * e retorna uma instância de Compartimento populada com os dados lidos do arquivo
     * @param in Arquivo de onde os dados serão lidos
     * @return instância de Compartimento populada com os dados lidos
     */
    public static CompartimentoHash le(RandomAccessFile in) throws IOException {
        return new CompartimentoHash(in.readInt());
    }
    
    /*
     * Compara o Compartimento atual com outro Compartimento
     * retorna true se os valores dos atributos de ambos os compartimentos forem iguais,
     * e falso caso contrário
     * @param obj Compartimento a ser comparado
     * @return True se compartimentos forem iguais, False caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompartimentoHash other = (CompartimentoHash) obj;        
        if (this.prox != other.prox) {
            return false;
        }        
        return true;
    }

    /*
     * Gera o hashCode para uma instância de Cliente
     * @return hashCode gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;        
        hash = 71 * hash + this.prox;
        return hash;
    }
}

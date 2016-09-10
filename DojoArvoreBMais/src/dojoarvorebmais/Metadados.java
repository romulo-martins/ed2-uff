/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojoarvorebmais;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Romulo
 * Esta classe representa os metadados da árvore B+
 */
public class Metadados {
    public static int TAMANHO = 4 + 1 + 4 + 4;
    //ponteiro para a raiz da arvore
    public int pontRaiz;
    //flag que diz se a raiz eh uma folha (nesse caso ela esta no arquivo de dados)
    public boolean raizFolha;
    //ponteiro para o próximo nó interno livre
    public int pontProxNoInternoLivre;
    //ponteiro para o próximo nó folha livre
    public int pontProxNoFolhaLivre;

    /*
     * Construtor dos Metadados 
     */
    public Metadados() {
        this.pontRaiz = 0;
        this.raizFolha = true;
        this.pontProxNoInternoLivre = 0;
        this.pontProxNoFolhaLivre = NoFolha.TAMANHO;
    }

    public Metadados(int pontRaiz, boolean raizFolha, int pontProxNoInternoLivre, int pontProxNoFolhaLivre) {
        this.pontRaiz = pontRaiz;
        this.raizFolha = raizFolha;
        this.pontProxNoInternoLivre = pontProxNoInternoLivre;
        this.pontProxNoFolhaLivre = pontProxNoFolhaLivre;
    }

    /*
     * Grava os metadados no arquivo em disco 
     */
    public void salva(RandomAccessFile out) throws IOException {
        out.writeInt(pontRaiz);
        out.writeBoolean(raizFolha);
        out.writeInt(pontProxNoInternoLivre);
        out.writeInt(pontProxNoFolhaLivre);
    }

    /*
     * Le os metadados do disco
     */
    public static Metadados le(RandomAccessFile in) throws IOException {
        Metadados m = new Metadados();
        m.pontRaiz = in.readInt();
        m.raizFolha = in.readBoolean();
        m.pontProxNoInternoLivre = in.readInt();
        m.pontProxNoFolhaLivre = in.readInt();
        return m;
    }
}

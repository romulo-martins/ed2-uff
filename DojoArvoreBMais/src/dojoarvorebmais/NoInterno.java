/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojoarvorebmais;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Romulo
 * Representa um nó interno de uma árvore B de ordem 2
 * Tamanho em bytes do nó:
 * = 4 + 1 + 4 + 5*4 + 4*4 =  45 bytes
 */
public class NoInterno implements Entidade {

    public static int TAMANHO = 4 + 1 + 4 + 5*4 + 4*4;
    public static int d = 2;

    public int m; //m é o número total de chaves armazenados no nó
    public boolean apontaFolha; //flag que diz se nó aponta para folha ou não
    public int pontPai; //ponteiro para o nó pai
    //essa lista tera sempre m+1 ponteiros
    public List<Integer> p;
    // essa lista tera sempre m clientes
    public List<Integer> chaves;

    /*
     * Construtor do Nó Interno
     */
    public NoInterno() {
        this.m = 0;
        this.apontaFolha = false;
        this.pontPai = -1;
        this.p = new ArrayList<Integer>();
        this.chaves = new ArrayList<Integer>();
    }

    public NoInterno(int m, boolean apontaFolha, int pontPai, List<Integer> p, List<Integer> chaves) {
        this.m = m;
        this.apontaFolha = apontaFolha;
        this.pontPai = pontPai;
        this.p = p;
        this.chaves = chaves;
    }

    /*
     * Grava uma pagina no arquivo em disco
     */
    @Override
    public void salva(RandomAccessFile out) throws IOException {        
        out.writeInt(m);
        out.writeBoolean(apontaFolha);
        out.writeInt(pontPai);
        //garantidamente, sempre havera pelo menos 1 chave no no'
        //portanto, p0 sempre vai existir
        out.writeInt(p.get(0));
        for (int i = 0; i < m; i++) {
            out.writeInt(chaves.get(i));
            out.writeInt(p.get(i+1));
        }

        //Termina de gravar dados vazios no restante do espaço do nó
        for (int i=m; i < 2*d; i++) {
            out.writeInt(-1);
            out.writeInt(-1);
        }
        
    }

    /*
     * Le uma pagina do disco
     */
    public static NoInterno le(RandomAccessFile in) throws IOException {
        NoInterno n = new NoInterno();
        n.m = in.readInt();
        n.apontaFolha = in.readBoolean();
        n.pontPai = in.readInt();
        n.p.add(in.readInt());

        for (int i = 0; i< n.m; i++) {
            n.chaves.add(in.readInt());
            n.p.add(in.readInt());
        }

        //Termina de ler dados nulos para resolver problema do cursor
        //Dados lidos são descartados
        for (int i=n.m; i < 2*d; i++) {
            in.readInt();
            in.readInt();
        }
        return n;
    }

     /*
     * Compara o Nó atual com outro Nó
     * retorna true se os valores dos atributos de ambos os nós forem iguais,
     * e falso caso contrário
     * @param obj Nó a ser comparado
     * @return True se nós forem iguais, False caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NoInterno other = (NoInterno) obj;
        if (this.m != other.m || this.pontPai != other.pontPai || this.apontaFolha != other.apontaFolha) {
            return false;
        }        
        if (!this.p.equals(other.p)) {
            return false;
        }
        if (!this.chaves.equals(other.chaves)) {
            return false;
        }
        return true;
    }

     /*
     * Gera o hashCode para uma instância
     * @return hashCode gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.m + this.pontPai;
        if (this.apontaFolha)
        hash = 71 *hash + 1;
        return hash;
    }

    /**
     * Gera uma String com uma representação de um Nó
     */
    @Override
    public String toString() {
        String s;
        s = this.m + ", " + this.apontaFolha + ", " + this.pontPai;
        for (int i=0; i< this.m+1; i++)
        {
            s = s + ", " + this.p.get(i).toString();
        }
        for (int i=0; i< this.m; i++)
        {
            s = s + ", " + this.chaves.get(i).toString();
        }
        return s;
    }
}

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
 * Representa um nó folha de uma árvore B de ordem 2
 * Tamanho em bytes do nó folha:
 * - cada registro de cliente tem 16 bytes
 * = 4 + 4 + 4 + 4*16 = 76 bytes
 */
public class NoFolha implements Entidade {

    public static int TAMANHO = 4 + 4 + 4 + 4 * Cliente.TAMANHO;
    public static int d = 2;
    public int m; //m é o número total de registros armazenados no nó
    public int pontPai; //ponteiro para o pai
    public int pontProx; //ponteiro para o próximo nó folha
    // essa lista tera sempre m clientes
    public List<Cliente> clientes;

    /*
     * Construtor do Nó Folha
     */
    public NoFolha() {
        this.m = 0;
        this.pontPai = -1;
        this.pontProx = -1;
        this.clientes = new ArrayList<Cliente>();
    }

    public NoFolha(int m, int pontPai, int pontProx, List<Cliente> clientes) {
        this.m = m;
        this.pontPai = pontPai;
        this.pontProx = pontProx;
        this.clientes = clientes;
    }

    /*
     * Grava uma pagina no arquivo em disco
     */
    @Override
    public void salva(RandomAccessFile out) throws IOException {        

        out.writeInt(m);
        out.writeInt(pontPai);
        out.writeInt(pontProx);

        for (int i = 0; i< m; i++) {
            clientes.get(i).salva(out);
        }

        //Termina de gravar dados vazios no restante do espaço do nó
        for (int i=m; i < 2*d; i++) {
            Cliente c = new Cliente(-1,"          ");
            c.salva(out);
        }
    }

    /*
     * Le uma pagina do disco
     */
    public static NoFolha le(RandomAccessFile in) throws IOException {
        NoFolha f = new NoFolha();
        f.m = in.readInt();
        f.pontPai = in.readInt();
        f.pontProx = in.readInt();

        for (int i = 0; i < f.m; i++) {
            f.clientes.add(Cliente.le(in));
        }

        //Termina de ler dados nulos para resolver problema do cursor
        //Dados lidos são descartados
        for (int i=f.m; i < 2*d; i++) {
            Cliente.le(in);
        }

        return f;
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
        final NoFolha other = (NoFolha) obj;
        if (this.m != other.m || this.pontPai != other.pontPai || this.pontProx != other.pontProx) {
            return false;
        }
        if (this.m > 0) {
            if (!this.clientes.equals(other.clientes)) {
                return false;
            }
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
        hash = 71 * hash + this.m + this.pontPai + this.pontProx;
        return hash;
    }

    /**
     * Gera uma String com uma representação de um Nó
     */
    @Override
    public String toString() {
        String s;
        s = this.m + ", " + this.pontPai + ", " + this.pontProx;
        for (int i = 0; i < this.m; i++) {
            s = s + ", " + this.clientes.get(i).toString();
        }
        return s;
    }
}

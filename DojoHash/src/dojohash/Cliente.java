package dojohash;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Classe que implementa um cliente com codigo, nome e dataNascimento
 */
public class Cliente implements Entidade {

    public static boolean LIBERADO = true;
    public static boolean OCUPADO = false;
    public static int tamanhoRegistro = 4 + 12 + 4 + 1;

    public int codCliente;
    //nome terá sempre tamanho 10 caracteres
    public String nome;
    public int prox;
    public boolean flag;

    /*
     * Construtor do Cliente
     */
    public Cliente(int codCliente, String nome, int prox, boolean flag) {
        this.codCliente = codCliente;
        this.nome = nome;
        this.prox = prox;
        this.flag = flag;
    }

    /**
     * Salva um Cliente no arquivo out, na posição atual do cursor
     * @param out aquivo onde os dados serão gravados
     */
    @Override
    public void salva(RandomAccessFile out) throws IOException {
        out.writeInt(codCliente);
        out.writeUTF(nome);
        out.writeInt(prox);
        out.writeBoolean(flag);
    }

    /**
     * Lê um Cliente do arquivo in na posição atual do cursor
     * e retorna uma instância de Cliente populada com os dados lidos do arquivo
     * @param in Arquivo de onde os dados serão lidos
     * @return instância de Cliente populada com os dados lidos
     */
    public static Cliente le(RandomAccessFile in) throws IOException {
        return new Cliente(in.readInt(), in.readUTF(), in.readInt(), in.readBoolean());
    }

    /**
     * Gera uma String com uma representação de um Cliente
     */
    @Override
    public String toString() {
        String s;
        if (this.flag == Cliente.LIBERADO) {
            s = "LIBERADO";
        } else {
            s = "OCUPADO";
        }
        return this.codCliente + ", " + this.nome + ", " + this.prox + ", " + s;
    }

    /*
     * Compara o cliente atual com outro cliente
     * retorna true se os valores dos atributos de ambos os clientes forem iguais,
     * e falso caso contrário
     * @param obj Cliente a ser comparado
     * @return True se clientes forem iguais, False caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.codCliente != other.codCliente) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.prox != other.prox) {
            return false;
        }
        if (this.flag != other.flag) {
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
        hash = 71 * hash + this.codCliente;
        hash = 71 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 71 * hash + this.prox;
        return hash;
    }
}

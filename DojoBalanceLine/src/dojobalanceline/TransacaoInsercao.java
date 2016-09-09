package dojobalanceline;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author vanessa
 * Representa uma transação de inserção do Balance Line
 */
public class TransacaoInsercao extends Transacao {

    public String nomeCliente;
    public String dataNascimento;

    /*
     * Construtor da Transacao de Inserção
     */
    public TransacaoInsercao(int chave, String nomeCliente, String dataNascimento) {
        super(chave, Transacao.INSERCAO);
        this.nomeCliente = nomeCliente;
        this.dataNascimento = dataNascimento;
    }

    /*
     * Construtor da Transacao de Inserção que lê os dados do arquivo in para popular a instância
     * @param int chave Chave do registro a ser lido
     * @param in Arquivo de onde os dados serão lidos
     */
    public TransacaoInsercao(int chave, DataInputStream in) throws IOException {
        this(chave, in.readUTF(), in.readUTF());
    }

    /**     
     * Salva uma Transacao no arquivo out, na posição atual do cursor
     */
    @Override
    public void salva(DataOutputStream out) throws IOException {
        super.salva(out);
        out.writeUTF(nomeCliente);
        out.writeUTF(dataNascimento);
    }

    /**
     *
     * Transforma uma Transação em uma String
     */
    @Override
    public String toString() {
        return super.toString() + "; Nome Cliente: " + this.nomeCliente
                + "; Data Nascimento: " + this.dataNascimento;
    }

    /*
     * Compara a transacao atual com outra transacao
     * retorna true se os valores dos atributos de ambas as transacoes forem iguais,
     * e falso caso contrário
     * @param obj Transacao a ser comparada
     * @return True se transacoes forem iguais, False caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransacaoInsercao other = (TransacaoInsercao) obj;
        if ((this.nomeCliente == null) ? (other.nomeCliente != null) : !this.nomeCliente.equals(other.nomeCliente)) {
            return false;
        }
        if ((this.dataNascimento == null) ? (other.dataNascimento != null) : !this.dataNascimento.equals(other.dataNascimento)) {
            return false;
        }
        return true;
    }

    /*
     * Gera o hashCode para uma instância de Transacao
     * @return hashCode gerado
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nomeCliente != null ? this.nomeCliente.hashCode() : 0);
        hash = 97 * hash + (this.dataNascimento != null ? this.dataNascimento.hashCode() : 0);
        return hash;
    }
}

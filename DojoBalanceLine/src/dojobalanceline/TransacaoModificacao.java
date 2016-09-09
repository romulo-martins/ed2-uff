package dojobalanceline;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author vanessa
 * Representa uma transação de modificação do Balance Line
 */
public class TransacaoModificacao extends Transacao {
    public String nomeAtributo;
    public String valorAtributo;

    /*
     * Construtor da Transacao de Modificação
     */
    public TransacaoModificacao(int chave, String nomeAtributo, String valorAtributo) {
        super(chave, Transacao.MODIFICACAO);
        this.nomeAtributo = nomeAtributo;
        this.valorAtributo = valorAtributo;
    }

    /*
     * Construtor da Transacao de Modificação que lê os dados do arquivo in para popular a instância
     * @param int chave Chave do registro a ser lido
     * @param in Arquivo de onde os dados serão lidos
     */
    public TransacaoModificacao(int chave, DataInputStream in) throws IOException {
        this(chave, in.readUTF(), in.readUTF());
    }

    /**
     *
     * Salva uma Transacao no arquivo out, na posição atual do cursor
     */
    @Override
    public void salva(DataOutputStream out) throws IOException {
        super.salva(out);
        out.writeUTF(nomeAtributo);
        out.writeUTF(valorAtributo);
    }

     /**
     *
     * Transforma uma Transação em uma String
     */
    @Override
    public String toString() {
        return super.toString() + "; Nome Atributo: " + this.nomeAtributo +
            "; Valor Atributo: " + this.valorAtributo;
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
        final TransacaoModificacao other = (TransacaoModificacao) obj;
        if ((this.nomeAtributo == null) ? (other.nomeAtributo != null) : !this.nomeAtributo.equals(other.nomeAtributo)) {
            return false;
        }
        if ((this.valorAtributo == null) ? (other.valorAtributo != null) : !this.valorAtributo.equals(other.valorAtributo)) {
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
        int hash = 5;
        hash = 29 * hash + (this.nomeAtributo != null ? this.nomeAtributo.hashCode() : 0);
        hash = 29 * hash + (this.valorAtributo != null ? this.valorAtributo.hashCode() : 0);
        return hash;
    }
}

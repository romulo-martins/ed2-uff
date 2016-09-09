package dojobalanceline;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author vanessa
 * Representa uma transação genérica do Balance Line
 */
public abstract class Transacao implements Entidade {

    public static final char INSERCAO = 'I';
    public static final char EXCLUSAO = 'E';
    public static final char MODIFICACAO = 'M';
    public int chave;
    public char tipoTransacao;

    /*
     * Construtor da Transacao
     */
    public Transacao(int chave, char tipoTransacao) {
        this.chave = chave;
        this.tipoTransacao = tipoTransacao;
    }

    /**
     *
     * Salva uma Transacao no arquivo out, na posição atual do cursor
     * @param out Arquivo onde a Transacao será salva
     */
    public void salva(DataOutputStream out) throws IOException {
        out.writeInt(chave);
        out.writeChar(tipoTransacao);
    }

    /**
     * Lê uma Transacao do arquivo, na posição atual do cursor
     * detecta o tipo de transação (inserção, exclusão ou modificação)
     * e lê a transação completa
     * @param in Arquivo de onde a transação será lida
     * @return uma instância de Transacao populada com os dados lidos do arquivo
     */
    public static Transacao le(DataInputStream in) throws IOException {
        Transacao result = null;

        int chave = in.readInt();
        char tipoTransacao = in.readChar();

        switch (tipoTransacao) {
            case INSERCAO:
                result = new TransacaoInsercao(chave, in);
                break;
            case MODIFICACAO:
                result = new TransacaoModificacao(chave, in);
                break;
            case EXCLUSAO:
                result = new TransacaoExclusao(chave);
        }

        return result;
    }

    /**
     * Gera uma String com uma representação de uma Transação
     */
    @Override
    public String toString() {
        return this.chave + ", " + this.tipoTransacao;
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
        final Transacao other = (Transacao) obj;
        if (this.chave != other.chave) {
            return false;
        }
        if (this.tipoTransacao != other.tipoTransacao) {
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
        int hash = 3;
        hash = 53 * hash + this.chave;
        hash = 53 * hash + this.tipoTransacao;
        return hash;
    }
}

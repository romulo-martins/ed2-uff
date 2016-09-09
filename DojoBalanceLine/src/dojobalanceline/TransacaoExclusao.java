package dojobalanceline;

/** 
 * @author vanessa
 * Representa uma transação de exclusão do Balance Line
 */
public class TransacaoExclusao extends Transacao {

    /*
     * Construtor da Transacao de Exclusão
     */
    public TransacaoExclusao(int chave) {
        super(chave, Transacao.EXCLUSAO);
    }
}

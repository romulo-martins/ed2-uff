/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojoarvorebmais;

/**
 *
 * @author Romulo
 */
public class ResultBusca {
    public int pontFolha;
    public int pos;
    public boolean encontrou;

        /*
     * Construtor do Cliente
     */
    public ResultBusca(int pontFolha, int pos, boolean encontrou) {
        this.pontFolha = pontFolha;
        this.pos = pos;
        this.encontrou = encontrou;
    }

}

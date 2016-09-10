/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojoarvorebmais;

public class ArvoreBMais {

    /**
     * Executa busca em Arquivos utilizando Arvore B+ como indice
     * Assumir que ponteiro para próximo nó é igual a -1 quando não houver próximo nó
     * @param codCli: chave do cliente que está sendo buscado
     * @param nomeArquivoMetadados nome do arquivo de metadados 
     * @param nomeArquivoIndice nome do arquivo de indice (que contém os nós internos da arvore B+)
     * @param nomeArquivoDados nome do arquivo de dados (que contém as folhas da arvore B+)
     * @return uma instancia de ResultBusca, preenchida da seguinte forma:
     * Caso a chave codCli seja encontrada:
         encontrou = true
         pontFolha aponta para a página folha que contém a chave
         pos aponta para a posição em que a chave se encontra dentro da página

       Caso a chave codCli não seja encontrada:
         encontrou = false
         pontFolha aponta para a última página folha examinada
         pos informa a posição, nessa página, onde a chave deveria estar inserida
     */
    public ResultBusca busca(int codCli, String nomeArquivoMetadados, String nomeArquivoIndice, String nomeArquivoDados) throws Exception {
        //TODO: Inserir aqui o código do algoritmo       
        ResultBusca result = new ResultBusca(Integer.MAX_VALUE, Integer.MAX_VALUE, false);
        return result;                               
    }

    /**
     * Executa inserção em Arquivos Indexados por Arvore B+
     * @param codCli: código do cliente a ser inserido
     * @param nomeCli: nome do Cliente a ser inserido
     * @param nomeArquivoMetadados nome do arquivo de metadados 
     * @param nomeArquivoIndice nome do arquivo de indice (que contém os nós internos da arvore B+)
     * @param nomeArquivoDados nome do arquivo de dados (que contém as folhas da arvore B+)* @return endereço da folha onde o cliente foi inserido, -1 se não conseguiu inserir
     * retorna ponteiro para a folha onde o registro foi inserido
     */
    public int insere(int codCli, String nomeCli, String nomeArquivoMetadados, String nomeArquivoIndice, String nomeArquivoDados) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de inserção        
        return Integer.MAX_VALUE;        
    }

    /**
     * Executa exclusão em Arquivos Indexados por Arvores B+
     * @param nomeArquivoMetadados nome do arquivo de metadados 
     * @param nomeArquivoIndice nome do arquivo de indice (que contém os nós internos da arvore B+)
     * @param nomeArquivoDados nome do arquivo de dados (que contém as folhas da arvore B+) * @return endereço do cliente que foi excluído, -1 se cliente não existe
     * retorna ponteiro para a folha onde o registro foi excluido
     */
    public int exclui(int CodCli, String nomeArquivoMetadados, String nomeArquivoIndice, String nomeArquivoDados) {
        //TODO: Inserir aqui o código do algoritmo de remoção
        return Integer.MAX_VALUE;
    }
}

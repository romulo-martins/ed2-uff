/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojohash;

import java.io.*;


public class EncadeamentoInterior {
    
    public static int NULO = -1;

    /**
     * Cria uma tabela hash vazia de tamanho tam, e salva no arquivo nomeArquivoHash
     * Compartimento que não tem lista encadeada associada deve ter registro com chave de Cliente igual a -1
     *     Quando o ponteiro para próximo for null, ele deve ser igual ao endereço do compartimento
     * @param nomeArquivoHash nome do arquivo hash a ser criado
     * @param tam tamanho da tabela hash a ser criada
     * @throws java.io.IOException
     */
    public void criaHash(String nomeArquivoHash, int tam) throws IOException {
        //TODO: criar a tabela hash
        RandomAccessFile arquivo = null;        
        try {
            arquivo = new RandomAccessFile(new File(nomeArquivoHash),"rw");
            
            for(int i = 0; i < tam; i++) {
                new Cliente(-1,"          ", i, Cliente.LIBERADO).salva(arquivo);
            }
        } catch(IOException e) {
            //
        } finally {
            if(arquivo != null) {
                arquivo.close();
            }
        }
    }
    
    /**
    * Executa busca em Arquivos por Encadeamento Interior (Hash)
    * Assumir que ponteiro para próximo nó é igual ao endereço do compartimento quando não houver próximo nó
    * @param codCli: chave do cliente que está sendo buscado
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @return Result contendo a = 1 se registro foi encontrado, e end igual ao endereco onde o cliente foi encontrado
    *                ou a = 2 se o registro não foi encontrado, e end igual ao primeiro endereço livre encontrado na lista encadeada, ou -1 se não encontrou endereço livre
     * @throws java.lang.Exception
    */
    public Result busca(int codCli, String nomeArquivoHash) throws Exception {
        //TODO: Inserir aqui o código do algoritmo    
        RandomAccessFile arquivo = null;
        int a = 0, j = NULO;
        int endereco = hash(codCli);
        
        try {
            arquivo = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            while(a == 0) {
                arquivo.seek(endereco*Cliente.tamanhoRegistro);
                Cliente cliente = Cliente.le(arquivo);
                if(cliente.flag == Cliente.LIBERADO) {
                    j = endereco;
                }
                if( (cliente.codCliente == codCli) && (cliente.flag == Cliente.OCUPADO)) {
                    a = 1; // chave encontrada
                } 
                else {
                    if(cliente.prox == endereco) {
                        a = 2; // chave não encontrada
                        endereco = j;
                    }
                    else {
                        endereco = cliente.prox;
                    }
                }
            }
        } catch(IOException e) {
            // tratar a exceção 
        } finally {
            if(arquivo != null) {
                arquivo.close();
            }
        }
        
        return (new Result( a, endereco));
    }

    /**
    * Executa inserção em Arquivos por Encadeamento Exterior (Hash)
    * @param codCli: código do cliente a ser inserido
    * @param nomeCli: nome do Cliente a ser inserido
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @return endereço onde o cliente foi inserido, -1 se não conseguiu inserir 
    * (inclusive em caso de overflow)
    */
    public int insere(int codCli, String nomeCli, String nomeArquivoHash) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de inserção
        Result resultado = busca(codCli, nomeArquivoHash); 
        if(resultado.a == 1) return -1; // o cliente com essa chave já está na tabela 
        
        int endereco = resultado.end;
        int i = 0, j , m;
        
        RandomAccessFile arquivo = null;
        try {
            arquivo = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            
            if(endereco != NULO) {
                j = endereco;
            }
            else {
                j = hash(codCli);
                m = (int) arquivo.length();
                
                while(i < m) {
                    arquivo.seek(j*Cliente.tamanhoRegistro);
                    Cliente cliente = Cliente.le(arquivo);
                    if(cliente.flag == Cliente.OCUPADO) {
                        j = (j + 1) % m;
                        i = i + 1;
                    }
                    else {
                        i = m + 2;
                    }
                    if(i == m + 1) {
                        break;
                    }
                    arquivo.seek(hash(codCli)*Cliente.tamanhoRegistro);
                    //cliente = Cliente.le(arquivo);
                    int temp = cliente.prox;

                    arquivo.seek(hash(codCli)*Cliente.tamanhoRegistro);
                    cliente = Cliente.le(arquivo);
                    cliente.prox = j;
                    arquivo.seek(hash(codCli)*Cliente.tamanhoRegistro);
                    cliente.salva(arquivo);

                    arquivo.seek(j*Cliente.tamanhoRegistro);
                    cliente = Cliente.le(arquivo);
                    cliente.prox = temp;
                    arquivo.seek(j*Cliente.tamanhoRegistro);
                    cliente.salva(arquivo);                
                }
                
            }
            arquivo.seek(j*Cliente.tamanhoRegistro);
            Cliente cliente = Cliente.le(arquivo);
            cliente.codCliente = codCli;
            cliente.nome = nomeCli;
            cliente.flag = Cliente.OCUPADO;
            arquivo.seek(j*Cliente.tamanhoRegistro);
            cliente.salva(arquivo);
            
            endereco = j;
        } catch(IOException e) {
            // tratar exceção
        } finally {
            if(arquivo != null) {
                arquivo.close();
            }
        }       
        
        return endereco;
    }

    /**
    * Executa exclusão em Arquivos por Encadeamento Exterior (Hash)
     * @param codCli
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @return endereço do cliente que foi excluído, -1 se cliente não existe
     * @throws java.lang.Exception
    */
    public int exclui(int codCli, String nomeArquivoHash) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de remoção
        
        Result resultado = busca(codCli, nomeArquivoHash);
        if(resultado.a == 2) return -1; // neste caso não achou o cliente para excluir 
        
        int endereco = resultado.end;        
        
        RandomAccessFile arquivo = null;
        
        try {
            arquivo = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            
            // para exclui apenas marcaremos a flag de cliente para LIBERADO
            arquivo.seek(endereco*Cliente.tamanhoRegistro);
            Cliente cliente = Cliente.le(arquivo);            
            cliente.flag = Cliente.LIBERADO;
            arquivo.seek(endereco*Cliente.tamanhoRegistro);
            cliente.salva(arquivo);
            
        } catch(IOException e) {
            //
        } finally {
            if(arquivo != null) {
                arquivo.close();
            }
        }
        
        return endereco;
    }

    private int hash(int x) {
        return (x % 7);
    }

}

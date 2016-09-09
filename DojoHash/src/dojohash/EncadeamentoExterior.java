/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojohash;

import java.io.*;


public class EncadeamentoExterior {

    /**
     * Cria uma tabela hash vazia de tamanho tam, e salva no arquivo nomeArquivoHash
     * Compartimento que não tem lista encadeada associada deve ter valor igual a -1
     * @param nomeArquivoHash nome do arquivo hash a ser criado
     * @param tam tamanho da tabela hash a ser criada
     * @throws java.io.IOException
     */
    public void criaHash(String nomeArquivoHash, int tam) throws IOException {
        //TODO: criar a tabela hash
        
        RandomAccessFile tabela = null;
        
        try {
            tabela = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            
            for(int i = 0; i < tam; i++) {
                new CompartimentoHash(-1).salva(tabela);
            }
            
        } catch(IOException e) {
            //
        } finally {
            if(tabela != null) {
                tabela.close();
            }
        }

    }
    
    /**
    * Executa busca em Arquivos por Encadeamento Exterior (Hash)
    * Assumir que ponteiro para próximo nó é igual a -1 quando não houver próximo nó
    * @param codCli: chave do cliente que está sendo buscado
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados    
    * @return o endereco onde o cliente foi encontrado, ou -1 se não for encontrado
     * @throws java.lang.Exception
    */
    public int busca(int codCli, String nomeArquivoHash, String nomeArquivoDados) throws Exception {
        //TODO: Inserir aqui o código do algoritmo  
        RandomAccessFile tabela = null;
        RandomAccessFile dados = null;
        
        boolean sair = false;
        
        int posicao = funcaoHash(codCli);
        
        try {
            tabela = new RandomAccessFile(new File(nomeArquivoHash), "r");
            dados = new RandomAccessFile(new File(nomeArquivoDados), "r");
            
            tabela.seek((posicao)*CompartimentoHash.tamanhoRegistro);
            CompartimentoHash compHash = CompartimentoHash.le(tabela);
            
            posicao = compHash.prox;
            
            if(posicao != -1) {
                do {
                    dados.seek((posicao)*Cliente.tamanhoRegistro);
                    Cliente cliente = Cliente.le(dados);
                    if(cliente.codCliente == codCli && cliente.flag == Cliente.OCUPADO) {
                        sair = true;
                    } else {
                        posicao = cliente.prox;
                        if(posicao == -1) {
                            sair = true;
                        }
                    }                    
                } while(!sair);
            }            
            
        } catch(IOException e) {
            //
        } finally {
            if(tabela != null) {
                tabela.close();
            }
            if(dados != null) {
                dados.close();
            }
        }               
        
        return posicao;
    }

    /**
    * Executa inserção em Arquivos por Encadeamento Exterior (Hash)
    * @param codCli: código do cliente a ser inserido
    * @param nomeCli: nome do Cliente a ser inserido
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     *@param numRegistros numero de registros que já existem gravados no arquivo
    * @return endereço onde o cliente foi inserido, -1 se não conseguiu inserir
     * @throws java.lang.Exception
    */
    public int insere(int codCli, String nomeCli, String nomeArquivoHash, String nomeArquivoDados, int numRegistros) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de inserção
        if(busca(codCli, nomeArquivoHash, nomeArquivoDados) != -1) return -1;
        
        RandomAccessFile tabela = null;
        RandomAccessFile dados = null;
        
        boolean sair = false;
        Cliente cliente = null;
                
        int posicao = funcaoHash(codCli);
        
        try {
            tabela = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            dados = new RandomAccessFile(new File(nomeArquivoDados), "rw");
            
            tabela.seek(posicao*CompartimentoHash.tamanhoRegistro);
            CompartimentoHash compHash = CompartimentoHash.le(tabela);
            
            if(compHash.prox != -1) {
                posicao = compHash.prox;
                
                do {                    
                    dados.seek(posicao*Cliente.tamanhoRegistro);
                    cliente = Cliente.le(dados);

                    if(cliente.flag == Cliente.LIBERADO) {
                        cliente.codCliente = codCli;
                        cliente.nome = nomeCli;
                        cliente.flag = Cliente.OCUPADO;
                        dados.seek(posicao*Cliente.tamanhoRegistro);
                        cliente.salva(dados);
                        
                        sair = true;
                    } 
                    else if(cliente.prox == -1) {
                        int posFinal = (int) (dados.length()/Cliente.tamanhoRegistro);
                        cliente.prox = posFinal;
                        dados.seek(posicao*Cliente.tamanhoRegistro);
                        cliente.salva(dados);

                        dados.seek(posFinal*Cliente.tamanhoRegistro);
                        cliente = new Cliente(codCli, nomeCli, -1, Cliente.OCUPADO);
                        cliente.salva(dados);
                        
                        posicao = posFinal;
                        
                        sair = true;
                    }
                    else {
                        posicao = cliente.prox;
                    }
                } while(!sair);                
                
            }
            else {
                int posFinal = (int) (dados.length()/Cliente.tamanhoRegistro);
                compHash.prox = posFinal;
                tabela.seek(posicao*CompartimentoHash.tamanhoRegistro);
                compHash.salva(tabela);
                
                cliente = new Cliente(codCli, nomeCli, -1, Cliente.OCUPADO);
                dados.seek(posFinal*Cliente.tamanhoRegistro);
                cliente.salva(dados);
                
            }           
            
        } catch(IOException e) {
            //
        } finally {
            if(tabela != null) {
                tabela.close();
            }
            if(dados != null) {
                dados.close();
            }
        }
        
        return posicao;
    }

    /**
    * Executa exclusão em Arquivos por Encadeamento Exterior (Hash)
     * @param codCli
    * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
    * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
    * @return endereço do cliente que foi excluído, -1 se cliente não existe
     * @throws java.lang.Exception
    */
    public int exclui(int codCli, String nomeArquivoHash, String nomeArquivoDados) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de remoção
        RandomAccessFile tabela = null;
        RandomAccessFile dados = null;
        
        int posicao = busca(codCli, nomeArquivoHash, nomeArquivoDados);
        
        if(posicao == -1) {
            return -1;
        }
                     
        try {
            dados = new RandomAccessFile(new File(nomeArquivoDados), "rw");
            
            dados.seek(posicao*Cliente.tamanhoRegistro);
            Cliente cliente = Cliente.le(dados);
            
            cliente.flag = Cliente.LIBERADO;
            
            dados.seek(posicao*Cliente.tamanhoRegistro);
            cliente.salva(dados);
            
        } catch(IOException e) {
            //
        } finally {
            if(dados != null) {
                dados.close();
            }
        }               
        
        return posicao;
    }

    private int funcaoHash(int codCli) {
        return (codCli % 7);
    }

}

package dojobalanceline;

import java.io.*;

public class BalanceLine {
    /**
     * Executa o algoritmo Balance Line
     * @param nomeArquivoMestre arquivo mestre
     * @param nomeArquivoTransacao arquivo de transação
     * @param nomeArquivoErros arquivo de erros
     * @param nomeArquivoSaida arquivo de saída
     */
    public void executa(String nomeArquivoMestre, String nomeArquivoTransacao,
            String nomeArquivoSaida, String nomeArquivoErros) throws Exception {
        
            //TODO: Inserir aqui o código do algoritmo balanceLine     
        DataInputStream mestre = null, transacao = null;
        DataOutputStream saida = null, erros = null;
        
        try {
            mestre = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivoMestre)));
            transacao = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivoTransacao))); 
            
            saida = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida)));
            erros = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoErros)));
            
            Cliente c = Cliente.le(mestre);
            Transacao t = Transacao.le(transacao);
            
            while( (c.codCliente != Integer.MAX_VALUE) || (t.chave != Integer.MAX_VALUE) ) {
                if(c.codCliente > t.chave) { // se a chave do registro de Transaçoes é menor inserimos ele na saida, porem ele deve ser um Cliente
                    if(t.tipoTransacao == Transacao.INSERCAO) { 
                        TransacaoInsercao ti = (TransacaoInsercao) t;
                        new Cliente(ti.chave, ti.nomeCliente, ti.dataNascimento).salva(saida);
                    }
                    else {
                        t.salva(erros);
                    }
                    t = Transacao.le(transacao);
                }
                else if(c.codCliente < t.chave) { // se a chave do arquivo mestre é maior inserimos ele na saida 
                    c.salva(saida);
                    c = Cliente.le(mestre);
                }
                else { //  neste caso c.codCliente == t.chave
                    if(t.tipoTransacao == Transacao.MODIFICACAO) {
                        TransacaoModificacao tm = (TransacaoModificacao) t;
                        if(tm.nomeAtributo.equals("nome")) {
                            c.nome = tm.valorAtributo;
                        } 
                        else {
                            c.dataNascimento = tm.valorAtributo;
                        }
                        c.salva(saida);
                    }
                    else if(t.tipoTransacao == Transacao.EXCLUSAO) {
                        
                    }
                    else {
                        c.salva(saida); // se temos as chaves inguais não podemos inserir um elemento repetido , porem um deles devem ser inseridos
                        t.salva(erros);
                    }
                    c = Cliente.le(mestre);
                    t = Transacao.le(transacao);
                }                
            }
            
            c.salva(saida);
            t.salva(erros);
            
        } catch(IOException e) {
            //
        } finally {
            if(mestre != null) {
                mestre.close();
            }
            if(transacao != null) {
                transacao.close();
            }
            if(saida != null) {
                saida.close();
            }
            if(erros != null) {
                erros.close();
            }
        }

    }
}
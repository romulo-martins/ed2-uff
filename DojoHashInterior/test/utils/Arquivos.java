package utils;

import dojohash.Cliente;
import dojohash.Entidade;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe que possui métodos úteis para manipulação de arquivos
 */
public class Arquivos {

    /**
     * Abre um arquivo para leitura
     * @param nomeArquivo: nome do arquivo a ser aberto
     * @return RandomAccessFile do arquivo
     */
    public static DataInputStream abreArquivoEntrada(String nomeArquivo)
            throws FileNotFoundException {
        return new DataInputStream(new BufferedInputStream(
                new FileInputStream(nomeArquivo)));
    }

    /**
     * Deleta um arquivo
     * @param nomeArquivo: nome do arquivo a ser deletado
     */
    public static void deletaArquivo(String nomeArquivo)  {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            arquivo.delete();            
        }        
    }

    /**
     * Abre um arquivo para gravação
     * @param nomeArquivo: nome do arquivo a ser aberto
     * @return RandomAccessFile que será aberto
     */
    public static RandomAccessFile abreArquivoSaida(String nomeArquivo)
            throws FileNotFoundException {
        return new RandomAccessFile(new File(nomeArquivo), "rw");
    }

    /**
     * Fecha um arquivo
     * @param arquivo Arquivo que será fechado
     */
    public static void fechaArquivo(Closeable arquivo) throws IOException {
        arquivo.close();
    }    

    /*
     * Salva um registro de um Entidade em um arquivo
     * @param nomeArquivo Nome do arquivo onde o registro será salvo
     * @param lista Lista de registros do tipo Entidade
     */
    public static <T extends Entidade> void salva(String nomeArquivo, List<T> lista) throws FileNotFoundException, IOException {
        RandomAccessFile out = new RandomAccessFile(new File(nomeArquivo), "rw");
        for (T entidade : lista) {
            entidade.salva(out);
        }
        out.close();
    }

    /*
     * Lê registros de um arquivo de clientes
     * @param nomeArquivo Nome do arquivo que será lido
     * @return Lista de objetos do tipo Cliente, lidos do arquivo
     */
    public static List<Cliente> leClientes(String nomeArquivo) throws FileNotFoundException, IOException {
        List<Cliente> result = new ArrayList<Cliente>();

        RandomAccessFile in = new RandomAccessFile(new File(nomeArquivo), "r");
        try {
            while (true) {
                result.add(Cliente.le(in));
            }
        } catch (EOFException e) {}
        in.close();

        return result;
    }


    /*
     * Lê uma arquivo e coloca seu conteúdo em uma string
     * @param nomeArquivo Nome do arquivo que será lido
     * @return String com o conteúdo do arquivo
     */
    @Deprecated
    public static String arquivoToString(String nomeArquivo) throws FileNotFoundException, IOException {
        String result = new String();

        FileInputStream in = null;
        try {
            in = new FileInputStream(nomeArquivo);
            int c;
            while ((c = in.read()) != -1) {
                result = result + c;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return result;
    }
}

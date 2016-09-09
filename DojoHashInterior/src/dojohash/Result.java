/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dojohash;

/**
 *
 * @author Romulo
 */
public class Result {
    
    // constantes criadas para ajudar na semantica do algoritmo
    public static int CONTINUA = 0;
    public static int CHAVE_ENCONTRADA = 1;
    public static int CHAVE_NAO_ENCONTRADA = 2;    
    
    // atributos da classe 
    public int a;
    public int end; // endereço
    
    // construtor 
    Result(int a, int end){
        this.a = a;
        this.end = end;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo_de_dobra;

import java.util.*;

/**
 *
 * @author Romulo
 */
public class MetodoDeDobra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // no caso utilizei o numero do slide da professora , e ela pediu para considerar 6 digitos
        // mas o algoritmo funciona para qualquer numero par de digitos
        int num = 813459;
        int cont = 0;
        
        Stack<Integer> p = new Stack<>();
        
        System.out.println("o numero informado é " + num);
        
        // o resto de num/10 é o ultimo digito de num , 
        // atualizamos fazendo "num = num/10" para rancar o ultimo digito 
        while(num > 0) {
            p.push(num % 10);
            num = num/10;
            cont++;
        }
        
        int aux1 = p.pop();
        int aux2 = p.pop();
        
        while(!p.empty()) {
            aux2 += p.pop();
            aux1 += p.pop();
            if(aux1 > 9) {
                aux1 = aux1 % 10;
            }
            if(aux2 > 9) {
                aux2 = aux2 % 10;
            }
            int temp = aux1;
            aux1 = aux2;
            aux2 = temp;
        }
        
        // aqui estamos concatenando os 2 ultimos numeros no caso 7 e 3 , entao 7*10 + 3 = 73 ;)
        int resp = aux1*10 + aux2;
        
        System.out.println("O numero de hash é " + resp);
        
    }
    
}

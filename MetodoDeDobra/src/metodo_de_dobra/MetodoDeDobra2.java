/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metodo_de_dobra;

/**
 *
 * @author Romulo
 */
public class MetodoDeDobra2 {
    
    public static void main(String[] args) {
        
        int num = 813459;
        int div = 100000;
        
        System.out.println("O numero informado é " + num);
        
        int aux1 = num/div;
        num = num % div;
        div = div/10;
        
        int aux2 = num/div;
        num = num % div;
        div = div/10;            
        
       while(num > 0) {
            aux2 = aux2 + (num/div);
            num = num % div;
            div = div/10;
            
            aux1 = aux1 + (num/div);
            num = num % div;
            div = div/10;
            
            if(aux2 > 9) {
                aux2 = aux2 % 10;
            }
            if(aux1 > 9) {
                aux1 = aux1 % 10;
            }
            
            int temp = aux1;
            aux1 = aux2;
            aux2 = temp;               
        }
        
        int resp = aux1*10 + aux2;
        
        System.out.println("o numero de hash é " + resp);
        
    }
    
}

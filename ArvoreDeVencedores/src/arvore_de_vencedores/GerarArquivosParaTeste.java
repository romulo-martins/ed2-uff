/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arvore_de_vencedores;

import java.io.*;

/**
 *
 * @author Romulo
 */
public class GerarArquivosParaTeste {
    
    public static void main(String[] args) throws IOException {
        
        DataOutputStream out = null;

        try {            

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part1.dat")));
            new Cliente(31, "João").salva(out);
            new Cliente(70, "Maria").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part2.dat")));
            new Cliente(76, "Carlos").salva(out);
            new Cliente(Integer.MAX_VALUE,"").salva(out);
            out.close();
            
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part3.dat")));
            new Cliente(41, "Manuel").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part4.dat")));
            new Cliente(69, "Antonio").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part5.dat")));
            new Cliente(13, "Sandra").salva(out);
            new Cliente(40, "Adriana").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part6.dat")));
            new Cliente(2, "Rachel").salva(out);
            new Cliente(20, "Camila").salva(out);
            new Cliente(51, "Laila").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();

            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("part7.dat")));
            new Cliente(6, "Suzana").salva(out);
            new Cliente(10, "Renan").salva(out);
            new Cliente(15, "Gabriel").salva(out);
            new Cliente(60, "Mariana").salva(out);
            new Cliente(Integer.MAX_VALUE, "").salva(out);
            out.close();
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
            
        } finally {
            if(out != null) {
                out.close();                    
            }
        }
        
    }
}

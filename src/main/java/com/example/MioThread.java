package com.example;
    
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.lang.NumberFormatException;

public class MioThread extends Thread {

    Socket s = new Socket();
    BufferedReader in;//input
    DataOutputStream out;
    int numero;
    int tentativi = 1;

    public MioThread(Socket s, int numero) throws IOException{
        this.s = s;
        this.numero = numero;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new DataOutputStream(s.getOutputStream());
    }
    @Override
    public void run() {
        int intInserito = 0;
        String numeroInserito = "";
        System.out.println(numero);
        do{

            try {
                numeroInserito = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
           
           //se il client invia un vaore null
            if(numeroInserito.equals("")){
                try {
                    out.writeBytes("errore valore null, riprova l'inserimento" + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                intInserito = Integer.parseInt(numeroInserito);

                //se il numero è fuori dal range 0-100
                if(intInserito > 100){
                    try {
                        out.writeBytes("Il numero inserito non rientra nell'intervallo previsto dal software " + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //non conto gli errori come tentativi
                }else{
                   
                //controllo del numero inserito
                if(intInserito == numero){ 
                    //se è uguale:
                    try {
                        out.writeBytes("=" + "\n");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    if (intInserito > numero) {
                        //se è minore: 
                        try {
                            out.writeBytes("<" + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //se è maggiore:
                        if (intInserito < numero) {
                            try {
                                out.writeBytes(">" + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                tentativi++;
                }
            }
                
        


          

        }while(intInserito != numero);

        //invio il numeor di tentativi impiegati
        try {
            out.writeBytes( "" + tentativi + "\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            s.close();
            System.out.println("Un client si è disconnesso");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
}

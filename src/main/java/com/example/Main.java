package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Main {
 
        public static void main(String[] args) throws IOException {
        //server
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("server partito");
    
                Random random = new Random();
                int numero = random.nextInt(100);
                System.out.println("numero generato");

            do{
                Socket s = serverSocket.accept();
    
                MioThread thread = new MioThread(s, numero);
                System.out.println("client connesso");
                thread.start();
    
            }while(true);
     
    
        }
    }

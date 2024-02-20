package proyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread
{
    @Override
    public void run()
    {
        ServerSocket servidor;
        Hilo hilo;
        final int PUERTO=5000;
        int grupo_vecinos = 0, contador = 0, contadorid = 0;
        Socket socket;
        ArrayList<ArrayList<Hilo>> hilos = new ArrayList();
        hilos.add(new ArrayList<Hilo>());
        
        System.out.print("Inicializando servidor...\n");
        try 
        {
            servidor = new ServerSocket(PUERTO);

            while(true) 
            {
                socket = servidor.accept();
                System.out.println("Un nuevo cliente se ha conectado " + socket);
                hilo =  new Hilo(socket, grupo_vecinos, contadorid);
                hilo.start();
                hilos.get(grupo_vecinos / 10).add(hilo);
                contador++;
                if(contador == 10)
                {
                    for(int i = 0; i < hilos.get(grupo_vecinos / 10).size(); i++)
                    {
                        hilos.get(grupo_vecinos / 10).get(i).pasarVecinos(hilos.get(grupo_vecinos / 10));
                    }
                    hilos.add(new ArrayList<Hilo>());
                    contador = 0;
                }
                contadorid++;
                grupo_vecinos++;
            }
        } catch (IOException ex) 
        {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}


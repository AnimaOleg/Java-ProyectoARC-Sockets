package proyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread
{
    int identificador;
    final int num_ciclos = 10;
    String coordenadas = "", mensaje = "";
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;

    public Cliente(int identificador)
    {
        this.identificador = identificador;
    }
    
    public void conectarServidor()
    {
        try 
        {
            //Nos conectamos al servidor
            //socket = new Socket("37.133.16.195", 5000);
            socket = new Socket("127.0.0.1", 5000);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String calcularCoordenadas()
    {
        String cor = "";
        int x, y, z;
        
        x = aleatorio(0, 200);
        y = aleatorio(0, 200);
        z = aleatorio(0, 200);
        
        cor = "x = " + x + "; y = " + y + "; z = " + z;
        
        return cor;
    }
    
    public int aleatorio(int min, int max)
    {
        Random rnd = new Random(); 
        
        return rnd.nextInt(max - min +1) + min;      
    }

    @Override
    public void run()
    {
        try 
        {
            //Abrimos canales de comunicación
            salida = new DataOutputStream(socket.getOutputStream());
            entrada = new DataInputStream(socket.getInputStream());
            //Esperamos el aviso
            entrada.readUTF();
            //Entramos en el ciclo de ejecucion
            for(int i = 0; i < num_ciclos; i++)
            {
                coordenadas = calcularCoordenadas();
                salida.writeUTF(coordenadas);
                mensaje = entrada.readUTF();
                System.out.println(mensaje);
                System.out.println("Ciclo " + i + " del cliente " + identificador);
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            //Cerramos conexiones
            entrada.close();
            salida.close();
            socket.close();
        } catch (IOException ex) 
        {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

package proyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo extends Thread
{
    int grupo_vecinos, id;
    final int num_ciclos = 10;
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;
    ArrayList<Hilo> vecinos;
    
    public Hilo(Socket socket, int grupo_vecinos, int id)
    {
        this.socket = socket;
        this.grupo_vecinos = grupo_vecinos / 10;
        this.id = id;
        
        try 
        {
            salida = new DataOutputStream(socket.getOutputStream());
            entrada = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) 
        {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cerrarSocket()
    {
        try 
        {
            socket.close();
        } catch (IOException ex) 
        {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pasarVecinos(ArrayList<Hilo> hilos)
    {
        vecinos = hilos;
    }
    
    public void enviarTodos(String coordenadas)
    {
        
    }
    
    @Override
    public void run() {
        String mensaje_salida = "Empieza simulacion";
        String coordenadas = "";
        
        try 
        {
            salida.writeUTF(mensaje_salida);
            
            for(int i = 0; i < num_ciclos; i++)
            {
                coordenadas = entrada.readUTF();
                System.out.println("Coordenadas del hilo " + id + " del grupo de vecinos " + grupo_vecinos + ": \n" + coordenadas);
                enviarTodos(coordenadas);
                salida.writeUTF("Coordenadas del hilo " + id + " del grupo de vecinos " + grupo_vecinos);
            }
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarSocket();
    }
    
}

package proyecto;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clientes extends Thread
{
    @Override
    public void run()
    {
        int i;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        //Primero creamos los clientes y los conectamos al servidor
        for(i = 0; i < 10000; i++)
        {
            clientes.add(new Cliente(i));
            clientes.get(i).conectarServidor();
        }
        //Esperamos para que todos se conecten
        try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        //Empieza la simulacion
        for(i = 0; i < clientes.size(); i++)
        {
            clientes.get(i).start();
            try {
                clientes.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}

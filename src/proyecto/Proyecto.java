package proyecto;

public class Proyecto 
{ 
    public static void main(String[] args) 
    {
        Servidor servidor = new Servidor();
        Clientes clientes = new Clientes();
        Ventana ventana = new Ventana(servidor, clientes);
        ventana.setVisible(true);
    }   
}

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.InetSocketAddress;


/**
 * Created by sergi on 7/02/16.
 */
public class ServidorCalculadora{

    public static void main(String[] args) throws IOException {

        // Mostramos un mensaje por pantalla
        System.out.println("Esperant instruccions");

        // Creamos nuestro socket
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket servidorSocket = (SSLServerSocket) ssf.createServerSocket();

        try {

            System.out.println("bindiiiing");

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar

            InetSocketAddress address = new InetSocketAddress("localhost", 5857);
            servidorSocket.bind(address);



            // Cada vez que entre alguna operación arrancamos un hilo
            while (true){
                SSLSocket cliente = (SSLSocket) servidorSocket.accept();
                Hilo hilo = new Hilo(cliente);
                hilo.run();
            }
        }
        catch (IOException e){}

        servidorSocket.close();
    }
}
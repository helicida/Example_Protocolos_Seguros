import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Created by sergi on 7/02/16.
 */
public class ClienteCalculadora {

    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {

        // Imprimimos un mensaje.
        System.out.println("Introdueix una operació");
            String operacionAEnviar = teclat.nextLine();

        try {

            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

            SSLSocket clienteSocket = (SSLSocket) ssf.createSocket();
            InetSocketAddress address = new InetSocketAddress("localhost", 5857);
            clienteSocket.connect(address);

            // Conectamos el InetSocketAdress al socket
            InputStream input = clienteSocket.getInputStream();
            OutputStream output = clienteSocket.getOutputStream();

            // Traducimos y enviamos la operación como bytes
            output.write(operacionAEnviar.getBytes());

            byte[] mensaje = new byte[10];  // Array de bytes en el que reconstruiremos el mensaje

            // Recibimos la operación
            input.read(mensaje);

            Integer contador = 0;   // Creamos un contador con el que sabremos el tamaño del mensaje

            // Usamos este for para contar cuantos bytes necesitamos para nuestro mensaje
            for (int iterador = 0; iterador < mensaje.length; iterador++)  {
                if (mensaje[iterador] == 0) {
                    break;
                }
                else {
                    contador++;
                }
            }

            // Creamos el array de bytes con el tamaño necesario
            byte[] mensajeConstruido = new byte[contador];

            // Pasamos nuetro mensaje al array del tamaño preciso
            for (int iterador = 0; iterador < mensajeConstruido.length; iterador++) {
                mensajeConstruido[iterador] = mensaje[iterador];
            }

            // Lo pasamos a string y lo imprimimos en pantalla
            String resultado = new String(mensajeConstruido);
            System.out.println("\nEl resultat és: " + resultado);

            // Cerramos todas las conexiones
            clienteSocket.close();
            input.close();
            output.close();
        }
        catch (IOException e) {}
    }
}

/*
        Instrucciones:
        ----------------------------------------
        Paso 1 - Abrimos un terminal y nos vamos a la carpeta del proyecto con el comando cd

        Pas 2 - Ejecutamos el siguiente comando
        · keytool -genkey -keystore sergikey -keyalg RSA
        · java -Djavax.net.ssl.keystore=sergikey -Djavax.net.ssl.trustStore=sergikey -Djavax.net.ssl.keyStorePassword=sergi123 ServidorCalculadora
        · java -Djavax.net.ssl.keystore=sergikey -Djavax.net.ssl.trustStore=sergikey -Djavax.net.ssl.keyStorePassword=sergi123 ClienteCalculadora
        */



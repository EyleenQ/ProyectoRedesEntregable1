/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import ConexionBaseDatos.UsuarioData;
import Domain.Usuario;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eyleen
 */
public class Server {
     private ServerSocket serverSocket;
 
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }
 
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        
        private OutputStream outArchivo;
        private InputStream inArchivo;
        private String nombreArchivo="";
 
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
 
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("1.Error al establecer conexion");
            }
             
            String inputLine;
            try {
                int contador = 0;
                boolean enviar=false;
                boolean recibir=false;
                Usuario u =new Usuario();
                UsuarioData ud = new UsuarioData();
                
                while ((inputLine = in.readLine()) != null ) {
                    //validar sesion
                    if(contador==0){
                        u.setNombreUsuario(inputLine);
                    }
                    if(contador == 1){
                        u.setContrasenna(inputLine);
                        Usuario recibido = ud.extraerUsuario(u.getNombreUsuario());
                        if(recibido.getContrasenna()!= null && recibido.getContrasenna().equals(u.getContrasenna())){
                            out.println("Correcto");
                            ManejoArchivos ma = new ManejoArchivos();
                            String archivosUsuario = ma.ObtenerArchivos(u.getNombreUsuario());
                            out.println(archivosUsuario);
                            System.out.println("El usuario "+u.getNombreUsuario()+" esta conectado");
                        }else{
                            out.println("Incorrecto");
                        }
                    }
                    contador= contador+1;
                   //fin validar sesion
                   //enviar archivo al cliente
                   if(enviar){
                        nombreArchivo = inputLine;
                        enviarArchivo(u.getNombreUsuario(),nombreArchivo);
                        enviar=false;
                    }
                   //recibo archivo del cliente
                   if(recibir){
                        nombreArchivo = inputLine;
                        recibir = recibirArchivo(u.getNombreUsuario(),nombreArchivo);
                        ManejoArchivos ma = new ManejoArchivos();
                        String archivosUsuario = ma.ObtenerArchivos(u.getNombreUsuario());
                        out.println(archivosUsuario);

                   }
                   
                   //Si el msj del cliente=Enviar, significa que voy a recibir algo
                   if(inputLine.equals("Enviar")){
                       recibir = true;
                   }
                   
                   //Si el msj del cliente=Recibir, significa que voy a enviar algo
                   if(inputLine.equals("Recibir")){
                       enviar = true;
                   }
                }
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Conexion finalizada");
            } catch (SQLException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);               
                //System.out.println("Conexion perdida con Base de datos");
            }
 
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("2.Error al establecer conexion");
            }
           
        }
        
        public void enviarArchivo(String nombreUsuario, String nombreArchivo) throws FileNotFoundException, IOException{
            File archivo = new File("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            int tamanio = (int)archivo.length();
            out.println(tamanio);
            System.out.println("Tamanio archivo: "+tamanio);
            
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
            
            byte[] buffer = new byte[tamanio];
            bis.read(buffer);
            
            for(int i=0; i<buffer.length; i++){
                bos.write(buffer[i]);
            }
           
            bis.close();
            bos.close();
            System.out.println("Archivo enviado");
            //clientSocket.close();

        }
        
        public boolean recibirArchivo(String nombreUsuario, String nombreArchivo) throws IOException{
            File fichero = new File ("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            
            int tam = Integer.parseInt(in.readLine());

            FileOutputStream fos = new FileOutputStream("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            BufferedOutputStream outF = new BufferedOutputStream(fos);
            BufferedInputStream inF = new BufferedInputStream(clientSocket.getInputStream());
            
            byte[] buff = new byte[tam];
            for(int i=0; i < buff.length; i++){
                buff[i] = (byte) inF.read();
            }
            outF.write(buff);
            outF.close();
            System.out.println("Recibido");          

            return false;
        }
    }
}
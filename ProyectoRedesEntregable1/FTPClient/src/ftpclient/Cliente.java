/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFileChooser;

/**
 *
 * @author Steven
 */
public class Cliente {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private InputStream fileIn;
    private OutputStream fileOut;

    public void iniciarConeccion(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void enviarMensaje(String msg) throws IOException {
        out.println(msg);
    }

    public String recibirMensaje() throws IOException {
        String respuesta = in.readLine();
        return respuesta;
    }

    public String[] recibirListadoArchivos() throws IOException {
        String respuesta = in.readLine();

        String listadoArchivos[] = respuesta.split("!--!");

        return listadoArchivos;
    }

    public void enviarArchivo(String rutaArchivo, String ip, int puerto, String usuario, String contrasenia) throws FileNotFoundException, IOException {
        File archivo = new File(rutaArchivo);
        
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
            clientSocket.close();
        System.out.println("Archivo Enviado");

        this.iniciarConeccion(ip, puerto);
        this.enviarMensaje(usuario);
        this.enviarMensaje(contrasenia);
        String respuesta = this.recibirMensaje();
        String listadoArchivos[] = this.recibirListadoArchivos();
    }

    public void recibirArchivo(String archivoADescargar, String rutaDestino, String ip, int puerto, String usuario, String contrasenia) throws IOException {
        File archivo = new File(rutaDestino+'/'+archivoADescargar);
        
        int tam = Integer.parseInt(in.readLine());

            FileOutputStream fos = new FileOutputStream(rutaDestino+'/'+archivoADescargar);
            BufferedOutputStream outF = new BufferedOutputStream(fos);
            BufferedInputStream inF = new BufferedInputStream(clientSocket.getInputStream());

            byte[] buff = new byte[tam];
            for(int i=0; i < buff.length; i++){
                buff[i] = (byte) inF.read();
            }
            outF.write(buff);
            outF.close();
            clientSocket.close();
            System.out.println("Recibido");
            
            this.iniciarConeccion(ip, puerto);
            this.enviarMensaje(usuario);
            this.enviarMensaje(contrasenia);
            String respuesta = this.recibirMensaje();
            String listadoArchivos[] = this.recibirListadoArchivos();
    }

    public String chooseFile() {
        String result = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            result = fileChooser.getSelectedFile().toString(); 
        }
        return result;
    }
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}

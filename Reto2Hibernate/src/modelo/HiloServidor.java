package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidor extends Thread {

    Socket conexionCli;

    public HiloServidor(Socket conexionCli) {
        // TODO Auto-generated constructor stub
        this.conexionCli = conexionCli;
    }

    public void run() {

        int opcion = 0;
        boolean terminar = false;

        try {
            DataOutputStream dos = new DataOutputStream(conexionCli.getOutputStream());
            DataInputStream dis = new DataInputStream(conexionCli.getInputStream());
            while (!terminar) {

                opcion = (int) dis.readInt();

                switch (opcion) {
                case 1:
                    login(dis, dos);
                    break;
                case 4: 
                    terminar = true;
                default:
                    break;
                }

            }
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void login(DataInputStream dis, DataOutputStream dos) {
        // TODO Auto-generated method stub

        try {
            String usuario =  dis.readUTF();
            String password = dis.readUTF();
            int usuarioComprobado = new Users().Login(usuario, password);
            dos.writeInt(usuarioComprobado);
            dos.flush();
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
package chat.Control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import chat.View.jServidor;
import static chat.View.jServidor.jBtnConectar;
import static chat.View.jServidor.jTxtLogs;
import static chat.View.jServidor.jTxtPorta;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1502727
 */
public class Servidor {

    private boolean conectado;
    private ServerSocket servidor;
    private Scanner entrada;
    private Socket cliente;
    private String texto;

    public Servidor() {
        conectado = false;
    }

    public void start() {
        if (!conectado) {
            Thread conectando = new Thread(
                    new Runnable() {
                @Override
                public void run() {
                    jServidor.jBtnConectar.setText("Desconectar");
                    conectar(Integer.parseInt(jTxtPorta.getText()));
                }
            });

            conectando.start();
        } else {
            jBtnConectar.setText("Conectar");
            desconectar();
        }
    }

    private void conectar(Integer porta) {
        try {

            texto = "Aguardando conexão na porta: " + porta;
            jTxtLogs.setText(texto);

            servidor = new ServerSocket(porta);
            
            cliente = servidor.accept();
            conectado = true;

            texto += "\nNova conexão com o cliente " + cliente.getInetAddress().getHostAddress() + "\n";
            jTxtLogs.setText(texto);

            receberMensagem();

        } catch (IOException ex) {
            Logger.getLogger(jServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void receberMensagem() {
        try {

            entrada = new Scanner(cliente.getInputStream());

            while (entrada.hasNextLine()) {
                texto += "\n O cliente digitou: " + entrada.nextLine();
                jTxtLogs.setText(texto);
            }

            desconectar();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            if (conectado) {
                conectado = false;

                if (cliente.isConnected()) {
                    cliente.close();
                }

                if (!servidor.isClosed()) {
                    servidor.close();
                }

                jServidor.jBtnConectar.setText("Desconectar");
                texto += "O cliente desconectou";
                jTxtLogs.setText(texto);
            }
        } catch (IOException ex) {
            Logger.getLogger(jServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

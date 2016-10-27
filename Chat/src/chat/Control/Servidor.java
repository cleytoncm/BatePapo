package chat.Control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import chat.View.jChat;
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
    private Socket cliente;
    private Scanner entrada;
    private String ip;
    private Chat chat;

    public Servidor() {
        conectado = false;
    }

    public void start(Chat c, Integer porta) {
        if (!conectado) {
            Thread conectando = new Thread(
                    new Runnable() {
                @Override
                public void run() {
                    chat = c;
                    jChat.jBtnConectar.setText("Conectado");
                    conectar(porta);
                }
            });

            conectando.start();
        } else {
            desconectar();
        }
    }

    private void conectar(Integer porta) {
        Integer p2;
        try {
            jChat.TEXTO = "Aguardando conexão na porta: " + porta;
            jChat.jTxAMensagens.setText(jChat.TEXTO);

            servidor = new ServerSocket(porta);

            cliente = servidor.accept();
            conectado = true;

            ip = cliente.getInetAddress().getHostAddress();
            jChat.TEXTO += "\nNova conexão com o cliente " + ip + "\n-------------------------\n";
            jChat.jTxAMensagens.setText(jChat.TEXTO);

            if(jChat.jTxtIP.getText().isEmpty()){
                jChat.jTxtIP.setText(ip);
                p2 = porta+1;
                chat.conectarCliente(jChat.jTxtIP.getText(), p2);
            }
            
            receberMensagem();

        } catch (IOException ex) {
            Logger.getLogger(jChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void receberMensagem() {
        try {

            entrada = new Scanner(cliente.getInputStream());

            while (entrada.hasNextLine()) {
                jChat.TEXTO += ip + ": " + entrada.nextLine() + "\n ";
                jChat.jTxAMensagens.setText(jChat.TEXTO);
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

                jChat.TEXTO += "\nO cliente desconectou";
                jChat.jTxAMensagens.setText(jChat.TEXTO);
            }
        } catch (IOException ex) {
            Logger.getLogger(jChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

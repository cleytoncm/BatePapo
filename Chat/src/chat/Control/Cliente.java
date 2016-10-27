/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.Control;

import chat.View.jChat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CLEYTON
 */
public class Cliente {

    private boolean conectado;
    private Scanner entrada;
    private Socket cliente;
    private String texto;

    public Cliente() {
        conectado = false;
    }

    public void start() {
        Thread conectando = new Thread(
                new Runnable() {
            @Override
            public void run() {
                jChat.jBtnConectar.setText("Desconectar");
                conectar(jChat.jTxtIP.getText(), Integer.parseInt(jChat.jTxtPorta.getText()));
            }
        });

        conectando.start();
    }

    private void conectar(String IP, Integer porta) {
        try {
            cliente = new Socket(IP, porta);

            conectado = true;

            texto += "\nConectado no servidor " + IP + "\n";
            jChat.jTxAMensagens.setText(texto);

            enviarMensagem();

        } catch (IOException ex) {
            Logger.getLogger(jChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarMensagem() {
        try {

            entrada = new Scanner(cliente.getInputStream());

            while (entrada.hasNextLine()) {
                texto += "\n O cliente digitou: " + entrada.nextLine();
                jChat.jTxAMensagens.setText(texto);
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

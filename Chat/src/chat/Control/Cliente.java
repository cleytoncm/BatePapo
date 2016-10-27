/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.Control;

import chat.View.jChat;
import java.io.IOException;
import java.io.PrintStream;
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
    private PrintStream saida;
    private Chat chat;

    public Cliente() {
        conectado = false;
    }

    public void start(Chat c, String ip, Integer porta) {
        Thread conectando = new Thread(
                new Runnable() {
            @Override
            public void run() {
                chat = c;
                jChat.jBtnConectar.setText("Conectado");
                conectar(ip, porta);
            }
        });

        conectando.start();
    }

    private void conectar(String IP, Integer porta) {
        try {
            cliente = new Socket(IP, porta);

            conectado = true;

            jChat.TEXTO += "Conectado no servidor " + IP;
            
            porta = porta+1;
            chat.conectarServidor(porta);
            
            jChat.jTxAMensagens.setText(jChat.TEXTO);

        } catch (IOException ex) {
            Logger.getLogger(jChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarMensagem(String texto) {
        try {
            saida = new PrintStream(cliente.getOutputStream());
            
            saida.println(texto);
            jChat.TEXTO += "\n Eu: " + texto;
            jChat.jTxAMensagens.setText(jChat.TEXTO);
            jChat.jTxAMensagem.setText("");
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

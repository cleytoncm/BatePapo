/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.Control;

/**
 *
 * @author a1502727
 */
public class Chat {

    Servidor servidor = new Servidor();
    Cliente cliente = new Cliente();

    public void conectarServidor(Integer porta) {
        servidor.start(this, porta);
    }

    public void conectarCliente(String ip, Integer porta) {
        cliente.start(this, ip, porta);
    }

    public void enviarMensagem(String texto){
        cliente.enviarMensagem(texto);
    }
}

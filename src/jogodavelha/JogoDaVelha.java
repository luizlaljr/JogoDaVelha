package jogodavelha;

/**
 * Classe Principal do Jogo da Velha
 * 
 * @author luizlaljr
 * 
 * @version 1.1
 */
public class JogoDaVelha {

    /**
     * Metodo principal da aplicacao responsavel por executar o Jogo da Velha.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Partida novaPartida = new Partida();
        novaPartida.iniciarPartida();
    }

}

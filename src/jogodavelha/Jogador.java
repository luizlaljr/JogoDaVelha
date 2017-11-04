package jogodavelha;

/**
 * Classe que representa o Jogador.
 * 
 * @author luizlaljr
 * 
 * @version 1.3
 */
class Jogador {

    private final String nome;
    private final char simbolo;
    
    /**
     * Construtor da Classe Jogador. Recebe o nome e o simbolo a fim de criar um jogador.
     * 
     * @param nome - Nome que será atribuído ao jogador criado.
     * 
     * @param simbolo - Símbolo que será atribuído ao jogador criado.
     */
    public Jogador(String nome, char simbolo) {
        this.nome = nome;
        this.simbolo = simbolo;
    }
    
    /**
     * Método responsável por retornar o nome do Jogador.
     * 
     * @return String - Nome do Jogador.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Método responsável por retornar o símbolo do Jogador.
     * 
     * @return Char - Símbolo do Jogador. 
     */
    public char getSimbolo() {
        return simbolo;
    }

}

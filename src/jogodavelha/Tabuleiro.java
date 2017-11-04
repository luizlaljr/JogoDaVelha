package jogodavelha;

/**
 * Classe Tabuleiro que representa o tabuleiro da partida.
 * 
 * @author luizlaljr
 * 
 * @version 1.5
 */
class Tabuleiro {
    
    /**
     * Constante do tipo Inteiro o qual representa a ordem da matriz de posiçoes.
     */
    public static final int TAMANHO = 3;
    
    /**
     * Matriz de Byte que representa o tabuleiro do Jogo da Velha.
     */
    private final byte[][] tabuleiro;
    
    /**
     * Construtor da Classe Tabuleiro. Cria a matriz de posições para as jogadas.
     */
    public Tabuleiro() {
        
        this.tabuleiro = new byte[TAMANHO][TAMANHO];
        
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = Partida.POSICAO_VAZIA;
            }
        }
        
    }
    
    /**
     * Método responsável por exibir como o tabuleiro está preenchido.
     */
    public void exibeTabuleiro() {
        
        System.out.println();
        System.out.println("*************");
        
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print("* ");
            for (int j = 0; j < TAMANHO; j++) {
                switch (tabuleiro[i][j]) {
                    case Partida.JOGADOR_1:
                        System.out.print("X");
                        break;
                    case Partida.JOGADOR_2:
                        System.out.print("O");
                        break;
                    default:
                        System.out.print(" ");
                }
                if (j != 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println(" *");
        }
        System.out.println("*************");
        System.out.println();
    }
    
    /**
     * Método responsável por retorna a matriz de posições para as jogadas.
     * @return Byte - Matriz de posições.
     */
    public byte[][] getTabuleiro() {
        return tabuleiro;
    }

}

package jogodavelha;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe Partida responsável por toda lógica do jogo e interface com o usuário.
 *
 * @author luizlaljr
 *
 * @version 1.5
 */
class Partida {
    
    /**
     * Constante do tipo int o qual representa que o jogador 1 é o da vez.
     */
    private static final int JOGADORDAVEZ_1 = 1;
    
    /**
     * Constante do tipo int o qual representa que o jogador 2 é o da vez.
     */
    private static final int JOGADORDAVEZ_2 = 2;
    
    /**
     * Constante do tipo String o qual representa que a Linha. 
     */
    private static final String LINHA = "Linha";
    
     /**
     * Constante do tipo String o qual representa que a Coluna.
     */
    private static final String COLUNA = "Coluna";
    
    /**
     * Constante do tipo Byte o qual representa que a partida continua.
     */
    public static final byte CONTINUA_PARTIDA = 2;
    
    /**
     * Constante do tipo Byte o qual representa que o tabuleiro está todo preenchido e a partida terminou empatada.
     */
    public static final byte EMPATE = 0;
    
    /**
     * Constante do tipo Byte o qual representa que a posição está marcada com o símbolo do jogador 1.
     */
    public static final byte JOGADOR_1 = 1;
    
    /**
     * Constante do tipo Byte o qual representa que a posição está marcada com o símbolo do jogador 2.
     */
    public static final byte JOGADOR_2 = -1;
    
    /**
     * Constante do tipo Byte o qual representa que a posição está vazia.
     */
    public static final byte POSICAO_VAZIA = 0;
    
    /**
     * Atributo que representa a coleção de jogadores.
     */
    private final Map<Integer,Jogador> jogadores = new HashMap<>();
    
    /**
     * Atributo que guarda os simbolos dos jogadores.
     */
    private final char[] simbolo = {'X','O'};
            
    /**
     * Atributo que representa o tabuleiro do jogo.
     */
    private final Tabuleiro tab;
    
    /**
     * Inteiro que determina o jogador que está na vez de realizar uma jogada.
     */
    private int vez;

    /**
     * Construtor da Classe Partida. Cria as instâncias dos jogadores e do tabuleiro.
     */
    public Partida() {

        determinarJogadores();
        tab = new Tabuleiro();
        vez = 0;

    }
    /**
     * Método que instancia os jogadores.
     */
    private void determinarJogadores(){
        
        Scanner sc = new Scanner(System.in);
        int chave = 1;
        int index = 0;
        do{
            System.out.println("Para iniciar a partida, digite o nome do Jogador "+chave+":");
            String nome = sc.nextLine();
            System.out.println("O simbolo do(a) " + nome + " será X");
            Jogador jogador = new Jogador(nome, simbolo[index++]);
            jogadores.put(chave++,jogador);        
            System.out.println();
                      
        }while(chave<3);
        
    }

    /**
     * Método que inicia a partida e aplica a lógica do jogo.
     */
    public void iniciarPartida() {

        Jogador jogadorVez = jogadorVez();
        tab.exibeTabuleiro();
        int retornoFimDePartida;

        do {
            realizarJogada(jogadorVez);
            jogadorVez = jogadorVez();
            tab.exibeTabuleiro();
            retornoFimDePartida = ehFimDePartida();

        } while (retornoFimDePartida == CONTINUA_PARTIDA);

        switch (retornoFimDePartida) {
            case EMPATE:
                System.out.println("A partida terminou empatada!");
                break;
            case JOGADOR_1:
                System.out.println(jogadores.get(JOGADORDAVEZ_1).getNome() + " ganhou o jogo.");
                break;
            case JOGADOR_2:
                System.out.println(jogadores.get(JOGADORDAVEZ_2).getNome() + " ganhou o jogo.");
                break;
                
        }
        
    }

    /**
     * Método responsável por altenar o jogador que está na vez.
     *
     * @return Jogador - Jogador que está na vez.
     */
    private Jogador jogadorVez() {
        return ((vez++ % 2 == 0) ? jogadores.get(JOGADORDAVEZ_1) : jogadores.get(JOGADORDAVEZ_2));
    }

    /**
     * Método responsável por realizar a jogada do jogador da vez.
     *
     * @param jogadorVez - Jogador que está na vez.
     */
    private void realizarJogada(Jogador jogadorVez) {

        boolean jogadaDisponivel;

        do {
            int linha = solicitar(LINHA,jogadorVez);
            int coluna = solicitar(COLUNA,jogadorVez);
            
            jogadaDisponivel = validarJogada(jogadorVez, --linha, --coluna);

            if (!jogadaDisponivel) {
                System.out.println();
                System.out.println("Jogada não disponível, tente outra posição!");
            }

        } while (!jogadaDisponivel);

        System.out.println("Jogada realizada com sucesso!");
    }
    
    /**
     * Método responsável por solicitar uma jogada ao jogador da vez.
     * 
     * @param posicao - String que determina se será a Linha ou a Coluna a ser informada.
     * @param jogadorVez - Jogador que está na vez.
     * @return 
     */
    private int solicitar(String posicao, Jogador jogadorVez){
        int pos = -1;
        Scanner sc = new Scanner(System.in);
        do {
            try {
 
                System.out.println("Digite a "+posicao+" que deseja jogar " + jogadorVez.getNome() + ":");
                pos = sc.nextInt();
                if((pos<1)||(pos>3)){
                    System.out.println(posicao+" inválida!");
                 }
            } catch (Exception e) {
                System.out.println(posicao+" inválida!");
            } finally {
                sc.nextLine();
                System.out.println();
            }
            
            
        } while ((pos<1)||(pos>3));
        
        return pos;
    }

    /**
     * Método responsável por verificar se a partida foi vencida por algum
     * jogador, se deu empate ou a partida continua.
     *
     * @return Int - indica a situação da partida.
     */
    private byte ehFimDePartida() {

        byte tabuleiro[][] = this.tab.getTabuleiro();

        byte result;
        boolean jogador1Venceu = false;
        boolean jogador2Venceu = false;
        int somaColuna;
        int somaLinha;
        int somaDiagonalPrincipal = 0;
        int somaDiagonalSecundaria = 0;
        int preenchidas = 0;
        
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            somaColuna = 0;
            somaLinha = 0;
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                preenchidas += (tabuleiro[i][j] != 0) ? 1 : 0;
                somaLinha += tabuleiro[i][j];
                somaColuna += tabuleiro[j][i];
                if (j == i) {
                    somaDiagonalPrincipal += tabuleiro[j][j];
                }
                if (j + i == 2) {
                    somaDiagonalSecundaria += tabuleiro[i][j];
                }
            }
            if ((somaColuna > 2) | (somaLinha > 2)) {
                jogador1Venceu = true;
            }
            if ((somaColuna < -2) | (somaLinha < -2)) {
                jogador2Venceu = true;
            }
        }
        if ((somaDiagonalPrincipal > 2) | (somaDiagonalSecundaria > 2)) {
            jogador1Venceu = true;
        }

        if ((somaDiagonalPrincipal < -2) | (somaDiagonalSecundaria < -2)) {
            jogador2Venceu = true;
        }

        if (jogador1Venceu) {
            result = Partida.JOGADOR_1;
        } else if (jogador2Venceu) {
            result = Partida.JOGADOR_2;
        } else if (preenchidas == Tabuleiro.TAMANHO * Tabuleiro.TAMANHO) {
            result = Partida.EMPATE;
        } else {
            result = Partida.CONTINUA_PARTIDA;
        }

        return result;
    }

    /**
     * Método responsável por validar uma jogada.
     *
     * @param jogadorVez - Jogador que está na vez.
     * 
     * @param linha - linha fornecida pelo jogador da vez.
     * 
     * @param coluna - coluna fornecida pelo jogador da vez.
     * 
     * @return Boolean - Condição da jogada.
     */
    private boolean validarJogada(Jogador jogadorVez, int linha, int coluna) {

        byte tabuleiro[][] = this.tab.getTabuleiro();

        boolean ehJogada = false;
        char simbolo = 'X';
        
        if (tabuleiro[linha][coluna] == POSICAO_VAZIA) {
            
            tabuleiro[linha][coluna] = (simbolo == jogadorVez.getSimbolo()) ? JOGADOR_1 : JOGADOR_2;
            ehJogada = true;
        }
        return ehJogada;

    }

}

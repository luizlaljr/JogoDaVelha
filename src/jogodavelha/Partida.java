package jogodavelha;

import java.util.Scanner;

/**
 * Classe Partida responsável por toda lógica do jogo e interface com o usuário.
 *
 * @author luizlaljr
 *
 * @version 1.3
 */
class Partida {
    
    public static final byte CONTINUA_PARTIDA = 2;
    public static final byte EMPATE = 0;
    public static final byte JOGADOR_1 = 1;
    public static final byte JOGADOR_2 = -1;
    public static final byte POSICAO_VAZIA = 0;

    private final Jogador jogador_1;
    private final Jogador jogador_2;
    private final Tabuleiro tab;
    
    private int vez;

    /**
     * Construtor da Classe Partida. Cria as instâncias dos jogadores e do tabuleiro.
     */
    public Partida() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Para iniciar a partida, digite o nome do Jogador 1:");
        String nome = sc.nextLine();
        System.out.println("O simbolo do(a) " + nome + " será X");
        char simbolo = 'X';
        jogador_1 = new Jogador(nome, simbolo);
        System.out.println();
        System.out.println("Digite o nome do Jogador 2:");
        nome = sc.nextLine();
        System.out.println("O simbolo do(a) " + nome + " será O");
        simbolo = 'O';
        jogador_2 = new Jogador(nome, simbolo);
        tab = new Tabuleiro();
        vez = 0;

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
                System.out.println(jogador_1.getNome() + " ganhou o jogo.");
                break;
            case JOGADOR_2:
                System.out.println(jogador_2.getNome() + " ganhou o jogo.");
                break;
        }
    }

    /**
     * Método responsável por altenar o jogador que está na vez.
     *
     * @return Jogador - Jogador que está na vez.
     */
    private Jogador jogadorVez() {
        return ((vez++ % 2 == 0) ? jogador_1 : jogador_2);
    }

    /**
     * Método responsável por solicitar uma jogada ao jogador da vez.
     *
     * @param jogadorVez - Jogador que está na vez.
     */
    private void realizarJogada(Jogador jogadorVez) {

        boolean jogadaDisponivel = false;

        do {
            int linha, coluna;
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite a linha que deseja jogar " + jogadorVez.getNome() + ":");
            linha = sc.nextInt();
            System.out.println("Digite a coluna que deseja jogar " + jogadorVez.getNome() + ":");
            coluna = sc.nextInt();
            jogadaDisponivel = validarJogada(jogadorVez, --linha, --coluna);

            if (!jogadaDisponivel) {
                System.out.println();
                System.out.println("Jogada não disponível, tente outra posição!");
            }

        } while (!jogadaDisponivel);

        System.out.println("Jogada realizada com sucesso!");
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

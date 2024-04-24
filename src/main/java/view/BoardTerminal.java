package view;

import exception.ExitException;
import exception.ExplosionException;
import model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardTerminal {

    private Board board;
    private static Scanner input = new Scanner(System.in);

    public BoardTerminal(Board board) {
        this.board = board;

        runGame();
    }

    private void runGame() {

        try {

            boolean continueGame = true;

            while (continueGame){

                gameCycle();

                System.out.println("Outra partida? (S/n) ");

                String response = input.nextLine();

                if("n".equalsIgnoreCase(response)){
                    continueGame=false;
                }else{
                    board.restart();
                }

            }

        }catch (ExitException e){
            System.out.println("Tchau!!!");
        } finally {
            input.close();
        }

    }

    private void gameCycle() {

        try {

            while (!board.goalAchieved()){
                System.out.println("\n" + board);

                String typed = captureEnteredValue("Digite sair se quiser encerrar o jogo\nDigite (x,y): ");

                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(p -> Integer.parseInt(p.trim())).iterator();

                typed = captureEnteredValue("\nDigite sair se quiser encerrar o jogo\n1 - Abrir \n2 - (Des)Marcar \nO quê quer fazer? ");

                if("1".equalsIgnoreCase(typed)){
                    board.open(xy.next(),xy.next());
                } else if ("2".equalsIgnoreCase(typed)) {
                    board.changeMarkedField(xy.next(),xy.next());
                }
            }

            System.out.println("Você ganhou!!!");

        }catch (ExplosionException e){

            System.out.println("\n" + board);
            System.out.println("Você perdeu!");

        }catch (NumberFormatException e){

            System.out.println("Resposta inválida :( , Tente novamente!");

        }

    }

    private String captureEnteredValue(String text) {

        System.out.print(text);

        String typed = input.nextLine();

        if("sair".equalsIgnoreCase(typed)){
            throw new ExitException();
        }

        return typed;
    }


}


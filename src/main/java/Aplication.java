import exception.ExitException;
import model.Board;
import view.BoardTerminal;

public class Aplication {

    public static void main(String[] args) {

        Board board = new Board(6,6,6);

        new BoardTerminal(board);

    }



}

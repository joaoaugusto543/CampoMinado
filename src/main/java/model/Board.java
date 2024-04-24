package model;

import exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int lines;
    private int column;
    private int mines;
    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int column, int mines) {
        this.lines = lines;
        this.column = column;
        this.mines = mines;

        drawMines();
        generateFields();
        gatherNeighbors();

    }

    private void drawMines() {

        for (int i = 0; i < lines; i++){
            for (int j = 0; j < column; j++) {
                fields.add(new Field(i,j));
            }
        }

    }

    private void gatherNeighbors() {

        for (Field f1: fields) {
            for (Field f2: fields) {
                f1.addNeighbors(f2);
            }
        }


    }



    private void generateFields() {

        long armedMines = 0;

        Predicate<Field> mined = Field::isMined;

        do {


            int randow = (int) (Math.random() * fields.size());

            fields.get(randow).undermine();

            armedMines = fields.stream().filter(mined).count();

        }while (armedMines < mines);

    }

    public boolean goalAchieved(){
       return fields.stream().allMatch(Field::goalAchieved);
    }

    public void restart(){
        fields.forEach(Field::restart);
        fields.clear();
        drawMines();
        generateFields();
        gatherNeighbors();
    }

    public void open(int line, int column){

        try {

            fields.parallelStream()
                    .filter(f -> f.getLine() == line && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(Field::open);


        }catch (ExplosionException e){

            fields.forEach(f -> f.setOpen(true));

            throw e;

        }
    }

    public void changeMarkedField(int line, int column){
        fields.parallelStream()
                .filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(Field::changeMarkedField);

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        int i = 0;

        for (int l = 0; l < lines; l++) {

            for (int c = 0; c < column; c++) {

                sb.append(" ");

                if(fields.get(i).getColumn() == 0){
                    sb.append("| ");
                }

                sb.append(fields.get(i));
                sb.append(" ");
                sb.append("|");
                i++;
            }

            sb.append("\n");

        }

        return sb.toString();
    }
}

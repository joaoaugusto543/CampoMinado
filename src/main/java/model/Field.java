package model;

import exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private boolean mined;
    private boolean open;

    private boolean flagField;

    private final int line;
    private final int column;

    private List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column){
        this.line=line;
        this.column=column;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isFlagField() {
        return flagField;
    }

    public boolean addNeighbors(Field neighbor){

        boolean isDifferentLine = line != neighbor.line;
        boolean isDifferentColumn = column != neighbor.column;
        boolean isDiagonal = isDifferentLine && isDifferentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);

        int generalDelta = deltaColumn + deltaLine;

        if(generalDelta == 1 && !isDiagonal){
            neighbors.add(neighbor);
            return true;
        }else if(generalDelta == 2 && isDiagonal){
            neighbors.add(neighbor);
            return true;
        }else{
            return false;
        }

    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void changeMarkedField(){
        if(!isOpen()){
            flagField = !isFlagField();
        }
    }

    public void undermine(){
        if(!mined){
            mined=true;
        }
    }

    public boolean open(){

        if(!isOpen() && !isFlagField()){
            open=true;

            if(isMined()){
                throw new ExplosionException();
            }

            if(safeNeighbors()){
                neighbors.forEach(Field::open);
            }

            return true;
        }

        return false;
    }

    private boolean safeNeighbors(){

        return  neighbors.stream().noneMatch(Field::isMined);

    }

    long minedNeighbors(){
        return neighbors.stream().filter((field) -> field.mined).count();
    }

    public void restart(){
        open=false;
        mined=false;
        flagField=false;
    }

    @Override
    public String toString() {
        if(flagField){
            return "x";
        } else if (open && mined) {
            return "*";
        } else if (open && minedNeighbors() > 0) {
            return Long.toString(minedNeighbors());
        } else if (open) {
            return " ";
        }else{
            return "?";
        }
    }

    boolean goalAchieved(){
        boolean unraveled = !mined && open;
        boolean safe = mined && flagField;
        return unraveled || safe;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}



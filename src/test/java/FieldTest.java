import exception.ExplosionException;
import model.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FieldTest {

    Field field;

    @BeforeEach
    void startField(){
        field= new Field(3,3);
    }
    @Test
    public void deveAdicionarOsVizinhos(){

        Field neighborOnTheLeft = new Field(3,2);
        Field neighborOnTheRight = new Field(3,4);
        Field neighborOnTop = new Field(2,3);
        Field neighborOnBottom = new Field(4,3);

        boolean resultNeighborOnTheRight = field.addNeighbors(neighborOnTheRight);
        boolean resultNeighborOnTheLeft = field.addNeighbors(neighborOnTheLeft);
        boolean resultNeighborOnTop = field.addNeighbors(neighborOnTop);
        boolean resultNeighborOnBottom = field.addNeighbors(neighborOnBottom);

        Assertions.assertTrue(resultNeighborOnTheRight);
        Assertions.assertTrue(resultNeighborOnTheLeft);
        Assertions.assertTrue(resultNeighborOnTop);
        Assertions.assertTrue(resultNeighborOnBottom);

    }

    @Test
    public void deveAdicionarOsVizinhosDaDiagonal(){

        Field neighborOne = new Field(2,2);
        Field neighborTwo = new Field(2,4);
        Field neighborThree = new Field(4,2);
        Field neighborFour = new Field(4,4);

        boolean resultNeighborOne = field.addNeighbors(neighborOne);
        boolean resultNeighborTwo = field.addNeighbors(neighborTwo);
        boolean resultNeighborThree = field.addNeighbors(neighborThree);
        boolean resultNeighborFour = field.addNeighbors(neighborFour);

        Assertions.assertTrue(resultNeighborOne);
        Assertions.assertTrue(resultNeighborTwo);
        Assertions.assertTrue(resultNeighborThree);
        Assertions.assertTrue(resultNeighborFour);

    }

    @Test
    public void naoDeveAdicionarOsCamposErrados(){

        Field wrongFieldOne = new Field(1,2);
        Field wrongFieldTwo = new Field(1,3);

        boolean resultNeighborOnTheRight = field.addNeighbors(wrongFieldTwo);
        boolean resultNeighborOnTheLeft = field.addNeighbors(wrongFieldOne);


        Assertions.assertFalse(resultNeighborOnTheRight);
        Assertions.assertFalse(resultNeighborOnTheLeft);

    }

    @Test
    public void deveSerTrueQuandoAlterarMarcacao(){

        field.changeMarkedField();

        Assertions.assertTrue(field.isFlagField());

    }

    @Test
    public void deveSerFalseQuandoAlterarDuasVezesAMarcacao(){

        field.changeMarkedField();
        field.changeMarkedField();

        Assertions.assertFalse(field.isFlagField());

    }

    @Test
    public void deveSerFalseValorPadraoMined(){
        Assertions.assertFalse(field.isMined());
    }

    @Test
    public void deveSerFalseValorPadraoFlagField(){
        Assertions.assertFalse(field.isFlagField());
    }

    @Test
    public void deveAbrirCampoNaoMarcadoENaoMinado(){
        Assertions.assertTrue(field.open());
    }

    @Test
    public void naoDeveAbrirCampoMarcado(){
        field.changeMarkedField();
        Assertions.assertFalse(field.open());
    }

    @Test
    public void deveMostrarOErroExplosionException(){
        field.undermine();
        Assertions.assertThrows(ExplosionException.class,() -> {
            field.open();
        });
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Opeyemi
 */
package javafxgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Opeyemi
 */
public class MemoryPuzzleGame extends Application {

    private static final int NUM_OF_PAIRS = 8;
    private static final int NUM_PER_ROW = 4;
    private Tile selected = null;
    private int clickCount =2;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(300, 300);

        char c = 'A';
        List<Tile> tiles = new ArrayList<>();
        
        for (int i = 0; i < NUM_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));
            c++;
        }

        Collections.shuffle(tiles);
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(50 * (i % NUM_PER_ROW));
            tile.setTranslateY(50 * (i / NUM_PER_ROW));
         
            root.getChildren().add(tile);
        }
        //root.getChildren().add(tile);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

    }

    // class that takes in a string value, return tiles with border 
    //with value in it
    private class Tile extends StackPane {
        private Text text = new Text();
        
        //constructor
        public Tile(String value) {
            

            //cell with width and height 50, 50
            Rectangle border = new Rectangle(50, 50);

            // rectangle comes with filled color
            //setFill to null to remove the fill color
            border.setFill(null);

            //set the color of the border lines to desired color
            border.setStroke(Color.RED);
            
            
            text.setText(value);

            //font size
            text.setFont(Font.font(30));
            text.setStroke(Color.BLUE);

            //centers everything within the stackpane
            setAlignment(Pos.CENTER);

            getChildren().addAll(border, text);
            setOnMouseClicked(this::handleMouseClick);
            close();

        }
        
        public void handleMouseClick(MouseEvent event){
             if (isOpen() || clickCount == 0)
                    return;
                   clickCount--;
                   
                if(selected == null){
                    selected = this;
                    open(()->{});
                }
                else{
                     open(()->{
                    if(!hasSameValue(selected)){
                        selected.close();
                        this.close(); 
                    }
                    selected = null;
                    clickCount = 2;
                    
                     });
                }
            
        }
        
        public boolean hasSameValue(Tile other){
 
            return text.getText().equals(other.text.getText());
        }
        
        public boolean isOpen(){
            return text.getOpacity() == 1;
        }
        
        
        public void open(Runnable action){
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
         ft.setToValue(2);
         ft.setOnFinished(e->action.run());
         ft.play();
        }
        
        public void close(){
         FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
         ft.setToValue(0);
         ft.play();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        launch(args);
    }

}

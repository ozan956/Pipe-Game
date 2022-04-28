package application;
import javafx.animation.PathTransition; 
import javafx.application.Application; 

import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 

import javafx.scene.shape.Circle; 
import javafx.scene.shape.LineTo; 
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path;  

import javafx.stage.Stage; 
import javafx.util.Duration; 
         
public class PathTransitionExample2 extends Application {   
   @Override 
   public void start(Stage stage) {   
      //Drawing a Circle 
      Circle circle = new Circle(); 
      
      //Setting the position of the circle 
      circle.setCenterX(300.0f); 
      circle.setCenterY(135.0f); 
      
      //Setting the radius of the circle 
      circle.setRadius(25.0f); 
      
      //Setting the color of the circle 
      circle.setFill(Color.BROWN); 
      
      //Setting the stroke width of the circle 
      circle.setStrokeWidth(20);     
       
      //Creating a Path 
      Path path = new Path(); 
      
      //Moving to the starting point 
      MoveTo moveTo = new MoveTo(108, 71);               
      
      //Creating 1st line 
      LineTo line1 = new LineTo(321, 161);        
      
      //Creating 2nd line 
      LineTo line2 = new LineTo(126,232); 
      
      //Creating 3rd line 
      LineTo line3 = new LineTo(232,52);        
      
      //Creating 4th line 
      LineTo line4 = new LineTo(269, 250);        
      
      //Creating 5th line 
      LineTo line5 = new LineTo(108, 71);       
      
      //Adding all the elements to the path  
      path.getElements().add(moveTo); 
      path.getElements().addAll(line1, line2, line3, line4, line5);     
      
      //Creating the path transition 
      PathTransition pathTransition = new PathTransition(); 
      
      //Setting the duration of the transition 
      pathTransition.setDuration(Duration.millis(1000));       
      
      //Setting the node for the transition 
      pathTransition.setNode(circle); 
      
      //Setting the path for the transition 
      pathTransition.setPath(path); 
      
      //Setting the orientation of the path 
      pathTransition.setOrientation(
         PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT); 
      
      //Setting the cycle count for the transition 
      pathTransition.setCycleCount(50); 
      
      //Setting auto reverse value to true 
      pathTransition.setAutoReverse(false); 
      
      //Playing the animation 
      pathTransition.play(); 
             
      //Creating a Group object  
      Group root = new Group(circle); 
         
      //Creating a scene object 
      Scene scene = new Scene(root, 600, 300);  
      
      //Setting title to the Stage 
      stage.setTitle("Path transition example"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show(); 
   }
   public static void main(String args[]){ 
      launch(args); 
   } 
} 
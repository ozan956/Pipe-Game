package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.Node;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	ArrayList<Integer> animationpath = new ArrayList<Integer>();
	double xval,yval;
	ArrayList<Cell> list=new ArrayList<Cell>();
	Scene scene=null;
	Pane root=null;
	int level=0;
	Stage stage=new Stage();
	Circle staticcircle=new Circle();

	@Override
	//I HATE GAYS.
	public void start(Stage primaryStage) throws FileNotFoundException {

		
		root=new Pane();

		create_episode(level);


		

		
		
		scene=new Scene(root,600,650);
		//scene.setFill(new ImagePattern(new Image(input)));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public class Cell extends ImageView {
		String type=null;
		private int id;
		boolean moveable,free;
		double xstart,ystart,xfin,yfin,xreal,yreal;
		int in,out;
		int moves;

		public Cell(int id,boolean moveable,boolean free,String inputway,String type,int in,int out) throws FileNotFoundException {
				this.in=in;this.out=out;
				this.id=id;
				this.moveable=moveable;
				this.free=free;
				this.type=type;
				Image img=new Image(inputway);
				this.setImage(img);
				this.setFitWidth(150);
				this.setFitHeight(150);
				
			this.setOnMousePressed(e->{
				xstart=e.getSceneX();
				ystart=e.getSceneY();
				xreal=this.getX();
				yreal=this.getY();
			});
			
			this.setOnMouseClicked(e ->{
			});
			this.setOnMouseDragged(e->{
				xfin=e.getSceneX();
				yfin=e.getSceneY();
				this.setX(e.getSceneX()-75);
				this.setY(e.getSceneY()-75);
				
				
			});
			this.setOnMouseReleased(e->{
				if(xfin ==0 && yfin==0) {
					return;
				}
				
				//SAG HAREKET
	        	if(xfin-xstart > 70 && xfin-xstart <270 && yfin-ystart < 75&& yfin-ystart>-75 && (id%4) <= 2) {
	        		
	        		if(this.moveable && list.get(this.id+1).moveable && (this.isFree() || list.get(this.id+1).isFree() )) {
				
			        		Image former = this.getImage();
			        		Image next = list.get(this.id+1).getImage();
			        		
			        		this.setImage(next);
			        		list.get(this.id+1).setImage(former);
			        		
			        		
			        		
			        		boolean temp=this.isFree();
			        		this.setFree(list.get(this.id+1).isFree());
			        		list.get(this.id+1).setFree(temp);
			        	
			        		String temp2=this.getType();
			        		this.setType((list.get(this.id+1).getType()));
			        		list.get(this.id+1).setType(temp2);
			        		
			        		
			        		int tempout=this.out;
			        		this.out=((list.get(this.id+1).out));
			        		list.get(this.id+1).out=tempout;
			        		
			        		int tempin=this.in;
			        		this.in=((list.get(this.id+1).in));
			        		list.get(this.id+1).in=tempin;
			        		
			        		moves++;
			        		
			        		

	        		}
	        		else {
	        			System.out.println("cant move");
	        		}
	        		

	        		
	        		
	        	}
	        	if(xfin-xstart  < -70 && xfin-xstart  > -270 && yfin-ystart<75&& yfin-ystart>-75 && (id%4) >= 1) {
	        		
	        		
	        		//SOL HAREKET
	        		if((this.moveable && list.get(this.id-1).moveable) && (this.isFree() || list.get(this.id-1).isFree())){
		        		Image former = this.getImage();
		        		Image next = list.get(this.id-1).getImage();
		        		
		        		this.setImage(next);
		        		list.get(this.id-1).setImage(former);
		        		
		        		boolean temp=this.isFree();
		        		this.setFree(list.get(this.id-1).isFree());
		        		list.get(this.id-1).setFree(temp);
		        		String temp2=this.getType();
		        		this.setType((list.get(this.id-1).getType()));
		        		list.get(this.id-1).setType(temp2);
		        		
		        		int tempout=this.out;
		        		this.out=((list.get(this.id-1).out));
		        		list.get(this.id-1).out=tempout;
		        		
		        		int tempin=this.in;
		        		this.in=((list.get(this.id-1).in));
		        		list.get(this.id-1).in=tempin;
		        		moves++;
		        		
		        
	        		}
	        		else {
	        			System.out.println("cant move");
	        		}
	        		

	        	}
	        	if(yfin-ystart > 70 && yfin-ystart < 270 && xfin-xstart<75 && xfin-xstart>-75 && (id+4) <= 15) {
	        		
	        		
	        		if((this.moveable && list.get(this.id+4).moveable) && (this.isFree() || list.get(this.id+4).isFree())){
		        		Image former = this.getImage();
		        		Image next = list.get(this.id+4).getImage();
		        		
		        		this.setImage(next);
		        		list.get(this.id+4).setImage(former);
		        		
		        		boolean temp=this.isFree();
		        		this.setFree(list.get(this.id+4).isFree());
		        		list.get(this.id+4).setFree(temp);
		        		
		        		String temp2=this.getType();
		        		this.setType((list.get(this.id+4).getType()));
		        		list.get(this.id+4).setType(temp2);
		        		
		        		
		        		int tempout=this.out;
		        		this.out=((list.get(this.id+4).out));
		        		list.get(this.id+4).out=tempout;
		        		
		        		int tempin=this.in;
		        		this.in=((list.get(this.id+4).in));
		        		list.get(this.id+4).in=tempin;
		        		moves++;
		        	
		        		
	        		}
	        		else {
	        			System.out.println("cant move");
	        		}
	        		
	        		

	        	}
	        	if(yfin-ystart < -70 && yfin-ystart > -270 && xfin-xstart<75 && xfin-xstart>-75 && (id-4) >= 0) {
	        		
	        		
	        		if( this.moveable && list.get(this.id-4).moveable && (this.isFree() || list.get(this.id-4).isFree() )){
		        		Image former = this.getImage();
		        		Image next = list.get(this.id-4).getImage();
		        		
		        		this.setImage(next);
		        		list.get(this.id-4).setImage(former);
		        		
		        		boolean temp=this.isFree();
		        		this.setFree(list.get(this.id-4).isFree());
		        		list.get(this.id-4).setFree(temp);
		        		
		        		String temp2=this.getType();
		        		this.setType((list.get(this.id-4).getType()));
		        		list.get(this.id-4).setType(temp2);
		        		
		        		int tempout=this.out;
		        		this.out=((list.get(this.id-4).out));
		        		list.get(this.id-4).out=tempout;
		        		
		        		int tempin=this.in;
		        		this.in=list.get(this.id-4).in;
		        		list.get(this.id-4).in=tempin;
		        		moves++;
	        		}
	        		else {
	        			System.out.println("cant move");
	        		}
	        		
	        	}

				this.setX(xreal);
				this.setY(yreal);
				
				for(Cell a:list) {
					if(a.getType().equals("Starter") || a.getType().equals("Starter90")) {
							animationpath.add(a.id);
						if(woncheck(a.in,a.out,a.id,99)) {
							Image circ=null;
							circ=new Image("circle.png");
							
							Path path=new Path();
							Circle circle=new Circle();
							root.getChildren().remove(staticcircle);
							
						      circle.setCenterX(150*animationpath.get(0)/4); 
						      circle.setCenterY(150*animationpath.get(0)%4);
						      //Setting the radius of the circle 
						      circle.setRadius(18f); 
						      
						      //Setting the color of the circle 
				    			
				    			circle.setFill(new ImagePattern(circ));
						      
						      //Setting the stroke width of the circle 
						      circle.setStrokeWidth(20);     
						      
						      path.getElements().add(new MoveTo(list.get(0).getX()+75,list.get(0).getY()+75));
						      
						      animationpath.remove(0);
						      
						      double xpos;
						      double ypos;
								for(Integer pathId:animationpath) {
									xpos=list.get(pathId).getX()+75;
									ypos=list.get(pathId).getY()+75;
									
									path.getElements().add(new LineTo(xpos,ypos));
									
								}
								
    
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
						      pathTransition.setCycleCount(1); 
						      pathTransition.play(); 
							
						      
						      root.getChildren().add(circle);
						      
						      pathTransition.setOnFinished(d->{

							      level++;
							    	  Notification not=new Notification();
							    	  
							    	  not.buton.setOnMouseClicked(arg->{
								    	  list.removeAll(list);

											
								    	  root.getChildren().clear();
								    	  
								    	  try {
											create_episode(level);
											not.hide();
										} catch (FileNotFoundException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
							    	  });
						      });
						}
						return;
					}
				}
				
			});

			
			xfin=0;
			yfin=0;
		}
		public boolean isMoveable() {
			return moveable;
		}
		public void setMoveable(boolean moveable) {
			this.moveable = moveable;
		}
		public boolean isFree() {
			return free;
		}
		public void setFree(boolean free) {
			this.free = free;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}


	public void create_episode(int level) throws FileNotFoundException {
		
  	  list.removeAll(list);

		
	  root.getChildren().clear();
		
	    try {
	    	
    		File file = null;
    		Scanner read = null;
	    	if(level==0) {
	    		file = new File("C:\\Users\\ozand\\Desktop\\assets\\read1.txt");
	    		read = new Scanner(file);	
	    	}

	    	else if(level==1) {
	    		file = new File("C:\\Users\\ozand\\Desktop\\assets\\read2.txt");
	    		read = new Scanner(file);	
	    	}
	    	
	    	else if(level==2){
	    		file = new File("C:\\Users\\ozand\\Desktop\\assets\\read3.txt");
	    		read = new Scanner(file);	
	    	}
	    	else if(level ==3){
	    		file = new File("C:\\Users\\ozand\\Desktop\\assets\\read6.txt");
	    		read = new Scanner(file);	
	    	}
	    	else {
	    		//GameFinished cart curt
	    		file = new File("C:\\Users\\ozand\\Desktop\\assets\\read4.txt");
	    		read = new Scanner(file);	
	    	}
	        
	        int id=0;
	        
	        while (read.hasNextLine()) {
	          String data = read.nextLine();
	          if(!(data.equals(""))) {
	        	  String[] item=data.split(",");
	        	  if(item[1].equals("Empty")) {
	        		  if(item[2].equals("none")) { 
		        			list.add(new Cell(id,true,false,"empty.png","Empty",0,0)) ;
	        		  }
	        		  else if(item[2].equals("Free")){
		        			list.add(new Cell(id,true,true,"empty_free.png","EmptyFree",0,0)) ;
	        		  }
	        	  }
	        	  else if(item[1].equals("Starter")) {
	        		  if(item[2].equals("Vertical")) { 
		        			list.add(new Cell(id,false,false,"starter.png","Starter",1,3)) ;
	        		  }
	        		  else { 
		        			list.add(new Cell(id,false,false,"starter90.png","Starter90",2,4)) ;
	        		  }
	        	
	        	  }
	        	  else if(item[1].equals("Pipe")) {
	        		  if(item[2].equals("Vertical")) { 
		        			list.add(new Cell(id,true,false,"pipe_free.png","PipeFree",1,3)) ;
	        		  }
	        		  else if(item[2].equals("Horizontal")){; 
		        			list.add(new Cell(id,true,false,"pipe_free90.png","PipeFree90",2,4)) ;
	        		  }
	        		  else if(item[2].equals("00")){
		        			list.add(new Cell(id,true,false,"curved0.png","Curved0",1,4)) ;
	        		  }
	        		  else if(item[2].equals("01")){
		        			list.add(new Cell(id,true,false,"curved90.png","Curved90",1,2)) ;
	        		  }
	        		  else if(item[2].equals("10")){
		        			list.add(new Cell(id,true,false,"curved270.png","Curved270",4,3)) ;
	        		  }
	        		  else if(item[2].equals("11")){
		        			list.add(new Cell(id,true,false,"curved180.png","Curved180",2,3)) ;
	        		  }
	        	
	        	  }
	        	  else if(item[1].equals("PipeStatic")) {
	        		  if(item[2].equals("Horizontal")) {
		        			list.add(new Cell(id,false,false,"pipe.png","PipeStatic",4,2)) ;
	        		  }
	        		  else if(item[2].equals("Vertical")){
		        			list.add(new Cell(id,false,false,"pipe90.png","PipeStatic90",1,3)) ;
	        		  }
	        		  else if(item[2].equals("00")){
		        			list.add(new Cell(id,false,false,"pipecurved0.png","StaticCurved0",1,4)) ;
	        		  }
	        		  else if(item[2].equals("01")){
		        			list.add(new Cell(id,false,false,"pipecurved90.png","StaticCurved90",1,2)) ;
	        		  }
	        		  else if(item[2].equals("10")){
		        			list.add(new Cell(id,false,false,"pipecurved270.png","StaticCurved270",4,3)) ;
	        		  }
	        		  else if(item[2].equals("11")){
		        			list.add(new Cell(id,false,false,"pipecurved180.png","StaticCurved180",2,3)) ;
	        		  }
	        	
	        	  }
	        	  else if(item[1].equals("End")) {
	        		  if(item[2].equals("Vertical")) {
		        			list.add(new Cell(id,false,false,"end.png","End",1,3)) ;
	        		  }
	        		  else {
		        			list.add(new Cell(id,false,false,"end90.png","End90",2,4)) ;
	        		  }
	        	
	        	  }
	        	  id++;
	          }

	        }
	        read.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
		int i=0;int j=0;
		for(Cell a:list) {
			a.setX(i*150);
			a.setY(j*150+50);
			root.getChildren().add(a);
			i++;
			if(i==4) {
				j++;
				i=0;
			}
			if(a.getType().equals("Starter")|| a.getType().equals("Starter90")) {
    			FileInputStream input = new FileInputStream("C:\\Users\\ozand\\Desktop\\assets\\circle.png");
    			Image circ=new Image(input);
				staticcircle.setCenterX(a.getX()+75);
				staticcircle.setCenterY(a.getY()+75);
				staticcircle.setRadius(18);
				staticcircle.setFill(new ImagePattern(circ));
				root.getChildren().add(staticcircle);
			}
		}
		String title="Pipe Game - Level: " + (level+1);
		stage.setTitle(title);
		
		Text text=new Text("NUMBER OF MOVES: ");
		text.setFont(Font.font("Courier", FontWeight.BOLD,FontPosture.REGULAR, 25));
		
		root.getChildren().add(text);
		text.setY(35);
		text.setX(1);
		MainMenuButton bt=new MainMenuButton();
		
		root.getChildren().add(bt.getButton());
		

	}


	public boolean woncheck(int in,int out,int id,int previous) {
		
		int[] idarray= {id-4,id-1,id+1,id+4};
		
		for(int next:idarray) {
			if((next) >=0 && (next) <= 15) {
				
				
				if((id%4 == 3 && next%4 == 0) || (id%4 == 0 && next%4 == 3)) {
					System.out.println("Skipped different rows");
					continue;
					
				}
				if(list.get(next).getType().equals("End") || list.get(next).getType().equals("End90")) {
					animationpath.add(next);
					System.out.println("You Finished!");
					return true;
				}
				if(list.get(next).out == in || list.get(next).in == in || list.get(next).in == out || list.get(next).out == out) {
					if(next == previous) {
						continue;
					}
					if(list.get(next).getType().equals("Empty") || list.get(next).getType().equals("EmptyFree") ) {
						continue;
					}
					else {
						animationpath.add(list.get(next).id);
						return woncheck(list.get(next).in,list.get(next).out,list.get(next).id,id);
					}
					 
				}	
			}
		}
		for(Integer integer:animationpath)
			System.out.print(integer +" ");
		
		
		animationpath.removeAll(animationpath);
		return false;
		
	}



	public class Notification extends Stage {
		Button buton=new Button("Press to move to next level");
		boolean pressed=false;
		Notification(){
	
			buton.setPrefWidth(200);
			buton.setPrefHeight(50);
			
			VBox pane=new VBox();
			pane.getChildren().add(buton);
			Scene scene=new Scene(pane);
			buton.setAlignment(Pos.CENTER);
			this.initModality(Modality.APPLICATION_MODAL);
			this.setResizable(false);
			this.setTitle("You Passed The Level!");

			this.setScene(scene);
			this.show();
			
		}
		public boolean getPressed() {
			return pressed;
		}
	}
	
	public class MainMenuButton extends Button {

		
		Button buton=new Button("");

		
		public Button getButton() {
			return buton;
		}


		public void setButton(Button button) {
			this.buton = button;
		}


		MainMenuButton(){
			Image img = new Image("home-button.png");
			ImageView imgv=new ImageView(img);
			buton.setPrefSize(40, 40);
			buton.setGraphic(imgv);
			buton.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");

			buton.setLayoutX(550);
			buton.setLayoutY(0);
			buton.setOnAction(e->{
				
		    	  list.removeAll(list);

					
		    	  root.getChildren().clear();
				
				Pane secondpane = new Pane();
				// Place nodes in the pane
				
					ImageView backGround = new ImageView(new Image ("board-background.png"));

					Button [] levels = {new Button(), new Button(), new Button(), new Button(),new Button(),new Button()};
					Image image = new Image ("level-pack-locked.png");
					Image image_1 = new Image ("level-pack-unlocked.png");

					backGround.setFitHeight(650);
					backGround.setFitWidth(600);
					root.getChildren().add(backGround);

					int i = 1;
					int j = 20;
					for (Button level: levels) {

						Text text1 = new Text(20, 20, "LEVEL "+i);
						text1.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
						
					if(i==1) {
						ImageView buttonBackGroundUnlocked = new ImageView(image_1);
						level.setLayoutX(220);
						text1.setLayoutX(270);
						level.setLayoutY(20+j);
						text1.setLayoutY(30+j);
						level.setPrefSize(40, 40);
						level.setGraphic(buttonBackGroundUnlocked);
						
					}
					else {
						ImageView buttonBackGroundLocked = new ImageView(image);
						level.setPrefSize(40, 40);
						level.setLayoutX(200);
						text1.setLayoutX(270);
						level.setLayoutY(20+j);
						text1.setLayoutY(35+j);
						level.setGraphic(buttonBackGroundLocked);
						
					}
					level.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
					level.setCursor(Cursor.HAND);
					root.getChildren().add(level);
					root.getChildren().add(text1);
					i++;
					j +=70;
					}


					scene.setRoot(root);
					
					levels[0].setOnAction(R->{
						try {
							create_episode(0);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					
					
					
					/*
					Scene scene2 = new Scene(secondpane,600,600);
					stage.setTitle("Menu"); 
					stage.setScene(scene); 
					levels[0].setOnAction(d ->{
						stage.setScene(scene);
					}); */
					

	});

}
}
	
}




	

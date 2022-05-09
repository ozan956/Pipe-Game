//OZAN DURGUT IBRAHIM TINAS 
//150719002   1507190046
//PIPE GAME
//Pipe Game is a game that you replace the blocks to create true path for moving all to start till the end.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	// level holds that user's passed level number. moveCount holds number of moves
	// in each level.
	private int level = 0;private int moveCount = 0;
	private boolean isPassed[]=new boolean[7];
	private int atLevel=1; 
	// animationPath holds the coordinates of Ball animation that happens when level
	// is passed.
	private ArrayList<Integer> animationPath = new ArrayList<Integer>();

	// frameList holds the all Blocks in the scene.
	private ArrayList<Block> frameList = new ArrayList<Block>();

	// All scene's default objects.
	private Circle staticBall = new Circle();
	private Text taskBarText = new Text("NUMBER OF MOVES: " + moveCount);
	
	// Defaults.
	private Scene scene = null;
	private Pane root = null;
	private Stage stage = new Stage();

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		// to open the stage for the first time.
		editText(taskBarText);

		root = new Pane();
		createEpisode(level);
		scene = new Scene(root, 600, 655);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void createEpisode(int level) throws FileNotFoundException {

		int position = 0;
		// clears all frames in the list and scene.
		frameList.removeAll(frameList);
		moveCount = 0;
		root.getChildren().clear();

		// Decides which level going to be created, then creates it.
		File file = null;
		Scanner read = null;
		try {
			switch (level) {

			case 0: {
				file = new File("level1.txt");
				read = new Scanner(file);
				atLevel=1;
				break;
			}
			case 1: {
				file = new File("level2.txt");
				read = new Scanner(file);
				atLevel=2;
				break;
			}
			case 2: {
				file = new File("level3.txt");
				read = new Scanner(file);
				atLevel=3;
				break;
			}
			case 3: {
				file = new File("level4.txt");
				read = new Scanner(file);
				atLevel=4;
				break;
			}
			case 4: {
				file = new File("level5.txt");
				read = new Scanner(file);
				atLevel=5;
				break;
			}
			case 5: {
				file = new File("level6.txt");
				read = new Scanner(file);
				atLevel=6;
				break;
			}
			default: {
				Image img = new Image("foto.jpeg");
				ImageView imgv = new ImageView(img);
				imgv.setFitWidth(600);
				imgv.setFitHeight(655);
				//Closes the project when clicked.
				imgv.setOnMouseClicked(e -> {
					stage.close();
				});

				//Finish scene.
				Text text1 = new Text("----CSE1241 PROJECT #1---- \n- "
						+ "OZAN DURGUT 150719002\n- IBRAHIM TINAS 1507190046\n THANKS FOR PLAYING");
				editText(text1);
				root.getChildren().add(imgv);
				root.getChildren().add(text1);

				return;
			}
			}

			// Reads file and decide their positions by int position value. Every creation,
			// position increases by 1.
			position = 0;
			while (read.hasNextLine()) {
				String data = read.nextLine();
				if (!(data.equals(""))) {
					String[] blockType = data.split(",");
					if (blockType[1].equals("Empty")) {
						if (blockType[2].equals("none")) {
							frameList.add(new Block(position, true, false, "empty.png", "Empty", 0, 0));
						} else if (blockType[2].equalsIgnoreCase("Free")) {
							frameList.add(new Block(position, true, true, "empty_free.png", "EmptyFree", 0, 0));
						}
					} else if (blockType[1].equals("Starter")) {
						if (blockType[2].equals("Vertical")) {
							frameList.add(new Block(position, false, false, "starter.png", "Starter", 1, 3));
						} else {
							frameList.add(new Block(position, false, false, "starter90.png", "Starter90", 2, 4));
						}

					} else if (blockType[1].equals("Pipe")) {
						if (blockType[2].equals("Vertical")) {
							frameList.add(new Block(position, true, false, "pipe_free.png", "PipeFree", 1, 3));
						} else if (blockType[2].equals("Horizontal")) {
							;
							frameList.add(new Block(position, true, false, "pipe_free90.png", "PipeFree90", 2, 4));
						} else if (blockType[2].equals("00")) {
							frameList.add(new Block(position, true, false, "curved0.png", "Curved0", 1, 4));
						} else if (blockType[2].equals("01")) {
							frameList.add(new Block(position, true, false, "curved90.png", "Curved90", 1, 2));
						} else if (blockType[2].equals("10")) {
							frameList.add(new Block(position, true, false, "curved270.png", "Curved270", 4, 3));
						} else if (blockType[2].equals("11")) {
							frameList.add(new Block(position, true, false, "curved180.png", "Curved180", 2, 3));
						}

					} else if (blockType[1].equals("PipeStatic")) {
						if (blockType[2].equals("Horizontal")) {
							frameList.add(new Block(position, false, false, "pipe.png", "PipeStatic", 4, 2));
						} else if (blockType[2].equals("Vertical")) {
							frameList.add(new Block(position, false, false, "pipe90.png", "PipeStatic90", 1, 3));
						} else if (blockType[2].equals("00")) {
							frameList.add(new Block(position, false, false, "pipecurved0.png", "StaticCurved0", 1, 4));
						} else if (blockType[2].equals("01")) {
							frameList
									.add(new Block(position, false, false, "pipecurved90.png", "StaticCurved90", 1, 2));
						} else if (blockType[2].equals("10")) {
							frameList.add(
									new Block(position, false, false, "pipecurved270.png", "StaticCurved270", 4, 3));
						} else if (blockType[2].equals("11")) {
							frameList.add(
									new Block(position, false, false, "pipecurved180.png", "StaticCurved180", 2, 3));
						}

					} else if (blockType[1].equals("End")) {
						if (blockType[2].equals("Vertical")) {
							frameList.add(new Block(position, false, false, "end.png", "End", 1, 3));
						} else {
							frameList.add(new Block(position, false, false, "end90.png", "End90", 2, 4));
						}

					}
					position++;
				}

			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// While creating blocks, program also add that Blocks to the scene, sets its
		// positions by position value.
		int i = 0;
		int j = 0;
		for (Block block : frameList) {
			block.setX(i * 150);
			block.setY(j * 150 + 55);
			root.getChildren().add(block);
			i++;
			if (i == 4) {
				j++;
				i = 0;
			}
			if (block.getType().equals("Starter") || block.getType().equals("Starter90")) {
				Image circ = new Image("circle.png");
				staticBall.setCenterX(block.getX() + 75);
				staticBall.setCenterY(block.getY() + 75);
				staticBall.setRadius(18);
				staticBall.setFill(new ImagePattern(circ));
				root.getChildren().add(staticBall);
			}
		}

		// After creating all blocks and adding them to frameList&scene, finally method
		// add it to the stage.
		Image img = new Image("taskbar.png");
		ImageView imgv = new ImageView(img);
		taskBarText.setText("NUMBER OF MOVES: " + moveCount);
		String title = "Pipe Game - Level: " + (level + 1);
		MainMenuButton mainMenu = new MainMenuButton();

		root.getChildren().add(imgv);
		root.getChildren().add(mainMenu.getButon());
		root.getChildren().add(taskBarText);

		stage.setTitle(title);
	}

	public void episodePassCheck() {

		// Searches for Start Block.
		for (Block block : frameList) {
			if (block.getType().equals("Starter") || block.getType().equals("Starter90")) {
				animationPath.removeAll(animationPath);
				// Add Starter Block's positions to path.
				animationPath.add(block.position);

				// After adding StarterBlock it runs isPathExist Algorithm to check that Start
				// block encounters
				// with End block or not.
				if (isPathExist(block.inPoint, block.outPoint, block.position, 99)) {
					
					
					

					// First deletes the static ball and then creates animatedBall.
					Circle animatedBall = new Circle();
					root.getChildren().remove(staticBall);
					animatedBall.setCenterX(150 * animationPath.get(0) / 4);
					animatedBall.setCenterY(150 * animationPath.get(0) % 4);
					animatedBall.setRadius(18f);
					animatedBall.setFill(new ImagePattern(new Image("circle.png")));
					animatedBall.setStrokeWidth(20);

					// Creates the path and set its start position Starter block.
					Path path = new Path();
					path.getElements().add(new MoveTo(block.getX() + 75, block.getY() + 75));
					// Deletes first coordinates because already added.
					animationPath.remove(0);

					double xpos;
					double ypos;
					// Adds block positions to animation path.
					for (Integer pathId : animationPath) {
						xpos = frameList.get(pathId).getX() + 75;
						ypos = frameList.get(pathId).getY() + 75;

						path.getElements().add(new LineTo(xpos, ypos));

					}

					// Creates path animation, set their settings and then waits till finished.
					PathTransition pathTransition = new PathTransition();
					pathTransition.setDuration(Duration.millis(1000));
					pathTransition.setNode(animatedBall);
					pathTransition.setPath(path);
					pathTransition.setCycleCount(1);
					pathTransition.play();

					root.getChildren().add(animatedBall);

					// When animation is finished, program creates a notification about
					// that user passed this level, and then sets new level.

					pathTransition.setOnFinished(d -> {
						
						
						if((!isPassed[atLevel-1])) {
							isPassed[level]=true;
							level++;
						}

						
						
						Notification not = new Notification();

						not.buton.setOnMouseClicked(arg -> {
							frameList.removeAll(frameList);

							root.getChildren().clear();

							try {
								createEpisode(level);
								not.hide();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
						});
					});
				}

				return;
			}
		}
	}

	public boolean isPathExist(int inPoint, int outPoint, int position, int previous) {

		// isPathExist a recursive algorithm that searches for potential paths that could be
		// go to the end block. Algorithm first starts with Starter Block and then look Starter Blocks,
		// neighbors. It compares two blocks by their in and out positions. When it find a match,
		// it restarts the algorithm for all blocks that has a match. Algorithm stops in two possible
		// situations:
		// - If it finds End block.
		// - If it finds no match.

		int[] neighborId = { position - 4, position - 1, position + 1, position + 4 };

		for (int neighbor : neighborId) {
			if ((neighbor) >= 0 && (neighbor) <= 15) {

				// While checking it reject different row, column situations and former blocks.
				if ((position % 4 == 3 && neighbor % 4 == 0) || (position % 4 == 0 && neighbor % 4 == 3)) {
					continue;
				}
				if (frameList.get(neighbor).getType().equals("End")
						|| frameList.get(neighbor).getType().equals("End90")) {
					animationPath.add(neighbor);
					return true;
				}
				if (frameList.get(neighbor).outPoint == inPoint || frameList.get(neighbor).inPoint == inPoint
						|| frameList.get(neighbor).inPoint == outPoint
						|| frameList.get(neighbor).outPoint == outPoint) {
					if (neighbor == previous) {
						continue;
					}
					if (frameList.get(neighbor).getType().equals("Empty")
							|| frameList.get(neighbor).getType().equals("EmptyFree")) {
						continue;
					} else {
						animationPath.add(frameList.get(neighbor).position);
						return isPathExist(frameList.get(neighbor).inPoint, frameList.get(neighbor).outPoint,
								frameList.get(neighbor).position, position);
					}

				}
			}
		}

		// If algorithm can not find the correct path, it comes here then animation Path
		// array is cleared.
		animationPath.removeAll(animationPath);
		return false;

	}

	public void editText(Text taskBarText) {
		// Set text settings.
		taskBarText.setFont(Font.font("Agency FB", FontWeight.BOLD, FontPosture.REGULAR, 30));
		taskBarText.setFill(Color.WHITESMOKE);
		taskBarText.setStroke(Color.BURLYWOOD);
		taskBarText.setStrokeWidth(0.5);
		taskBarText.setY(40);
		taskBarText.setX(5);
	}

	public class Block extends ImageView {
		private String type = null;
		private int position;
		private boolean moveable, emptyFree;
		private double xStart, yStart, xFinal, yFinal, xInitial, yInital;
		private int inPoint, outPoint;

		public Block(int position, boolean moveable, boolean emptyFree, String fileName, String type, int inPoint,
				int outPoint) throws FileNotFoundException {

			// Sets values and Events of block.
			setPosition(position);
			setMoveable(moveable);
			setEmptyFree(emptyFree);
			setType(type);
			setInPoint(inPoint);
			setOutPoint(outPoint);

			Image img = new Image(fileName);
			this.setImage(img);
			this.setFitWidth(150);
			this.setFitHeight(150);

			this.setOnMouseEntered(e -> {
				scene.setCursor(Cursor.HAND);
			});
			this.setOnMouseExited(e -> {
				scene.setCursor(Cursor.DEFAULT);
			});
			this.setOnMousePressed(e -> {
				xStart = e.getSceneX();
				yStart = e.getSceneY();
				xInitial = this.getX();
				yInital = this.getY();
			});

			this.setOnMouseClicked(e -> {
			});
			this.setOnMouseDragged(e -> {
				scene.setCursor(Cursor.CLOSED_HAND);
				xFinal = e.getSceneX();
				yFinal = e.getSceneY();
				this.setX(e.getSceneX() - 75);
				this.setY(e.getSceneY() - 75);

			});
			this.setOnMouseReleased(e -> {

				// When mouse is released, program compares final and start position of the
				// mouse
				// to decide that movement that it will do.
				if (xFinal == 0 && yFinal == 0) {
					return;
				}

				if ((xFinal - xStart > 70 && xFinal - xStart < 270 && yFinal - yStart < 75 && yFinal - yStart > -75
						&& (position % 4) <= 2)
						&& (this.moveable && frameList.get(this.position + 1).moveable
								&& (this.isEmptyFree() || frameList.get(this.position + 1).isEmptyFree()))) {

					swapBlocks(this, this.position + 1);
				} else if ((xFinal - xStart < -70 && xFinal - xStart > -270 && yFinal - yStart < 75
						&& yFinal - yStart > -75 && (position % 4) >= 1)
						&& ((this.moveable && frameList.get(this.position - 1).moveable)
								&& (this.isEmptyFree() || frameList.get(this.position - 1).isEmptyFree()))) {

					swapBlocks(this, this.position - 1);
				} else if ((yFinal - yStart > 70 && yFinal - yStart < 270 && xFinal - xStart < 75
						&& xFinal - xStart > -75 && (position + 4) <= 15)
						&& ((this.moveable && frameList.get(this.position + 4).moveable)
								&& (this.isEmptyFree() || frameList.get(this.position + 4).isEmptyFree()))) {

					swapBlocks(this, this.position + 4);
				} else if ((yFinal - yStart < -70 && yFinal - yStart > -270 && xFinal - xStart < 75
						&& xFinal - xStart > -75 && (position - 4) >= 0)
						&& (this.moveable && frameList.get(this.position - 4).moveable
								&& (this.isEmptyFree() || frameList.get(this.position - 4).isEmptyFree())))
					swapBlocks(this, this.position - 4);
				else {
				}

				this.setX(xInitial);
				this.setY(yInital);

				// After each movement it checks that level is passed or not.
				episodePassCheck();

			});
			// resets the final values.
			xFinal = 0;
			yFinal = 0;
		}

		public void swapBlocks(Block block, int swapped) {

			// In each movement blocks are changing because of that
			// this method changes it's images, boolean values, types, etc.
			Image imgTemp = block.getImage();
			block.setImage(frameList.get(swapped).getImage());
			frameList.get(swapped).setImage(imgTemp);

			boolean boolTemp = block.isEmptyFree();
			block.setEmptyFree(frameList.get(swapped).isEmptyFree());
			frameList.get(swapped).setEmptyFree(boolTemp);

			String typeTemp = block.getType();
			block.setType((frameList.get(swapped).getType()));
			frameList.get(swapped).setType(typeTemp);

			int outTemp = block.outPoint;
			block.outPoint = ((frameList.get(swapped).outPoint));
			frameList.get(swapped).outPoint = outTemp;

			int inTemp = block.inPoint;
			block.inPoint = ((frameList.get(swapped).inPoint));
			frameList.get(swapped).inPoint = inTemp;

			moveCount++;
			root.getChildren().remove(taskBarText);
			taskBarText.setText("NUMBER OF MOVES: " + moveCount);
			root.getChildren().add(taskBarText);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public boolean isMoveable() {
			return moveable;
		}

		public void setMoveable(boolean moveable) {
			this.moveable = moveable;
		}

		public boolean isEmptyFree() {
			return emptyFree;
		}

		public void setEmptyFree(boolean emptyFree) {
			this.emptyFree = emptyFree;
		}

		public double getxStart() {
			return xStart;
		}

		public void setxStart(double xStart) {
			this.xStart = xStart;
		}

		public double getyStart() {
			return yStart;
		}

		public void setyStart(double yStart) {
			this.yStart = yStart;
		}

		public double getxFinal() {
			return xFinal;
		}

		public void setxFinal(double xFinal) {
			this.xFinal = xFinal;
		}

		public double getyFinal() {
			return yFinal;
		}

		public void setyFinal(double yFinal) {
			this.yFinal = yFinal;
		}

		public double getxInitial() {
			return xInitial;
		}

		public void setxInitial(double xInitial) {
			this.xInitial = xInitial;
		}

		public double getyInital() {
			return yInital;
		}

		public void setyInital(double yInital) {
			this.yInital = yInital;
		}

		public int getInPoint() {
			return inPoint;
		}

		public void setInPoint(int inPoint) {
			this.inPoint = inPoint;
		}

		public int getOutPoint() {
			return outPoint;
		}

		public void setOutPoint(int outPoint) {
			this.outPoint = outPoint;
		}

	}

	public class Notification extends Stage {
		private Button buton = new Button("Press to move to next level");
		private boolean pressed = false;

		// It creates a stage with a button when a level is passed.
		 Notification() {
			// set button settings.
			buton.setCursor(Cursor.HAND);
			buton.setPrefWidth(200);
			buton.setPrefHeight(50);
			buton.setAlignment(Pos.CENTER);

			// creates new scene for notification stage.
			VBox pane = new VBox();
			pane.getChildren().add(buton);
			Scene scene = new Scene(pane);

			// sets stage settings.
			this.initModality(Modality.APPLICATION_MODAL);
			this.setResizable(false);
			this.setTitle("You Passed The Level!");
			this.setOnCloseRequest(e -> e.consume());
			this.setScene(scene);
			this.show();

		}

		public boolean getPressed() {
			return pressed;
		}
	}

	public class MainMenuButton extends Button {
		public int i = 1;public int j = 20;
		private Button buton = new Button("");
		private ArrayList<menubuttons> menubuttons = new ArrayList<menubuttons>();
		
		MainMenuButton() {
			//Button settings and graphics.
			Image img = new Image("home-button.png");
			ImageView imgv = new ImageView(img);
			buton.setPrefSize(40, 40);
			buton.setGraphic(imgv);
			buton.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
			buton.setLayoutX(540);
			buton.setLayoutY(0);
			
			//When pressed, it sets stage as menu.
			buton.setOnAction(e-> getMenu());
		}
		

		
		public void getMenu() {
			
			//Clears frame list and scene.
			frameList.removeAll(frameList);
			root.getChildren().clear();
			
			
			//Background settings.
			ImageView backGround = new ImageView(new Image("board-background.png"));
			backGround.setFitHeight(655);
			backGround.setFitWidth(600);
			root.getChildren().add(backGround);
			
			
			//Adding buttons
			for (int i = 0; i < 6; i++) {
				menubuttons.add(new menubuttons(i));
			}

			//sets images and positions of buttons.
			
			for (menubuttons buton : menubuttons) {

				if (i <= level + 1) {
					buton.setLayoutX(200);
					buton.setLayoutY(70 + j);
					buton.setPrefSize(40, 40);
					buton.setGraphic(new ImageView(new Image("level" + i + "-pack-unlocked.png")));
					buton.setCursor(Cursor.HAND);
					buton.setOnAction(R -> {
						try {
							createEpisode(buton.getSira());
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					});

				} else {

					buton.setPrefSize(40, 40);
					buton.setLayoutX(180);
					buton.setLayoutY(70 + j);
					buton.setGraphic(new ImageView(new Image("level" + i + "-pack-locked.png")));
					buton.setCursor(Cursor.DEFAULT);

				}
				buton.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");

				root.getChildren().add(buton);
				i++;
				j += 80;
			}

			scene.setRoot(root);

		}
		
		
		public Button getButon() {
			return buton;
		}

		public void setButon(Button buton) {
			this.buton = buton;
		}
		
		
		public class menubuttons extends Button {
			private int sira;

			menubuttons(int sira) {
				this.sira = sira;
			}

			public int getSira() {
				return sira;
			}

			public void setSira(int sira) {
				this.sira = sira;
			}

		}
		
	}

}

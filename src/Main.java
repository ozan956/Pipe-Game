//OZAN DURGUT IBRAHIM TINAS 
//150719002   1507190046
//PIPE GAME
//Pipe Game is a game that you replace the blocks to create true path for moving all to start till the end.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
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
	private static final int BOARD_SIZE = 4;
	private static final int TILE_SIZE = 150;
	private static final int TASKBAR_HEIGHT = 55;
	private static final int BOARD_BLOCK_COUNT = BOARD_SIZE * BOARD_SIZE;

	private int unlockedLevel = 0;
	private int currentLevel = 0;
	private int moveCount = 0;
	private boolean[] isPassed = new boolean[0];

	// animationPath holds the coordinates of Ball animation that happens when level
	// is passed.
	private ArrayList<Integer> animationPath = new ArrayList<Integer>();

	// frameList holds the all Blocks in the scene.
	private ArrayList<Block> frameList = new ArrayList<Block>();
	private ArrayList<File> levelFiles = new ArrayList<File>();

	// All scene's default objects.
	private Circle staticBall = new Circle();
	private Text taskBarText = new Text("NUMBER OF MOVES: " + moveCount);
	
	// Defaults.
	private Scene scene = null;
	private Pane root = null;
	private Stage stage = null;

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		// to open the stage for the first time.
		editText(taskBarText);

		stage = primaryStage;
		root = new Pane();
		loadLevelFiles();
		createEpisode(currentLevel);
		scene = new Scene(root, 600, 655);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void createEpisode(int levelIndex) throws FileNotFoundException {

		int position = 0;
		String levelName = "";
		// clears all frames in the list and scene.
		frameList.clear();
		moveCount = 0;
		root.getChildren().clear();
		currentLevel = levelIndex;

		// Decides which level going to be created, then creates it.
		try {
			if (levelFiles.isEmpty()) {
				showNoLevelsScene();
				return;
			}
			if (levelIndex < 0 || levelIndex >= levelFiles.size()) {
				showFinishScene();
				return;
			}

			File file = levelFiles.get(levelIndex);
			levelName = file.getName();

			// Reads file and decide their positions by int position value. Every creation,
			// position increases by 1.
			try (Scanner read = new Scanner(file)) {
				while (read.hasNextLine()) {
					String data = read.nextLine();
					if (!(data.equals(""))) {
						String[] blockType = data.split(",");
						Block block = createBlockFromDefinition(position, blockType[1].trim(), blockType[2].trim());
						if (block != null) {
							frameList.add(block);
						}
						position++;
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// While creating blocks, program also add that Blocks to the scene, sets its
		// positions by position value.
		int i = 0;
		int j = 0;
		for (Block block : frameList) {
			block.setX(i * TILE_SIZE);
			block.setY(j * TILE_SIZE + TASKBAR_HEIGHT);
			root.getChildren().add(block);
			i++;
			if (i == BOARD_SIZE) {
				j++;
				i = 0;
			}
			if (block.getType().equals("Starter") || block.getType().equals("Starter90")) {
				Image circ = loadImage("circle.png");
				staticBall.setCenterX(block.getX() + 75);
				staticBall.setCenterY(block.getY() + 75);
				staticBall.setRadius(18);
				staticBall.setFill(new ImagePattern(circ));
				root.getChildren().add(staticBall);
			}
		}

		// After creating all blocks and adding them to frameList&scene, finally method
		// add it to the stage.
		Image img = loadImage("taskbar.png");
		ImageView imgv = new ImageView(img);
		taskBarText.setText("NUMBER OF MOVES: " + moveCount);
		String title = "Pipe Game - Level: " + (levelIndex + 1) + " - " + levelName;
		MainMenuButton mainMenu = new MainMenuButton();

		root.getChildren().add(imgv);
		root.getChildren().add(mainMenu.getButon());
		root.getChildren().add(taskBarText);

		stage.setTitle(title);
	}

	private void loadLevelFiles() {
		levelFiles.clear();

		File[] txtFiles = new File(".").listFiles((directory, name) -> name.toLowerCase().endsWith(".txt"));
		if (txtFiles == null) {
			isPassed = new boolean[0];
			return;
		}

		for (File file : txtFiles) {
			if (isLevelFile(file)) {
				levelFiles.add(file);
			}
		}

		levelFiles.sort(Comparator.comparingInt(this::levelOrder).thenComparing(File::getName));
		isPassed = new boolean[levelFiles.size()];
	}

	private boolean isLevelFile(File file) {
		int nonEmptyLines = 0;

		try (Scanner read = new Scanner(file)) {
			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.equals("")) {
					continue;
				}

				String[] blockType = line.split(",");
				if (blockType.length < 3 || !isKnownBlockDefinition(blockType[1].trim(), blockType[2].trim())) {
					return false;
				}
				nonEmptyLines++;
			}
		} catch (FileNotFoundException e) {
			return false;
		}

		return nonEmptyLines == BOARD_BLOCK_COUNT;
	}

	private boolean isKnownBlockDefinition(String blockName, String direction) {
		if (blockName.equals("Empty")) {
			return direction.equals("none") || direction.equalsIgnoreCase("Free");
		} else if (blockName.equals("Starter") || blockName.equals("End")) {
			return direction.equals("Vertical") || direction.equals("Horizontal");
		} else if (blockName.equals("Pipe")) {
			return direction.equals("Vertical") || direction.equals("Horizontal") || direction.equals("00")
					|| direction.equals("01") || direction.equals("10") || direction.equals("11");
		} else if (blockName.equals("PipeStatic")) {
			return direction.equals("Vertical") || direction.equals("Horizontal") || direction.equals("00")
					|| direction.equals("01") || direction.equals("10") || direction.equals("11");
		}
		return false;
	}

	private int levelOrder(File file) {
		String name = file.getName();
		String number = "";

		for (int i = 0; i < name.length(); i++) {
			char character = name.charAt(i);
			if (Character.isDigit(character)) {
				number += character;
			}
		}

		if (number.equals("")) {
			return Integer.MAX_VALUE;
		}
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}

	private Block createBlockFromDefinition(int position, String blockName, String direction)
			throws FileNotFoundException {
		if (blockName.equals("Empty")) {
			if (direction.equals("none")) {
				return new Block(position, true, false, "empty.png", "Empty", 0, 0);
			} else if (direction.equalsIgnoreCase("Free")) {
				return new Block(position, true, true, "empty_free.png", "EmptyFree", 0, 0);
			}
		} else if (blockName.equals("Starter")) {
			if (direction.equals("Vertical")) {
				return new Block(position, false, false, "starter.png", "Starter", 1, 3);
			}
			return new Block(position, false, false, "starter90.png", "Starter90", 2, 4);
		} else if (blockName.equals("Pipe")) {
			if (direction.equals("Vertical")) {
				return new Block(position, true, false, "pipe_free.png", "PipeFree", 1, 3);
			} else if (direction.equals("Horizontal")) {
				return new Block(position, true, false, "pipe_free90.png", "PipeFree90", 2, 4);
			} else if (direction.equals("00")) {
				return new Block(position, true, false, "curved0.png", "Curved0", 1, 4);
			} else if (direction.equals("01")) {
				return new Block(position, true, false, "curved90.png", "Curved90", 1, 2);
			} else if (direction.equals("10")) {
				return new Block(position, true, false, "curved270.png", "Curved270", 4, 3);
			} else if (direction.equals("11")) {
				return new Block(position, true, false, "curved180.png", "Curved180", 2, 3);
			}
		} else if (blockName.equals("PipeStatic")) {
			if (direction.equals("Horizontal")) {
				return new Block(position, false, false, "pipe.png", "PipeStatic", 4, 2);
			} else if (direction.equals("Vertical")) {
				return new Block(position, false, false, "pipe90.png", "PipeStatic90", 1, 3);
			} else if (direction.equals("00")) {
				return new Block(position, false, false, "pipecurved0.png", "StaticCurved0", 1, 4);
			} else if (direction.equals("01")) {
				return new Block(position, false, false, "pipecurved90.png", "StaticCurved90", 1, 2);
			} else if (direction.equals("10")) {
				return new Block(position, false, false, "pipecurved270.png", "StaticCurved270", 4, 3);
			} else if (direction.equals("11")) {
				return new Block(position, false, false, "pipecurved180.png", "StaticCurved180", 2, 3);
			}
		} else if (blockName.equals("End")) {
			if (direction.equals("Vertical")) {
				return new Block(position, false, false, "end.png", "End", 1, 3);
			}
			return new Block(position, false, false, "end90.png", "End90", 2, 4);
		}
		return null;
	}

	private Image loadImage(String fileName) {
		InputStream resource = getClass().getResourceAsStream("/" + fileName);
		if (resource != null) {
			return new Image(resource);
		}
		return new Image(new File("src/" + fileName).toURI().toString());
	}

	private void showNoLevelsScene() {
		Image img = loadImage("board-background.png");
		ImageView imgv = new ImageView(img);
		imgv.setFitWidth(600);
		imgv.setFitHeight(655);

		Text text1 = new Text("No level files found.\nAdd valid .txt level files to the project root.");
		editText(text1);
		root.getChildren().add(imgv);
		root.getChildren().add(text1);
	}

	private void showFinishScene() {
		Image img = loadImage("board-background.png");
		ImageView imgv = new ImageView(img);
		imgv.setFitWidth(600);
		imgv.setFitHeight(655);
		imgv.setOnMouseClicked(e -> stage.close());

		Text text1 = new Text("----CSE1241 PROJECT #1---- \n- "
				+ "OZAN DURGUT 150719002\n- IBRAHIM TINAS 1507190046\n THANKS FOR PLAYING");
		editText(text1);
		root.getChildren().add(imgv);
		root.getChildren().add(text1);
	}

	public void episodePassCheck() {

		// Searches for Start Block.
		for (Block block : frameList) {
			if (block.getType().equals("Starter") || block.getType().equals("Starter90")) {
				animationPath.clear();
				// Add Starter Block's positions to path.
				animationPath.add(block.position);

				// After adding StarterBlock it runs isPathExist Algorithm to check that Start
				// block encounters
				// with End block or not.
				if (isPathExist(block.inPoint, block.outPoint, block.position, 99)) {
					// First deletes the static ball and then creates animatedBall.
					Circle animatedBall = new Circle();
					root.getChildren().remove(staticBall);
					animatedBall.setCenterX(block.getX() + 75);
					animatedBall.setCenterY(block.getY() + 75);
					animatedBall.setRadius(18f);
					animatedBall.setFill(new ImagePattern(loadImage("circle.png")));
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
						if (!isPassed[currentLevel]) {
							isPassed[currentLevel] = true;
							unlockedLevel = Math.max(unlockedLevel, Math.min(currentLevel + 1, levelFiles.size() - 1));
						}

						Notification not = new Notification();

						not.buton.setOnMouseClicked(arg -> {
							frameList.clear();

							root.getChildren().clear();

							try {
								createEpisode(currentLevel + 1);
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
		animationPath.clear();
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

			Image img = loadImage(fileName);
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
		private Button buton = new Button("");
		private ArrayList<menubuttons> menubuttons = new ArrayList<menubuttons>();
		
		MainMenuButton() {
			//Button settings and graphics.
			Image img = loadImage("home-button.png");
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
			frameList.clear();
			menubuttons.clear();
			root.getChildren().clear();
			
			
			//Background settings.
			ImageView backGround = new ImageView(loadImage("board-background.png"));
			backGround.setFitHeight(655);
			backGround.setFitWidth(600);
			root.getChildren().add(backGround);
			
			
			//Adding buttons.
			for (int levelIndex = 0; levelIndex < levelFiles.size(); levelIndex++) {
				menubuttons.add(new menubuttons(levelIndex));
			}

			//sets images and positions of buttons.
			int levelNumber = 1;
			for (menubuttons buton : menubuttons) {
				int row = (levelNumber - 1) / 3;
				int column = (levelNumber - 1) % 3;
				int x = 35 + column * 185;
				int y = 90 + row * 60;

				if (buton.getSira() <= unlockedLevel) {
					buton.setLayoutX(x);
					buton.setLayoutY(y);
					buton.setPrefSize(160, 45);
					buton.setText("Level " + levelNumber);
					buton.setCursor(Cursor.HAND);
					buton.setOnAction(R -> {
						try {
							createEpisode(buton.getSira());
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					});

				} else {

					buton.setPrefSize(160, 45);
					buton.setLayoutX(x);
					buton.setLayoutY(y);
					buton.setText("Level " + levelNumber + " - Locked");
					buton.setCursor(Cursor.DEFAULT);
					buton.setDisable(true);

				}

				root.getChildren().add(buton);
				levelNumber++;
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

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.Timer;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.effect.Glow; 
import javafx.scene.text.Text;
import javafx.scene.effect.Bloom;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.DoubleProperty;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Vue extends Application {
  
  protected Controller controler;
  GridPane contentPane;
  Stage primaryStage;
  public static Grid map;
  public static Interpreter1 interp;
  public static Program prog;
  String fichier;
  String[] args = new String[2];

 private Desktop desktop = Desktop.getDesktop();
 

  public static void main(String[] args) throws Exception{
  	 	String exeName = "Vue";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);
        Lexer lexer = new Lexer(ioEnv.inProgram);
        LookAhead1 look = new LookAhead1(lexer);
        Parser parser = new Parser1(look);

        try {
            prog = parser.parseProgram(exeName, ioEnv.inProgram);
            System.out.println(" programme accepte !");
            map = Grid.parseGrid(exeName, ioEnv.inGrid);
            System.out.println(" grid ok!");
            interp = new Interpreter1();
            //interp.run(prog, map);
            ioEnv.outGrid.println(map);

        }
        catch (Exception e)
        {
            System.out.println(" programme non accepté !");
            e.printStackTrace();

        }
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

  	  	primaryStage=stage;
  	  	AnchorPane main = new AnchorPane();
  	  	main.setId("main");


		Button submit = new Button("       COMMENCER       ");
		submit.setId("submit");



		final Label label2 = new Label("GRILLE ET PROGRAMME OK!");
		label2.setId("label2");
		final Label label3 = new Label("YANIS TOUAHRI");
		label3.setId("label3");
		final Label label4 = new Label("AHMED AIT ALLALA");
		label4.setId("label4");

		//Setting an action for the Submit button
		submit.setOnAction(new EventHandler<ActionEvent>() {
		@Override
   		public void handle(ActionEvent e) {
              changeStage();
          }
 		});
    
		
		AnchorPane.setTopAnchor(label3, 20.0);
		AnchorPane.setLeftAnchor(label3, 50.0);
		AnchorPane.setTopAnchor(label4, 40.0);
		AnchorPane.setLeftAnchor(label4, 50.0);
		AnchorPane.setTopAnchor(label2, 100.0);
		AnchorPane.setLeftAnchor(label2, 50.0);
    AnchorPane.setBottomAnchor(submit, 60.0);
    AnchorPane.setLeftAnchor(submit, 35.0);
  		main.getChildren().addAll(label2,label3,label4, submit);
		Scene scene = new Scene(main,300,250);
		String css = getClass().getResource("theme1.css").toExternalForm();
		scene.getStylesheets().addAll(css);
		
		
  		primaryStage.setResizable(false);
  		primaryStage.setScene(scene);
   		primaryStage.show();
  }


  	public void changeStage() {
      
  		AnchorPane root = new AnchorPane();
  		root.setId("root");
    	primaryStage.setTitle("HASHIWOKAKERO TEAM4");
    	contentPane = new GridPane();
   		controler = new Controller(this);
		final Tooltip tooltip = new Tooltip("COCHEZ SI VOUS VOULEZ PLACER UN PONT DOUBLE");
        tooltip.setFont(new Font("Arial", 16));
      

        Button exit = new Button("EXIT");
        exit.setPrefWidth(140);
        Button hint = new Button("INSTRUCTION");
        hint.setPrefWidth(140);
        exit.setId("exit");
        hint.setId("hint");
        Label instru = new Label("instruction");
        instru.setId("instru");
        hint.setOnAction(new EventHandler<ActionEvent>() {

    @Override
        public void handle(ActionEvent e) {
          controler.pressed();
          instru.setText(prog.getInstruction().action+ ": "+interp.indicator);
      }
    });
        
    

       exit.setOnAction(new EventHandler<ActionEvent>() {

    @Override
        public void handle(ActionEvent e) {
          System.exit(0);
      }
    });
        //final Label labell = new Label("Les règles sont simples.\nHashi se joue sur une grille\n rectangulaire \n. Certaines cellules\n contiennent un nombre compris\n entre 1 et 8 (inclus) ;\n ce sont des îles. Les\n autres cellules sont vides.\n Le but est de connecter toutes \nles îles en un seul groupe\n interconnecté en dessinant\n des ponts entre les îles.\n Les ponts doivent suivre \ncertains critères : Ils doivent \ncommencer et finir sur des îles distinctes,\n selon une ligne droite ; Il\n ne doivent pas croiser d'autres ponts ou îles ; Ils ne sont que verticaux ou horizontaux ; Il y a au plus deux ponts entre deux îles ; et Le nombre de pont partant de chaque île doit correspondre au nombre indiqué sur l'île. Cliquez avec le bouton gauche sur une île et glissez vers une autre île. Relâchez le bouton de la souris lorsque les deux îles sont en surbrillance.");
        //labell.setMaxWidth(180);
		AnchorPane.setTopAnchor(contentPane, 0.0);
       	AnchorPane.setRightAnchor(exit, 30.0);
       	AnchorPane.setTopAnchor(exit, 100.0);
       	AnchorPane.setRightAnchor(hint, 30.0);
       	AnchorPane.setTopAnchor(hint, 160.0);
        AnchorPane.setRightAnchor(instru, 50.0);
        AnchorPane.setTopAnchor(instru, 220.0);
		root.getChildren().addAll( contentPane, exit, hint,instru);
		Scene scene = new Scene(root,800,600);
		//scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
    	

   		




       String css = "";
       css = getClass().getResource("theme1.css").toExternalForm();
      
		scene.getStylesheets().addAll(css);
    	primaryStage.setScene(scene);
    	primaryStage.show();

      }
  	
        private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                Vue.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }

}
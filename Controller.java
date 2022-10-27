import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
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
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable{
	protected Vue vue;
	protected int posx;
	protected int posy;

	public Controller(Vue nVue){
		vue=nVue;
		posx=vue.map.getPosX();
		posy=vue.map.getPosY();
		
		for(int i=0; i<vue.map.getSizeX(); i++) {
			for(int j=0; j <vue.map.getSizeY(); j++) {
       		this.placeImage(i,j);
			}
		} 
		runInterface();
	}

	public void initialize(URL location, ResourceBundle rs){

	}

	public void pressed(){
		if(vue.prog.getProgram()!=null) {
		 	vue.prog=vue.prog.getProgram();
		 	vue.interp.run2(vue.prog,vue.map);
        	updateInterface();
     	} else {
     		placeImage(posx,posy);
     	}
	}

	public void runInterface() {
        vue.interp.run2(vue.prog,vue.map);
        updateInterface();
	}

	public void placeImage(int x, int y) {
		String s = vue.map.grid[x][y]+".jpg";
		ImageView image =new ImageView(s);
        Label label1 = new Label(); 
        int n = 0;
        if(vue.map.getSizeX() > vue.map.getSizeY()) {
        	n = 600/vue.map.getSizeX();
        } else {
        	n = 600/vue.map.getSizeY();
        }
        image.setFitWidth(n);
        image.setFitHeight(n);
        label1.setGraphic(image);
        vue.contentPane.add(label1,x,y);
	}
	public void updateInterface() {
   		for(int i=0; i <vue.map.getSizeX(); i++) {
			for(int j=0; j <vue.map.getSizeY(); j++) {
				if(i==vue.map.getPosX() && j==vue.map.getPosY()) {
					placeImage(posx,posy);
       				ImageView image =new ImageView("robot2.jpg");
        			Label label1 = new Label(); 
       				image.setFitWidth(600/vue.map.getSizeX());
        			image.setFitHeight(300/vue.map.getSizeY());
        			label1.setGraphic(image);
        			vue.contentPane.add(label1,i,j);
        			posx=vue.map.getPosX();
        			posy=vue.map.getPosY();
        		}
				
		} 
     }

    }
    }
  
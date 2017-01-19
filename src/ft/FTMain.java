//@Authors : Juan Cantero Jarilla - Manuel Lorenzo Cobacho
//@DATE: 19/01/2017

package ft;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import ft.controllers.FTMainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FTMain extends Application{
	//Variables necesarias:
	public static Stage stage;
	public static AnchorPane rootLayout;
	//public static String path="C:\\Users\\ManU\\Desktop\\FtallersPrevioInformacion\\Usuarios";
	//public static String path="C:\\Users\\Juan\\Desktop\\Usuarios";
	public static String path="//Serverapp/Equip_Ocupacional/Programa_Memories/GentGran/Usuarios";
	FTMain main;
	BufferedImage im;
	//metodo de inicio de la clase:
	@Override
	public void start(Stage primaryStage) {
		stage=primaryStage;
		stage.setTitle("Memories");
		main=this;		
		initVistaInicial();
	}
	//Carga de la vista inicial:
	public void initVistaInicial() {
		try{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(FTMain.class.getResource("vista/VistaInicial.fxml"));
			Parent rootLayout=(AnchorPane)loader.load();
			
			FTMainController control=loader.getController();
			control.setMain(this);	
			
			Scene scene=new Scene(rootLayout);
			//eventos de teclado para los botones:
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
	            	try{
	            		   switch (event.getCode()) {
		                    case UP:   
		                    	FTMainController.id++;
		                    	FTMainController.idBack();
		                    	checkFocus(FTMainController.botonesIniciales[FTMainController.id]);    
		                    	break;
		                    case DOWN: 		                    	
		                    	FTMainController.id--;
		                    	FTMainController.idBack();
		                    	checkFocus(FTMainController.botonesIniciales[FTMainController.id]);    
		                    	break;
		                    case LEFT:
		                    	FTMainController.id--;
		                    	FTMainController.idBack();
		                    	checkFocus(FTMainController.botonesIniciales[FTMainController.id]);                	
		                    	break;
		                    case RIGHT:
		                    	FTMainController.id++;
		                    	FTMainController.idBack();
		                    	checkFocus(FTMainController.botonesIniciales[FTMainController.id]);    
		                    	break;
		                    case ENTER: 
		                    	FTMainController.botonesIniciales[FTMainController.id].fire();
		                    	scene.setOnKeyPressed(null);
		                    	break;
						default:
							break;
		                }
	            	}catch(IndexOutOfBoundsException e){
	            		FTMainController.idBack();
	            	} catch (MalformedURLException e) {
						e.printStackTrace();
					}
	             
	            }
	        });
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.show();
		}catch(IOException e){
			e.printStackTrace();
		}		
	}
	//Comprobacion de que boton recibe el foco para aplicarle efecto:
	public void checkFocus(Button bt) throws MalformedURLException{
		
		DropShadow	sh = new DropShadow();
	    sh.setRadius(5.0);
	    sh.setOffsetX(4.0);
	    sh.setOffsetY(4.0);
	    sh.setColor(Color.web("#d80f0f", 0.7));
	    DropShadow sh1 = new DropShadow();
	    sh1.setRadius(8.0);
	    sh1.setOffsetX(8.0);
	    sh1.setOffsetY(8.0);
	    sh1.setColor(Color.web("#00e600", 0.7));
	    
		if (bt.getId().equals("usuario")){
			URL urlAd= FTMain.class.getResource("/usersBig.png");
			Image imageAd = new Image(urlAd.toString());
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}
		else if(bt.getId().equals("admin")){
			URL urlAd= FTMain.class.getResource("/admin0Big.png");
			Image imageAd = new Image(urlAd.toString());
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}
		else if(bt.getId().equals("exit")){
			URL urlAd= FTMain.class.getResource("/exit-icon-26Big.png");
			Image imageAd = new Image(urlAd.toString());		
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}
		if(!bt.getId().equals("usuario")){
			URL urlAd= FTMain.class.getResource("/users.png");
			Image imageAd = new Image(urlAd.toString());
			FTMainController.botonesIniciales[0].setGraphic(new ImageView(imageAd));	
			FTMainController.botonesIniciales[0].setEffect(sh);
		}
		if(!bt.getId().equals("admin")){
			URL urlAd= FTMain.class.getResource("/admin0.png");
			Image imageAd = new Image(urlAd.toString());
			FTMainController.botonesIniciales[1].setGraphic(new ImageView(imageAd));	
			FTMainController.botonesIniciales[1].setEffect(sh);
		}
		if(!bt.getId().equals("exit")){
			URL urlAd= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd = new Image(urlAd.toString());
			FTMainController.botonesIniciales[2].setGraphic(new ImageView(imageAd));	
			FTMainController.botonesIniciales[2].setEffect(sh);
		}
	}
	//main del programa:
	public static void main(String[] args) {
		launch(args);
	}
}
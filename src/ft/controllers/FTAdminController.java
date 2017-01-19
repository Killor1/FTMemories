package ft.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import ft.FTMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class FTAdminController {
	//Elementos escena AdminPass:
		@FXML private Button ok;
		@FXML private Button back;
		@FXML private Label user;
		@FXML private Label pass;
		@FXML private TextField userName;
		@FXML private PasswordField userPass;
		@FXML private Button exit;
		
		private FTMain  main;		
		public static Button[] botonesAdmin=new Button[2];
		private DropShadow sh;
		private DropShadow sh1;		
		public  static int id=0;
		
		//inicializacion de la vista:
		@FXML
		private void initialize() throws MalformedURLException{			
			botonesAdmin[0]=back;
			botonesAdmin[1]=exit;
			
			sh = new DropShadow();
		    sh.setRadius(5.0);
		    sh.setOffsetX(4.0);
		    sh.setOffsetY(4.0);
		    sh.setColor(Color.web("#d80f0f", 0.7));
		    
		    sh1 = new DropShadow();
		    sh1.setRadius(8.0);
		    sh1.setOffsetX(8.0);
		    sh1.setOffsetY(8.0);
		    sh1.setColor(Color.web("#00e600", 0.7));		    
		    
			URL urlAd2= FTMain.class.getResource("/back.png");
			Image imageAd2 = new Image(urlAd2.toString());
		    back.setGraphic(new ImageView(imageAd2));
		    back.setBackground(null);
		    back.setEffect(sh);
		    back.setId("back");
		    
		    URL urlAd1= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd1 = new Image(urlAd1.toString());		
			exit.setGraphic(new ImageView(imageAd1));
			exit.setBackground(null);
			exit.setEffect(sh);
			exit.setId("exit");
		}
		//llamada del metodo para volver a la pantalla anterior:
		public void back1() throws Exception{
			main.initVistaInicial();
		}		
		//Comprobacion de las claves, usuario y password:
		@SuppressWarnings("static-access")
		public void checkAdminPass(){
			//si son correctas cargamos la siguiente vista, sino, hacemos que se limpie elcampo que falla:
			if(userName.getText().equalsIgnoreCase("memories") && userPass.getText().equalsIgnoreCase("memories")){				
				Scene scene;
				AnchorPane root;
				try{
					FXMLLoader loader= new FXMLLoader();
					loader.setLocation(FTMain.class.getResource("vista/ListaAdmin.fxml"));
					root=(AnchorPane)loader.load();
					
					FTListaAdminController control=loader.getController();
					control.setMain(this.main);
					scene=new Scene(root);
					//listener para los eventos de teclado:
					scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			            @Override
			            public void handle(KeyEvent event) {
			            	try{
			            		   switch (event.getCode()) {				                   
				                    case LEFT:
				                    	FTListaAdminController.id--;
				                    	FTListaAdminController.idBack();
				                    	checkFocus2(FTListaAdminController.userListButtons[FTListaAdminController.id]);
				                    	break;
				                    case RIGHT:
				                    	FTListaAdminController.id++;
				                    	FTListaAdminController.idBack();
				                    	checkFocus2(FTListaAdminController.userListButtons[FTListaAdminController.id]);
				                    	break;
				                    case ENTER:
				                    	FTListaAdminController.userListButtons[FTListaAdminController.id].fire();
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
					main.stage.setScene(scene);
					main.stage.setFullScreen(true);					
				}catch(IOException e){
					e.printStackTrace();
				}
			}else{
				if(!userName.getText().equalsIgnoreCase("memories")){
					userName.setText(null);
				}else if(!userPass.getText().equalsIgnoreCase("memories")){
					userPass.setText(null);
				}
			}
		}
		//metodo para comprobar el foco sobre los botones para aplicarle los efectos:
		public static void checkFocus2(Button bt) throws MalformedURLException{
			
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
			
		    if(bt.getId().equals("back")){
		    	URL urlAd2= FTMain.class.getResource("/backBig.png");
				Image imageAd2 = new Image(urlAd2.toString());
				bt.setGraphic(new ImageView(imageAd2));
				bt.setEffect(sh1);
			}
		    else if (bt.getId().equals("exit")){
		    	URL urlAd1= FTMain.class.getResource("/exit-icon-26Big.png");
				Image imageAd1 = new Image(urlAd1.toString());
				bt.setGraphic(new ImageView(imageAd1));
				bt.setEffect(sh1);
			}		
			
			if(!bt.getId().equals("back")){
				URL urlAd1= FTMain.class.getResource("/back.png");
				Image imageAd1 = new Image(urlAd1.toString());
				FTListaAdminController.userListButtons[0].setGraphic(new ImageView(imageAd1));
				FTListaAdminController.userListButtons[0].setEffect(sh);
			}
			if(!bt.getId().equals("exit")){
				URL urlAd1= FTMain.class.getResource("/exit-icon-26.png");
				Image imageAd1 = new Image(urlAd1.toString());
				FTListaAdminController.userListButtons[1].setGraphic(new ImageView(imageAd1));
				FTListaAdminController.userListButtons[1].setEffect(sh);
			}
		}
		
		//metodo para cerrar el programa:
		public void exit(){
			System.exit(1);
		}
		
		//metodos para aplicar sobre los botones para los eventos de raton: 
		public void exitOn() throws MalformedURLException{
			URL urlAd1= FTMain.class.getResource("/exit-icon-26Big.png");
			Image imageAd1 = new Image(urlAd1.toString());
			exit.setGraphic(new ImageView(imageAd1));
			exit.setEffect(sh1);
		}
		
		public void exitOff() throws MalformedURLException{
			URL urlAd1= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd1 = new Image(urlAd1.toString());		
			exit.setGraphic(new ImageView(imageAd1));
			exit.setEffect(sh);
		}
		
		public void backOn() throws MalformedURLException{
			URL urlAd1= FTMain.class.getResource("/backBig.png");
			Image imageAd1 = new Image(urlAd1.toString());
			back.setGraphic(new ImageView(imageAd1));
			back.setEffect(sh1);
		}
		
		public void backOff() throws MalformedURLException{
			URL urlAd1= FTMain.class.getResource("/back.png");
			Image imageAd1 = new Image(urlAd1.toString());		
			back.setGraphic(new ImageView(imageAd1));
			back.setEffect(sh);
		}
		//metodo para volver a la vista anterior:
		public void initVistaInicial() {
			main.initVistaInicial();
		}
		
		public static void idBack(){
			if(id<0){
				id=botonesAdmin.length-1;
			}
			if(id>botonesAdmin.length-1){
				id=0;
			}
		}
		//setters y getters:
		public void setMain(FTMain main){
			this.main=main;
		}
		
		public FTMain getMain(){
			return this.main;
		}
}
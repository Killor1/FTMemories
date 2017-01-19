package ft.controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import ft.FTMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

public class FTMainController {
	
	//Elementos primera escena:
	@FXML Button userChoice;
	@FXML Button adminChoice;
	@FXML Button exit;	
	
	public  static int id=0;
	public static Button[] botonesIniciales=new Button[3];
	private FTMain main;
	private DropShadow sh;
	private DropShadow sh1;
	//inicializacion de la vista inicial:
	@FXML
	private void initialize() throws MalformedURLException{
		botonesIniciales[0]=userChoice;
		botonesIniciales[1]=adminChoice;
		botonesIniciales[2]=exit;
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
		
		    URL urlUs= FTMainController.class.getResource("/users.png");
			Image imageUs = new Image(urlUs.toString());
		userChoice.setGraphic(new ImageView(imageUs));
		userChoice.setBackground(null);
		userChoice.setEffect(sh);
		userChoice.setId("usuario");
		URL urlAd= FTMainController.class.getResource("/admin0.png");
		Image imageAd = new Image(urlAd.toString());		
		adminChoice.setGraphic(new ImageView(imageAd));
		adminChoice.setBackground(null);
		adminChoice.setEffect(sh);
		adminChoice.setId("admin");
		URL urlEx= FTMainController.class.getResource("/exit-icon-26.png");
		Image imageEx = new Image(urlEx.toString());		
		exit.setGraphic(new ImageView(imageEx));
		exit.setBackground(null);
		exit.setEffect(sh);
		exit.setId("exit");
	}
	//metodo para cargar la vista de administrador:
	@SuppressWarnings("static-access")
	public void callAdminPass(){
		Scene scene;		
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(FTMain.class.getResource("vista/AdminView.fxml"));
			Parent root=(AnchorPane)loader.load();
			
			FTAdminController control=loader.getController();
			control.setMain(this.main);			
			scene=new Scene(root);
			//eventos de teclado sobre los botones en la vista de administrador:
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
	            	try{
	            		   switch (event.getCode()) {
		                    case UP:   
		                    	FTAdminController.id++;
		                    	FTAdminController.idBack();
		                    	FTMainController.checkFocus(FTAdminController.botonesAdmin[FTAdminController.id]);       
		                    	break;
		                    case DOWN: 		                    	
		                    	FTAdminController.id--;
		                    	FTAdminController.idBack();
		                    	FTMainController.checkFocus(FTAdminController.botonesAdmin[FTAdminController.id]);      
		                    	break;
		                    case LEFT:
		                    	FTAdminController.id--;
		                    	FTAdminController.idBack();
		                    	FTMainController.checkFocus(FTAdminController.botonesAdmin[FTAdminController.id]);                   	
		                    	break;
		                    case RIGHT:
		                    	FTAdminController.id++;
		                    	FTAdminController.idBack();
		                    	FTMainController.checkFocus(FTAdminController.botonesAdmin[FTAdminController.id]);    
		                    	break;
		                    case ENTER: 
		                    	FTAdminController.botonesAdmin[FTAdminController.id].fire();
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
	}
	//metodo para cargar la vista de eleccion de usuarios:
	@SuppressWarnings("static-access")
	public void callUserList() throws IOException{
		Scene scene;
		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(FTMain.class.getResource("vista/ListaUser.fxml"));
		Parent root=(AnchorPane)loader.load();
		
		FTListaUsersController control=loader.getController();
		control.setMain(this.main);		
		scene=new Scene(root);
		//eventos de teclado sobre la vista de lista de usuarios: 
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	try{
            		   switch (event.getCode()) {	                   
	                    case LEFT:
	                    	FTListaUsersController.id--;
	                    	FTListaUsersController.idBack();
	                    	checkFocus2(FTListaUsersController.userListButtons[FTListaUsersController.id]);
	                    	break;
	                    case RIGHT:
	                    	FTListaUsersController.id++;
	                    	FTListaUsersController.idBack();
	                    	checkFocus2(FTListaUsersController.userListButtons[FTListaUsersController.id]);
	                    	break;
	                    case ENTER:
	                    	FTListaUsersController.userListButtons[FTListaUsersController.id].fire();
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
	}
	
	//setter:
	public void setMain(FTMain main){
		this.main=main;
	}	
	//metodos de control de los efectos de raton sobre los botones:
	public void userOn() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/usersBig.png");
		Image imageAd = new Image(urlAd.toString());
		userChoice.setGraphic(new ImageView(imageAd));	
		userChoice.setEffect(sh1);
	}
	public void adminOn() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/admin0Big.png");
		Image imageAd = new Image(urlAd.toString());
		adminChoice.setGraphic(new ImageView(imageAd));
		adminChoice.setEffect(sh1);
	}
	public void exitOn() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/exit-icon-26Big.png");
		Image imageAd = new Image(urlAd.toString());
		exit.setGraphic(new ImageView(imageAd));
		exit.setEffect(sh1);
	}
	public void userOff() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/users.png");
		Image imageAd = new Image(urlAd.toString());
		userChoice.setGraphic(new ImageView(imageAd));	
		userChoice.setEffect(sh);
	}
	public void adminOff() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/admin0.png");
		Image imageAd = new Image(urlAd.toString());
		adminChoice.setGraphic(new ImageView(imageAd));
		adminChoice.setEffect(sh);
	}
	public void exitOff() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd = new Image(urlAd.toString());		
		exit.setGraphic(new ImageView(imageAd));
		exit.setEffect(sh);
	}
	//comprobacion del foco sobre los botones para los eventos de teclado en la vista de la lista de usuarios:
	public static void checkFocus(Button bt) throws MalformedURLException{
		
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
	    	URL urlAd= FTMain.class.getResource("/backBig.png");
			Image imageAd = new Image(urlAd.toString());
			FTAdminController.botonesAdmin[0].setGraphic(new ImageView(imageAd));	
			FTAdminController.botonesAdmin[0].setEffect(sh);
			FTListaUsersController.userListButtons[0].setGraphic(new ImageView(imageAd));
			FTListaUsersController.userListButtons[0].setEffect(sh);
		}
	    else if (bt.getId().equals("exit")){
	    	URL urlAd= FTMain.class.getResource("/exit-icon-26Big.png");
			Image imageAd = new Image(urlAd.toString());
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}		
		
		if(!bt.getId().equals("back")){
			URL urlAd= FTMain.class.getResource("/back.png");
			Image imageAd = new Image(urlAd.toString());
			FTAdminController.botonesAdmin[0].setGraphic(new ImageView(imageAd));	
			FTAdminController.botonesAdmin[0].setEffect(sh);
			FTListaUsersController.userListButtons[0].setGraphic(new ImageView(imageAd));
			FTListaUsersController.userListButtons[0].setEffect(sh);
		}
		if(!bt.getId().equals("exit")){
			URL urlAd= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd = new Image(urlAd.toString());
			FTAdminController.botonesAdmin[1].setGraphic(new ImageView(imageAd));	
			FTAdminController.botonesAdmin[1].setEffect(sh);
			FTListaUsersController.userListButtons[1].setGraphic(new ImageView(imageAd));
			FTListaUsersController.userListButtons[1].setEffect(sh);
		}
	}
	//comprobacion del foco sobre los botones para los eventos de teclado en la vista administrador:
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
	    	URL urlAd= FTMain.class.getResource("/backBig.png");
			Image imageAd = new Image(urlAd.toString());
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}
	    else if (bt.getId().equals("exit")){
	    	URL urlAd= FTMain.class.getResource("/exit-icon-26Big.png");
			Image imageAd = new Image(urlAd.toString());
			bt.setGraphic(new ImageView(imageAd));
			bt.setEffect(sh1);
		}		
		
		if(!bt.getId().equals("back")){
			URL urlAd= FTMain.class.getResource("/back.png");
			Image imageAd = new Image(urlAd.toString());
			FTListaUsersController.userListButtons[0].setGraphic(new ImageView(imageAd));
			FTListaUsersController.userListButtons[0].setEffect(sh);
		}
		if(!bt.getId().equals("exit")){
			URL urlAd= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd = new Image(urlAd.toString());
			FTListaUsersController.userListButtons[1].setGraphic(new ImageView(imageAd));
			FTListaUsersController.userListButtons[1].setEffect(sh);
		}
	}
	//control de la lista de botones para que no se salga de rango:
	public static void idBack(){
		if(id<0){
			id=botonesIniciales.length-1;
		}
		if(id>botonesIniciales.length-1){
			id=0;
		}
	}
	//metodo para cierre del programa:
	public void exit(){
		System.exit(1);
	}	
}
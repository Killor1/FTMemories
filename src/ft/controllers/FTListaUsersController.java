package ft.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import ft.FTMain;
import ft.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

@SuppressWarnings("unused")
public class FTListaUsersController {
	//variables necesarias:
	@FXML private ListView<String> userList;
	@FXML private Button back,exit;
	
	private FTMain main;
	private DropShadow sh;
	private DropShadow sh1;
	
	public static Button[] userListButtons= new Button[2];
	public static int id;
	public static String eleccion;
	int scaledWidth = 150;
    int scaledHeight = 150;
	public void setMain(FTMain main){
		this.main=main;
	}
	//inicializacion de la vista:
	@FXML 
	private void initialize() throws MalformedURLException{
		id=0;
		userListButtons[0]=back;
		userListButtons[1]=exit;
		
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
	    
	    URL urlAd= FTMain.class.getResource("/back.png");
		Image imageAd = new Image(urlAd.toString());
	    back.setGraphic(new ImageView(imageAd));
	    back.setBackground(null);
	    back.setEffect(sh);
	    back.setId("back");
	    
	    URL urlAd1= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd1 = new Image(urlAd1.toString());		
		exit.setGraphic(new ImageView(imageAd1));
		exit.setBackground(null);
		exit.setEffect(sh);
		exit.setId("exit");
		loadUserList();
	}
	//Metodo para cargar los elementos de la lista:
	public void loadUserList() throws MalformedURLException{
		List<Image> imagenes = new ArrayList<>();
		List<Usuario> usuarios = new ArrayList<>();  
		File f=new File(FTMain.path);
		File[] files=f.listFiles();
		
	    for (File fi:files){
	    	usuarios.add(new Usuario(fi.getAbsolutePath(),fi.getName()));	    	
	    }	    
	    List<String> nombres =new ArrayList<String>();  
	 	  for (Usuario us:usuarios){
	 		  nombres.add(us.getNombre());
	 		  imagenes.add(us.getIcon());
	 	  }
	 	  userList.setMaxSize(300, 600);
	 	  userList.setPrefWidth(300);
	 	  
	 	  ObservableList<String> observableList = FXCollections.observableList(nombres);
	 	userList.setCellFactory(listView -> new ListCell<String>() {
    private ImageView imageView = new ImageView();
    @Override
    public void updateItem(String friend, boolean empty) {
        super.updateItem(friend, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	for (Usuario us:usuarios){
        		if(this.getIndex()==usuarios.indexOf(us)){
        			Image image =us.getIcon();
        			imageView.setImage(image);
                    setGraphic(imageView);
                    setText(friend);
        		}              
        	}            
        }
    } 
});
	 userList.setItems(observableList);
	 	  //eventos de raton sobre la lista:
	 	 userList.setOnMouseClicked(new EventHandler<MouseEvent>() {	           
				@SuppressWarnings("static-access")
				@Override
	            public void handle(MouseEvent event) {
	                eleccion=userList.getSelectionModel().getSelectedItem();
	                String seleccion=FTMain.path+"/"+eleccion;
	                Usuario user=new Usuario(seleccion, eleccion);
	                Scene scene;
					BorderPane root;
					try{
						//carga de la vista de usuario sobre el elemento elegido:
						FXMLLoader loader= new FXMLLoader();
						loader.setLocation(FTMain.class.getResource("vista/UserView.fxml"));
						root=(BorderPane)loader.load();
						FTUserController control=loader.getController();
						control.setMain(main);
						control.setUser(user);
						scene=new Scene(root);
						//eventos de teclado sobre los botones de la vista:
						scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				            @Override
				            public void handle(KeyEvent event) {
				            	try{
				            		   switch (event.getCode()) {
					                    case UP:   
					                    	FTUserController.id++;
					                    	FTUserController.idBack();
					                    	checkFocus(FTUserController.botonesUsuario[FTUserController.id]); 
					                    	break;
					                    case DOWN: 		                    	
					                    	FTUserController.id--;
					                    	FTUserController.idBack();
					                    	checkFocus(FTUserController.botonesUsuario[FTUserController.id]);   
					                    	break;
					                    case LEFT:
					                    	FTUserController.id--;
					                    	FTUserController.idBack();
					                    	checkFocus(FTUserController.botonesUsuario[FTUserController.id]);                	
					                    	break;
					                    case RIGHT:
					                    	FTUserController.id++;
					                    	FTUserController.idBack();
					                    	checkFocus(FTUserController.botonesUsuario[FTUserController.id]);  
					                    	break;
					                    case ENTER: 
					                    	FTUserController.botonesUsuario[FTUserController.id].fire();
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
	        });
	      //Para capturar el elemento de la lista que seleccionamos con el teclado
	      userList.setOnKeyPressed(new EventHandler<KeyEvent>(){
	        @Override
	        public void handle(KeyEvent event) {
	        	switch (event.getCode()) {
             case ENTER: 
             	eleccion=userList.getSelectionModel().getSelectedItem();
	                String seleccion=FTMain.path+"\\"+eleccion;
	                Usuario user=new Usuario (seleccion, eleccion);
				default:
					break;	               
	        	}}});	 	  
	}
	//metodo de cierre del programa:
	public void exit(){
		System.exit(1);
	}
	//metodo de vuelta a la vista anterior:
	public void initVistaInicial() {
		main.initVistaInicial();
	}
	//metodo para controlar sobre que boton tenemos el foco:
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
	    
		if (bt.getId().equals("foto")){
			bt.setEffect(sh1);
		}
		else if(bt.getId().equals("audio")){
			bt.setEffect(sh1);
		}
		else if(bt.getId().equals("video")){
			bt.setEffect(sh1);
		}else if(bt.getId().equals("power")){
			bt.setEffect(sh1);
		}
		else if(bt.getId().equals("exit")){
			URL urlAd1= FTMain.class.getResource("/exit-icon-26Big.png");
			Image imageAd1 = new Image(urlAd1.toString());		
			bt.setGraphic(new ImageView(imageAd1));
			bt.setEffect(sh1);
		}
		if(!bt.getId().equals("foto")){
			FTUserController.botonesUsuario[0].setEffect(sh);			
		}
		if(!bt.getId().equals("audio")){
			FTUserController.botonesUsuario[1].setEffect(sh);
		}
		if(!bt.getId().equals("video")){
			FTUserController.botonesUsuario[2].setEffect(sh);
		}
		if(!bt.getId().equals("power")){
			FTUserController.botonesUsuario[3].setEffect(sh);
		}
		if(!bt.getId().equals("exit")){
			URL urlAd1= FTMain.class.getResource("/exit-icon-26.png");
			Image imageAd1 = new Image(urlAd1.toString());
			FTUserController.botonesUsuario[4].setGraphic(new ImageView(imageAd1));
			FTUserController.botonesUsuario[4].setEffect(sh);
		}
	}
	//metodos para aplicar efectos sobre los botones con el raton:
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
		URL urlAd1= FTMain.class.getResource("/backBig.png");
		Image imageAd1 = new Image(urlAd1.toString());		
		back.setGraphic(new ImageView(imageAd1));
		back.setEffect(sh);
	}
	//metodo para controlar el indice en la lista de botones para los eventos de teclado:
	public static void idBack(){
		if(id<0){
			id=userListButtons.length-1;
		}
		if(id>userListButtons.length-1){
			id=0;
		}
	}
}
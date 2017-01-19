package ft.controllers;

import java.awt.Desktop;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("static-access")
public class FTListaAdminController {
	@FXML private ListView<String> adminList;
	@FXML private Button back,exit;
	
	private FTMain main;
	
	public static String eleccion;
	public static Button[] userListButtons= new Button[2];
	public static int id;
	
	private DropShadow sh;
	private DropShadow sh1;	
	
	public void setMain(FTMain main){
		this.main=main;
	}
	
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
		
		try {
			loadAdminList();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void loadList(){
		
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
	 	  
	 	  adminList.setMaxSize(300, 600);
	 	  adminList.setPrefWidth(300);
	 	  ObservableList<String> observableList = FXCollections.observableList(nombres);
	 	  
	 	 adminList.setCellFactory(listView -> new ListCell<String>() {
	 	    private ImageView imageView = new ImageView();
	 	    @Override
	 	    public void updateItem(String friend, boolean empty) {
	 	        super.updateItem(friend, empty);
	 	        if (empty) {
	 	            setText(null);
	 	            setGraphic(null);
	 	        }else{
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
	 	  
	 	 adminList.setItems(observableList);
	 }
	public void loadAdminList() throws MalformedURLException{
		loadList();
		
 	  final ContextMenu contextMenu = new ContextMenu();
 	  MenuItem subMen = new MenuItem("Nuevo Usuario");
 	  
 	  subMen.setOnAction(new EventHandler<ActionEvent>() {		 		
			@Override
	 		public void handle(ActionEvent t) {
	 			final Stage dialog = new Stage();
	 			dialog.setHeight(300);
	 			dialog.setWidth(500);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(main.stage);
                VBox dialogVbox = new VBox();
                dialogVbox.setPadding(new Insets(20));
                Text mssg= new Text("Introduce el nombre del nuevo Usuario: ");
               // mssg.setFont(arg0);
                DropShadow ds = new DropShadow();
                ds.setOffsetY(3.0f);
                ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
                mssg.setEffect(ds);
                mssg.setCache(true);
                mssg.setX(10.0f);
                mssg.setY(270.0f);
                mssg.setFill(Color.BLACK);
               
                mssg.setFont(Font.font(null, FontWeight.BOLD, 24));
                dialogVbox.getChildren().add(mssg);
                final TextField newuser =new TextField();
                dialogVbox.setMargin(newuser, new Insets(20));
                dialogVbox.getChildren().add(newuser);
                final Button confirm=new Button("Confirmar");
                dialogVbox.getChildren().add(confirm);
                confirm.setOnAction(new EventHandler<ActionEvent>() {
   		 		@Override
		 		public void handle(ActionEvent t) {
   		 			if(newuser.getText()!=null){
   		 				createUser(newuser);
   		 				dialog.close();
   		 				loadList();
   		 			}
   		 		}});
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
	 		}
	 		});
 	  
 	  MenuItem subMen1 = new MenuItem("Borrar Usuario");
 	  subMen1.setOnAction(new EventHandler<ActionEvent>() {
	 		@Override
	 		public void handle(ActionEvent t) {
	 			String delete=FTMain.path+"/"+adminList.getItems().remove(adminList.getSelectionModel().getSelectedIndex());
	 			File f=new File (delete);		 			
	 			deleteFile(f);		 			
	 			loadList();
	 		}
	 		});
 	  
 	  MenuItem subMen2 = new MenuItem("Modificar Usuario");
 	  subMen2.setOnAction(new EventHandler<ActionEvent>() {
	 		@Override
	 		public void handle(ActionEvent t) {
	 			String show=FTMain.path+"/"+adminList.getItems().remove(adminList.getSelectionModel().getSelectedIndex());
	 			loadList();
	 			File f=new File(show);
	 			showDirsView(f);
	 		}
	 		});
 	  
 	  contextMenu.getItems().addAll(subMen,subMen1,subMen2);
 	  adminList.setContextMenu(contextMenu);
	  adminList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		  @Override
		  public void handle(ContextMenuEvent event) {
		  contextMenu.show(adminList, event.getScreenX(), event.getScreenY());
		  event.consume();
		  }
		  });
	}
	
	public static void showDirsView(File element){
		try {
			Desktop.getDesktop().open(element);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(File element) {
	    if (element.isDirectory()) {
	        for (File sub : element.listFiles()) {
	            deleteFile(sub);
	        }
	    }
	    element.delete();
	}
	
	public void createUser(TextField newuser){
			String newfile=FTMain.path+"/"+newuser.getText();
			String newFotos=newfile+"/Imagenes";
			String newMusica=newfile+"/Musica";
			String newVideos=newfile+"/Videos";	
			File f=new File(newfile);
			File f1=new File(newFotos);
			File f2=new File(newMusica);
			File f3=new File(newVideos);
			f.mkdir();
			f1.mkdir();
			f2.mkdir();
			f3.mkdir();
			showDirsView(f);
	}
	
	public void exit(){
		System.exit(1);
	}
	
	public static void idBack(){
		if(id<0){
			id=userListButtons.length-1;
		}
		if(id>userListButtons.length-1){
			id=0;
		}
	}
	public void callAdminPass(){
		Scene scene;		
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(FTMain.class.getResource("vista/AdminView.fxml"));
			Parent root=(AnchorPane)loader.load();
			
			FTAdminController control=loader.getController();
			control.setMain(this.main);			
			scene=new Scene(root);
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
	
	public void exitOn() throws MalformedURLException{
		URL urlAd1= FTMain.class.getResource("/exit-icon-26Big.png");
		Image imageAd1 = new Image(urlAd1.toString());		
		exit.setGraphic(new ImageView(imageAd1));
		exit.setEffect(sh1);
		
		URL urlAd2= FTMain.class.getResource("/back.png");
		Image imageAd2 = new Image(urlAd2.toString());
		back.setGraphic(new ImageView(imageAd2));
		back.setEffect(sh);
	}
	
	public void exitOff() throws MalformedURLException{		
		URL urlAd3= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd3 = new Image(urlAd3.toString());		
		exit.setGraphic(new ImageView(imageAd3));
		exit.setEffect(sh);
		
		URL urlAd= FTMain.class.getResource("/backBig.png");
		Image imageAd = new Image(urlAd.toString());		
		back.setGraphic(new ImageView(imageAd));
		back.setEffect(sh1);		
	}
	
	public void backOn() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/backBig.png");
		Image imageAd = new Image(urlAd.toString());		
		back.setGraphic(new ImageView(imageAd));
		back.setEffect(sh1);		

		URL urlAd3= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd3 = new Image(urlAd3.toString());
		exit.setGraphic(new ImageView(imageAd3));
		exit.setEffect(sh);
	}
	
	public void backOff() throws MalformedURLException{		
		URL urlAd2= FTMain.class.getResource("/back.png");
		Image imageAd2 = new Image(urlAd2.toString());		
		back.setGraphic(new ImageView(imageAd2));
		back.setEffect(sh);
		
		URL urlAd1= FTMain.class.getResource("/exit-icon-26Big.png");
		Image imageAd1 = new Image(urlAd1.toString());
		exit.setGraphic(new ImageView(imageAd1));
		exit.setEffect(sh1);
	}
}
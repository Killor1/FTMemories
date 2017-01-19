package ft.controllers;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ft.FTMain;
import ft.model.BaseButton;
import ft.model.Usuario;
import ft.utils.RepVideo;
import ft.utils.ReproductorMusica;
import ft.utils.MusicSceneGenerator;
import ft.utils.VideoSceneGenerator;
import ft.utils.VisorImagenes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

@SuppressWarnings("unused")
public class FTUserController {
	//elementos de la la vista:
	@FXML private Button foto,video,musica,exit,power;
	@FXML private HBox fotosBand;
	@FXML private VBox caja;
	@FXML private VBox cajaCentral;	
	@FXML private BorderPane base;
	
	//variables de clase:	
	private Usuario user;
	private FTMain main;
	public static Button[] botonesUsuario=new Button[5];
	private DropShadow sh;
	private DropShadow sh1;
	public static int id;
	private Background background;
	double left, right, bottom, top;
	boolean showed;
	Usuario aux;
	MediaPlayer mediaAux;
	public ArrayList<RepVideo> listaVideo = new ArrayList<RepVideo>();
	
	//setters y getters:
	public void setMain(FTMain main){
		this.main=main;
	}	
	private FTMain getMain(){
		return this.main;
	}
	public void setUser(Usuario user) throws MalformedURLException{
		this.user=user;
		Image imagePow = new Image(new File(user.getPathIcon()).toURI().toURL().toExternalForm());
		power.setGraphic(new ImageView(imagePow));
		power.setStyle("-fx-border-color: white;"
				+ "-fx-border-width: 3;"				
				+ "-fx-border-radius: 10;");
		
	}
	private Usuario getUser(){
		return this.user;
	}
	
	public static Thread proceso;
	private RepVideo videorep;
	private ReproductorMusica musicarep;
	//inicializacion de la vista inicial: 
	@FXML 
	public void initialize() throws MalformedURLException{
		
		id=0;
		showed=false;
		top=(fotosBand.getHeight()-150)/2;
		bottom=top;
		right=10;
		left=10;	
		URL urlAd1= FTMain.class.getResource("/backgroundStrip.png");
		Image imageAd1 = new Image(urlAd1.toString());
		BackgroundRepeat br=BackgroundRepeat.NO_REPEAT;
		BackgroundPosition bp=BackgroundPosition.DEFAULT;
		background=new Background(new BackgroundImage(imageAd1,br,br, bp, null));
		
		botonesUsuario[0]=foto;
		botonesUsuario[1]=musica;
		botonesUsuario[2]=video;
		botonesUsuario[3]=power;
		botonesUsuario[4]=exit;
		
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
		
	    URL urlAd= FTMain.class.getResource("/photo.png");
		Image imageAd = new Image(urlAd.toString());
		foto.setGraphic(new ImageView(imageAd));
		foto.setBackground(null);
		foto.setEffect(sh);
		foto.setId("foto");
		
		
		URL urlAd2= FTMain.class.getResource("/music.png");
		Image imageAd2 = new Image(urlAd2.toString());		
		musica.setGraphic(new ImageView(imageAd2));
		musica.setBackground(null);
		musica.setEffect(sh);
		musica.setId("audio");
		
		URL urlAd3= FTMain.class.getResource("/video.png");
		Image imageAd3 = new Image(urlAd3.toString());		
		video.setGraphic(new ImageView(imageAd3));
		video.setBackground(null);
		video.setEffect(sh);
		video.setId("video");
		
		URL urlAd4= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd4 = new Image(urlAd4.toString());		
		exit.setGraphic(new ImageView(imageAd4));
		exit.setBackground(null);
		exit.setEffect(sh);
		exit.setId("exit");
		
		Image imagePow = new Image(new File("Images/pow.png").toURI().toURL().toExternalForm());
		power.setGraphic(new ImageView(imagePow));
		power.setBackground(null);
		power.setEffect(sh);
		power.setId("power");		
		
		videorep=new RepVideo(null, base);
		musicarep=new ReproductorMusica(null, base);
		
		base.setLeft(caja);		
		//cajaCentral.setPadding(new Insets(10));
		base.setCenter(cajaCentral);
		aux=this.getUser();
	}
	//para la limpiar la zona inferior del Borderpane:
	public void cleanBottom(){
		base.setBottom(null);
	}
	//getter para coger el panel central:
	public BorderPane getBase(){
		return base;
	}
	
	//metodo de carga del powerpoint, llamando al programa determinado del sistema, ya que java no reproduce powerpoints:
	public void loadPowerPoint() throws IOException, AWTException, InterruptedException{
		if(musicarep.isRunning()){
				MusicSceneGenerator.mediaView.getMediaPlayer().stop();
				MusicSceneGenerator.mediaView.getParent().setVisible(false);
			}
			if(videorep.isRunning()){
				VideoSceneGenerator.mediaView.getMediaPlayer().stop();
				VideoSceneGenerator.mediaView.getParent().setVisible(false);
			}
			cajaCentral=null;
		File archivo=new File(user.getPathPower()); 
		Desktop.getDesktop( ).open(archivo);
		Robot robot = new Robot();
		Thread.sleep(6000);
		robot.keyPress(116);
		System.out.println("Pulsado F5");
		this.initialize();
		this.setUser(aux);
	}
	//Cargar elementos de fotos:
	public void loadFotoBand() throws MalformedURLException{
		showed=true;
		fotosBand.setBackground(background);
		fotosBand.getChildren().clear();
		if(!showed){
			top=(fotosBand.getHeight()-150)/2;
			bottom=top;
			fotosBand.setPadding(new Insets(top, right, bottom, left));
			fotosBand.setOpacity(0.2);
		}		
		
		String path=user.getPathFotos();
		File f=new File (path);
		File[] files=f.listFiles();
		for (File fi:files){
			if (fi.isDirectory()){
				String paths=fi.getAbsolutePath()+"\\icon.jpg";								
				BaseButton but=new BaseButton(paths);
				fotosBand.getChildren().add(but);				
				but.setOnAction(new EventHandler<ActionEvent>() {
		  	    	@Override public void handle(ActionEvent e) {
		  	    		if (but.getPath().contains("Fotos")){
		  	    			String s = but.getPath(); 		  	    			
		  	    			s = s.replaceAll("icon.jpg", "");
		  	    			but.setPath(s);		  	    			    	
		  	    			VisorImagenes visor =new VisorImagenes();
		  	    			try {
		  	    				visor.genVisor(but.getPath(), base);												
							} catch (MalformedURLException e1) {
								e1.printStackTrace();
							}
		  	    			}}});
			}
		}
		if (base.getCenter()!=null){
			base.setCenter(null);
		}
		fotosBand.setVisible(true);
		base.setCenter(fotosBand);
	}
	//cargar elementos de musica:
	public void loadMusicBand() throws MalformedURLException{
		BorderPane base=this.base;
		 fotosBand.getChildren().clear();
		 fotosBand.setBackground(background);
			String path=user.getPathMusica();
			File f=new File (path);
			File[] files=f.listFiles();
			for (File fi:files){
				if (fi.isDirectory()){
					String paths=fi.getAbsolutePath()+"\\icon.jpg";
					BaseButton but=new BaseButton(paths);
					fotosBand.getChildren().add(but);					
					but.setOnAction(new EventHandler<ActionEvent>() {
			  	    	@Override public void handle(ActionEvent e) {
			  	    		if (but.getPath().contains("Musica")){
			  	    			String s = but.getPath(); 
			  	    			s = s.replaceAll("icon.jpg", "");
			  	    			but.setPath(s);	
			  	    			musicarep = new ReproductorMusica(but.getPath(), base);
			  	    			if(musicarep.isRunning()){
			  	    				MusicSceneGenerator.mediaView.getMediaPlayer().stop();
			  	    				MusicSceneGenerator.mediaView.getParent().setVisible(false);
			  	    			}
			  	    			if(videorep.isRunning()){
			  	    				VideoSceneGenerator.mediaView.getMediaPlayer().stop();
			  	    				VideoSceneGenerator.mediaView.getParent().setVisible(false);
			  	    			}
			  	    			musicarep.run();
			  	    			
			  	    		}}});
				}
			}				
			if (base.getCenter()!=null){
				base.setCenter(null);
			}
			fotosBand.setVisible(true);
			base.setCenter(fotosBand);
		}
	@SuppressWarnings("deprecation")
	public void loadVideoBand() throws MalformedURLException{
		VideoSceneGenerator.players.clear();
		for (RepVideo v:listaVideo){
  			if(v.isRunning()){
  				v.stop();
  			}
  		}try{
  			VideoSceneGenerator.mediaView.getMediaPlayer().stop();
			VideoSceneGenerator.mediaView.getParent().setVisible(false);
  		}catch(NullPointerException ex){
  			
  		}
		
		int cont=0;
		fotosBand.getChildren().clear();
		fotosBand.setBackground(background);
		String path=user.getPathVideos();
		File f=new File (path);
		File[] files=f.listFiles();
		for (File fi:files){
			if (fi.isDirectory()){				
				String paths=fi.getAbsolutePath()+"\\icon.jpg";
				BaseButton but=new BaseButton(paths);	
				fotosBand.getChildren().add(but);
				if (but.getPath().contains("Videos")){
  	    			String s = but.getPath(); 
  	    			s = s.replaceAll("icon.jpg", "");
  	    			but.setPath(s);	
  	    			videorep = new RepVideo(but.getPath(), base);
  	    			listaVideo.add(videorep);
  	    			but.setid(cont);
  	    			cont++;
					
				but.setOnAction(new EventHandler<ActionEvent>() {
		  	    	@Override public void handle(ActionEvent e) {	
		  	    		for (RepVideo v:listaVideo){
		  	    			if(v.isRunning()){
		  	    				v.stop();
		  	    			}
		  	    		}
		  	    			if(musicarep.isRunning()){
		  	    				System.out.println("Habia musica");
		  	    				MusicSceneGenerator.mediaView.getMediaPlayer().stop();
		  	    				MusicSceneGenerator.mediaView.getParent().setVisible(false);
		  	    			}
		  	    			if(videorep.isRunning()){		  	    				
		  	    				VideoSceneGenerator.mediaView.getMediaPlayer().stop();
		  	    				VideoSceneGenerator.mediaView.getParent().setVisible(false);
		  	    			}	
		  	    			if (base.getBottom()!=null){
			    				base.getBottom().setDisable(true);
			    				base.setBottom(null);	    				
			    			}								
							listaVideo.get(but.getid()).run();
							
		  	    		}			
	  	    	
				});				
				}			
		}			
		fotosBand.setVisible(true);
		base.setCenter(fotosBand);
		}
	}	 
	 //metodo de cierre del programa:
	public void exit(){
		System.exit(1);
	}
	//metodos de controlo para el foco sobre los botones:
	public void fotoOn(){
			foto.setEffect(sh1);
	}
	public void musicOn(){
		musica.setEffect(sh1);
	}
	public void videoOn(){
		video.setEffect(sh1);
	}
	public void powOn(){
		power.setEffect(sh1);
	}
	public void exitOn() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/exit-icon-26Big.png");
		Image imageAd = new Image(urlAd.toString());
		exit.setGraphic(new ImageView(imageAd));
		exit.setEffect(sh1);
	}
	public void fotoOff() throws MalformedURLException{
		foto.setEffect(sh);
	}
	public void musicOff() throws MalformedURLException{
		musica.setEffect(sh);
	}
	public void videoOff() throws MalformedURLException{
		video.setEffect(sh);
	}
	public void powOff(){
		power.setEffect(sh);
	}
	public void exitOff() throws MalformedURLException{
		URL urlAd= FTMain.class.getResource("/exit-icon-26.png");
		Image imageAd = new Image(urlAd.toString());
		exit.setGraphic(new ImageView(imageAd));
		exit.setEffect(sh);
	}
	//metodo de control sobre la lista de botones:
	public static void idBack(){
		if(id<0){
			id=botonesUsuario.length-1;
		}
		if(id>botonesUsuario.length-1){
			id=0;
		}
	}	
}
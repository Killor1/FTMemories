package ft.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import ft.controllers.FTUserController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

//clase para cargar el reproductor de musica:
public class ReproductorMusica extends Thread{
	private String path;
	private boolean exec;
	private BorderPane pane;
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	 
	public ReproductorMusica(String path, BorderPane pane){
		this.path=path;
		this.pane=pane;
		this.exec=false;
	}
	//setters y getters:
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path=path;
	}	
	public boolean isRunning() {		
		return exec;		
	}
	//inicializacion del elemento:
	public void initFX(Pane fxPanel, BorderPane base) throws MalformedURLException {
	    
	    StackPane flow = new MusicSceneGenerator().createScene(this.path);
	    flow.setVisible(true);	    
	    base.setBottom(flow);	   
	}	
	//metodo para detener el proceso:
	@SuppressWarnings("static-access")
	public void stopIt() {
		this.currentThread().interrupt();
	}
	//metodo para poner en marcha el elemento:
	@Override
	public void run() {		
		FlowPane frame = new FlowPane();
	    final Pane fxPanel = new Pane();
	    frame.getChildren().add(fxPanel);
	    frame.setVisible(true);
	    fxPanel.setVisible(true);
	   
	    FTUserController.proceso=new Thread() {
	    	 @Override public void run() {
	 	        try {
	 				initFX(fxPanel, pane);
	 			}catch (MalformedURLException e) {
	 				e.printStackTrace();
	 			}}};
		FTUserController.proceso.run();
		this.exec=true;
	}
}
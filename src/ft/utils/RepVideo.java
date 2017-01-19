package ft.utils;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import ft.controllers.FTUserController;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

//clase para el reproductor de video:
public class RepVideo extends Thread{
	
	private BorderPane pane;
	private boolean exec=false;
	private String path;
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	public RepVideo(){
		
	}
	public RepVideo(String path, BorderPane pane){
		this.path=path;
		this.pane=pane;
		this.exec=false;
	}
	//getters y setters:
	public BorderPane getPane(){
		return this.pane;
	}
	public void setPane(BorderPane pane){
		this.pane=pane;
	}
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path=path;
	}	
	public boolean isRunning() {		
		return exec;		
	}
	public void setRunning(boolean bool){
		this.exec=bool;
	}
	//inicializacion del elemento:
	public void initFX(Pane fxPanel,BorderPane base) throws MalformedURLException {	  
	    Pane flow = new VideoSceneGenerator().createScene(this.path);
	    if (base.getBottom()!=null){
			base.setBottom(null);
		}	
	   flow.setPrefSize(dim.width*0.6, dim.height*0.6);
	   flow.setVisible(true);
	   flow.setPadding(new Insets(0, 0,300, 0));
	   base.setCenter(flow);
	   
	  }
	
	//metodo para parar la reproduccion:
	@SuppressWarnings("static-access")
	public void stopIt() {
		this.currentThread().interrupt();
		
	}	
	//arranque del reproductor:
	@Override
	public void run() {		
		BorderPane frame = new BorderPane();
	    final Pane fxPanel = new Pane();
	    frame.setCenter(fxPanel);
	    frame.setVisible(true);
	    frame.setPrefSize( 800, 550 );
	    fxPanel.setVisible(true);
	   
	    FTUserController.proceso=new Thread() {
	    	 @Override public void run() {
	 	        try {
	 				initFX(fxPanel, pane);
	 			} catch (MalformedURLException e) {
	 				e.printStackTrace();
	 			}}};
	 			FTUserController.proceso.run();
	 			this.exec=true;
		}
}
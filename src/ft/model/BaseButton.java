package ft.model;

import java.io.File;
import java.net.MalformedURLException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class BaseButton extends Button{
	private String path;
	private int id;
	
	//Constructor
	public BaseButton(String path) throws MalformedURLException{
		DropShadow ds = new DropShadow();
		ds.setOffsetX(1.0f);
		ds.setOffsetY(1.0f);
		ds.setHeight(50);
		ds.setWidth(50);
		ds.setColor(Color.WHITE);
		this.path=path;		
		this.setEffect(ds);
		setIcon(this.path);
		setVisible(true);
		setMinSize(100,100);
		setMaxSize(250,250);
		setPrefSize(200,200);
		
		this.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ds.setColor(Color.GREEN);				
			}
		});
		this.setOnMouseExited(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				ds.setColor(Color.WHITE);				
			}			
		});
		if(this.isFocused()){
		  	this.setHover(true);			
		}		
	}
	//SETTER Y GETTER
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path=path;
	}	
	public void setid(int id){
		this.id=id;
	}
	public int getid(){
		return id;
	}
	//carga la imagen a raiz del path buscando una capa inferior al path recibido
	public void setIcon(String path) throws MalformedURLException{
		if((this.path.endsWith(".png")) || (this.path.endsWith(".jpg") )){
			Image image = new Image(new File(path).toURI().toURL().toExternalForm());
			this.setGraphic(new ImageView(image));
			this.setBackground(null);			
		}else{
			File iconImg=null;
			File pathf=new File(this.path);
			File[] folder=pathf.listFiles();
			for (File f:folder){
				if(f.getName().equals("icon.png")){
					iconImg=f;
				}
			}
			Image im = new Image(getClass().getResourceAsStream(iconImg.getName()));
			setGraphic(new ImageView(im));		
		}		
	}	
}
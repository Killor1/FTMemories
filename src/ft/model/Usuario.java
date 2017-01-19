package ft.model;

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;

public class Usuario {
	private String path;
	private String pathFotos;
	private String pathMusica;
	private String pathVideos;
	private Image icon;
	private String nombre;
	private String pathPower;	
	private String pathIcon; 
	
	//GETTERS
	public String getNombre(){
		return nombre;
	}
	public String getPath() {
		return path;
	}
	public String getPathFotos() {
		return pathFotos;
	}
	public String getPathMusica() {
		return pathMusica;
	}
	public String getPathVideos() {
		return pathVideos;
	}
	public Image getIcon() {
		return icon;
	}
	public String getPathIcon(){
		return pathIcon;
	}
	public String getPathPower(){
		return this.pathPower;
	}
	//SETTERS
	public void setPath(String path) {
		this.path = path;
	}
	public void setPathFotos(String pathFotos) {
		this.pathFotos = pathFotos;
	}
	public void setPathMusica(String pathMusica) {
		this.pathMusica = pathMusica;
	}
	public void setPathVideos(String pathVideos) {
		this.pathVideos = pathVideos;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setPathIcon(String pathIcon){
		this.pathIcon=pathIcon;
	}
	
	public Usuario(String path, String name){
		this.nombre=name;
		this.path=path;
		this.pathFotos=path+"/Fotos";
		this.pathMusica=path+"/Musica";
		this.pathVideos=path+"/Videos";
		this.pathPower=path+"/powerpoint.pps";
		this.pathIcon=path+"/icon.jpg";
		
		File icono=new File(pathIcon);		
		try {
			Image im= new Image(icono.toURI().toURL().toExternalForm());
			this.icon=im;
		} catch (MalformedURLException e) {
			System.out.println("Falla la carga");
			e.printStackTrace();
		}		
	}
}


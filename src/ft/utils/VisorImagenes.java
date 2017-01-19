package ft.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

//clase que nos cargas las imagenes para reproducirlas:
public class VisorImagenes {
	
	private static List<Image> images;
	private static Iterator<Image> imageIterator;	
	//metodo para generar el visor:		
	public void genVisor(String pathBotton,BorderPane base) throws MalformedURLException{
		
		ArrayList<Image> imgs= new ArrayList<Image>();
		File f=new File(pathBotton);
		System.out.println(f.getAbsolutePath());
		File[] files=f.listFiles();
		System.out.println(files.length);
		for(File fo:files){
			if(!fo.getName().startsWith("icon")){
				Image im= new Image(fo.toURI().toURL().toExternalForm());				
				System.out.println("fo: "+fo.getName());
				imgs.add(im );
			}			
		}
		images = imgs;
		Collections.shuffle(images);
        imageIterator = images.iterator();
        ImageView imageView = new ImageView();
        
        imageView.setFitWidth(1000); 
        imageView.setFitHeight(700); 
        imageView.setPreserveRatio(false);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        e -> {
                            imageView.setImage(imageIterator.next());
                            imageIterator.remove();                           
                        }
                ),
                new KeyFrame(Duration.seconds(2))
        );
        
        timeline.setOnFinished(event -> {
            Collections.shuffle(images);
            imageIterator = images.iterator();
            timeline.playFromStart();
            if(!imageIterator.hasNext()){
            	timeline.stop();
            	base.setCenter(null);
            }
        });
        timeline.play();
        StackPane box=new StackPane(imageView);        
		base.setCenter(box);
	}
}
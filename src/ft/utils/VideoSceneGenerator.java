package ft.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import ft.controllers.FTMainController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

//metodo para generar los videos, y la escena donde se reproduciran:
@SuppressWarnings("deprecation")
public class VideoSceneGenerator {
	 public static List<MediaPlayer> players = new ArrayList<MediaPlayer>();
	public static MediaView mediaView;
	final Label currentlyPlaying = new Label();
	final ProgressBar progress = new ProgressBar();
	private ChangeListener<Duration> progressChangeListener;
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();	  
	
	public BorderPane createScene(String path) throws MalformedURLException {
		
	    final BorderPane layout = new BorderPane();
	    // decimos de donde ha de cargar los videos:
	    final File dir = new File(path);
	    if (!dir.exists() || !dir.isDirectory()) {
	      Platform.exit();
	      return null;
	    }

	    // creamos varios la lista de media players:
	   
	    
	    for (String file : dir.list(new FilenameFilter(){
	      @Override public boolean accept(File dir, String name) {
	        return name.endsWith(".mp4");
	      }})){
	    	players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
	    }	    
	    if (players.isEmpty()) {
	      Platform.exit();
	      return null;
	    }
	    // creamos las vistas para los medias:
	     mediaView = new MediaView(players.get(0));
	    final Button skip = new Button();
	    final Button play = new Button();
	    final Button stop = new Button();
	    
	    URL urlUs= FTMainController.class.getResource("/play.png");
		Image imPlay = new Image(urlUs.toString());
		ImageView iconPlay =new ImageView(imPlay);
		play.setGraphic(iconPlay);
		play.setText("Play");
		
		URL urlUs1= FTMainController.class.getResource("/pause.png");
		Image imPause = new Image(urlUs1.toString());
		ImageView iconPause =new ImageView(imPause);
		play.setGraphic(iconPause);
		play.setText("Pause");
		
		URL urlUs2= FTMainController.class.getResource("/stop.png");
		Image imStop = new Image(urlUs2.toString());
		ImageView iconStop =new ImageView(imStop);
		stop.setGraphic(iconStop);
		
		URL urlUs3= FTMainController.class.getResource("/btforward.png");
		Image imSkip = new Image(urlUs3.toString());
		ImageView iconSkip =new ImageView(imSkip);
		skip.setGraphic(iconSkip);
		
	    //reproducimos cada media en orden:
	    for (int i = 0; i < players.size(); i++) {
	      final MediaPlayer player     = players.get(i);
	      final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
	      player.setOnEndOfMedia(new Runnable() {
	        @Override public void run() {
	          player.currentTimeProperty().removeListener(progressChangeListener);
	          mediaView.setMediaPlayer(nextPlayer);
	          nextPlayer.play();	          
	        }
	      });
	    }
	    // boton para pasar las canciones
	    skip.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent actionEvent) {
	        final MediaPlayer curPlayer = mediaView.getMediaPlayer();
	        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
	        mediaView.setMediaPlayer(nextPlayer);
	        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
	        curPlayer.stop();
	        nextPlayer.play();
	      }
	    });

	    // configuramos el boton de pausa/play:
	    play.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent actionEvent) {
	        if ("Pause".equals(play.getText())) {
	          mediaView.getMediaPlayer().pause();	          
				URL urlUs= FTMainController.class.getResource("/play.png");
				Image imPlay = new Image(urlUs.toString());
				ImageView iconPlay =new ImageView(imPlay);
				play.setGraphic(iconPlay);	
				play.setText("Play");
	        } else if("Play".equals(play.getText())){
	        	URL urlUs= FTMainController.class.getResource("/pause.png");
				Image imPlay = new Image(urlUs.toString());
				ImageView iconPlay =new ImageView(imPlay);
				play.setGraphic(iconPlay);
				mediaView.getMediaPlayer().play();
	        }
	      }
	    });
	    
	    stop.setOnAction(new EventHandler<ActionEvent>() {
		      @Override public void handle(ActionEvent actionEvent) {
			        mediaView.getMediaPlayer().stop();
			       mediaView.getParent().setVisible(false);			       
			      }
			    });
	    
	    // indicamos que media estamos reproduciendo:
	    mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
	      @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
	        setCurrentlyPlaying(newPlayer);
	      }
	    });

	    // empezamos a reproducir el primer elemento:
	    mediaView.setMediaPlayer(players.get(0));
	    mediaView.getMediaPlayer().play();
	    setCurrentlyPlaying(mediaView.getMediaPlayer());
	    mediaView.setFitWidth(dim.width*0.6);
	    mediaView.setFitHeight(dim.height*0.6);
	    

	    // boton inutil pero necesario:
	    Button invisiblePause = new Button();
	    invisiblePause.setVisible(false);
	    play.prefHeightProperty().bind(invisiblePause.heightProperty());
	    play.prefWidthProperty().bind(invisiblePause.widthProperty());
	    StackPane pane =new StackPane();
	    pane.setPrefSize( 800, 600 );
	    // emplazamos la escena:
	    pane.setStyle("-fx-background-color: transparent; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
	    pane.getChildren().addAll(
	      invisiblePause,
	      VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
	        currentlyPlaying,
	        mediaView,
	        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, stop, progress).build()
	      ).build()
	    );
	    
	    progress.setMaxWidth(Double.MAX_VALUE);
	    HBox.setHgrow(progress, Priority.ALWAYS);
	    layout.setPrefSize(800, 550);
	   
	    layout.setCenter(pane);
	    
	    layout.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
	    return layout;
	  }

	  //ponemos la etiqueta de lo que estamos reproduciendo asi como la barra de progreso:
	  private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
	    progress.setProgress(0);
	    progressChangeListener = new ChangeListener<Duration>() {
	      @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
	        progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
	      }
	    };
	    newPlayer.currentTimeProperty().addListener(progressChangeListener);

	    String source = newPlayer.getMedia().getSource();
	    source = source.substring(0, source.length() - ".mp4".length());
	    source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
	    currentlyPlaying.setText("Now Playing: " + source);
	  }

	  //metodo que devuelve el media player para pasarlo a las otras clases:
	  private MediaPlayer createPlayer(String aMediaSrc) {
	    final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
	    player.setOnError(new Runnable() {
	      @Override public void run() {
	      }
	    });
	    return player;
	  }	
}
package ft.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import ft.controllers.FTUserController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

//generador de las vistas de video:
@SuppressWarnings("deprecation")
public class MusicSceneGenerator { 
	
	public static MediaView mediaView;
	FTUserController control =new FTUserController();
	final Label currentlyPlaying = new Label();
	final ProgressBar progress = new ProgressBar();
	private ChangeListener<Duration> progressChangeListener;
	
	//metodo para crear la escena de reproduccion:
	public StackPane createScene(String path) throws MalformedURLException {
		
	    StackPane layout = new StackPane();
	    final File dir = new File(path);
	    if (!dir.exists() || !dir.isDirectory()) {
	      Platform.exit();
	      return null;
	    } 
		
	    //creamos los media players sobre los archivos a reproducir:
	    final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
	    for (String file : dir.list(new FilenameFilter() {
	      @Override public boolean accept(File dir, String name) {
	        return name.endsWith(".mp3");
	      }
	    })) 
	    players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
	    
	    if (players.isEmpty()) {
	      Platform.exit();
	      return null;
	    }	    
	    // creamos las vistas sobre los medias a reproducir:
	    mediaView = new MediaView(players.get(0));
	    final Button skip = new Button("Skip");
	    final Button play = new Button("Pause");
	    final Button stop = new Button("Stop");
	    
	    Image imPlay = new Image(new File("Images/play.png").toURI().toURL().toExternalForm());
		ImageView iconPlay =new ImageView(imPlay);
		play.setGraphic(iconPlay);
		
		Image imPause = new Image(new File("Images/pause.png").toURI().toURL().toExternalForm());
		ImageView iconPause =new ImageView(imPause);
		play.setGraphic(iconPause);
		
		Image imStop = new Image(new File("Images/stop.png").toURI().toURL().toExternalForm());
		ImageView iconStop =new ImageView(imStop);
		stop.setGraphic(iconStop);
		
		Image imSkip = new Image(new File("Images/btforward.png").toURI().toURL().toExternalForm());
		ImageView iconSkip =new ImageView(imSkip);
		skip.setGraphic(iconSkip);
		
	    // reproducimos cada audio en orden sobre el array.
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
	    // boton para poder pasar las canciones:
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

	    // boton para poder pausar la reproduccion:
	    play.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent actionEvent) {
	        if ("Pause".equals(play.getText())) {
	          mediaView.getMediaPlayer().pause();	          
	          try {
					Image imPlay = new Image(new File("Images/play.png").toURI().toURL().toExternalForm());
					ImageView iconPlay =new ImageView(imPlay);
			  		play.setGraphic(iconPlay);
			        play.setText("Play");
				} catch (MalformedURLException e) {					
					e.printStackTrace();
				}	  		
	        }else{
	        	try {
					Image imPlay = new Image(new File("Images/pause.png").toURI().toURL().toExternalForm());
					ImageView iconPlay =new ImageView(imPlay);
			  		play.setGraphic(iconPlay);
			  		mediaView.getMediaPlayer().play();
			        play.setText("Pause");
				}catch (MalformedURLException e){					
					e.printStackTrace();
				}          
	        }
	      }
	    });
	    
	    stop.setOnAction(new EventHandler<ActionEvent>() {
		      @Override public void handle(ActionEvent actionEvent) {
			        mediaView.getMediaPlayer().stop();
			       mediaView.getParent().setVisible(false);
			  }
		});

	    // Para mostrar que cancion se esta reproduciendo:
	    mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
	      @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
	        setCurrentlyPlaying(newPlayer);
	      }
	    });

	    // empieza a reproducir desde el primer media:
	    mediaView.setMediaPlayer(players.get(0));
	    mediaView.getMediaPlayer().play();
	    setCurrentlyPlaying(mediaView.getMediaPlayer());

	    // boton inutil, pero necesario para que funcione correctamente:
	    Button invisiblePause = new Button("Pause");
	    invisiblePause.setVisible(false);
	    play.prefHeightProperty().bind(invisiblePause.heightProperty());
	    play.prefWidthProperty().bind(invisiblePause.widthProperty());

	    // le damos el estilo al reproductor:
	    layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
	    layout.getChildren().addAll(
	      invisiblePause,
	      VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
	        currentlyPlaying,
	        mediaView,
	        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, stop, progress).build()
	      ).build()
	    );
	    progress.setMaxWidth(Double.MAX_VALUE);
	    HBox.setHgrow(progress, Priority.ALWAYS);
	    return layout;
	  }

	  //mostramos el elemento que se esta reproduciendo asi como la barra de progreso:
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

	  //metodo que devuelve el media player creado:
	  private MediaPlayer createPlayer(String aMediaSrc) {
	    System.out.println("Creating player for: " + aMediaSrc);
	    final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
	    player.setOnError(new Runnable() {
	      @Override public void run() {
	        System.out.println("Media error occurred: " + player.getError());
	      }
	    });
	    return player;
	  }
	}

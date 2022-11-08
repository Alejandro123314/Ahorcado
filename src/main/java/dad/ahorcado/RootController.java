package dad.ahorcado;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import dad.ahorcado.palabras.PalabrasController;
import dad.ahorcado.partida.PartidaController;
import dad.ahorcado.puntuaciones.PuntuacionesController;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RootController implements Initializable {
	
	// controllers
	
	private PalabrasController palabrasController = new PalabrasController(); 
	private PuntuacionesController puntuacionesController = new PuntuacionesController();
	private PartidaController partidaController = new PartidaController();
	
	// model
	
	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());
	// view
	
	@FXML
	private TabPane view;
	
	@FXML 
	private Tab partidaTab, palabrasTab, puntuacionesTab;
	
	public RootController(List<String> palabras2, List<String> puntuaciones2) {
		this.palabras.addAll(palabras2);
		this.puntuaciones.addAll(puntuaciones2);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		palabrasTab.setContent(palabrasController.getView());
		puntuacionesTab.setContent(puntuacionesController.getView());
		partidaTab.setContent(partidaController.getView());
		
		// bindings
		
		palabrasController.palabrasProperty().bind(palabras);
		puntuacionesController.puntuacionesProperty().bindBidirectional(puntuaciones);
		partidaController.puntuacionesProperty().bindBidirectional(puntuaciones);
		
		partidaController.setPalabraAdivinar(palabras.get(new Random().nextInt(palabras.size())));
		
		
	}
	
	public TabPane getView() {
		return view;
	}

	public final ListProperty<String> palabrasProperty() {
		return this.palabras;
	}
	

	public final ObservableList<String> getPalabras() {
		return this.palabrasProperty().get();
	}
	

	public final void setPalabras(final ObservableList<String> palabras) {
		this.palabrasProperty().set(palabras);
	}
	
	public final ListProperty<String> puntuacionesProperty() {
		return this.puntuaciones;
	}
	

	public final ObservableList<String> getPuntuaciones() {
		return this.puntuacionesProperty().get();
	}
	

	public final void setPuntuaciones(final ObservableList<String> puntuaciones) {
		this.puntuacionesProperty().set(puntuaciones);
	}

}

package dad.ahorcado.puntuaciones;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PuntuacionesController implements Initializable {

	// model

	private ListProperty<String> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view

	@FXML
	private ListView<String> puntuacionList;

	@FXML
	private BorderPane view;

	public PuntuacionesController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		puntuacionList.itemsProperty().bind(puntuaciones);

	}

	public BorderPane getView() {
		return view;
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

//generar modelo con 1 private StringProperty y 1 private IntegerProperty constructor recibe String y int


package dad.ahorcado.palabras;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class PalabrasController implements Initializable {

	// model

	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty palabraSelecionada = new SimpleStringProperty();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Button nuevoButton;

	@FXML
	private ListView<String> palabrasList;

	@FXML
	private Button quitarButton;

	public PalabrasController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabrasView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		palabrasList.itemsProperty().bind(palabras);
		palabraSelecionada.bind(palabrasList.getSelectionModel().selectedItemProperty());

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onNuevoAction(ActionEvent event) {

		TextInputDialog td = new TextInputDialog();
		td.setHeaderText("Palabra a añadir");
		Optional<String> palabra = td.showAndWait();
		palabras.add(palabra.get());

	}

	@FXML
	void onQuitarAction(ActionEvent event) {
		palabras.remove(palabraSelecionada.getValue());
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

}

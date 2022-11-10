package dad.ahorcado.partida;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javax.naming.Binding;

import dad.ahorcado.RootController;
import dad.ahorcado.palabras.PalabrasController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PartidaController implements Initializable {
	

	private BooleanProperty actualiza = new SimpleBooleanProperty();
	private StringProperty palabraAdivinar = new SimpleStringProperty();
	private StringProperty letraInsertada = new SimpleStringProperty("");
	private StringProperty palabraIntroducida = new SimpleStringProperty();
	private IntegerProperty puntosLetras = new SimpleIntegerProperty();
	private StringProperty palabraOculta = new SimpleStringProperty();
	private IntegerProperty puntosPalabras = new SimpleIntegerProperty();
	private IntegerProperty fallos = new SimpleIntegerProperty(1);
	private StringProperty usuario = new SimpleStringProperty();
	private ListProperty<String> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());

	@FXML
	private Button adivinar;

	@FXML
	private Label guiones;

	@FXML
	private ImageView image;

	@FXML
	private Button letra;

	@FXML
	private Label letras;

	@FXML
	private TextField palabra;

	@FXML
	private Label puntuacion;

	@FXML
	private BorderPane view;

	public PartidaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		puntuacion.textProperty().bind(Bindings.concat("Puntos: ", puntosLetras, " ", puntosPalabras));
		palabra.textProperty().bindBidirectional(palabraIntroducida);
		letras.textProperty().bind(letraInsertada);
		guiones.textProperty().bind(palabraOculta);
		cambiarImagen();

		palabraAdivinar.addListener((o, ov, nv) -> {
			palabraOculta.setValue("");
			for (int i = 0; i < nv.length(); i++) {
				if (nv.charAt(i) == ' ') {
					palabraOculta.setValue(palabraOculta.getValue() + " ");
				} else {
					palabraOculta.setValue(palabraOculta.getValue() + "_");

				}
			}
		});

		letra.setOnAction(e -> letra(e));
		adivinar.setOnAction(e -> adivinar(e));

	}

	private void adivinar(ActionEvent e) {
		if (palabraAdivinar.getValue().equalsIgnoreCase(palabraIntroducida.getValue())) {
			puntosPalabras.setValue(puntosPalabras.getValue() + 1);
			letraInsertada.setValue(letraInsertada.getValue() + " " + palabraIntroducida.getValue());
			setActualiza(true);
		} else {
			fallos();
		}
		palabraIntroducida.setValue(null);
	}

	private void fallos() {
		fallos.setValue(fallos.getValue() + 1);
		if (fallos.getValue() == 10) {
			fallos.setValue(1);
			TextInputDialog td = new TextInputDialog();
			td.setHeaderText("Nombre del jugador");
			Optional<String> nombre = td.showAndWait();
			usuario.setValue(nombre.get());
			puntuaciones.add(usuario.getValue() + " " + puntosLetras.getValue() + " " + puntosPalabras.getValue());
			puntosLetras.setValue(0);
			puntosPalabras.setValue(0);
			
		}
		cambiarImagen();
	}

	private void letra(ActionEvent e) {

		int contador = 0;

		for (int i = 0; i < palabraAdivinar.getValue().length(); i++) {
			if (palabraOculta.getValue().toUpperCase().charAt(i) != palabraAdivinar.getValue().toUpperCase()
					.charAt(i)) {
				if (palabraAdivinar.getValue().toUpperCase().charAt(i) == palabraIntroducida.getValue().toUpperCase()
						.charAt(0)) {
					StringBuilder palabra = new StringBuilder(palabraOculta.getValue());
					palabra.setCharAt(i, palabraIntroducida.getValue().charAt(0));
					palabraOculta.setValue(palabra.toString());

					contador += 1;
				}
			}

		}
		if (contador == palabraOculta.getValue().length()) {
			setActualiza(true);
		}
		if (contador == 0) {
			fallos();
		}

		puntosLetras.set(contador + puntosLetras.getValue());

		boolean noExiste = true;
		for (int i = 0; i < letraInsertada.getValue().length(); i++) {

			if (letraInsertada.getValue().toUpperCase().charAt(i) == palabraIntroducida.getValue().toUpperCase()
					.charAt(0)) {
				noExiste = false;
			}

		}
		if (noExiste == true) {
			letraInsertada.setValue(letraInsertada.getValue() + " " + palabraIntroducida.getValue().charAt(0));
		}

		palabraIntroducida.setValue(null);

	}

	private void cambiarImagen() {
		image.setImage(new Image(getClass().getResourceAsStream("/images/" + fallos.getValue() + ".png")));
	}

	public BorderPane getView() {
		return view;
	}

	public final StringProperty palabraAdivinarProperty() {
		return this.palabraAdivinar;
	}

	public final String getPalabraAdivinar() {
		return this.palabraAdivinarProperty().get();
	}

	public final void setPalabraAdivinar(final String palabraAdivinar) {
		this.palabraAdivinarProperty().set(palabraAdivinar);
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

	public final BooleanProperty actualizaProperty() {
		return this.actualiza;
	}
	

	public final boolean isActualiza() {
		return this.actualizaProperty().get();
	}
	

	public final void setActualiza(final boolean actualiza) {
		this.actualizaProperty().set(actualiza);
	}
	

}

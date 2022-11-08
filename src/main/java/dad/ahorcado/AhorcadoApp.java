package dad.ahorcado;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AhorcadoApp extends Application {
	
	private static final File PALABRAS_FILE = new File("palabras.txt");
	private static final File PUNTUACIONES_FILE = new File("puntuaciones.txt");

	public static Stage primaryStage;
	
	private RootController rootController;
	
	@Override
	public void init() throws Exception {
			
		// cargar las palabras desde fichero
		List<String> palabras = new ArrayList<>();
		if (PALABRAS_FILE.exists()) {
			palabras.addAll(
					Files.readAllLines(
							PALABRAS_FILE.toPath(), 
							StandardCharsets.UTF_8
					)
			);
		}
		List<String> puntuaciones = new ArrayList<>();
		if (PUNTUACIONES_FILE.exists()) {
			puntuaciones.addAll(
					Files.readAllLines(
							PUNTUACIONES_FILE.toPath(), 
							StandardCharsets.UTF_8
					)
			);
		}
		rootController = new RootController(palabras, puntuaciones);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		AhorcadoApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("Ahorcado");
		primaryStage.setScene(new Scene(rootController.getView()));
		primaryStage.show();
		
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println("terminando!!");
		// guardar las palabras en un fichero
		final StringBuffer contenido = new StringBuffer();
		rootController.getPalabras().forEach(palabra -> contenido.append(palabra + "\n"));
		Files.writeString(
				PALABRAS_FILE.toPath(), 
				contenido.toString().trim(), 
				StandardCharsets.UTF_8, 
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
		);
		
		final StringBuffer contenidoPuntuaciones = new StringBuffer();
		rootController.getPuntuaciones().forEach(puntuacion -> contenidoPuntuaciones.append(puntuacion + "\n"));
		Files.writeString(
				PUNTUACIONES_FILE.toPath(), 
				contenidoPuntuaciones.toString().trim(), 
				StandardCharsets.UTF_8, 
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
		);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

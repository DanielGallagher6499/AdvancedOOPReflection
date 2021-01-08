package ie.gmit.sw;

import javafx.application.*;
/**
 * The Class Runner.
 */
public class Runner {
	
	/**
	 * This is the runner class. Once this is run the gui is loaded for the user and our program begins to load.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("[INFO] Launching GUI...");
		Application.launch(AppWindow.class, args);
	}
}
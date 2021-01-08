package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppWindow extends Application {
	private TextField txtFile; // A control, part of the View and a leaf node.

	@Override
	public void start(Stage stage) throws Exception { // This is a ***Template Method***

		/*
		 * The GUI is based on the ** Composite Pattern ** and is a tree of nodes, some
		 * of which are composite nodes (containers) and some are leaf nodes (controls).
		 * A stage contains 1..n scenes, each of which is a container window for other
		 * containers or controls.
		 * 
		 * JavaFX, Android and most GUI frameworks allow the creation of windows using a
		 * declarative format, typically XML. In the case of JavaFX, the syntax is
		 * called FXML. The idea of this (which is quite an old one!), is to separate
		 * the View from the Controller and Model (good practice) and allow
		 * non-programmers to create Views in XML using SceneBuilder or the equivalent
		 * that should integrate seamlessly with a suite of controllers and models
		 * designed by a programmer. In practice, I find that XML slows down development
		 * to a crawl and I prefer to programme the GUI from scratch, as it's much
		 * quicker, even if it is verbose.
		 */
		stage.setTitle("Daniel Gallagher | G00360986");
		stage.setWidth(800);
		stage.setHeight(600);

		/*
		 * The following is an example of the ** Observer Pattern**. Use a lambda
		 * expression to plant an EventHandler<WindowEvent> observer on the stage close
		 * button. The "click" will be queued and handled by an event dispatch thread
		 * when it gets a chance.
		 */
		stage.setOnCloseRequest((e) -> System.exit(0));

		/*
		 * Create the MVC View using the **Composite Pattern**. We can Build the GUi
		 * tree using one or more composites to create branches and one or more controls
		 * to handle user interactions. Composites and containers and controls are leaf
		 * nodes that the user can send gestures to.
		 * 
		 * The root container we will use is a VBox. All the subtypes of the class Pane
		 * are composite nodes and can be used as containers for other nodes (composites
		 * and leaf nodes). The choice of container is also an example of the **Strategy
		 * Pattern**. The Scene object is the Context and the layout container
		 * (AnchorPane, BorderPanel, VBox, FlowPane etc) is a concrete strategy.
		 */
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		// **Strategy Pattern**. Configure the Context with a Concrete Strategy
		Scene scene = new Scene(box);
		stage.setScene(scene);

		ToolBar toolBar = new ToolBar(); // A ToolBar is a composite node for Buttons (leaf nodes)

		Button btnQuit = new Button("Quit"); // A Leaf node
		btnQuit.setOnAction(e -> System.exit(0)); // Plant an observer on the button
		toolBar.getItems().add(btnQuit); // Add to the parent node and build the tree
		
		/*
		 * Add all the sub trees of nodes to the parent node and build the tree
		 */
		box.getChildren().add(getFileChooserPane(stage)); // Add the sub tree to the main tree
		box.getChildren().add(toolBar); // Add the sub tree to the main tree

		// Display the window
		stage.show();
		stage.centerOnScreen();
	}

	/*
	 * This method builds a TitledPane containing the controls for the file chooser
	 * part of the application. We could have created a specialised instance of the
	 * class TitledPane using inheritance and moved all of the method into its own
	 * class (OCP).
	 */
	private TitledPane getFileChooserPane(Stage stage) throws Exception, ClassNotFoundException, NoClassDefFoundError {
		VBox panel = new VBox(); // ** A concrete strategy ***

		txtFile = new TextField(); // A leaf node

		FileChooser fc = new FileChooser(); // A leaf node
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		Button btnOpen = new Button("Select File"); // A leaf node
		btnOpen.setOnAction(e -> { // Plant an observer on the button
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		Button btnProcess = new Button("Process"); // A leaf node
		btnProcess.setOnAction(e -> { // Plant an observer on the button
			File f = new File(txtFile.getText());
			System.out.println("[INFO] Processing file " + f.getName());
			try {
				processFile(f);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoClassDefFoundError e1) {
				e1.printStackTrace();
			}
		});

		ToolBar tb = new ToolBar(); // A composite node
		tb.getItems().add(btnOpen); // Add to the parent node and build a sub tree
		tb.getItems().add(btnProcess); // Add to the parent node and build a sub tree

		panel.getChildren().add(txtFile); // Add to the parent node and build a sub tree
		panel.getChildren().add(tb); // Add to the parent node and build a sub tree

		TitledPane tp = new TitledPane("Select File to Process", panel); // Add to the parent node and build a sub tree
		tp.setCollapsible(false);
		return tp;
	}
	
	@SuppressWarnings("rawtypes")
	private void processFile(File f) throws FileNotFoundException, IOException, ClassNotFoundException, NoClassDefFoundError {
		JarInputStream input = new JarInputStream(new FileInputStream(f));
		JarEntry next = input.getNextJarEntry();
		// read contents of a jar file
		while (next != null) {
			if (next.getName().endsWith(".class")) {
				String name = next.getName().replaceAll("/", "\\.");
				name = name.replaceAll(".class", "");
				if (!name.contains("$"))
					name.substring(0, name.length() - ".class".length());

				// load the class object dynamically into JVM
				try {
					Class cls = Class.forName(name);
					String clsName = cls.getName();
					System.out.println("Name: " + clsName);
					Package pack = cls.getPackage(); // Get the package
					System.out.println("Package: " + pack);
					boolean iface = cls.isInterface(); // Is it an interface?
					System.out.println("Interface: " + iface);
					Class[] interfaces = cls.getInterfaces(); //Get the set of interface it implements
					System.out.println("Interface: " + interfaces);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoClassDefFoundError e) {
					e.printStackTrace();
				} 
				
			}
			next = input.getNextJarEntry();
		}
		input.close();
	}
}
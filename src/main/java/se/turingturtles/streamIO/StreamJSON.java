package se.turingturtles.streamIO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.control.Alert;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.*;

public class StreamJSON {

    private ObjectMapper mapper;
    private static final String JSON_FILE_PATH = "turtleData.json";
    private static final String DATA_FILE_PATH = "data.db";


    public StreamJSON(){
        ProjectFactory factory = new ProjectFactory();
        mapper = factory.makeObjectMapper();
    }

    public void exportToJSON() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH));
            objectOutputStream.writeObject(ProjectManagementImp.getProject());
            objectOutputStream.flush();
            objectOutputStream.close();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
            mapper.writeValue(new File(JSON_FILE_PATH), ProjectManagementImp.getProject());
        }catch(IOException e){
            Alert exportError = new Alert(Alert.AlertType.ERROR);
            exportError.setTitle("Error!");
            exportError.setHeaderText("Export to json failed...");
            exportError.setContentText("An internal error occurred, the application was not able to export the data.");
            exportError.showAndWait();
        }
    }

    public void importFromJSON() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH));
            ProjectManagementImp.setProject((Project) objectInputStream.readObject());
            objectInputStream.close();
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            ProjectManagementImp.setProject(mapper.readValue(new File(JSON_FILE_PATH), Project.class));
        }catch (IOException | ClassNotFoundException e){
            Alert importError = new Alert(Alert.AlertType.ERROR);
            importError.setTitle("Error!");
            importError.setHeaderText("Import from json failed!");
            importError.setContentText("An internal error occurred, the application was not able to import the data.");
            importError.showAndWait();
        }
    }

}

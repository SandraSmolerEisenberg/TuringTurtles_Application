package se.turingturtles.streamIO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.scene.control.Alert;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class StreamJSON {

    private ObjectMapper mapper;
    private static final String JSON_FILE_PATH = "turtleData.json";

    public StreamJSON(){
        ProjectFactory factory = new ProjectFactory();
        mapper = factory.makeObjectMapper();


    }

    public void exportToJSON() {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            ProjectManagementImp.setProject(mapper.readValue(new File(JSON_FILE_PATH), Project.class));
        }catch (IOException e){
            Alert importError = new Alert(Alert.AlertType.ERROR);
            importError.setTitle("Error!");
            importError.setHeaderText("Import from json failed!");
            importError.setContentText("An internal error occurred, the application was not able to import the data.");
            importError.showAndWait();
        }
    }

}

package se.turingturtles.streamIO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.File;
import java.io.IOException;

public class StreamJSON {

    private ObjectMapper mapper;

    public StreamJSON(){
        ProjectFactory factory = new ProjectFactory();
        mapper = factory.makeObjectMapper();
    }

    public void exportToJSON(String filePath) throws IOException {
        // Doing error handling in controller
        mapper.writeValue(new File(filePath), ProjectManagementImp.getProject());
    }

    public void importFromJSON(String filePath) throws IOException{
        //  DOing error handling in controller
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ProjectManagementImp.setProject(mapper.readValue(new File(filePath), Project.class));
    }

}

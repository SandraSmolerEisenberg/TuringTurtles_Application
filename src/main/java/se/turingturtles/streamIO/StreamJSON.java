package se.turingturtles.streamIO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.File;
import java.io.IOException;

public class StreamJSON {

    public void exportToJSON() throws IOException {



        ObjectMapper mapper = new ObjectMapper();

        // We need to catch the exception as well!
        mapper.writeValue(new File("project.json"), ProjectManagementImp.getProject());


    }


    public void importFromJSON() throws IOException{

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProjectManagementImp.setProject(mapper.readValue(new File("project.json"), Project.class));

    }

}

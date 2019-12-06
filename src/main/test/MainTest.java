import se.turingturtles.ProjectManagement;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

public class MainTest {
/*

    private void testProjectManagement() {
        ProjectFactory factory = new ProjectFactory();
        ProjectManagement projectManagement;
        projectManagement = factory.makeProjectManagement();
        System.out.println("-----Testing-----");
        System.out.println("Testing method Create Project: ");
        System.out.println("Result: "+ ProjectManagementImp.getProject().toString());
        System.out.println("Testing method Create Task: ");
        projectManagement.createTask("Task1", 47,10);
        projectManagement.createTask("Task2", 47,20);
        System.out.println("Result: "+ProjectManagementImp.getProject().getTasks() + "Array size: " + ProjectManagementImp.getProject().getTasks().size());
        System.out.println("Testing method Create Team Member: ");
        projectManagement.createMember("TeamMember1", 1, 1000);
        projectManagement.createMember("TeamMember2", 2, 2000);
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers() + "Array size: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Testing method Assign to Task: ");
        projectManagement.assignTask(ProjectManagementImp.getProject().getTeamMembers().get(0),ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers() + "Team Member: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers().get(0).getTasks());
        System.out.println("Testing method Assign to Task: ");
        projectManagement.assignTask(ProjectManagementImp.getProject().getTeamMembers().get(1), ProjectManagementImp.getProject().getTasks().get(1));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().get(1).getTasks());
        System.out.println("Testing method retrieve member tasks: ");
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().get(0).getTasks());
        System.out.println("Testing method retrieve Team members: ");
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers() + "Arrays size: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Testing method find all team member: ");
        System.out.println("Result: " + projectManagement.findTeamMember(1));
        System.out.println("Testing method find Task: ");
        System.out.println("Result: " + projectManagement.findTask("Task1"));
        System.out.println("Testing method retrieve all tasks: ");
        System.out.println("Result: " + projectManagement.retrieveTasks());
        System.out.println("Testing method complete Task: ");
        System.out.println("Task status: " + ProjectManagementImp.getProject().getTasks().get(0).getCompletion());
        projectManagement.completeTask(ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTasks().get(0).getCompletion() );
        System.out.println("Testing method remove Task: ");
        projectManagement.removeTask(ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result Project: " + ProjectManagementImp.getProject().getTasks().size() );
        System.out.println("Result Member: " + ProjectManagementImp.getProject().getTeamMembers().get(0).getTasks() );
        System.out.println("Testing method remove Member: ");
        projectManagement.removeMember(ProjectManagementImp.getProject().getTeamMembers().get(1));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Result Task: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers());
        System.out.println("Testing Time spent on project:");
        System.out.println(ProjectManagementImp.getProject().getStartWeek());
        System.out.println("Result: " + projectManagement.timeSpentOnProject());
        System.out.println("Testing method time spent by team member: ");
        System.out.println("Result: " + projectManagement.timeSpentByTeamMember(ProjectManagementImp.getProject().getTeamMembers().get(0)));
        System.out.println("Testing create Risk Method: ");
        projectManagement.createRisk("Risk1", 10,1);
        System.out.println("Result: " + ProjectManagementImp.getProject().getRisk().get(0) );
        projectManagement.createRisk("Risk2", 20,20);
        System.out.println("Testing retrieve all Risk: ");
        System.out.println("Result: " + projectManagement.getProjectRisks());


    }
*/

}

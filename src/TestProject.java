
public class TestProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Rmb to del file in main proj b4 running
		
		ProjectHandler test = new ProjectHandler();
		Event pseudoEvent = new Event("eatpieday", "20 Nov 2015", "08:00", "18:00", "i love pies");
		Event pseudoEvent2 = new Event("eatpieday2", "21 Nov 2015", "08:00", "18:00", "i love pies a lot");
		Event pseudoEvent3 = new Event("eatpieday3", "21 Nov 2015", "08:00", "18:00", "i love pies a lot");
		
		System.out.println(test.deleteProject("projectdiva2"));
		System.out.println(test.createProject("projectdiva3"));
		System.out.println(test.deleteProject("projectdiva3"));
		System.out.println(test.createProject("projectdiva2"));
		
		//System.out.println(test.createProject("projectdiva2"));
		System.out.println(test.addProjectEvent(pseudoEvent, "projectdiva2"));
		System.out.println(test.addProjectEvent(pseudoEvent2, "projectdiva2"));
		System.out.println(test.addProjectEvent(pseudoEvent3, "projectdiva2"));
		
		//System.out.println(test.addProjectEvent(pseudoEvent, "projectdiva3"));
		System.out.println(test.viewProjectTimeline("projectdiva2"));
		System.out.println();
		System.out.println(test.viewProjectTimeline(2));
		System.out.println(test.viewProjectTimeline(0));
		
		// Open file to check edited, lazy to view again. Too much scrolling involved.
		//System.out.println(test.editProjectEvent(pseudoEvent, 1, "test","projectdiva2"));
		System.out.println("C1"+test.viewProjectTimeline(0));
		test.editProjectEvent(2, 2, "21 October 2015", "projectdiva2");
		System.out.println("C2"+test.viewProjectTimeline(0));
		test.editProjectEvent(2, 3, "22 November 2015", "projectdiva2");
		System.out.println("C3"+test.viewProjectTimeline(0));
		test.editProjectEvent(2, 4, "09:00", "projectdiva2");
		System.out.println("C4"+test.viewProjectTimeline(0));
		test.editProjectEvent(2, 5, "20:00", "projectdiva2");
		System.out.println("C5"+test.viewProjectTimeline(0));
		
		System.out.println(test.viewProjectTimeline(0));
		
		System.out.println(test.viewProjectTimeline(0));
		System.out.println(test.deleteProjectEvent(0, "projectdiva2"));
		System.out.println(test.deleteProjectEvent(pseudoEvent2.getName(), "projectdiva2"));
	}

}

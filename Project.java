package ProjectManagement;

import java.util.ArrayList;

public class Project implements Comparable<Project>{
	String name;
	int budget , priority;
	ArrayList<Job> incompleted = new ArrayList<Job>();
	ArrayList<Job> completed = new ArrayList<Job>();
	public int count;
	public Project(String name , int budget, int priority) {
		this.name=name;
		this.priority=priority;
		this.budget=budget;
	}
	@Override
	public int compareTo(Project project) {
		
		return project.count - this.count;
	}
	
}

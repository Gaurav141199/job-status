package ProjectManagement;

public class Job implements Comparable<Job> {
	String name ;
	Project project;
	User user;
	public int runtime;
	public int donetime=0;
	public int count;
	public int intime;
	public int time;
	public Job(String name ,Project project,User user ,int runtime) {
		this.name=name;
		this.project=project;
		this.user=user;
		this.runtime=runtime;
	}
    @Override
    public int compareTo(Job job) {
    	if(this.project.priority-job.project.priority!=0)
    		return this.project.priority-job.project.priority;
    else {
    	return job.time-this.time;
    }
    }
}
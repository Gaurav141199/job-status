package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {


    public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        //URL url = Scheduler_Driver.class.getResource("INP");
        //file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

            switch (cmd[0]) {
                case "PROJECT":
                    handle_project(cmd);//done
                    break;
                case "JOB":
                    handle_job(cmd);//done
                    break;
                case "USER":
                    handle_user(cmd[1]);//done
                    break;
                case "QUERY":
                    handle_query(cmd[1]);//done
                    break;
                case "":
                    handle_empty_line();//done
                    break;
                case "ADD":
                    handle_add(cmd);//done
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();//done

        print_stats();//done

    }
    
    ArrayList<Job> jobs = new ArrayList<Job>();
    ArrayList<Job> jobnot = new ArrayList<Job>();
    ArrayList<User> userlist = new ArrayList<User>();
    RBTree<String,Project> projecttree = new RBTree<String,Project>();
    MaxHeap<Job> jobheap = new MaxHeap<Job>();
    static int globaltime = 0;
    static int jobesdone=0;
    int global=0;
    int h=0;
    int z=0;
    public void handle_user(String name) {
    	System.out.println("Creating user");
    	int size = userlist.size();
    	for (int i = 0; i<size;i++) {
    		if(((userlist.get(i)).name).equals(name)) return;
    	}
    	User user= new User(name);
    	userlist.add(user);
    }
    //done
    
    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	Project project = new Project(cmd[1], Integer.valueOf(cmd[3]),Integer.valueOf(cmd[2]));
    	projecttree.insert(cmd[1], project);
    	z++;
    	project.time=z;
    }
    //done
    
    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	String name = cmd[1];
    	Project project = null;
    	User user = null;
    	int time =0;
    	for (int i = 0; i<userlist.size();i++) {
    		if(((userlist.get(i)).name).equals(cmd[3])) user = userlist.get(i);
    	}
    	if(user==null) {
    		System.out.println("No such user exists: "+cmd[3]);
    		return;
    	}
    	RedBlackNode search =  projecttree.search(cmd[2]);
    	if(search!=null) {
    		project = (Project) search.object;
    		time = Integer.parseInt(cmd[4]);
    	}
    	if (project==null) {
    		System.out.println("No such project exists. "+cmd[2]);
    		return;
    	}
    	Job job1 = new Job(cmd[1],project,user,time);
    	h++;
    	job1.time=h;
    	job1.intime = globaltime;
    	global++;
    	job1.count=global;
    	jobheap.insert(job1);
    }
    
    
    public void run() {
        // till there are JOBS
        schedule();
    }


    public void run_to_completion() {
    	while(jobheap.Heap.size()!=1) {
    	System.out.println("Running code");
    	int size= jobheap.Heap.size();
    	int size3 =0;
    	for (int i= 1 ; i<size;i++) {
    		size3= size3+ jobheap.Heap.get(i).size();
    	}
    	System.out.println("Remaining jobs: " + size3);
    	int i =0;
    	Job job1 = jobheap.extractMax();
    	while(i<size3) {
    	if(job1.runtime<=job1.project.budget) {
    		jobs.add(job1);
        	job1.user.count++;
    		globaltime = globaltime +job1.runtime;
    		job1.project.budget = job1.project.budget - job1.runtime;
    		job1.donetime=globaltime;
    		System.out.println("Executing: "+job1.name +" from: "+job1.project.name);
    		System.out.println("Project: "+job1.project.name+" budget remaining: "+job1.project.budget);
    		System.out.println("System execution completed");
    		break;
    	}
    	
    	else {
    		jobnot.add(job1);
    		System.out.println("Executing: "+job1.name +" from: "+job1.project.name);
    		System.out.println("Un-sufficient budget.");
    		job1.project.incompleted.add(job1);
    		job1 = jobheap.extractMax();
    		i++;
//    		continue;
    	}
    	}
    	}
    }




    
    public void handle_query(String key) {
    	System.out.println("Querying");
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if(key.equals(jobheap.Heap.get(i).get(j).name)) {
    				System.out.println(key+": NOT FINISHED");
    				return;
    			}
    		}
    	}
    	for(int i=0 ; i <jobs.size();i++) {
    		if(key.equals(jobs.get(i).name)) {
    			System.out.println(key+": COMPLETED");
    			return;
    		}
    	}
    	System.out.println(key+": NO SUCH JOB");
    }

    public void handle_empty_line() {
    	System.out.println("Running code");
    	int size = jobheap.Heap.size();    	    	    	
    	int size2 =0;
    	for (int i= 1 ; i<size;i++) {
    		size2= size2+ jobheap.Heap.get(i).size();
    	}
    	System.out.println("Remaining jobs: " + size2);
    	int i =0;
    	Job job1 = jobheap.extractMax();
    	int size1 = jobheap.Heap.size();
    	while(i<size2) {
    	if(job1.runtime<=job1.project.budget) {
    		globaltime = globaltime +job1.runtime;
    		job1.project.budget = job1.project.budget - job1.runtime;
        	jobs.add(job1);
        	job1.user.count++;
        	job1.donetime=globaltime;
    		job1.project.completed.add(job1);
    		System.out.println("Executing: "+job1.name +" from: "+job1.project.name);
    		System.out.println("Project: "+job1.project.name+" budget remaining: "+job1.project.budget);
    		break;
    	}
    	else {
    		System.out.println("Executing: "+job1.name +" from: "+job1.project.name);
    		System.out.println("Un-sufficient budget.");
    		jobnot.add(job1);
    		job1.project.incompleted.add(job1);
    		job1 = jobheap.extractMax();
    		i++;
    		continue;
    	}
    	}
    	System.out.println("Execution cycle completed");
    	
    }


    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	RedBlackNode<String, Project> search = projecttree.search(cmd[1]);
    	if(search!=null) {
    		(search.object).budget = (search.object).budget + Integer.valueOf(cmd[2]);
    	}
    	ArrayList<Job> list = (search.object).incompleted;
//    	Job job2 = search.object.incompleted.get(0);
    	int siz= list.size();
    	for (int i =0; i<siz;i++) {
    		Job job1= list.get(0);
    		for(int h =0 ;h<jobnot.size() ;h++) {
    			if(job1.equals(jobnot.get(h))) {
    				jobnot.remove(h);
    				break;
    			}
    		}
    		list.remove(0);
    		jobheap.insert(job1);
    	}
    	
    	
    	
    }

    public void sort(ArrayList<Job> list1) {
    	int size= list1.size();
    	for(int i=0; i<size;i++) {
    		for(int j=i+1; j<size;j++) {
    			if(list1.get(i).compareTo(list1.get(j))<0) {
    				Job temp  =list1.get(i);
    				list1.set(i, list1.get(j));
    				list1.set(j, temp);
    			}
    		}
    	}
    }
    
    public void sortuser(ArrayList<User> list1){
    	int size= list1.size();
    	for(int i=0; i<size;i++) {
    		for(int j=i+1; j<size;j++) {
    			if((list1.get(i).count)-(list1.get(j).count)<0) {
    				User temp  =list1.get(i);
    				list1.set(i, list1.get(j));
    				list1.set(j, temp);
    			}
    		}
    	}
    }
    public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	int size = jobs.size();
    	System.out.println("Total jobs done: "+size);
    	for(int i =0 ; i<size; i++) {
    		Job job1 = jobs.get(i);
    		System.out.println("Job{user='"+job1.user.name+"', project='"+job1.project.name+"', jobstatus=COMPLETED, execution_time="+job1.runtime+", end_time="+job1.donetime+", name='"+job1.name+"'}");
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	sort(jobnot);
    	int size1 = jobnot.size();
    	for(int j = 0 ;j<size1;j++) {
    			Job job1 = jobnot.get(j);
    			System.out.println("Job{user='"+job1.user.name+"', project='"+job1.project.name+"', jobstatus=REQUESTED, execution_time="+job1.runtime+", end_time=null"+", name='"+job1.name+"'}");
    	}
    	System.out.println("Total unfinished jobs: "+size1);
    	System.out.println("--------------STATS DONE---------------");
    }


    public void schedule() {
    	
    }
    
    public ArrayList<Job> handle_new_user(String[] cmd) {
    	int in = Integer.valueOf(cmd[2]);
    	int out= Integer.valueOf(cmd[3]);
    	ArrayList<Job> list1 = new ArrayList<Job>();
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if(((jobheap.Heap.get(i).get(j).user.name).equals(cmd[1])) &&  (jobheap.Heap.get(i).get(j).intime>=in) && (jobheap.Heap.get(i).get(j).intime<=out)) {
    				Job job1 = jobheap.Heap.get(i).get(j);
    				list1.add(job1);
    			}
    		}
    	}
    	int h = jobnot.size();
    	for(int i=0;i<h;i++) {
    		if(((jobnot.get(i).user.name).equals(cmd[1])) &&  (jobnot.get(i).intime>=in) && (jobnot.get(i).intime<=out)) {
    		list1.add(jobnot.get(i));
    		}
    	}
        return list1;
    }
    
    private ArrayList<Job> handle_new_project(String[] cmd) {
    	int in = Integer.valueOf(cmd[2]);
    	int out= Integer.valueOf(cmd[3]);
    	ArrayList<Job> list1 = new ArrayList<Job>();
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if(((jobheap.Heap.get(i).get(j).project.name).equals(cmd[1])) &&  (jobheap.Heap.get(i).get(j).intime>=in) && (jobheap.Heap.get(i).get(j).intime<=out)) {
    				Job job1 = jobheap.Heap.get(i).get(j);
    				list1.add(job1);
    			}
    		}
    	}
    	int h = jobnot.size();
    	for(int i=0;i<h;i++) {
    		if(((jobnot.get(i).project.name).equals(cmd[1])) &&  (jobnot.get(i).intime>=in) && (jobnot.get(i).intime<=out)) {
    		list1.add(jobnot.get(i));
    		}
    	}
        return list1;
    } 
    
    private ArrayList<Job> handle_new_projectuser(String[] cmd) {
    	int in = Integer.valueOf(cmd[3]);
    	int out= Integer.valueOf(cmd[4]);
    	ArrayList<Job> list1 = new ArrayList<Job>();
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if(((jobnot.get(i).project.name).equals(cmd[1])) && ((jobnot.get(i).user.name).equals(cmd[2]))  && (jobnot.get(i).intime>=in) && (jobnot.get(i).intime<=out)) {
    				Job job1 = jobheap.Heap.get(i).get(j);
    				list1.add(job1);
    			}
    		}
    	}
    	int h = jobnot.size();
    	for(int i=0;i<h;i++) {
    		if(((jobnot.get(i).project.name).equals(cmd[1])) && ((jobnot.get(i).user.name).equals(cmd[2]))  && (jobnot.get(i).intime>=in) && (jobnot.get(i).intime<=out)) {
    		list1.add(jobnot.get(i));
    		}
    	}
    	sort(list1);
        return list1;
    } 
    
    public ArrayList<Job> handle_new_priority(String s) {
    	int h = Integer.valueOf(s);
    	ArrayList<Job> list1 = new ArrayList<Job>();
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if((jobheap.Heap.get(i).get(j).project.priority)>=h) {
    				Job job1 = jobheap.Heap.get(i).get(j);
    				list1.add(job1);
    			}
    		}
    	}
    	int h1 = jobnot.size();
    	for(int i=0;i<h1;i++) {
    		if(jobnot.get(i).project.priority>=h) {
    		list1.add(jobnot.get(i));
    		}
    	}
        return list1;
    }
    
    public ArrayList<User> timed_top_consumer(int top) {
    	sortuser(userlist);
    	ArrayList<User> list1 = new ArrayList<User>();
    	for(int i=0;i<top;i++) {
    		list1.add(userlist.get(i));
    	}
        return list1;
    }
    
    public void timed_flush(int waittime) {
    	for(int i=1;i<jobheap.Heap.size();i++) {
    		for(int j=0;j<jobheap.Heap.get(i).size();j++) {
    			if(globaltime-jobheap.Heap.get(i).get(j).intime>=waittime && jobheap.Heap.get(i).get(j).project.budget>=jobheap.Heap.get(i).get(j).runtime) {
    				jobs.add(jobheap.Heap.get(i).get(j));
    				jobheap.Heap.get(i).get(j).project.budget =jobheap.Heap.get(i).get(j).project.budget-jobheap.Heap.get(i).get(j).runtime;
    				jobheap.Heap.get(i).remove(j);
    				j--;
       			}
    		}
    	}
    }   
}

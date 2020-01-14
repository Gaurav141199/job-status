package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
    	name=trim;
    	marks=parseInt;
    }

    public int getmarks() {
    	return marks;
    }
    @Override
    public int compareTo(Student student) {
        return marks-student.getmarks();        	
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
    	return "Student{name='"+name+"', marks="+marks+"}";
    }
}

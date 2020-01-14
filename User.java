package ProjectManagement;

public class User implements Comparable<User> {
	public String name;
	public int count;
	public User(String name) {
		this.name=name;
	}
	
	@Override
    public int compareTo(User user) {
        return 0;
    }
}

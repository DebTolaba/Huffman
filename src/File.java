import java.util.ArrayList;

public abstract class File {
	protected String path_file;
	
	public File(String path_file) {
		this.path_file = path_file;
	}
	abstract public ArrayList<Information> readFile ();
    abstract public String writeFile (ArrayList<Information> list);
}
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JProgressBar;

public class CompressFile extends File{
	private JProgressBar progressBar;
	
	public CompressFile(String path_file, JProgressBar progressBar) {
		super(path_file);
		this.progressBar = progressBar;
	}
	
	// lee un archivo y obtiene los elementos con su frecuencia 
	@Override
	public ArrayList<Information> readFile() {
		RandomAccessFile file;
		
	    int element,position,frequency;
	    float progress, increment;
	    ArrayList<Information> list = new ArrayList<Information>();
	    
	    try {
	        file = new RandomAccessFile(path_file,"r");
	        
	        element = file.read(); frequency = 1; // primer elemento del archivo
	        (list).add(new Information((char)element,frequency)); // agrega el elemento en el Arraylist list
	      
	        progress=0;
	        increment=(float)2300/file.length();
	        
	        // lee el archivo
	        for (element = file.read(); element != -1; element = file.read() ) {
	            position = searchElement((char)element,list);
	           
	            if(position == list.size()){ // Si el elemento no se encuentra en el Arraylist list entonces se agrega
	            	frequency = 1;
	                (list).add(new Information((char)element,frequency));
	                
	            }else{ // Si el elemento se encuentra en el Arraylist list entonces se incrementa su frecuencia
	                frequency = (list.get(position)).getFrequency();
	                (list.get(position)).setFrequency(frequency+1);
	            }
	            
	            
	            if(progress+increment<2300){
	            	progress += increment;
					progressBar.setValue((int)progress);
				}
	        }  
	    file.close();
	    
	    }catch(IOException io) { System.err.println("Problem " + path_file); }
	    
	    return list;
	}
	
	// escribe un nuevo archivo .huffman con los datos necesarios para luego poder descomprimirlo
	@Override
	public String writeFile(ArrayList<Information> list) {
		RandomAccessFile file;
		RandomAccessFile newFile;
		
		int position, element;
		float progress, increment;
		String code="";
		String binary="";
		String outFileName = "";
		
		try {
	    	file = new RandomAccessFile(path_file,"r");
	    	
	    	outFileName = createfileName();
	    	newFile  = new RandomAccessFile(outFileName,"rw");
	   		
	    	// escribe en el nuevo archivo los elementos con su frecuencia
	    	for (int i=0; i< list.size() ; i++){
	    		newFile.write(list.get(i).getElement());
	    		newFile.writeInt(list.get(i).getFrequency());
	    	}
	    	
	    	newFile.write(list.get(0).getElement()); // escribe el primer elemento del Arraylist list
	    	
	        progressBar.setValue(3200);
	        progress=3200;
			increment = (float)6800/file.length();
			
			// lee el archivo
	   		for (element =file.read(); element != -1; element = file.read()) { 
	   			position = searchElement((char)element,list); // busca el elemento en el Arraylist list
	            code = list.get(position).getCode(); // obtiene el codigo correspondiente al elemento 
	            
	            if (progress+ increment < 9950) {
	            	progress += increment;
   		    		progressBar.setValue((int)progress);
				}
	            
	            // escribe en el nuevo archivo un caracter que corresponde a un binario de longitud 8
	            for(int i=0;i<code.length();i++){
	            	
	            	if(binary.length()!=8){ 
	            		binary += code.charAt(i);
	            	}else{
	            		newFile.write(convertBinaryToChar(binary));  
	            		binary="";
	            		binary += code.charAt(i);
	            	}
	            }
	     	}
	   		
	   		int length = binary.length(); 
	   		newFile.write(convertBinaryToChar(binary)); // escribe el caracter correspodiente al ultimo binario
	   		newFile.write(length); // escribe la longitud del ultimo binario
	   		newFile.close(); 
	   		file.close();
			
		}catch(IOException io) { System.err.println("Problem " + path_file); }
		
		return outFileName;
	}
	
	public String createfileName() {
		return path_file + ".huffman";
	}
	
	public char convertBinaryToChar(String binary){
		return (char)Integer.parseInt(binary,2);
	}
	 
	public int searchElement(char element, ArrayList<Information> list){
	    int position = 0;
	    boolean found = false;
	    while(!found && position<list.size()){
	    	if(list.get(position).getElement() == element){
	    		found = true;
	    	}else{
	    		position++;
	    	}
	    }
	    
	    return position;
	 }

}

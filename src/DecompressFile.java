import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JProgressBar;

public class DecompressFile extends File{
	private long positionFilePointer;
	private RandomAccessFile file;
	private JProgressBar progressBar;
	
	public DecompressFile(String path_file, JProgressBar progressBar) {
		super(path_file);
		this.progressBar = progressBar;
	}
	
	// lee el archivo .huffman y obtiene los datos necesarios para descomprimirlo
	@Override
	public ArrayList<Information> readFile() {
		
	    int element,frequency,bandera;
	    ArrayList<Information> list = new ArrayList<Information>();
		
	    try {
			file = new RandomAccessFile(path_file,"r");
			bandera = element = file.read(); // primer elemento del archivo
    		
			if(element != -1) {
    			do {
    				
    				frequency = file.readInt();
    				Information info = new Information((char)element,frequency);
	    			list.add(info);
	    			element = file.read();
	    			
	    		}while(element != bandera);	
    		
			}else { System.out.println ("file empty"); }
    		
    	positionFilePointer = file.getFilePointer(); // posicion de puntero
    	file.close();
		
	    }catch(IOException io) { System.err.println("Un poblema con " + path_file); }
		
	    return list;
	}
	
	// escribe un nuevo archivo descomprimido  
	@Override
	public String writeFile(ArrayList<Information> list) {
		
		String binary="";
		String previousBinary="";
		String newBinary="";
		String outFileName = "";
	    int element,position;
	    float progress, increment;
	    
		try {
			file = new RandomAccessFile(path_file,"r");
    		outFileName = createfileName();
    		
    		RandomAccessFile newfile = new RandomAccessFile(outFileName,"rw");
    		file.seek(positionFilePointer); //desplazamiento del puntero
    		
    		progress =2250;
    		increment=(float)7700/file.length();
    		
    		//lee el archivo
    		for (element =file.read(); element != -1; element = file.read()) {
    			positionFilePointer++;
    			
    			if(progress+increment<9950){
    				progress += increment;
   		    		progressBar.setValue((int)progress);
    			}
    			
    			if(positionFilePointer == file.length()-1){ // indica si es el ultimo binario para no ponerle ceros adelante
    				
    				binary = Integer.toBinaryString(element);  // convierte el elemento a binario
    				
    				String  incomplete = "" + file.read();
					Integer length = Integer.parseInt(incomplete); // obtiene la longitud del binario
					
					for (int i = binary.length(); i < length; i++){
						binary = "0" + binary;	
					}
					file.seek(file.length()+1);

    			}else{
    				binary = convertCharToBinary((char)element); 
    			}
    			
    			// Une el binario (binary) con el binario anterior (previousBinary)
    			previousBinary += binary;
   
                for(int i=0;i<previousBinary.length();i++){ // recorre el binario (previousBinary)
                	newBinary += previousBinary.charAt(i);   // crea el nuevo binario (newBinary)
    		    	position = searchCode(newBinary,list); // busca el nuevo binario (newBinary) en el Arraylist list
    		    	   
    		    	if(position<list.size()){ // indica que encontro newBinary en list
    		    		newfile.write(list.get(position).getElement());// escribe el elemento que corresponde con el codigo binario
    		    		newBinary="";
    		    	}
    		    } 
                previousBinary = newBinary; // vuelve a empezar el ciclo
                newBinary="";
    	}
    	newfile.close();
    	file.close();
    	
		}catch(IOException io) { System.err.println("Problem " + path_file); }
		
		return outFileName;
    }
	
	public String createfileName() {
		String nameFile = path_file.replace(".huffman", "");
		
		if(nameFile.contains(".")) {
			int positionPoint = nameFile.lastIndexOf(".");
			nameFile = nameFile.substring(0,positionPoint) + "_descompress" + nameFile.substring(positionPoint);
		}else {
			nameFile += "_descompress";
		}
		return nameFile;
	}

	public String convertCharToBinary(char charb){
		String binary = Integer.toBinaryString((int)charb);
	    
		if(binary.length()!=8){   // completa con ceros a la izquierda del binario 
	    	while(binary.length()!=8){
	    		binary="0"+binary;
	    	}
	    }
	    return binary;
	}
	
	public int searchCode(String code,ArrayList<Information> list){
	    int position = 0;
	    boolean found = false;
	   
	    while(!found && position<list.size()){
	
	    	if(list.get(position).getCode().equals(code)){
	    		found = true;
	    	}else{
	    		position++;
	    	}
	    }
	  
	    return position;
	 }
	
	
}

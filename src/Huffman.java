import java.util.ArrayList;
import javax.swing.JProgressBar;

public class Huffman {
	private ArrayList<Information> list;
	private JProgressBar progressBar;
	private String outFileName;
  
    public Huffman(){ 
        this.list = new ArrayList<Information>();
        this.outFileName = "";
    }
    
    // Comprime un archivo
    public void compress(String path_file){
    	CompressFile file = new CompressFile(path_file,progressBar);
    	
    	this.list = file.readFile();
    	progressBar.setValue(2300);
    	
    	BinaryTreeNode HuffmanTree = TreeHuffman();
    	progressBar.setValue(2500);
    	
    	createCode(HuffmanTree,"");
    	progressBar.setValue(2700);
    	
    	this.outFileName = file.writeFile(this.list);
    	progressBar.setValue(10000);
    }
    
    // Descomprime un archivo .huffman
    public void descompress(String path_file) {
    	DecompressFile file = new DecompressFile(path_file,progressBar);
    	
    	this.list = file.readFile();
    	progressBar.setValue(750);
    	
    	BinaryTreeNode HuffmanTree = TreeHuffman();
    	progressBar.setValue(1500);
    	
    	createCode(HuffmanTree,"");
    	progressBar.setValue(2250);
    	
    	this.outFileName = file.writeFile(this.list);
    	progressBar.setValue(10000);
    }
    
    // Crea el arbol binario y devuelve la raiz
    public BinaryTreeNode TreeHuffman () {
    	PriorityQueue Pqueue = new PriorityQueue();
    	BinaryTreeNode HuffmanTree;

    	for (int i = 0; i < list.size() ; i++){
    		Pqueue.insertNode( new BinaryTreeNode (list.get(i)));
    	}
    	
    	while (!Pqueue.oneNode()){
    		BinaryTreeNode firstTreeNode , secondTreeNode ;
    		firstTreeNode = (BinaryTreeNode) Pqueue.removeNode();
    		secondTreeNode = (BinaryTreeNode) Pqueue.removeNode();
      
    		int newFrequency = ( (Information)firstTreeNode.getNodeInfo() ).getFrequency() + ((Information)secondTreeNode.getNodeInfo()).getFrequency() ;   
    		Pqueue.insertNode(  new BinaryTreeNode ( new Information (newFrequency) ,  firstTreeNode  ,  secondTreeNode )  );
    	}
    
    	HuffmanTree = (BinaryTreeNode)Pqueue.removeNode();
    	return HuffmanTree;
    }
    
    // Crea el codigo de cada elemento del Arraylist list
    public void createCode (BinaryTreeNode treeNode ,String code){
        if (treeNode.getLeftChild() == null && treeNode.getRightChild() == null){ 
            int position= searchElement(((Information)treeNode.getNodeInfo()).getElement());
            list.get(position).setCode(code);
        }else {                                   
            createCode ( treeNode.getLeftChild(), code+0);
            createCode ( treeNode.getRightChild() ,code+1) ;
        }
    }
    
    public int searchElement(char element){
        int position = 0;
        boolean found = false;
        Information file_info;
        while(!found && position<list.size()){
            file_info = list.get(position);
            if(file_info.getElement() == element){
                found = true;
            }else{
                position++;
            }
        }
        return position;
    }
    
    public void setProgressBar (JProgressBar progressB) {
    	this.progressBar = progressB ;
    }
    
    public String getOutFileName() {
    	return this.outFileName;
    }
    
}

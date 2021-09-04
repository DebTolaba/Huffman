import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener{
	private Container container;
	private JFileChooser fileChooser;
	private JButton search, start, message;
	private JTextField textField;
	private JTextPane details;
	private String path_file, detailsText ;
	private JRadioButton optionCompress, optionDecompress ;
	private boolean compress = true;
	private JProgressBar progressBar;
	private Thread hilo;	
	private Huffman huffman;
	private File file;
	

	public Window(){

		setTitle("HUFFMAN");		
		setSize(475,600);
		setLocationRelativeTo(null);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        fileChooser=new JFileChooser();
        
        container=getContentPane();
        container.setLayout(null);
		
		search = new JButton();
		search.setText("BUSCAR");
		search.setBounds(30,150,90, 35);
	    search.addActionListener(this); 
	    search.setOpaque(false);
		
	    textField = new JTextField();
	    textField.setForeground(Color.BLACK);
	    textField.setBounds(130, 150, 310, 35);
	    textField.setEditable(true);
	    textField.setHorizontalAlignment(JTextField.LEFT);
	    textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    textField.setCaretColor(Color.BLACK);
	    textField.setToolTipText("Ingrese la Dirección");
	    textField.setSelectionColor(Color.WHITE);
	    textField.setSelectedTextColor(Color.BLUE);
	    textField.setOpaque(false);
		   
	    start= new JButton();
	    start.setText("INICIAR");
	    start.setBounds(198,320,90, 35);
	    start.addActionListener(this);  
	  
	    ButtonGroup buttonGroup = new ButtonGroup();
	    
	    optionCompress = new JRadioButton();
	    optionCompress.setSelected(true);
	    optionCompress.setForeground(Color.BLACK);
	    optionCompress.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    optionCompress.setBounds(30, 200, 120, 25);	
	    optionCompress.setText(" COMPRIMIR");
	    optionCompress.addActionListener(this);
	    
	    optionDecompress = new JRadioButton();
	    optionDecompress.setForeground(Color.BLACK);
	    optionDecompress.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    optionDecompress.setBounds(30, 230, 140, 25);
	    optionDecompress.setText(" DESCOMPRIMIR");
	    optionDecompress.addActionListener(this);
	    
	    buttonGroup.add(optionCompress);
	    buttonGroup.add(optionDecompress);
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(30, 275, 410, 20);
	    progressBar.setVisible(false);
	    progressBar.setBackground(Color.WHITE);
	    progressBar.setForeground(Color.BLUE);
	    progressBar.setStringPainted(true);			
	    progressBar.setMaximum( 10000 ); 
	    progressBar.setBorderPainted(false);
	       
	    message= new JButton();
	    message.setBounds(30, 275, 410, 30);
	    message.setForeground(Color.BLACK);
	    message.setOpaque(false);
	    message.setContentAreaFilled(false); 
	    message.setBorderPainted(false);
	    message.setFocusable(false);
	    
	    details = new JTextPane();
	    details.setForeground(Color.BLACK);
	    details.setBounds(30, 410, 410, 135);
	    details.setEditable(false);
	    details.setOpaque(false);
	        
	    SimpleAttributeSet attribs = new SimpleAttributeSet();
	    StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
	    StyleConstants.setFontFamily(attribs, "Tahoma");
	    StyleConstants.setFontSize(attribs,14);
	    details.setParagraphAttributes(attribs,true);
	    
		container.add(textField);
	    container.add(search);
	    container.add(start);
	    container.add(optionCompress);
	    container.add(optionDecompress);
	    container.add(progressBar);
	    container.add(message);
	    container.add(details);
	}
	
	// evento de los botones
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==search){ 
			fileChooser.showOpenDialog(this);
			file = fileChooser.getSelectedFile();
			message.setText(null);
			
			if(file!=null) {
				path_file = file.getPath();
				textField.setText(path_file);
				
				detailsText = "Archivo Original - Peso : " + sizeFile(file);
				details.setText(detailsText);
			}
			
		}
		if(event.getSource() == optionCompress){
			compress = true ;	 
		}
		if(event.getSource() == optionDecompress){
			compress = false ;
		}
		if(event.getSource()==start){
			hilo  = new Thread(new Hilo()) ;
			hilo.start();
		}
	}
	
	// comprime o descomprime un archivo
	private void process() {
		file = new File ( textField.getText().toString() );
		details.setText(null);
		String entry = textField.getText().toString();
	
		if(textField.getText().toString().isEmpty()){
			message.setForeground(Color.RED);
			message.setText("ADVERTENCIA : DIRECCIÓN VACIA");
			details.setText(null);
		}else{
			if(file.exists()) {
				path_file = textField.getText().toString();
				
				if(compress){
					
					huffman = new Huffman();
					huffman.setProgressBar(progressBar);
					huffman.compress(path_file);
					
					detailsText = "Archivo Original - Peso : " + sizeFile(file);
					file = new File(huffman.getOutFileName());
				
					if(file.exists()){
						message.setForeground(Color.BLUE);
						message.setText("COMPRESION CORRECTA");
							
						detailsText += "\nArchivo Comprimido - Peso : "+ sizeFile(file);
						details.setText(detailsText);
					}else{
						 message.setForeground(Color.RED);
						 message.setText("ERROR : NO SE PUDO COMPRIMIR");
					}
				}else {
					if(entry.substring(entry.lastIndexOf(".")+1).equals("huffman")) {
						message.setText(path_file);
					
						huffman = new Huffman();
						huffman.setProgressBar(progressBar);
						huffman.descompress(path_file);
					
						detailsText = "Archivo Original - Peso : " + sizeFile(file);
						file = new File (huffman.getOutFileName());
						
						if(file.exists()){
							message.setForeground(Color.BLUE);
							message.setText("DESCOMPRESION CORRECTA");
							
							detailsText+= "\nArchivo Descomprimido - Peso : "+ sizeFile(file) ;
							details.setText(detailsText);
						}else{
							message.setForeground(Color.RED);
							message.setText("ERROR : NO SE PUDO DESCOMPRIMIR");
						}				
					}else{
						message.setForeground(Color.RED);
						message.setText("ERROR : NO ES UN ARCHIVO CON EXTENSION .huffman ");	
					}
				}				
			}else {
				 message.setForeground(Color.RED);
				 message.setText("ADVERTENCIA : DIRECCIÓN NO VALIDA ");
				 details.setText(null);
			}
		}
	}
	
	// peso del archivo
	public String sizeFile(File file) {
		String sizeFile ;
		int quantity = 0;  // 0= bytes , 1= kbytes , 2 = MB , 3= GB , 4 = TB ;
		double size ;
		
		size = file.length();  
		while  (size / 1024 > 1){
			size = size / 1024;
			quantity ++;
		}
		
		DecimalFormat df = new DecimalFormat("#.###");
		sizeFile=""+df.format(size);
		
		switch(quantity) {
			case 0: sizeFile+=" bytes ";
				break;
			case 1 : sizeFile+=" KB ";
				break;
			case 2 : sizeFile+=" MB ";
				break;
			case 3 : sizeFile+=" GB ";
				break;
			case 4 : sizeFile+=" TB ";
				break;
		}			
		return sizeFile;
	}
	
	public class Hilo implements Runnable {
		@Override
		public void run(){
			progressBar.setVisible(true);
			search.setEnabled(false);
			textField.setEditable(false);
			start.setVisible(true);
			start.setEnabled(false);
			message.setVisible(false);
			process();
			progressBar.setVisible(false);
			progressBar.setValue(0);
			search.setEnabled(true);
			textField.setEditable(true);
			message.setVisible(true);
			start.setVisible(true);
			start.setEnabled(true);
		}
	}
	
}

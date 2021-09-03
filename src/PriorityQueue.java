public class PriorityQueue {
	private QueueNode frontPQ, finalPQ;

    public PriorityQueue(){
    	cleanQueue();
    }
    
    public boolean emptyQueue(){
		return (this.frontPQ==null);
	}	
   
	public void cleanQueue(){
		this.frontPQ=this.finalPQ=null;
	}	
	
	public boolean oneNode () {
		return this.frontPQ == this.finalPQ;
	}
	
    public void insertNode ( Object newNode ) {
		BinaryTreeNode newTreeNode= (BinaryTreeNode) newNode;
		if (!emptyQueue()){
			if(((Information) newTreeNode.getNodeInfo()).getFrequency()  < ((Information) ((BinaryTreeNode) frontPQ.getNodeInfo()).getNodeInfo() ).getFrequency()){
                 frontPQ  = new QueueNode (newTreeNode , frontPQ) ;
			 }else{
			    if(((Information) newTreeNode.getNodeInfo()).getFrequency() >=  ((Information) ((BinaryTreeNode) finalPQ.getNodeInfo()).getNodeInfo() ).getFrequency()){
			    	 	finalPQ.setNextNode( new QueueNode (newTreeNode) );
			    	 	finalPQ = finalPQ.getNextNode();
			     }else{
                    QueueNode ant= frontPQ ;
                    QueueNode temp= frontPQ.getNextNode() ;
			    	boolean band= false;
	                while( temp!=null && !band ){
                        if(((Information) newTreeNode.getNodeInfo()).getFrequency() >= ((Information) ( (BinaryTreeNode) ant.getNodeInfo()).getNodeInfo()).getFrequency()  
                         && ((Information) newTreeNode.getNodeInfo()).getFrequency()  <  ((Information) ( (BinaryTreeNode) temp.getNodeInfo()).getNodeInfo()).getFrequency()){
	                		ant.setNextNode(new QueueNode (newTreeNode , temp) );
	                		band = true;
	                	}else{
	                		ant = temp;
	        		    	temp = temp.getNextNode() ;
	                	}
	                } 	 
			     }
			 }		
		}else{
			this.frontPQ=this.finalPQ= new QueueNode(newTreeNode);			
		}
	}
    
	public Object removeNode(){
		Object element = null;
		
		if (!emptyQueue()){
			element=this.frontPQ.getNodeInfo();
			this.frontPQ=this.frontPQ.getNextNode();
			
			if (emptyQueue()){
				this.finalPQ=null;
			}
			
		}else{
			System.out.println("Error remove. Empty Queue");			
		}
		return element;
	}	
}

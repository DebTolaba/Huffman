public class QueueNode {
	private Object nodeInfo;
	private QueueNode nextNode;

	QueueNode(Object nodeInfo){
		this(nodeInfo,null);
	}

	QueueNode(Object nodeInfo, QueueNode nextNode){
		this.nodeInfo=nodeInfo;
		this.nextNode=nextNode;
	}
	
	Object getNodeInfo(){return this.nodeInfo; }
	void setNodeInfo(Object nodeInfo){this.nodeInfo=nodeInfo;}
	
	QueueNode getNextNode(){return this.nextNode;}
	void setNextNode(QueueNode nextNode){this.nextNode=nextNode;}
}

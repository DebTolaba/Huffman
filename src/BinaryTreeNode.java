public class BinaryTreeNode {
	private Object nodeInfo;
	private BinaryTreeNode leftChild, rightChild;
	
	public BinaryTreeNode(){
		setNodeInfo(null);
		setLeftChild(null);
		setRightChild(null);
	}

	public BinaryTreeNode(Object nodeInfo){
		this(nodeInfo, null,null);
	}
	
	public BinaryTreeNode(Object nodeInfo, BinaryTreeNode leftChild, BinaryTreeNode rightChild){
		setNodeInfo(nodeInfo);
		setLeftChild(leftChild);
		setRightChild(rightChild);
	}
	
	public Object getNodeInfo(){	return this.nodeInfo;}
	public void setNodeInfo(Object nodeInfo){	this.nodeInfo=nodeInfo;}
	
	public BinaryTreeNode getLeftChild(){	return this.leftChild;}
	public void setLeftChild(BinaryTreeNode leftChild){	this.leftChild=leftChild;}
	
	public BinaryTreeNode getRightChild(){	return this.rightChild;}
	public void setRightChild(BinaryTreeNode rightChild){	this.rightChild=rightChild;}
	
}

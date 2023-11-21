package org.tuc.btree;


enum TreeNodeType {
	InnerNode,
	LeafNode
}

/**
 * The abstract class `BTreeNode` represents a node in a B+ tree.
 * It provides common functionality and properties for both inner nodes and leaf nodes.
 *
 * @param <TKey> the generic type of the keys stored in the node
 */
abstract class BTreeNode<TKey extends Comparable<TKey>> {

	/** The keys stored in the node. */
	protected Object[] keys;

	/** The number of keys currently stored in the node. */
	protected int keyCount;

	/** The parent node of the current node. */
	protected BTreeNode<TKey> parentNode;

	/** The left sibling node. */
	protected BTreeNode<TKey> leftSibling;

	/** The right sibling node. */
	protected BTreeNode<TKey> rightSibling;


	/**
	 * Constructs a new instance of the `BTreeNode` class.
	 * Initializes the node with default values.
	 */
	protected BTreeNode() {
		this.keyCount = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}

	/**
	 * Retrieves the number of keys currently stored in the node.
	 *
	 * @return the number of keys in the node
	 */
	public int getKeyCount() {
		return this.keyCount;
	}

	/**
	 * Retrieves the key at the specified index.
	 *
	 * @param index the index of the key to retrieve
	 * @return the key at the specified index
	 */
	@SuppressWarnings("unchecked")
	public TKey getKey(int index) {
		return (TKey) this.keys[index];
	}

	/**
	 * Sets the key at the specified index.
	 *
	 * @param index the index of the key to set
	 * @param key   the key to set
	 */
	public void setKey(int index, TKey key) {
		this.keys[index] = key;
	}

	/**
	 * Retrieves the parent node of the current node.
	 *
	 * @return the parent node
	 */
	public BTreeNode<TKey> getParent() {
		return this.parentNode;
	}

	/**
	 * Sets the parent node of the current node.
	 *
	 * @param parent the new parent node
	 */
	public void setParent(BTreeNode<TKey> parent) {
		this.parentNode = parent;
	}

	/**
	 * Retrieves the type of the node (InnerNode or LeafNode).
	 *
	 * @return the type of the node
	 */
	public abstract TreeNodeType getNodeType();

	/**
	 * Searches for the specified key in the current node.
	 * If the key is found, returns its position.
	 * If the node is a leaf node and the key is not found, returns -1.
	 * If the node is an inner node, returns the index of the child node that should contain the key.
	 *
	 * @param key the key to search for
	 * @return the position of the key or the index of the child node
	 */
	public abstract int search(TKey key);


	/* The codes below are used to support insertion operation */

	/**
	 * Checks if the node is overflowed (i.e., the number of keys exceeds the capacity of the node).
	 *
	 * @return true if the node is overflowed, false otherwise
	 */
	public boolean isOverflow() {
		return this.getKeyCount() == this.keys.length;
	}

	/**
	 * Handles the overflow condition in the node by splitting it into two nodes.
	 *
	 * @return the new right node after splitting
	 */
	public BTreeNode<TKey> dealOverflow() {
		int midIndex = this.getKeyCount() / 2;
		TKey upKey = this.getKey(midIndex);

		BTreeNode<TKey> newRightNode = this.split();

		if

 (this.getParent() == null) {
			this.setParent(new BTreeInnerNode<TKey>());
		}
		newRightNode.setParent(this.getParent());

		// Maintain links of sibling nodes
		newRightNode.setLeftSibling(this);
		newRightNode.setRightSibling(this.rightSibling);
		if (this.getRightSibling() != null)
			this.getRightSibling().setLeftSibling(newRightNode);
		this.setRightSibling(newRightNode);

		// Push up a key to the parent internal node
		return this.getParent().pushUpKey(upKey, this, newRightNode);
	}

	/**
	 * Splits the node into two separate nodes.
	 *
	 * @return the new right node after splitting
	 */
	protected abstract BTreeNode<TKey> split();

	/**
	 * Pushes up a key to the parent internal node during the overflow handling.
	 *
	 * @param key        the key to push up
	 * @param leftChild  the left child node
	 * @param rightChild the right child node
	 * @return the parent node after pushing up the key
	 */
	protected abstract BTreeNode<TKey> pushUpKey(TKey key, BTreeNode<TKey> leftChild, BTreeNode<TKey> rightChild);


	/* The codes below are used to support deletion operation */

	/**
	 * Checks if the node is underflowed (i.e., the number of keys is less than half of the capacity).
	 *
	 * @return true if the node is underflowed, false otherwise
	 */
	public boolean isUnderflow() {
		return this.getKeyCount() < (this.keys.length / 2);
	}

	/**
	 * Checks if the node can lend a key to its sibling.
	 *
	 * @return true if the node can lend a key, false otherwise
	 */
	public boolean canLendAKey() {
		return this.getKeyCount() > (this.keys.length / 2);
	}

	/**
	 * Retrieves the left sibling node of the current node.
	 *
	 * @return the left sibling node if it exists and has the same parent, otherwise null
	 */
	public BTreeNode<TKey> getLeftSibling() {
		if (this.leftSibling != null && this.leftSibling.getParent() == this.getParent())
			return this.leftSibling;
		return null;
	}

	/**
	 * Sets the left sibling node of the current node.
	 *
	 * @param sibling the new left sibling node
	 */
	public void setLeftSibling(BTreeNode<TKey> sibling) {
		this.leftSibling = sibling;
	}

	/**
	 * Retrieves the right sibling node of the current node.
	 *
	 * @return the right sibling node if it exists and has the same parent, otherwise null
	 */
	public BTreeNode<TKey> getRightSibling() {
		if (this.rightSibling != null && this.rightSibling.getParent() == this.getParent())
			return this.rightSibling;
		return null;
	}

	/**
	 * Sets the right sibling node of the current node.
	 *
	 * @param sibling the new right sibling node
	 */
	public void setRightSibling(BTreeNode<TKey> sibling) {
		this.rightSibling = sibling;
	}

	/**
	 * Handles the underflow condition in the node by borrowing a key from its sibling,
	 * transferring a key from the sibling to the current node.
	 *
	 * @return the new root node after handling the underflow or null if no restructuring is needed
	 */
	public BTreeNode<TKey> dealUnderflow() {
		if (this.getParent() == null)
			return null;

		// Try to borrow a key from the left sibling
		BTreeNode<TKey> leftSibling = this.getLeftSibling();
		if (leftSibling

 != null && leftSibling.canLendAKey()) {
			this.getParent().processChildrenTransfer(this, leftSibling, leftSibling.getKeyCount() - 1);
			return null;
		}

		// Try to borrow a key from the right sibling
		BTreeNode<TKey> rightSibling = this.getRightSibling();
		if (rightSibling != null && rightSibling.canLendAKey()) {
			this.getParent().processChildrenTransfer(this, rightSibling, 0);
			return null;
		}

		// Cannot borrow a key from any sibling, perform fusion with a sibling
		if (leftSibling != null) {
			return this.getParent().processChildrenFusion(leftSibling, this);
		} else {
			return this.getParent().processChildrenFusion(this, rightSibling);
		}
	}


	/**
	 * Processes the transfer of a key from a sibling node to the current node during the underflow handling.
	 *
	 * @param borrower     the borrower node (the current node)
	 * @param lender       the lender node (the sibling node)
	 * @param borrowIndex  the index of the key in the sibling to borrow
	 */
	protected abstract void processChildrenTransfer(BTreeNode<TKey> borrower, BTreeNode<TKey> lender, int borrowIndex);

	/**
	 * Processes the fusion (merging) of two child nodes of the current node during the underflow handling.
	 *
	 * @param leftChild   the left child node
	 * @param rightChild  the right child node
	 * @return the new merged node
	 */
	protected abstract BTreeNode<TKey> processChildrenFusion(BTreeNode<TKey> leftChild, BTreeNode<TKey> rightChild);

	/**
	 * Performs the fusion (merging) of the current node with a sibling node.
	 *
	 * @param sinkKey       the key to sink from the parent node to the merged node
	 * @param rightSibling  the right sibling node to fuse with
	 */
	protected abstract void fusionWithSibling(TKey sinkKey, BTreeNode<TKey> rightSibling);

	
	/**
	 * Transfers a key from a sibling node to the current node during the underflow handling.
	 *
	 * @param sinkKey      the key to sink to the current node
	 * @param sibling      the sibling node to transfer from
	 * @param borrowIndex  the index of the key in the sibling to borrow
	 * @return the transferred key
	 */
	protected abstract TKey transferFromSibling(TKey sinkKey, BTreeNode<TKey> sibling, int borrowIndex);
}


package org.tuc.btree;

import org.tuc.utils.MultiCounter;

/**
 * Represents a leaf node in a B-tree.
 *
 * @param <TKey>   the type of keys stored in the node (must implement Comparable)
 * @param <TValue> the type of values stored in the node
 */
class BTreeLeafNode<TKey extends Comparable<TKey>, TValue> extends BTreeNode<TKey> {

	/** The array of values stored in the leaf node. */
	private Object[] values;

	/** The order of the leaf node. */
	protected int leafOrder;

	/**
	 * Instantiates a new B+ Tree leaf node with a custom leaf order.
	 *
	 * @param leafOrder the leaf order
	 */
	public BTreeLeafNode(int leafOrder) {
		this.leafOrder = leafOrder;
		this.keys = new Object[leafOrder + 1];
		this.values = new Object[leafOrder + 1];
	}

	/**
	 * Gets the value at the specified index.
	 *
	 * @param index the index of the value
	 * @return the value at the specified index
	 */
	@SuppressWarnings("unchecked")
	public TValue getValue(int index) {
		return (TValue) this.values[index];
	}

	/**
	 * Sets the value at the specified index.
	 *
	 * @param index the index of the value
	 * @param value the new value
	 */
	public void setValue(int index, TValue value) {
		this.values[index] = value;
	}

	/**
	 * Gets the type of the node.
	 *
	 * @return the node type
	 */
	@Override
	public TreeNodeType getNodeType() {
		return TreeNodeType.LeafNode;
	}

	/**
	 * Searches for the specified key in the node.
	 *
	 * @param key the key to search for
	 * @return the index of the key if found, otherwise -1
	 */
	@Override
	public int search(TKey key) {
		for (int i = 0; i < this.getKeyCount(); ++i) {
			MultiCounter.increaseCounter(3); // This counter is used to count the keys comparison in leafs.
			int cmp = this.getKey(i).compareTo(key);
			if (cmp == 0) {
				return i;
			} else if (cmp > 0) {
				return -1;
			}
		}

		return -1;
	}

	/* The codes below are used to support insertion operation */

	/**
	 * Inserts a key-value pair into the leaf node.
	 *
	 * @param key   the key to insert
	 * @param value the value to insert
	 */
	public void insertKey(TKey key, TValue value) {
		int index = 0;
		while (index < this.getKeyCount() && this.getKey(index).compareTo(key) < 0)
			++index;
		this.insertAt(index, key, value);
	}

	private void insertAt(int index, TKey key, TValue value) {
		// move space for the new key
		for (int i = this.getKeyCount() - 1; i >= index; --i) {
			this.setKey(i + 1, this.getKey(i));
			this.setValue(i + 1, this.getValue(i));
		}

		// insert new key and value
		this.setKey(index, key);
		this.setValue(index, value);
		++this.keyCount;
	}

	/**
	 * When splits a leaf node, the middle key is kept on new node and be pushed to parent node.
	 *
	 * @return the b tree node
	 */
	@Override
	protected BTreeNode<TKey> split() {
		int midIndex = this.getKeyCount() / 2;

		BTreeLeafNode<TKey, TValue> newRNode = new BTreeLeafNode<TKey, TValue>(leafOrder);
		for (int i = midIndex; i < this.getKeyCount(); ++i) {
			newRNode.setKey(i - midIndex, this.getKey(i));
			newRNode.setValue(i - midIndex, this.getValue(i));
			this.setKey(i, null);
			this.setValue(i, null);
		}
		newRNode.keyCount = this.getKeyCount() - midIndex;
		this.keyCount = midIndex;

		return newRNode;
	}

	/**
	 * Push up key.
	 *
	 * @param key        the key
	 * @param leftChild  the left child
	 * @param rightNode  the right node
	 * @return the b tree node
	 */
	@Override
	protected BTreeNode<TKey> pushUpKey(TKey key, BTreeNode<TKey> leftChild, BTreeNode<TKey> rightNode) {
		throw new UnsupportedOperationException();
	}

	/* The codes below are used to support deletion operation */

	/**
	 * Delete.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean delete(TKey key) {
		int index = this.search(key);
		if (index == -1)
			return false;

		this.deleteAt(index);
		return true;
	}

	/**
	 * Delete at.
	 *
	 * @param index the index
	 */
	private void deleteAt(int index) {
		int i;
		for (i = index; i < this.getKeyCount() - 1; ++i) {
			this.setKey(i, this.getKey(i + 1));
			this.setValue(i, this.getValue(i + 1));
		}
		this.setKey(i, null);
		this.setValue(i, null);
		--this.keyCount;
	}

	/**
	 * Process children transfer.
	 *
	 * @param borrower    the borrower
	 * @param lender      the lender
	 * @param borrowIndex the borrow index
	 */
	@Override
	protected void processChildrenTransfer(BTreeNode<TKey> borrower, BTreeNode<TKey> lender, int borrowIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Process children fusion.
	 *
	 * @param leftChild  the left child
	 * @param rightChild the right child
	 * @return the b tree node
	 */
	@Override
	protected BTreeNode<TKey> processChildrenFusion(BTreeNode<TKey> leftChild, BTreeNode<TKey> rightChild) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Notice that the key sunk from parent is be abandoned.
	 *
	 * @param sinkKey       the sink key
	 * @param rightSibling  the right sibling
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void fusionWithSibling(TKey sinkKey, BTreeNode<TKey> rightSibling) {
		BTreeLeafNode<TKey, TValue> siblingLeaf = (BTreeLeafNode<TKey, TValue>) rightSibling;

		int j = this.getKeyCount();
		for (int i = 0; i < siblingLeaf.getKeyCount(); ++i) {
			this.setKey(j + i, siblingLeaf.getKey(i));
			this.setValue(j + i, siblingLeaf.getValue(i));
		}
		this.keyCount += siblingLeaf.getKeyCount();

		this.setRightSibling(siblingLeaf.rightSibling);
		if (siblingLeaf.rightSibling != null)
			siblingLeaf.rightSibling.setLeftSibling(this);
	}

	/**
	 * Transfer from sibling.
	 *
	 * @param sinkKey     the sink key
	 * @param sibling     the sibling
	 * @param borrowIndex the borrow index
	 * @return the t key
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected TKey transferFromSibling(TKey sinkKey, BTreeNode<TKey> sibling, int borrowIndex) {
		BTreeLeafNode<TKey, TValue> siblingNode = (BTreeLeafNode<TKey, TValue>) sibling;

		this.insertKey(siblingNode.getKey(borrowIndex), siblingNode.getValue(borrowIndex));
		siblingNode.deleteAt(borrowIndex);

		return borrowIndex == 0 ? sibling.getKey(0) : this.getKey(0);
	}
}

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import CITS2200.ListLinked;
import CITS2200.WindowLinked;


/**
 * My implementation of an AVL tree dictionary. This class can handle strings and integers as long as the
 * integers have the same number of number places since it is treated as a string. This class is based on the
 * recursive doubly linked list structure so each Node has a parent node and left and right subtree nodes as well.
 * This class also provides a private class called Node that provides this doubly linked data structure. This class also
 * provides all the methods implemented from the Dictionary.java interface provided. 
 * @author Shaun Leong
 *
 * @param <E>
 */
public class AVLDict<E extends Comparable<E>> implements Dictionary<E> {

	Node dictRoot; // root of the dictionary
	Node window; // for predecessor and successors
	String logString;
	int modCount; // Modification counts
	int nComp; // number of comparisons
	int totalComparisons; //total number of comparisons per logString method invocation
	int insertions;//number of insertions into dictionary
	
	public class Node {
		Node parent; //parent subtree
		Node left; //left subtree
		Node right; //right subtree
		E element; //the item in the node
		int height; //height of the node

		/**
		 * This method constructs a Node object
		 * @param p the parent node of the node
		 * @param e the item of the node
		 * @param leftT the left subtree node of the node
		 * @param rightT the right subtree node of the node
		 * @param h the height of the node
		 */
		public Node(Node p, E e, Node leftT, Node rightT, int h) {
			parent = p;
			left = leftT;
			right = rightT;
			element = e;
			height = h;
		}

	}
	
	/**
	 * Constructor to build the AVLDict Class
	 */
	public AVLDict() {

		// Initializing the instance variables
		dictRoot = null;
		logString = "";
		modCount = 0;
		nComp = 0;
	}

	/**
	 * Checks to see whether the Dictionary is empty
	 * @return true if and only if the Dictionary is Empty
	 **/
	public boolean isEmpty() {

		logString = logString + "Operation isEmpty() completed using " + 0
				+ " comparisions\n";
		return dictRoot == null;
	}

	/**
	 * Traverse through the dictionary until the desired node is 
	 * found if not, it still returns a node, ie, the last visited node with an element
	 * @param item the desired item to look for
	 * @return Node the last visited node with an element 
	 */
	private Node dictTraversal(E item) {

		// Resetting nComp
		nComp = 0;

		// If dictRoot is empty, return null
		if (isEmpty()) {

			return null;
		}

		// Temp variable of dictRoot to traverse through the tree
		Node temp = dictRoot;

		// Obtaining string representation of item for comparison
		String sItem = item.toString();

		// Finds the node that equates to item, if can't find, then temp will be
		// the last visited node with an element
		while (temp.left != null || temp.right != null) {
			// Incrementing nComp
			nComp++;
			
			if (sItem.compareToIgnoreCase(temp.element.toString()) < 0) {
				

				// If the item string value is less than the element in the node, 
				//traverse to the left subtree
				if (temp.left != null) {
					temp = temp.left;
				} else {
					// Break out of while loop if no more left nodes can be
					// found
					break;
				}
			} else if (sItem.compareToIgnoreCase(temp.element.toString()) > 0) {

				//If the item string value is more than the element in the node, 
				//traverse to the right subtree
				if (temp.right != null) {
					temp = temp.right;
				} else {
					// Break out of while loop if no more right nodes can be
					// found
					break;
				}
			} else {
					
				// Else break if sItem is equal to the node element
				break;
			}
		}

		

		return temp;

	}

	
	/**
	*Checks to see if an item is contained in AVLDict 
	*@param item the item to be checked.
	*@return true if and only if the Dictionary contains something equal to item.
	**/
	public boolean contains(E item) {

		// If item is null or dictRoot is empty, return false
		if (item == null || isEmpty()) {

			return false;
		} else {
			// Compare the node returned by dictTraversal to the item. If the
			// node element is not equal to the item, then return false

			logString = logString + "Operation contains(E item) completed using " + nComp + " comparisions\n";
			
			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			return item.equals(dictTraversal(item).element);

		}

	}

	/**
	*Checks to see if the item has a predecessor in the dictionary
	*@return true if and only if there is an item strictly less than item in the Dictionary
	*@param item the item to be checked
	**/ 
	public boolean hasPredecessor(E item) {

		// Gets the desired or similar element node
		Node currNode = dictTraversal(item);

		if (currNode.element.equals(item)) {

			// Assign the specific node that has the item to the window instance
			// variable
			window = currNode;

			logString = logString + "Operation hasPredecessor(E item) completed using " + nComp + " comparisions\n";

			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			// Returns a boolean if there's a predecessor
			return currNode.left != null;
		} else {
			return false;
		}

	}

	/**
	*Checks to see if the item has a successor in the dictionary
	*@return true if and only if there is an item strictly greater than item in the Dictionary
	*@param item the item to be checked
	**/ 
	public boolean hasSuccessor(E item) {

		// Gets the desired or similar element node
		Node currNode = dictTraversal(item);

		// Checks to see if the element in the node is equal to the item
		if (currNode.element.equals(item)) {

			// Assign the specific node that has the item to the window instance
			// variable
			window = currNode;

			logString = logString+ "Operation hasSuccessor(E item) completed using " + nComp + " comparisions\n";

			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			return currNode.right != null;

		} else {
			return false;
		}

	}

	/**
	*Find the greatest element less than the specified item
	*@return the item strictly less than item in the Dictionary
	*@param item the item to be checked
	*@throws NoSuchElementException if there is no lesser element.
	**/ 
	public E predecessor(E item) throws NoSuchElementException {

		if (hasPredecessor(item)) {
			// Traverse the window instance variable, which already has the
			// element with a predecessor,
			// after calling hasPredecessor, to the left
			window = window.left;

			// Keep traversing result to the right until the predecessor has
			// been obtained
			while (window.right != null) {
				window = window.right;
			}

			//Concatenate string operation to logString
			logString = logString+ "Operation predecessor(E item) completed using " + nComp+ " comparisions\n";

			
			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			return window.element;

		} else {
			throw new NoSuchElementException();
		}

	}

	/**
	*Find the least element greater than the specified item
	*@return the item strictly greater than item in the Dictionary
	*@param item the item to be checked
	*@throws NoSuchElementException if there is no greater element.
	**/ 
	public E successor(E item) {

		if (hasSuccessor(item)) {
			// Traverse the window instance variable after calling
			// hasPredecessor to
			// the left
			window = window.right;

			// Keep traversing result to the right until the predecessor has
			// been obtained
			while (window.left != null) {
				window = window.left;
			}

			//Concatenate string operation to logString
			logString = logString+ "Operation successor(E item) completed using " + nComp+ " comparisions\n";

			
			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			return window.element;

		} else {
			throw new NoSuchElementException();
		}

	}

	/**
	*Return the least item in the Dictionary
	*@return the least item in the Dictionary
	*@throws NoSuchElementException if the Dictionary is empty.
	**/ 
	public E min() throws NoSuchElementException {
		
		if(!isEmpty()){
		// Resetting nComp
		nComp = 0;

		// Temp reference for dictRoot
		Node temp = dictRoot;

		// Keep assigning temp to be the left most node, which has the least
		// value element
		while (temp.left != null) {
			temp = temp.left;
		}
		//Concatenating string operation to logString
		logString = logString + "Operation min() completed using " + nComp+ " comparisions\n";
		//Adding nComp to totalComparisons
		totalComparisons = totalComparisons + nComp;
		
		return temp.element;}
		else{
			throw new NoSuchElementException();
		}
	}

	/**
	*Return the greatest item in the dictionary
	*@return the greatest item in the Dictionary
	*@throws NoSuchElementException if the Dictionary is empty.
	**/ 
	public E max() throws NoSuchElementException {
		
		if(!isEmpty()){
		// Resetting nComp
		nComp = 0;

		// Temp reference for dictRoot
		Node temp = dictRoot;

		// Keep assigning temp to be the right most node, which has the most
		// value element
		while (temp.right != null) {
			temp = temp.right;
		}
		//Concatenating string operation to logString
		logString = logString + "Operation max() completed using " + nComp+ " comparisions\n";
		
		//Adding nComp to totalComparisons
		totalComparisons = totalComparisons + nComp;
		return temp.element;}
		else{
			throw new NoSuchElementException();
		}
	}

	/**
	 * Obtains the max height from either the left or right subtree.
	 * @param l the left subtree given from a node
	 * @param r the right subtree given from a node
	 * @return int the max height from the left or right subtree, if there there's no subtree node, then returns -1
	 */
	private int getMaxHeight(Node l, Node r) {
		return Math.max(l == null ? -1 : l.height, r == null ? -1 : r.height);
	}

	
	/**
	 * Obtains the height of a node
	 * @param n node to obtained height from
	 * @return int height of a node
	 */
	private int getHeight(Node n) {
		return n == null ? -1 : n.height;
	}

	/**
	*Adds a new item to the Dictionary 
	*If there is an equal item already in the dictionary, or the item is null it returns false.
	*@param item the item to be added.
	*@return true if the item is not null, and not already in the dictionary.
	**/
	public boolean add(E item) {

		// If dictRoot is empty and the item is not null, add in the item
		if (isEmpty() && item != null) {
			// Reseting nComp
			nComp = 0;

			//increment number of insertions
			insertions++;
			
			//Concatenating string operation to logString
			logString = logString + "Operation add(E item) completed using "+ nComp + " comparisions\n";

			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			dictRoot = new Node(null, item, null, null, 0);
			return true;

			
		}

		// Calling dictTraversal to get the node just before a null node
		Node currNode = dictTraversal(item);

		// Boolean variable to check whether the currNode returned already contains the
		// item in dictRoot
		boolean result = currNode.element.equals(item);

		// If the item is null or the currNode returned already contains the
		// item in dictRoot, return false
		if (item == null || result) {
			return false;
		} else {

			//increment number of insertions
			insertions++;
			
			// Inserting the new item into left or right subtree depending on
			// string value
			if (item.toString().compareToIgnoreCase(currNode.element.toString()) < 0) {
				// Incrementing nComp
				nComp++;

				currNode.left = new Node(currNode, item, null, null, 0);

				// Increment the modification count because of item insertion
				modCount++;
			} else {
				// Incrementing nComp
				nComp++;

				// If the item string value is greater than currNode, put it in
				// right subtree
				currNode.right = new Node(currNode, item, null, null, 0);

				// Increment the modification count because of item insertion
				modCount++;
			}

			// Traversing back up to root to update structure
			traverseUp(currNode);

			//Concatenating string operation to logString
			logString = logString + "Operation add(E item) completed using "
					+ nComp + " comparisions\n";

			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			// After traversing back to the root parent null value, return the
			// boolean
			return item != null && result;
		}

	}

	
	/**
	 * LLRotates the node that has the illegal height difference
	 * @param child the node to be rotated
	 * @return Node the node that replaced the old node
	 */
	private Node rotationLL(Node child) {
		// Copies the reference of child
		Node childRef = child;

		// Creating a copy variable of child
		Node temp = new Node(null, child.element, child.left, child.right,
				child.height);

		// Assigning the left subtree of child to the left subtree of child's
		// parent which would erase the original child node
		child = new Node(child.parent, child.left.element, child.left.left,
				child.left.right, child.left.height);

		// Updating the left subtree to its new parents
		if (child.left != null) {
			child.left.parent = child;
		}

		// Now assign temp to the child right subtree
		child.right = new Node(child, temp.element, child.right, temp.right,
				temp.height);

		// Updating child.right subtrees parents and its height
		if (child.right.right != null) {
			child.right.right.parent = child.right;
			
		}

		
		//If there's a child.right.left subtree (which was originally from child),
		//change its parent and obtain the initial child's height instead of the initial child.left subtree height
		if (child.right.left != null) {
			
			
			child.right.left.parent = child.right;
			
			
			
		}

		// Updating the height of child.right since it's moved to the bottom and afterwards update the child's height
		//child.right.height = child.right.height - 2;
		child.right.height = getMaxHeight(child.right.left, child.right.right) +1;
		child.height = getMaxHeight(child.left, child.right) +1;
		
		// Updating the child subtrees to its new parent
		child.left.parent = child;
		child.right.parent = child;

		
		
		// Change dictRoot to the child if child has a null parent
		if (child.parent == null) {
			dictRoot = child;

			// If child's parent is not null, one of the parent's subtree needs
			// to be updated which would hold the child
		} else {

			// Updating the one of the child's parent's subtrees to be the child
			Node childP = childRef.parent;

			if (childP.left == childRef) {
				childP.left = child;
			} else if (childP.right == childRef) {
				childP.right = child;
			}

		}

		// return the node position that was given initially
		return child;
	}
	
	/**
	 * LRRotates the node that has the illegal height difference
	 * @param child the node to be rotated
	 * @return Node the node that replaced the old node
	 */
	private Node rotationLR(Node child) {

		// Copies the reference of child
		Node childRef = child;

		// Creating a copy of child and child's left subtree along with their
		// respective subtrees from the bottom node
		Node temp = new Node(null, child.element, child.left.right.right,
				child.right, child.height);
		Node tempLT = new Node(null, child.left.element, child.left.left,
				child.left.right.left, child.left.height);

		// Updating the bottom nodes subtrees in temp and tempRT to its new
		// parent
		if (temp.left != null) {
			temp.left.parent = temp;

		}

		if (tempLT.right != null) {
			tempLT.right.parent = tempLT;
		}

		// Initializing child to its left-right subtree and reinitializing its
		// subtrees
		child = new Node(child.parent, child.left.right.element, tempLT, temp,
				child.height - 1);

		// Modifying the newly initialized child left subtree parent and right
		// subtree
		child.left.parent = child;
		child.right.parent = child;

		// Updating the nodes' height
		child.left.height = child.left.height - 1;
		child.right.height = child.right.height - 2;
		
		
		// Change dictRoot to the child if child has a null parent because it
		// would be the new root
		if (child.parent == null) {
			dictRoot = child;

			// If child's parent is not null, one of the parent's subtree needs
			// to be updated which would hold the child
		} else {

			// Updating the one of the child's parent's subtrees to be the child
			Node childP = childRef.parent;

			if (childP.left == childRef) {
				childP.left = child;
			} else if (childP.right == childRef) {
				childP.right = child;
			}

		}

		// Return the replaced node in the initial node position
		return child;

	}

	/**
	 * RLRotates the node that has the illegal height difference
	 * @param child the node to be rotated
	 * @return Node the node that replaced the old node
	 */
	private Node rotationRL(Node child) {

		// Copies the reference of child
		Node childRef = child;

		// Creating a copy of child and child's right subtree along with the
		// subtrees of the bottom node
		Node temp = new Node(null, child.element, child.left,
				child.right.left.left, child.height);
		Node tempRT = new Node(null, child.right.element,
				child.right.left.right, child.right.right, child.right.height);

		// Updating the bottom nodes subtrees in temp and tempRT to its new
		// parent

		if (temp.right != null) {
			temp.right.parent = temp;
		}

		

		if (tempRT.left != null) {
			tempRT.left.parent = tempRT;
		}

	

		// Initializing child to its right-left subtree and reinitializing its
		// subtrees
		child = new Node(child.parent, child.right.left.element, temp, tempRT,
				child.height - 1);

		// Modifying the newly initialized child left subtree and right subtree
		// parents
		child.left.parent = child;
		child.right.parent = child;

		// Updating the nodes' height
		child.left.height = child.left.height - 2;
		child.right.height = child.right.height - 1;

		// Change dictRoot to the child if child has a null parent
		if (child.parent == null) {
			dictRoot = child;

			// If child's parent is not null, one of the parent's subtree needs
			// to be updated which would hold the child
		} else {

			// Updating the one of the child's parent's subtrees to be the child
			Node childP = childRef.parent;

			if (childP.left == childRef) {
				childP.left = child;
			} else if (childP.right == childRef) {
				childP.right = child;
			}

		}

		// Return the replace node in the position of the initial node.
		return child;
	}
	
	/**
	 * RRRotates the node that has the illegal height difference
	 * @param child the node to be rotated
	 * @return Node the node that replaced the old node
	 */
	private Node rotationRR(Node child) {

		// Copies the reference of child
		Node childRef = child;

		// Creating a copy of child
		Node temp = new Node(null, child.element, child.left, child.right,
				child.height);

		// Assigning the right subtree of child to the right subtree of child's
		// parent
		child = new Node(child.parent, child.right.element, child.right.left,
				child.right.right, child.right.height);

		// Now assign temp to the child left subtree and any child left subtree
		// to the right subtree of child left subtree

		child.left = new Node(child, temp.element, temp.left, child.left,
				temp.height);

		// Updating the child.left subtrees to its new parent
		if (child.left.left != null) {
			child.left.left.parent = child.left;
		}

		
		//If there's a child.left.right subtree, then update its parent. Also change the height of the child
		//to the original child's height.
		if (child.left.right != null) {
			child.left.right.parent = child.left;
			
		}

		// Updating the height of temp since it's moved to the bottom
		//child.left.height = child.left.height - 2;
		child.left.height = getMaxHeight(child.left.left, child.left.right) +1;
		child.height = getMaxHeight(child.left, child.right) +1;
		
		// Updating the child subtrees to its new parent
		child.left.parent = child;
		child.right.parent = child;
		
		

		// Updating the parents of the subtrees. Change dictRoot to the child if
		// child has a null parent because the root has been changed after
		// rotation
		if (child.parent == null) {
			dictRoot = child;

			// If child's parent is not null, one of the parent's subtree needs
			// to be updated which would hold the child
		} else {

			// Updating the one of the child's parent's subtrees to be the child
			Node childP = childRef.parent;

			if (childP.left == childRef) {
				childP.left = child;
			} else if (childP.right == childRef) {
				childP.right = child;
			}

		}

		// return the new replaced node in the same position as the initial one
		return child;
	}

	/**
	 * Traverse the back up to the root from the currNode position
	 * @param currNode the node to be traversed up to the root to
	 */
	private void traverseUp(Node currNode) {

		// Adjust height as you traverse back up to the root and
		// Traversing back up to root to after deletion and insertion to update
		// the tree structure if needed
		while (currNode != null) {

			int adjustedHeight = getMaxHeight(currNode.left, currNode.right) + 1;

		
			
			// Height difference between the subtrees of currNode
			int heightDiff = getHeight(currNode.left)- getHeight(currNode.right);

		

			
			// Increment nComp because of comparing the currNode's subtrees
			// height difference
			nComp++;

			// Update the height of currNode if needed
			if (adjustedHeight != currNode.height || heightDiff > 1|| heightDiff < -1) {

				
				
				// Change currNode height to adjustedHeight
				currNode.height = adjustedHeight;

				// After the height update, if the height difference between the
				// subtrees are greater than 1, do rotations
				
				if (getHeight(currNode.left) - getHeight(currNode.right) > 1) {
					
				

					// Increment nComp because of comparing left subtree's
					// subtrees' height difference
					nComp++;

					// If the left subtree's subtrees' height difference is
					// positive, do LL rotation
					if (getHeight(currNode.left.left)
							- getHeight(currNode.left.right) >= 0) {

						// Rotation method returns original node position. Set
						// currNode to the parent of the node returned

						currNode = rotationLL(currNode).parent;

						// If the left subtree's subtrees' height difference is
						// negative do LR rotation
					} else if (getHeight(currNode.left.left)
							- getHeight(currNode.left.right) < 0) {

						currNode = rotationLR(currNode).parent;
					}

					// Else if the height difference between the currNode
					// subtrees is less than -1, change structure
				} else if (getHeight(currNode.left) - getHeight(currNode.right) < -1) {

					// Increment nComp because of comparing right subtree's
					// subtrees' height difference
					nComp++;

					// If the right subtree's subtrees' height difference is
					// positive, do RL rotation
					if (getHeight(currNode.right.left)
							- getHeight(currNode.right.right) >= 0) {

						// Rotation method returns original node position. Set
						// currNode to the parent of the node returned

						currNode = rotationRL(currNode).parent;

						// If the right subtree's subtrees' height
						// difference is
						// negative do RR rotation
					} else if (getHeight(currNode.right.left)
							- getHeight(currNode.right.right) < 0) {

						currNode = rotationRR(currNode).parent;
					}

					// If there's no illegal height differences even though
					// there's a height adjustment, then go up to the parent
					// and also update the parent's subtree as well
				} else {

					// Iterate to the next parent
					currNode = currNode.parent;

				}

				// If the height doesn't need to be adjusted or if the subtree
				// height difference is not illegal, then go up to
				// the parent
			} else {

				currNode = currNode.parent;

			}

		}

	}

	/**
	*Deletes the specified item from the dictionary if it is present.
	*@param item the item to be removed
	*@return true if the item was in the dictionary and has now been removed. False otherwise.
	**/
	public boolean delete(E item) {

		// Traverse through dictRoot to find the node that has the item
		Node currNode = dictTraversal(item);

		// If the currNode element doesn't equate to the item or dict is empty,
		// return false
		if (isEmpty() || !currNode.element.equals(item)) {
			return false;
		} else {

			// If both the currNode (to be deleted) subtrees are null, just
			// delete it
			if (currNode.left == null && currNode.right == null) {

				// Set the deleted node's parent to null after setting a temp
				// variable of its parent.
				// Then reinitialized currNode to the temp
				Node currParent = currNode.parent;

				// If the node to be deleted is the root and only the root
				if (currParent == null) {
					dictRoot = null;
				} else {

					// If currParent left subtree has currNode, assign null to
					// the
					// whole subtree of currParent, else do the right subtree
					if (currParent.left == currNode) {
						currParent.left = null;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent;

					} else {
						currParent.right = null;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent;
					}
				}

				// Increment the modification count because of item deletion
				modCount++;

				// If the currNode only has right subtree
			} else if (currNode.left == null && currNode.right != null) {

		
				// Reference of currNode parent
				Node currParent = currNode.parent;

				// If the currNode parent is null, delete the root and replace
				// it with the only right subtree that it had
				if (currParent == null) {

					// Assign dictRoot to be its right subtree
					dictRoot = dictRoot.right;

					// Set the dictRoot parent now to be null
					dictRoot.parent = null;

					// Update the subtrees of dictRoot to have the new dictRoot
					// as parents
					if (dictRoot.left != null) {
						dictRoot.left.parent = dictRoot;
					}
					dictRoot.right.parent = dictRoot;

					// set currNode to the new dictRoot
					currNode = dictRoot;

					// Else if the currParent is not null, then it must have a
					// node and the parent's subtrees needs to be updated to the
					// replaced ones
				} else {

					// If the currParent left subtree has the currNode, replace
					// the
					// left subtree with currNode right subtree
					if (currParent.left == currNode) {

						// Set the left subtree of currParent to the right
						// subtree of currNode
						currParent.left = currNode.right;

						// Update the left subtree parent to its new parent,
						// currParent
						currParent.left.parent = currParent;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent.left;

						// Else, the currNode to be deleted must be in the right
						// subtree of currParent
					} else {

						currParent.right = currNode.right;

						// Update the right subtree parent to its new parent
						currParent.right.parent = currParent;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent.right;
					}
				}
				// Increment the modification count because of item deletion
				modCount++;

				// If the currNode only has left subtree
			} else if (currNode.right == null && currNode.left != null) {

				// Reference of currNode parent
				Node currParent = currNode.parent;

				// If the currParent is null, that means currNode must be the
				// root. Delete the root and replace it with the only left
				// subtree that it had
				if (currParent == null) {

					// Assign dictRoot to be its right subtree
					dictRoot = dictRoot.left;

					// Update the dictRoot parent now to be null
					dictRoot.parent = null;

					// Update the subtrees of dictRoot to have dictRoot as
					// parents
					dictRoot.left.parent = dictRoot;
					if (dictRoot.right != null) {
						dictRoot.right.parent = dictRoot;
					}

					// Set currNode to the new dictRoot
					currNode = dictRoot;

					// Else if the currParent is not null, then it must have a
					// node and the parent's subtrees needs to be updated to the
					// replaced ones
				} else {

					// If the currParent left subtree has currNode, replace it
					// with
					// currNode left subtree
					if (currParent.left == currNode) {
						// If the currParent has the currNode to be deleted,
						// replace
						// it with the right subtree of the currNode.
						currParent.left = currNode.left;

						// Update the left subtree parent to its new parent
						currParent.left.parent = currParent;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent.left;

						// Else, the currNode to be deleted must be in the right
						// subtree
					} else {

						currParent.right = currNode.left;

						// Update the right subtree parent to its new parent
						currParent.right.parent = currParent;

						// Set currNode to its replaced node for traversing back
						// up
						currNode = currParent.right;

					}
				}

				// Increment the modification count because of item deletion
				modCount++;

				// Both of the subtrees are non nulls, get the predecessor of
				// deleted node
			} else {

				// Obtain the predecessor of deleted node. hasPredecessor would
				// initialize it to window instance variable
				if (hasPredecessor(item)) {

					// Traverse the window instance variable after calling
					// hasPredecessor to the left once
					window = window.left;

					// Keep traversing result to the right until the predecessor
					// has
					// been obtained
					while (window.right != null) {
						window = window.right;
					}

				}

				// Replace one of the deleting node's parent subtrees with the
				// predecessor

				// References the currNode's parent
				Node currParent = currNode.parent;

				// If the currParent is null, currNode must be the root. Delete
				// the root and replace with the predecessor
				if (currParent == null) {

					dictRoot.element = window.element;
					dictRoot.left = currNode.left;
					dictRoot.parent = null;
					dictRoot.right = currNode.right;
					dictRoot.height = currNode.height;

					dictRoot.left.parent = dictRoot;
					dictRoot.right.parent = dictRoot;

					

					// If currParent is not null, update the parent's subtree
					// which has the deleted node to a new node
				} else {

					// If the currNode is in the currParent left subtree,
					// replace it
					// with window but keep the currNode subtrees
					if (currParent.left == currNode) {

						currParent.left = new Node(currParent, window.element,
								currNode.left, currNode.right, currNode.height);

						// Updating the currParent.left subtrees to new parents
						if (currParent.left.left != null) {
							currParent.left.left.parent = currParent.left;
						}
						if (currParent.left.right != null) {
							currParent.left.right.parent = currParent.left;
						}

						

						// Else, the currNode to be deleted must be in the right
						// subtree of currParent
					} else {

						currParent.right = new Node(currParent, window.element,
								currNode.left, currNode.right, currNode.height);

						// Updating the currParent.right subtrees to new parents
						if (currParent.right.left != null) {
							currParent.right.left.parent = currParent.right;
						}
						if (currParent.right.right != null) {
							currParent.right.right.parent = currParent.right;
						}

						
					}
				}

				// Increment the modification count because of item deletion
				modCount++;

				// Delete the predecessor (ie window). If there's a left subtree, create a
				// copy and connect to one of window's subtrees. Traverse back
				// up to the root with the window's parent
				if (window.left != null) {

			
					// Referencing window's parent
					Node windowP = window.parent;

					// If the window is in the window's parent left subtree,
					// replace it with the window's left subtree
					if (windowP.left == window) {
						windowP.left = window.left;

						// Updating the windowP left subtree parent
						windowP.left.parent = windowP;

						// Also updating the windowP.left subtrees parents to
						// the new windowP.left
						if (windowP.left.left != null) {
							windowP.left.left.parent = windowP.left;
						}
						if (windowP.left.right != null) {
							windowP.left.right.parent = windowP.left;
						}

						// Set currNode to windowP for traversal
						currNode = windowP;

						// Else if window is in the right subtree of window's
						// parent, replace it
					} else {
						windowP.right = window.left;

						// Updating the windowP right subtree parent
						windowP.right.parent = windowP;

						// Also updating the windowP.left subtrees parents to
						// the new windowP.left

						if (windowP.right.left != null) {
							windowP.right.left.parent = windowP.right;
						}

						if (windowP.right.right != null) {
							windowP.right.right.parent = windowP.right;
						}

						// Set currNode to windowP for traversal
						currNode = windowP;

					}

					// If the predecessor doesn't have a left subtree, (and
					// predecessor definitely not have a right subtree)
					// just delete the predecessor
				} else {

					Node windowP = window.parent;

					if (windowP.left == window) {
						windowP.left = null;
					} else {
						windowP.right = null;
					}

					// Set currNode to windowP for traversal
					currNode = windowP;

				}
			}

			// Adjust height as you traverse back up to the root
			// Traversing back up to root to update structure
			traverseUp(currNode);

			//Concatenating string operation to logString
			logString = logString + "Operation delete(E item) completed using "
					+ nComp + " comparisions\n";

			//Adding nComp to totalComparisons
			totalComparisons = totalComparisons + nComp;
			
			return true;
		}

	}


	/**
	*Provides a fail fast iterator for the Dictionary, starting at the least item. The iterator provides methods
	*such as haxNext(), next() and remove()
	*@return an iterator whose next element is the least element in the dictionary, and which will 
	*iterate through all the elements in the Dictionary in ascending order. 
	*/
	public Iterator<E> iterator() {
		//Concatenating string operation to logString
		logString = logString + "Operation iterator() completed using " + nComp + " comparisions\n";
		
		//Adding nComp to totalComparisons
		totalComparisons = totalComparisons + nComp;
		return new AVLTreeIterator<E>(this);
	}

	/**
	*Provides a fail fast iterator for the dictionary, starting at the least element greater than or equal to start
	*The iterator provides methods such as hasNext(), next() and remove(). 
	*@param start the item at which to start iterating at.
	*@return an iterator whose next item is the least item greater than or equal to start in the dictionary, and which will iterate through all the items in the dictionary in ascending order. 
	*/
	public Iterator<E> iterator(E start) {
		
		//Concatenating string operation to logString
		logString = logString + "Operation iterator(E start) completed using "+ nComp + " comparisions\n";
		
		//Adding nComp to totalComparisons
		totalComparisons = totalComparisons + nComp;
		return new AVLTreeIterator<E>(this, start);

	}

	/**
	*Present a string describing all operations performed on the table since its construction, or since the last time getLogString was called
	* Each operation that is directly called on the dictionary or provide an iterator of the dictionary, append a new log string. 
	*@return A string listing all operations called on the Dictionary, and how many comparisons were required to complete each operation.
	**/ 
	public String getLogString() {
		
		//Adding comparisons results after the operations of logString
		logString = logString + "Operation getLogString() was called\n";
		logString = logString + "Total Number of Comparisons: " + totalComparisons + "\n";
		logString = logString + "Number of Insertions: " + insertions + "\n";
		
		

		String result = logString;
		
		//Resetting totalComparisions, insertions and logString
		totalComparisons = 0;
		logString = "";
		insertions =0;
		return result;
	}

	
	/**
	*Presents a String representation of all the items in the dictionary
	*@return a String representation of the Dictionary
	**/
	public String toString() {
		String result = "";
		Iterator<E> it = new AVLTreeIterator(this);

		// Iterator returns the dictionary words in alphabetical order
		while (it.hasNext()) {
			result = result + it.next().toString() + "\n";
		}
		
		logString = logString + "Operation toString() completed using " + nComp + " comparisions\n";

		return result;

	}

	
	
	
	/**
	 * This class is the AVL tree iterator that the iterator methods calls from to provide an iterator 
	 * of the dictionary. It implements from the Iterator class form java.util providing
	 * methods such as next(), hasNext() and remove()
	 * @author Shaun Leong
	 *
	 * @param <E>
	 */
	private class AVLTreeIterator<E> implements Iterator<E> {

		private ListLinked itList; //ListLinked from the CITS2200 package for the iterator
		private E startPoint; //the point where where the iterator should start from or slightly greater than start
		private WindowLinked itListWin; //Window object from itList
		private AVLDict dictionary; //dictionary to iterate from 
		private int expModCount; // Expected modification count

		private boolean nextCall; //Indicates whether the next() has been recalled prior to remove

		
		/**
		 * Iterator constructor for the dictionary
		 * @param dict the dictionary to iterate from
		 */
		private AVLTreeIterator(AVLDict dict) {
			// Initializing ListLinked and WindowLinked objects
			itList = new ListLinked();
			itListWin = new WindowLinked();
			dictionary = dict;

			// Setting itListWin to beforeFirst position in itList
			itList.afterLast(itListWin);

			// Inorder traversing through tree and inserting elements
			traverseTree(dictionary.dictRoot);

			// After traversing, set window to the first element
			itList.beforeFirst(itListWin);
			itList.next(itListWin);

			// Initialize expModCount to dictionaryModcount, which should not
			// change while iterator is being used
			expModCount = dictionary.modCount;

			// Initializing nextCall to false since next hasn't been called yet
			nextCall = false;
		}

		/**
		 * Iterator constructor for the dictionary to start from the start item or 
		 * slightly greater than the start item
		 * @param dict the dictionary to iterate from
		 * @param start the item to which to start the iterator from 
		 */
		private AVLTreeIterator(AVLDict dict, E start) {
			startPoint = start;

			// Initializing ListLinked, WindowLinked objects and dictionary
			itList = new ListLinked();
			itListWin = new WindowLinked();
			dictionary = dict;

			// Setting itListWin to beforeFirst position in itList
			itList.afterLast(itListWin);

			// Finds the position for dictionary to start iterating

			// Inorder traversing through tree and inserting elements to itList
			traverseTree(dictionary.dictRoot);

			// After traversing, set window to the where its is equal or greater
			// than the startPoint
			itList.beforeFirst(itListWin);
			itList.next(itListWin);

			// If the list item is still less than the startPoint and the item
			// is not found in the list, keep traversing the list. After the
			// loop, itListWin should be over the item in the list
			while ((itListWin.link.item != (Object) start)
					&& (itListWin.link.item.toString().compareToIgnoreCase(
							startPoint.toString()) < 0)
					&& !itList.isAfterLast(itListWin)) {

				itList.next(itListWin);
			}

			// Initialize expModCount to dictionaryModcount, which should not
			// change while iterator is being used
			expModCount = dictionary.modCount;

			// Initializing nextCall to false since next hasn't been called yet
			nextCall = false;
		}

		/**
		 * Provides an in order traversal of the dictionary to add items
		 * to the itList for the iterator to iteterate from
		 * @param e the assumed dictionary root node
		 */
		private void traverseTree(Node e) {
			if (e != null) {
				traverseTree(e.left);
				itList.insertBefore(e.element, itListWin);
				traverseTree(e.right);

			}

		}

		/**
		 * Outputs a boolean to indicate whether there's an item left or not
		 * @return a boolean to indidcate whether there's an item left or not
		 */
		public boolean hasNext() {

			// If the modification counts are not the same, then throw exception
			if (expModCount != dictionary.modCount) {
				throw new ConcurrentModificationException();
			}

			logString = logString + "Operation hasNext() completed using "
					+ nComp + " comparisions\n";
			return !itList.isAfterLast(itListWin);
		}

		/**
		 * Provides the next item in the list if there is
		 * @return the item in the itList
		 */
		public E next() {
			// If the modification counts are not the same, then throw exception
			if (expModCount != dictionary.modCount) {
				throw new ConcurrentModificationException();
			}

			if (hasNext()) {
				E currItem = (E) itList.examine(itListWin);

				// Delete the item in itListWin to next element
				itList.next(itListWin);

				// Update nextCall boolean to true since next has been called
				nextCall = true;

				logString = logString + "Operation next() completed using "
						+ nComp + " comparisions\n";

				return currItem;
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 * Removes the last called item from the next() method in the itList structure
		 */
		public void remove() {

			// If the modification counts are not the same, then throw exception
			if (expModCount != dictionary.modCount) {
				throw new ConcurrentModificationException();
			}

			// Moving the window back to the element just returned by next
			itList.previous(itListWin);

			// If the window link is not null and next has been called
			if (itListWin.link != null && nextCall) {

				// Deleting the element from list. It will move to the next
				// element after
				itList.delete(itListWin);

				// Set nextCall back to false so it can been updated to true
				// when next is called again
				nextCall = false;

				logString = logString + "Operation remove() completed using "
						+ nComp + " comparisions\n";
			} else {

				// Move itListWin back to its initial element
				itList.next(itListWin);

				throw new IllegalStateException();
			}

		}

	}

}
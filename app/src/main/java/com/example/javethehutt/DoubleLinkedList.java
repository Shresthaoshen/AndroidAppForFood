package com.example.javethehutt;

import com.example.javethehutt.ListInterface;

public class DoubleLinkedList<T> implements ListInterface<T> {
    private DoubleLinkedNode first;
    private DoubleLinkedNode last;
    private int numElements;

    public DoubleLinkedList() {
        initializeDataFields();
    }

    /**
     * Adds a new entry to the end of this list.
     * Entries currently in the list are unaffected.
     * The list's size is increased by 1.
     *
     * @param newEntry The object to be added as a new entry.
     */
    @Override
    public void add(T newEntry) {
        DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.setNextNode(newNode);
            newNode.setPreviousNode(last);
            last = newNode;
        }

        numElements++;
    }

    /**
     * Adds a new entry at a specified position within this list.
     * Entries originally at and above the specified position
     * are at the next higher position within the list.
     * The list's size is increased by 1.
     *
     * @param newPosition An integer that specifies the desired
     *                    position of the new entry.
     * @param newEntry    The object to be added as a new entry.
     * @throws IndexOutOfBoundsException if either
     *                                   newPosition < 0 or newPosition > getLength().
     */
    @Override
    public void add(int newPosition, T newEntry) {

        if (newPosition < 0 || newPosition > getLength()) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);

        if (newPosition == 0) {

            if(!isEmpty()) {
                DoubleLinkedNode temp = getNodeAt(newPosition);

                newNode.setNextNode(temp);
                temp.setPreviousNode(newNode);
            }

            else{
                last = newNode;
            }

            first = newNode;
            numElements++;

        }

        if (!isEmpty() && newPosition > 0 && newPosition < getLength()) {
            DoubleLinkedNode temp = getNodeAt(newPosition);
            newNode.setNextNode(temp);
            newNode.setPreviousNode(temp.previous);

            newNode.getPreviousNode().setNextNode(newNode);
            temp.setPreviousNode(newNode);

            numElements++;
        }

        if (newPosition == getLength()) {

            newNode.setPreviousNode(last);
            last = newNode;
            numElements++;
        }

    }

    /**
     * Removes the entry at a given position from this list.
     * Entries originally at positions higher than the given
     * position are at the next lower position within the list,
     * and the list's size is decreased by 1.
     *
     * @param givenPosition An integer that indicates the position of
     *                      the entry to be removed.
     * @return A reference to the removed entry.
     * @throws IndexOutOfBoundsException if either
     *                                   givenPosition < 0 or givenPosition > getLength() - 1.
     */
    @Override
    public T remove(int givenPosition) {
        if (givenPosition > getLength()-1 || givenPosition < 0 || isEmpty()){
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }

        T removedData = getEntry(givenPosition);
        DoubleLinkedNode removedNode = getNodeAt(givenPosition);

        if (givenPosition == 0){
            first = removedNode.next;
            first.setPreviousNode(null);
        }

        if (givenPosition > 0 && givenPosition < getLength()-1) {

            removedNode.previous.setNextNode(removedNode.next);
            removedNode.next.setPreviousNode(removedNode.previous);
        }

        if (givenPosition == getLength()-1){
            last = removedNode.previous;
            last.setNextNode(null);
        }

        numElements--;
        return removedData;
    }


    /**
     * Removes all entries from this list.
     */
    @Override
    public void clear() {
        initializeDataFields();
    }

    /**
     * Replaces the entry at a given position in this list.
     *
     * @param givenPosition An integer that indicates the position of
     *                      the entry to be replaced.
     * @param newEntry      The object that will replace the entry at the
     *                      position givenPosition.
     * @return The original entry that was replaced.
     * @throws IndexOutOfBoundsException if either
     *                                   givenPosition < 0 or givenPosition > getLength() -1.
     */
    @Override
    public T replace(int givenPosition, T newEntry) {
        if (givenPosition > getLength()-1 || givenPosition < 0 || isEmpty()){
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }

        T replacedData = getEntry(givenPosition);
        DoubleLinkedNode replacedNode = getNodeAt(givenPosition);
        DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);

        if (givenPosition == 0){
            newNode.setNextNode(first.next);
            newNode.next.setPreviousNode(newNode);
            first = newNode;
        }

        if (givenPosition > 0 && givenPosition < getLength()-1) {
            newNode.setNextNode(replacedNode.next);
            newNode.setPreviousNode(replacedNode.previous);
        }

        if (givenPosition == getLength()-1){
            newNode.setPreviousNode(last.previous);
            newNode.previous.setNextNode(newNode);
            last = newNode;
        }

        return replacedData;
    }

    /**
     * Retrieves all entries that are in this list in the order in which
     * they occur in the list.
     *
     * @return A newly allocated array of all the entries in the list.
     * If the list is empty, the returned array is empty.
     */
    @Override
    public T[] toArray() {
        T[] dataArray = (T[])new Object[numElements];

        for (int i = 0; i < numElements; i++){
            dataArray[i] = getEntry(i);
        }

        return dataArray;
    }

    /**
     * Sees whether this list contains a given entry.
     *
     * @param anEntry The object that is the desired entry.
     * @return True if the list contains anEntry, or false if not.
     */
    @Override
    public boolean contains(T anEntry) {
        for (int i = 0; i < numElements; i++){
            if (getEntry(i) == anEntry){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the length of this list.
     *
     * @return The integer number of entries currently in the list.
     */
    @Override
    public int getLength() {
        return numElements;
    }

    /**
     * Sees whether this list is empty.
     *
     * @return True if the list is empty, or false if not.
     */
    @Override
    public boolean isEmpty() {
        return first == null && last == null && numElements == 0;
    }

    // Initializes the class's data fields to indicate an empty list.
    private void initializeDataFields() {
        first = null;
        last = null;
        numElements = 0;
    }

    public DoubleLinkedNode getFirst() {
        return first;
    }

    public DoubleLinkedNode getLast() {
        return last;
    }

    /**
     * Retrieves the entry at a given position in this list.
     *
     * @param givenPosition An integer that indicates the position of
     *                      the desired entry.
     * @return A reference to the indicated entry.
     * @throws IndexOutOfBoundsException if either
     *                                   givenPosition < 0 or givenPosition > getLength() - 1.
     */
    @Override
    public T getEntry(int givenPosition) {
        if ((givenPosition >= 0) && (givenPosition < numElements)) {
            assert !isEmpty();
            return getNodeAt(givenPosition).getData();
        } else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    }

    // Returns a reference to the node at a given position.
    // Precondition: The chain is not empty;
    //               0 <= givenPosition <= numberOfEntries.
    DoubleLinkedNode getNodeAt(int givenPosition) {
        assert !isEmpty() && (0 <= givenPosition) && (givenPosition < numElements);
        DoubleLinkedNode currentNode = first;

        // Traverse the chain to locate the desired node
        // (skipped if givenPosition is 1)
        for (int counter = 0; counter < givenPosition; counter++) {
            currentNode = currentNode.getNextNode();
        }
        assert currentNode != null;

        return currentNode;
    }


    class DoubleLinkedNode {
        private T data;
        private DoubleLinkedNode next;     // Link to next node
        private DoubleLinkedNode previous;       // Link to previous node

        private DoubleLinkedNode(T dataPortion) {
            data = dataPortion;
            next = null;
            previous = null;
        }

        private DoubleLinkedNode(T dataPortion, DoubleLinkedNode nextNode, DoubleLinkedNode previousNode) {
            data = dataPortion;
            next = nextNode;
            previous = previousNode;
        }

        T getData() {
            return data;
        }

        private void setData(T newData) {
            data = newData;
        }

        DoubleLinkedNode getNextNode() {
            return next;
        }

        private void setNextNode(DoubleLinkedNode nextNode) {
            next = nextNode;
        }

        DoubleLinkedNode getPreviousNode() {
            return previous;
        }

        private void setPreviousNode(DoubleLinkedNode previousNode) {
            previous = previousNode;
        }
    }
}

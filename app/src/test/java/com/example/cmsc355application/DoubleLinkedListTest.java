package com.example.cmsc355application;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoubleLinkedListTest {
	DoubleLinkedList<String> list;

	@Before
	public void setUp()  {
		list = new DoubleLinkedList<String>();
	}

	public void populateList() {
		list.add("Will");
		list.add("Dustin");
		list.add("Lucas");
		list.add("Mike");
		list.add("Eleven");
	}
	
	@Test
	// test that clear sets the number of elements in the list to 0
	public final void testClear1() {
		assertTrue(list.getLength() == 0);
	}
	
	@Test
	// test that clear sets the first node to null
	public final void testClear2() {
		assertTrue(list.getFirst() == null);
	}
	
	@Test
	// test that clear sets the last node to null
	public final void testClear3() {
		assertTrue(list.getLast() == null);
	}
	
//	 ********* Second group of tests - add method *********
	 @Test
	 // Test add to an empty list for last reference
	 public final void testAddT0() {
	 	list.add("Barb");
	 	assertNotNull(list.getLast());;
	 }

	 @Test
	 // Test add to an empty list for first reference
	 public final void testAddT00() {
	 	list.add("Barb");
	 	assertNotNull(list.getFirst());;
	 }

	 @Test
	 // Test add to an empty list for updating counter
	 public final void testAddT1() {
	 	list.add("Barb");
	 	assertTrue(list.getLength()==1);
	 }

  	@Test
  	// After adding into a position
  	public final void testAddGetLength() {
  		populateList();
  		list.add(4, "Jonathan");
  		assertEquals(6, list.getLength());
  	}
	
// ********* Third group of tests - more add method *********
	 @Test
	 // Test add to end of non-empty list
	 public final void testAddT2() {
	 	list.add("Will");
	 	list.add("Barb");
	 	assertTrue(list.getLength()==2);
	 }

	 @Test
	 // Test add to end of non-empty list
	 public final void testAddT3() {
	 	list.add("Will");
	 	list.add("Barb");
	 	assertNotNull(list.getLast());
	 }

	 @Test
	 // Test add to end of non-empty list
	 public final void testAddT4() {
	 	list.add("Will");
	 	list.add("Barb");
	 	assertTrue(list.getLast().getData().equals("Barb"));
	 }

// ********* Fourth group of tests - add to position method *********
		@Test
	 // Add to first position of an empty list
	 public final void testAddIntT1a() {
	 	list.add(0, "Barb");
	 	assertTrue(list.getFirst().getData().equals("Barb"));
	 }
	
	 @Test
	 // Add to first position of an empty list
	 public final void testAddIntT1b() {
	 	list.add(0, "Barb");
	 	assertTrue(list.getFirst()==list.getNodeAt(0));
	 }
	
	 @Test
	 // Add to first position of an empty list
	 public final void testAddIntT1c() {
	 	list.add(0, "Barb");
	 	assertNotNull(list.getLast());
	 }

	 @Test
	 // Add to first position of an non-empty list
	 public final void testAddIntT2a() {
	 	populateList();
	 	list.add(0, "Barb");
	 	assertTrue(list.getNodeAt(1).getPreviousNode() != null);
	 }
	
	 @Test
	 // Add to first position of an non-empty list
	 public final void testAddIntT2b() {
	 	populateList();
	 	list.add(0, "Barb");
	 	assertTrue(list.getNodeAt(0) == list.getFirst());
	 }
	
	 @Test
	 // Add to third position of an non-empty list
	 public final void testAddIntT3a() {
	 	populateList();
	 	list.add(2, "Barb");
	 	assertNotNull(list.getNodeAt(2).getPreviousNode());
	 }
	
	 @Test
	 // Add to third position of an non-empty list
	 public final void testAddIntT3b() {
	 	populateList();
	 	list.add(2, "Barb");
	 	assertEquals(list.getNodeAt(3).getPreviousNode(), list.getNodeAt(2));

	 }
	
	 @Test
	 // Add to last position of an non-empty list
	 public final void testAddIntT4() {
	 	populateList();
	 	list.add(5, "Barb");
	 	assertNull(list.getLast().getNextNode());
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 // Add to illegal position of an empty list
	 public final void testAddIntT5() {
	 	populateList();
	 	list.add(-1, "Barb");
	 	assertNull(list.getLast().getNextNode());
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 // Add to illegal position of an non-empty list
	 public final void testAddIntT6() {
	 	populateList();
	 	list.add(11, "Barb");
	 	assertNull(list.getLast().getNextNode());
	 }
	
	
	 //********* Fifth group of tests - remove method *********
	 @Test
	 // Remove from front
	 public final void testRemove1a() {
	 	populateList();
	 	assertEquals("Will", list.remove(0));
	 }
	
	 @Test
	 // Remove from front
	 public final void testRemove1b() {
	 	populateList();
	 	list.remove(0);
	 	assertNull(list.getFirst().getPreviousNode());
	 }
	
	 @Test
	 // Remove last node
	 public final void testRemove2() {
	 	populateList();
	 	assertEquals("Eleven", list.remove(4));
	 }

	 @Test
	 // Remove from the middle
	 public final void testRemove3a() {
	 	populateList();
	 	assertEquals("Lucas", list.remove(2));
	 }
	
	 @Test
	 // Remove from the middle
	 public final void testRemove3b() {
	 	populateList();
	 	list.remove(2);
	 	assertEquals("Dustin", ((list.getNodeAt(2)).getPreviousNode()).getData());
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 // Remove too small index
	 public final void testRemove4() {
	 	populateList();
	 	assertEquals("Will", list.remove(-1));
	 }

	 @Test (expected=IndexOutOfBoundsException.class)
	 // Remove too large index
	 public final void testRemove5() {
	 	populateList();
	 	assertEquals("Will", list.remove(9));
	 }

	
	// ********* Sixth group of tests - replace method *********
	 @Test
	 public final void testReplace1() {
	 	populateList();
	 	list.add("Barb");
	 	list.replace(5, "Nothing");
	 	assertEquals(list.getLast().getData(), "Nothing");
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 public final void testReplace2() {
	 	list.add("Barb");
	 	list.replace(5, "Nothing");
	 	assertEquals(list.getLast().getData(), "Nothing");
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 public final void testReplace3() {
	 	list.replace(0, "Nothing");
	 	assertEquals(list.getFirst().getData(), "Nothing");
	 }


	// ********* Seventh group of tests - getEntry method *********	
	 @Test
	 // Position is close to front
	 public final void testGetEntry1() {
	 	populateList();
	 	assertEquals(list.getEntry(1), "Dustin");
	 }

	 @Test
	 // Position is close to back
	 public final void testGetEntry2() {
	 	populateList();
	 	assertEquals(list.getEntry(4), "Eleven");
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 // Position is too large
	 public final void testGetEntry3() {
	 	populateList();
	 	assertEquals(list.getEntry(7), "Eleven");
	 }
	
	 @Test (expected=IndexOutOfBoundsException.class)
	 // Position is too small
	 public final void testGetEntry4() {
	 	populateList();
	 	assertEquals(list.getEntry(-1), "Dustin");
	 }

	
	// ********* Eighth group of tests - toArray method *********
	 @Test
	 // Empty list
	 public final void testToArray1() {
	 	String[] names = new String[0];
	 	assertArrayEquals(names, list.toArray());
	 }
	
	 @Test
	 // Populated list
	 public final void testToArray2() {
	 	populateList();
	 	String[] names = {"Will", "Dustin", "Lucas", "Mike", "Eleven"};
	 	assertArrayEquals(names, list.toArray());
	 }

	 //********* Ninth group of tests - contains method *********
	 @Test
	 // Empty list
	 public final void testContains1() {
	 	assertFalse(list.contains("Eleven"));
	 }
	 @Test
	 // Populated list
	 public final void testContains2() {
	 	populateList();
	 	assertTrue(list.contains("Eleven"));
	 }
	 @Test
	 // Added to list
	 public final void testContains3() {
	 	populateList();
	 	list.add("Barb");
	 	assertTrue(list.contains("Barb"));
	 }
	 @Test
	 // Not added to list
	 public final void testContains4() {
	 	populateList();
	 	assertFalse(list.contains("Barb"));
	 }
}

package ex21_04;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Test;

public class TestShortStrings_List
{
	private ArrayList<String> createList()
	{
		ArrayList<String> list = new ArrayList<String>();

		list.add("123456");
		list.add("abc");
		list.add("abcdef");
		list.add("a");
		list.add("ab");
		list.add("abcdefg");
		list.add("abcdefgh");
		list.add("yz");
		list.add("vwxyz");
		
		return list;
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testEmptyList_previous()
	{
		ArrayList<String> list = new ArrayList<>();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 10);
		iter.previous();
	}

	@Test(expected=NoSuchElementException.class)
	public void testEmptyList_next()
	{
		ArrayList<String> list = new ArrayList<>();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 10);
		iter.next();
	}

	@Test
	public void testEmptyList_hasPrevious()
	{
		ArrayList<String> list = new ArrayList<>();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 10);
		assertFalse(iter.hasPrevious());
	}

	@Test
	public void testEmptyList_hasNext()
	{
		ArrayList<String> list = new ArrayList<>();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 10);
		assertFalse(iter.hasNext());
	}

	@Test
	public void testIterator1()
	{
		ArrayList<String> list = this.createList();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 5);

		assertEquals(-1, iter.previousIndex());
		assertEquals(0, iter.nextIndex());
		
		assertTrue(iter.hasNext());
		assertEquals("abc", iter.next());
		assertEquals(0, iter.previousIndex());
		assertEquals(1, iter.nextIndex());
		
		assertTrue(iter.hasNext());
		assertEquals("a", iter.next());
		assertEquals(1, iter.previousIndex());
		assertEquals(2, iter.nextIndex());
		
		assertTrue(iter.hasNext());
		assertEquals("ab", iter.next());
		assertEquals(2, iter.previousIndex());
		assertEquals(3, iter.nextIndex());
		
		assertTrue(iter.hasNext());
		assertEquals("yz", iter.next());
		assertEquals(3, iter.previousIndex());
		assertEquals(4, iter.nextIndex());
		
		assertTrue(iter.hasNext());
		assertEquals("vwxyz", iter.next());
		assertEquals(4, iter.previousIndex());
		assertEquals(5, iter.nextIndex());
		
		assertFalse(iter.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void testIterator2()
	{
		ArrayList<String> list = this.createList();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 5);
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		iter.next();
	}

	@Test
	public void testIterator3()
	{
		ArrayList<String> list = this.createList();
		ShortStrings_List iter = new ShortStrings_List(list.listIterator(), 5);
		assertTrue(iter.hasNext());
		assertEquals("abc", iter.next());
		assertTrue(iter.hasNext());
		assertEquals("a", iter.next());
		assertTrue(iter.hasPrevious());
		assertEquals("a", iter.previous());
		assertTrue(iter.hasPrevious());
		assertEquals("abc", iter.previous());
	}
}

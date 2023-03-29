package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
    private Event e2;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Added an item!");   // (1)
        e2 = new Event("Deleted an item!");
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Added an item!", e.getDescription());
		assertTrue(Math.abs(d.getTime() - e.getDate().getTime()) < 10);
	}

    @Test
    public void testEqualsNull() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(e.equals(d));
    }

    @Test
    public void testEqualsDifferentDescription() {
        assertNotEquals(e, e2);
    }

    @Test
    public void testHashCode() {
        e2 = new Event("Added an item!");
        assertEquals(e2.hashCode(), e.hashCode());
    }

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Added an item!", e.toString());
	}
}

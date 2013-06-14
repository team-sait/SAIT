package sait.model.database;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryDBTest {

	@Test
	public void testGetCategoryIDByCategory() {

		// MyClass is tested
		QueryDB tester1 = new QueryDB();

		// Check if multiply(10,5) returns 50

		assertEquals("Category should be 1", 1,
				tester1.GetCategoryIDByCategory("Innenraum"));

	}

	@Test
	public void testqueryDBByID() {
		QueryDB tester2 = new QueryDB();
		String[] shouldBeThis = { "Ein Brandloch im Autositz entfernen.htm",
				"Frühlingsputz im Auto.htm", "Sauberes Auto ist Gesundheit.htm" };

		assertArrayEquals(shouldBeThis, tester2.queryDBByID("hinweise", 1));
		
		assertNull(tester2.queryDBByID("Gibtsnicht", 1));
	}

	@Test
	public void testqueryDB() {
		QueryDB tester3 = new QueryDB();

		String[] shouldBeThis2 = { "Innenraum","Karosserie","Klimaanlage und Innenraumfilter","Motor und Motorraum","Reifen und Räder","Sicherheit","Werkstatt - Tipps" };
		assertArrayEquals(shouldBeThis2, tester3.queryDB("hinweise_kategorien"));
		
		

	}

}

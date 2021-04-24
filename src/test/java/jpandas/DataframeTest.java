package jpandas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataframeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testConstructor() {
		Dataframe test = new Dataframe(new String[]{"count", "name"},
									   new Integer[]{5, 2, 4},
									   new String[]{"Anabelle", "Gertrude", "Diana"});
		assertArrayEquals(new Integer[]{5, 2, 4},
						  ((ArrayList<Integer>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"Anabelle", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
	}
	
	@Test
	void testConstructorCSV() {
		Dataframe test = new Dataframe("test.csv");
		assertArrayEquals(new Integer[]{5, 2, 4},
						  ((ArrayList<Integer>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"Anabelle", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
	}
	
	@Test
	void testConstructorCSVAmbiguousTypes() {
		Dataframe test = new Dataframe("test_types.csv");
		assertArrayEquals(new Double[]{5.0, 2.5, 4.5},
						  ((ArrayList<Double>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"5", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
	}
	
	@Test
	void testDisplay() {
		Dataframe test = new Dataframe("test.csv");
		System.out.println("Should print all 3 lines");
		test.display();
	}
	
	@Test
	void testDisplayFirst() {
		Dataframe test = new Dataframe("test.csv");
		System.out.println("Should print first line");
		test.displayFirst(1);
	}
	
	@Test
	void testDisplayLast() {
		Dataframe test = new Dataframe("test.csv");
		System.out.println("Should print last 2 lines");
		test.displayLast(2);
	}
	
	@Test
	void testMin() throws Exception {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(2.5, test.min("count"));
		
		assertThrows(Exception.class, () -> {
			test.min("name");
		});
	}

	@Test
	void testMean() throws Exception {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(4, test.mean("count"));
		
		assertThrows(Exception.class, () -> {
			test.mean("name");
		});
	}
	
	@Test
	void testMax() throws Exception {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(5, test.max("count"));
		
		assertThrows(Exception.class, () -> {
			test.max("name");
		});
	}
	
	@Test
	void testCount() {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(3, test.count("name"));
	}
	
	@Test
	void testIloc() {
		Dataframe test = new Dataframe("test.csv");
		Dataframe selected = test.iloc(new int[]{2, 1, 0}); // Should reverse the order of lines
		assertArrayEquals(new Integer[]{4, 2, 5},
				  ((ArrayList<Integer>) selected.get("count")).toArray());
		assertArrayEquals(new String[]{"Diana", "Gertrude", "Anabelle"},
				  ((ArrayList<String>) selected.get("name")).toArray());
	}
	
	@Test
	void testLoc() {
		Dataframe test = new Dataframe("test.csv");
		Dataframe selected = test.loc("name");
		assertArrayEquals(new String[]{"Anabelle", "Gertrude", "Diana"},
				  ((ArrayList<String>) selected.get("name")).toArray());
		assertEquals(null, selected.get("count"));
	}
}

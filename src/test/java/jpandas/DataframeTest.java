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
		Dataframe test = new Dataframe(new String[]{"count", "name", "time"},
									   new Integer[]{5, 2, 4},
									   new String[]{"Anabelle", "Gertrude", "Diana"},
									   new Double[]{3.5, 4.2, 3.3});
		assertArrayEquals(new Integer[]{5, 2, 4},
						  ((ArrayList<Integer>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"Anabelle", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
		assertArrayEquals(new Double[]{3.5, 4.2, 3.3},
		  		  		  ((ArrayList<Integer>) test.get("time")).toArray());
	}
	
	@Test
	void testConstructorCSV() {
		Dataframe test = new Dataframe("test.csv");
		assertArrayEquals(new Integer[]{5, 2, 4},
						  ((ArrayList<Integer>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"Anabelle", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
		assertArrayEquals(new Double[]{3.5, 4.2, 3.3},
		  		  		  ((ArrayList<Integer>) test.get("time")).toArray());
	}
	
	@Test
	void testConstructorCSVAmbiguousTypes() {
		Dataframe test = new Dataframe("test_types.csv");
		assertArrayEquals(new Double[]{5.0, 2.5, 4.5},
						  ((ArrayList<Double>) test.get("count")).toArray());
		assertArrayEquals(new String[]{"5", "Gertrude", "Diana"},
						  ((ArrayList<String>) test.get("name")).toArray());
		assertArrayEquals(new Integer[]{5423, 4626, 4852},
				  		  ((ArrayList<Integer>) test.get("time")).toArray());
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
		assertEquals(4626, test.min("time"));
		
		assertThrows(Exception.class, () -> {
			test.min("name");
		});
	}

	@Test
	void testMean() throws Exception {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(4, test.mean("count"));
		assertEquals(4967, test.mean("time"));
		
		assertThrows(Exception.class, () -> {
			test.mean("name");
		});
	}
	
	@Test
	void testMax() throws Exception {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(5, test.max("count"));
		assertEquals(5423, test.max("time"));
		
		assertThrows(Exception.class, () -> {
			test.max("name");
		});
	}
	
	@Test
	void testCount() {
		Dataframe test = new Dataframe("test_types.csv");
		assertEquals(3, test.count("name"));
		assertEquals(3, test.count("count"));
		assertEquals(3, test.count("time"));
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
	
	@Test
	void testLocIloc() {
		Dataframe test = new Dataframe("test.csv");
		Dataframe selected = test.loc("name").iloc(0);
		assertArrayEquals(new String[]{"Anabelle"},
				  ((ArrayList<String>) selected.get("name")).toArray());
		assertEquals(null, selected.get("count"));
	}
	
	@Test
	void testSelectWhere() {
		Dataframe test = new Dataframe("test_selectwhere.csv");
		Dataframe selected = test.selectWhere("name", "test2");
		assertArrayEquals(new Integer[]{21, 22, 23},
				  ((ArrayList<Integer>) selected.get("count")).toArray());
		assertArrayEquals(new String[]{"test2", "test2", "test2"},
				  ((ArrayList<String>) selected.get("name")).toArray());
	}
}

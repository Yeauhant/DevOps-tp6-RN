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
}

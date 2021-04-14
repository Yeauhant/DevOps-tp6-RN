package jpandas;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Dataframe extends HashMap<String, Object> {
	public Dataframe(String[] labels, Object[]... columns) {
		for (int i = 0; i < labels.length; i++) {
			if (columns[i] instanceof Double[]) {
				ArrayList<Double> al = new ArrayList<Double>();
				for(Object s : columns[i])
					al.add((Double) s);
				this.put(labels[i], al);
			} else if (columns[i] instanceof Integer[]) {
				ArrayList<Integer> al = new ArrayList<Integer>();
				for(Object s : columns[i])
					al.add((Integer) s);
				this.put(labels[i], al);
			} else if (columns[i] instanceof String[]) {
				ArrayList<String> al = new ArrayList<String>();
				for(Object s : columns[i])
					al.add((String) s);
				this.put(labels[i], al);
			}
		}
	}
}

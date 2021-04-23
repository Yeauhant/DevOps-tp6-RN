package jpandas;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public Dataframe(String filename) {
		try {
			List<String> linesNotSeparated = Files.readAllLines(Paths.get(filename));
			List<String[]> lines = new ArrayList<String[]>();
			for (String line : linesNotSeparated) {
				lines.add(line.split(","));
			}
			
			boolean firstTime = true;
			
			String[] labels = lines.get(0);
			
			for (int i = 1; i < lines.size(); i++) {
				String[] line = lines.get(i);
				
				for (int j = 0; j < labels.length; j++) {
					String word = line[j];
					
					try {
						Integer wordI = Integer.parseInt(word);
						// Is an Integer
						if (firstTime)
							this.put(labels[j], new ArrayList<Integer>());
						
						ArrayList<Integer> al = (ArrayList<Integer>) this.get(labels[j]);
						al.add(wordI);
					} catch(NumberFormatException e) {
						try {
							Double wordD = Double.parseDouble(word);
							// Is a Double
							if (firstTime)
								this.put(labels[j], new ArrayList<Double>());
							
							ArrayList<Double> al = (ArrayList<Double>) this.get(labels[j]);
							al.add(wordD);
						} catch(NumberFormatException e2) {
							// Is a String
							if (firstTime)
								this.put(labels[j], new ArrayList<String>());
							
							ArrayList<String> al = (ArrayList<String>) this.get(labels[j]);
							al.add(word);
						}
					}
				}
				firstTime = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

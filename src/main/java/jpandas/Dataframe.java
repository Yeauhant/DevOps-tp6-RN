package jpandas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Dataframe extends LinkedHashMap<String, Object> {
	private HashMap<String, Type> types = new HashMap<String, Type>();
	
	public Dataframe(String[] labels, Object[]... columns) {
		for (int i = 0; i < labels.length; i++) {
			if (columns[i] instanceof Double[]) {
				ArrayList<Double> al = new ArrayList<Double>();
				for(Object s : columns[i])
					al.add((Double) s);
				this.put(labels[i], al);
				types.put(labels[i], Type.DOUBLE);
			} else if (columns[i] instanceof Integer[]) {
				ArrayList<Integer> al = new ArrayList<Integer>();
				for(Object s : columns[i])
					al.add((Integer) s);
				this.put(labels[i], al);
				types.put(labels[i], Type.INTEGER);
			} else if (columns[i] instanceof String[]) {
				ArrayList<String> al = new ArrayList<String>();
				for(Object s : columns[i])
					al.add((String) s);
				this.put(labels[i], al);
				types.put(labels[i], Type.STRING);
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
						if (firstTime) {
							this.put(labels[j], new ArrayList<Integer>());
							types.put(labels[j], Type.INTEGER);
						}
						
						ArrayList<Integer> al = (ArrayList<Integer>) this.get(labels[j]);
						al.add(wordI);
					} catch(NumberFormatException e) {
						try {
							Double wordD = Double.parseDouble(word);
							// Is a Double
							if (firstTime) {
								this.put(labels[j], new ArrayList<Double>());
								types.put(labels[j], Type.DOUBLE);
							}

							ArrayList<Double> al = (ArrayList<Double>) this.get(labels[j]);
							al.add(wordD);
						} catch(NumberFormatException e2) {
							// Is a String
							if (firstTime) {
								this.put(labels[j], new ArrayList<String>());
								types.put(labels[j], Type.STRING);
							}
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
	
	private static int maxChars(ArrayList<ArrayList<String>> disp, int column) {
		int max = 0;
		for (int i = 0; i < disp.get(0).size(); i++) {
			int currLength = disp.get(i).get(column).length();
			if (currLength > max)
				max = currLength;
		}
		return max;
	}
	
	private void displayLabels(ArrayList<ArrayList<String>> disp) {

		boolean firstTime = true;
		for (String label : this.keySet()) {
			if (firstTime) {
				firstTime = false;
				
				ArrayList<String> a = new ArrayList<String>();
				a.add(label);
				disp.add(a);
			} else {
				disp.get(0).add(label);
			}
		}
	}
	
	private void displayPrint(ArrayList<ArrayList<String>> disp) {
		for (ArrayList<String> al : disp) {
			boolean firstTime = true;
			for (int i = 0; i < al.size(); i++) {
				int maxSize = maxChars(disp, i);
				String s = al.get(i);
				if (firstTime) {
					firstTime = false;
					System.out.print(s);
					for(int k = 0; k < maxSize - s.length(); k++)
						System.out.print(" ");
				} else {
				   	System.out.print(" | " + s);
					for(int k = 0; k < maxSize - s.length(); k++)
						System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void display() {
		ArrayList<ArrayList<String>> disp = new ArrayList<ArrayList<String>>();
		displayLabels(disp);

		boolean firstTime = true;
		for (String label : this.keySet()) {
			switch(types.get(label)) {
			case DOUBLE:
				ArrayList<Double> entryD = (ArrayList<Double>) this.get(label);
				for (int i = 0; i < entryD.size(); i++) {
					String elem = entryD.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			case INTEGER:
				ArrayList<Integer> entryI = (ArrayList<Integer>) this.get(label);
				for (int i = 0; i < entryI.size(); i++) {
					String elem = entryI.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			case STRING:
				ArrayList<String> entry = (ArrayList<String>) this.get(label);
				for (int i = 0; i < entry.size(); i++) {
					String elem = entry.get(i);
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			}
			firstTime = false;
		}
		
		displayPrint(disp);
	}
	
	public void displayFirst(int nbLines) {
		ArrayList<ArrayList<String>> disp = new ArrayList<ArrayList<String>>();
		displayLabels(disp);

		boolean firstTime = true;
		for (String label : this.keySet()) {
			switch(types.get(label)) {
			case DOUBLE:
				ArrayList<Double> entryD = (ArrayList<Double>) this.get(label);
				for (int i = 0; i < (entryD.size() < nbLines ? entryD.size() : nbLines); i++) {
					String elem = entryD.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			case INTEGER:
				ArrayList<Integer> entryI = (ArrayList<Integer>) this.get(label);
				for (int i = 0; i < (entryI.size() < nbLines ? entryI.size() : nbLines); i++) {
					String elem = entryI.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			case STRING:
				ArrayList<String> entry = (ArrayList<String>) this.get(label);
				for (int i = 0; i < (entry.size() < nbLines ? entry.size() : nbLines); i++) {
					String elem = entry.get(i);
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i+1).add(elem);
					}
				}
				break;
			}
			firstTime = false;
		}
		
		displayPrint(disp);
	}
	
	public void displayLast(int nbLines) {
		ArrayList<ArrayList<String>> disp = new ArrayList<ArrayList<String>>();
		displayLabels(disp);

		boolean firstTime = true;
		for (String label : this.keySet()) {
			int startVal;
			switch(types.get(label)) {
			case DOUBLE:
				ArrayList<Double> entryD = (ArrayList<Double>) this.get(label);
				startVal = (entryD.size() - nbLines < 0 ? 0 : entryD.size() - nbLines);
				for (int i = startVal; i < entryD.size(); i++) {
					String elem = entryD.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i-(entryD.size() - nbLines)+1).add(elem);
					}
				}
				break;
			case INTEGER:
				ArrayList<Integer> entryI = (ArrayList<Integer>) this.get(label);
				startVal = (entryI.size() - nbLines < 0 ? 0 : entryI.size() - nbLines);
				for (int i = startVal; i < entryI.size(); i++) {
					String elem = entryI.get(i).toString();
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i-startVal+1).add(elem);
					}
				}
				break;
			case STRING:
				ArrayList<String> entry = (ArrayList<String>) this.get(label);
				startVal = (entry.size() - nbLines < 0 ? 0 : entry.size() - nbLines);
				for (int i = startVal; i < entry.size(); i++) {
					String elem = entry.get(i);
					if (firstTime) {
						ArrayList<String> a = new ArrayList<String>();
						a.add(elem);
						disp.add(a);
					} else {
						disp.get(i-startVal+1).add(elem);
					}
				}
				break;
			}
			firstTime = false;
		}
		
		displayPrint(disp);
	}
}

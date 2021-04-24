package jpandas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
			
			for (int j = 0; j < labels.length; j++) {
				boolean isInteger = true;
				boolean isDouble = true;
				for (int i = 1; i < lines.size(); i++) {
					String word = lines.get(i)[j];
					
					try {
						Integer wordI = Integer.parseInt(word);
						// Is an Integer
					} catch(NumberFormatException e) {
						try {
							Double wordD = Double.parseDouble(word);
							// Is a Double
							isInteger = false;
						} catch(NumberFormatException e2) {
							// Is a String
							isDouble = false;
							isInteger = false;
							break;
						}
					}
				}
				
				if (isInteger) {
					this.put(labels[j], new ArrayList<Integer>());
					types.put(labels[j], Type.INTEGER);
				} else if (isDouble) {
					this.put(labels[j], new ArrayList<Double>());
					types.put(labels[j], Type.DOUBLE);
				} else {
					this.put(labels[j], new ArrayList<String>());
					types.put(labels[j], Type.STRING);
				}
			}
			
			for (int i = 1; i < lines.size(); i++) {
				String[] line = lines.get(i);
				
				for (int j = 0; j < labels.length; j++) {
					String word = line[j];
					
					switch (types.get(labels[j])) {
					case INTEGER:
						Integer wordI = Integer.parseInt(word);
						// Is an Integer
						ArrayList<Integer> alI = (ArrayList<Integer>) this.get(labels[j]);
						alI.add(wordI);
						break;
					case DOUBLE:
						Double wordD = Double.parseDouble(word);
						// Is a Double
						ArrayList<Double> alD = (ArrayList<Double>) this.get(labels[j]);
						alD.add(wordD);
						break;
					case STRING:
						// Is a String
						ArrayList<String> al = (ArrayList<String>) this.get(labels[j]);
						al.add(word);
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

	public double max(String label) throws Exception {
		switch (types.get(label)) {
		case INTEGER:
			ArrayList<Integer> alI = (ArrayList<Integer>) this.get(label);
			int maxI = alI.get(0);
			for (int i = 1; i < alI.size(); i++) {
				if (alI.get(i) > maxI)
					maxI = alI.get(i);
			}
			return (double)maxI;
		case DOUBLE:
			ArrayList<Double> alD = (ArrayList<Double>) this.get(label);
			double maxD = alD.get(0);
			for (int i = 1; i < alD.size(); i++) {
				if (alD.get(i) > maxD)
					maxD = alD.get(i);
			}
			return maxD;
		default:
			throw new Exception("Unimplemented for String columns");
		}
	}

	public double min(String label) throws Exception {
		switch (types.get(label)) {
		case INTEGER:
			ArrayList<Integer> alI = (ArrayList<Integer>) this.get(label);
			int minI = alI.get(0);
			for (int i = 1; i < alI.size(); i++) {
				if (alI.get(i) < minI)
					minI = alI.get(i);
			}
			return (double)minI;
		case DOUBLE:
			ArrayList<Double> alD = (ArrayList<Double>) this.get(label);
			double minD = alD.get(0);
			for (int i = 1; i < alD.size(); i++) {
				if (alD.get(i) < minD)
					minD = alD.get(i);
			}
			return minD;
		default:
			throw new Exception("Unimplemented for String columns");
		}
	}

	public double mean(String label) throws Exception {
		switch (types.get(label)) {
		case INTEGER:
			ArrayList<Integer> alI = (ArrayList<Integer>) this.get(label);
			double meanI = alI.get(0);
			for (int i = 1; i < alI.size(); i++) {
				meanI += alI.get(i);
			}
			meanI /= alI.size();
			return (double)meanI;
		case DOUBLE:
			ArrayList<Double> alD = (ArrayList<Double>) this.get(label);
			double meanD = alD.get(0);
			for (int i = 1; i < alD.size(); i++) {
				meanD += alD.get(i);
			}
			meanD /= alD.size();
			return meanD;
		default:
			throw new Exception("Unimplemented for String columns");
		}
	}

	public int count(String label) {
		switch (types.get(label)) {
		case INTEGER:
			ArrayList<Integer> alI = (ArrayList<Integer>) this.get(label);
			return alI.size();
		case DOUBLE:
			ArrayList<Double> alD = (ArrayList<Double>) this.get(label);
			return alD.size();
		default:
			ArrayList<String> al = (ArrayList<String>) this.get(label);
			return al.size();
		}
	}
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class HammingDist {

	private ArrayList<String> stations = new ArrayList<String>();
	
	public HammingDist() throws IOException {
		readFile();
	}

	
	//hamdist, takes in a ham distance and a given station. returns an array with all stations with same ham dist
	public ArrayList<String> calcHammingDist(int hamDist, String station) {
		
		char charInputStation[] = station.toCharArray();
		char charStation[];
		
		ArrayList<String> returnArray = new ArrayList<String>();
		
		//loops through arraylist comparing each station
		for (String s: stations) {
			
			int currentHam = 0;
			charStation = s.toCharArray();
			
			//loops through each letter of the char array containing the 4 letter station
			for (int i = 0; i < 4; i++) {
				
				//compares each letter, if they match add one to counter.
				if (charInputStation[i] == charStation[i])
					currentHam++;
			}
			
			//if counter equals the ham distance we want then add the current station to the return array
			if (hamDist == currentHam)
				returnArray.add(s);
		}
		return returnArray;
	}
	
	
	
	
	//ham dist, inputs station string. returns int array with number of stations with same ham dist.
	
	public int[] calcHammingDist(String station) {
		int[] counter = new int[] {0, 0, 0, 0, 0};
		char charInputStation[] = station.toCharArray();
		char charStation[];
		
		for (String s: stations) {
			
			int currentHam = 0;
			charStation = s.toCharArray();
			
			//loops through each letter of the char array containing the 4 letter station
			for (int i = 0; i < 4; i++) {
				
				//compares each letter, if they match add one to counter.
				if (charInputStation[i] == charStation[i])
					currentHam++;
			}		
			counter[currentHam]++;	
		}
		
		
		
		return counter;
		
	}
	
	
	
	
	
	
	private void readFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Mesonet.txt"));
		while (br.readLine() != null) {
			stations.add(br.readLine());
		}
	}


	public ArrayList<String> getStations() {
		return stations;
	}
	
	
	public void updateStation(String station) {
		//cap all letters then add to array, sort array.
		stations.add(station.toUpperCase());
		Collections.sort(stations);

	}

	
	
}
























package edu.wheaton.simulator.statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Serializer {
	
	

	public static void serializer(String serializable) {
		String filename = "tester.txt"; // TODO: Change if necessary
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					filename));
			writer.write(serializable);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: Move to a separate method
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					filename));
			
			System.out.println(reader.readLine());
		
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package edu.wheaton.simulator.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Serializer {

	public static void serializer(String serializable) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"tester.txt"));
			writer.write(serializable);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

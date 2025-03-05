package com.goblinskeep.App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddSpacesToFile {
    public static void main(String[] args) {
        String inputFilePath = "CMPT276S25_group9/src/main/resources/Maps/map1.txt";
        String outputFilePath = "CMPT276S25_group9/src/main/resources/Maps/map1_spaced.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder spacedLine = new StringBuilder();
                for (char c : line.toCharArray()) {
                    spacedLine.append(c).append(' ');
                }
                // Remove the last space
                writer.write(spacedLine.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
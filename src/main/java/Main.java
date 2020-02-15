import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import java.nio.charset.StandardCharsets;

class Artist {
  String name;
  Artist next;

  public Artist(String artist) {
    name = artist;
  }
}

class TopStreamingArtists {
  private Artist first;

  public TopStreamingArtists(){
    first = null;
  }

  public boolean isEmpty(){
   return (first == null);
  }

  public void displayList() {
    Artist current = first;
    while(current != null) {
      System.out.println(current.name);
      current = current.next;
    }
  }

  public void displayAlphabeticalList() {
    Artist current = first;
    List<String> l = new ArrayList<>();
    while(current != null) {
      l.add(current.name);
      current = current.next;
    }

    Collections.sort(l, String.CASE_INSENSITIVE_ORDER);

    for (String item : l) {
      System.out.println(item);
    }
    
  }

  public void insert(String artist) {
    Artist newNode = new Artist(artist);
    newNode.next = first;
    first = newNode;
  }
}

public class Main {

  public static String[][] Array2DSolution(String file) throws Exception {
    String[][] artistCount = new String[200][2];

    try (FileInputStream fis = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
      CSVReader reader = new CSVReader(isr)) {
      String[] nextLine;
      List<String> artists = new ArrayList<>();

      while ((nextLine = reader.readNext()) != null) {
        artists.add(nextLine[2]);
      }

      int count = 0;
      for (String artist : artists) {
        boolean DNE = true;
        for (int j = 0; j < count; j++){
          if (artistCount[j][0] == artist) {
            DNE = false;
          }
        }

        if (DNE) {
          for (int r = 0; r < 200 ; r++ ) {
            if (artistCount[r][0] == null) {
              artistCount[r][0] = artist;
              artistCount[r][1] = Long.toString(artists.stream().filter(p -> p.equals(artist)).count());
              break;
            }
          }
        } else {
          for (int r = 0; r < 200 ; r++ ) {
            if (artistCount[r][0] == artist) {
              int temp = Integer.parseInt(artistCount[r][1]);
              artistCount[r][1] = Integer.toString(temp + 1);
            }
          }
        }

        
      }
    }

    return artistCount;
  }
  public static void main(String [] args) throws Exception{    
    // System.out.println(Arrays.deepToString(Array2DSolution("data/regional-us-weekly-latest.csv")));

    TopStreamingArtists tsa = new TopStreamingArtists();
    String[][] aList = Array2DSolution("data/regional-us-weekly-latest.csv");
    for (int r = 0; r < 200 ; r++ ) {
      tsa.insert(aList[r][0]);
    }

    tsa.displayList();
    System.out.println();
    tsa.displayAlphabeticalList();

  }
}
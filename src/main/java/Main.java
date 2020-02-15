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
    String[][] artistCount;

    try (FileInputStream fis = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
      CSVReader reader = new CSVReader(isr)) {
      String[] nextLine;
      List<String> artists = new ArrayList<>();

      while ((nextLine = reader.readNext()) != null) {
        artists.add(nextLine[2]);
      }

      artistCount = new String[artists.size()][2];

      int count = 0;
      for (String artist : artists) {
          boolean exists = false;
          for (int j = 0; j < artistCount.length; j++){
            try {
              if (artistCount[j][0].equals(artist)) {
                exists = true;
              }
            } catch (NullPointerException e) {
              ;
            }
          }
          if (!exists) {
            for (int r = 0; r < artistCount.length ; r++ ) {
              if (artistCount[r][0] == null) {
                artistCount[r][0] = artist;
                artistCount[r][1] = Long.toString(artists.stream().filter(p -> p.equals(artist)).count());
                break;
              }
            }
          } else {
            for (int r = 0; r < artistCount.length; r++ ) {
              try {
                if (artistCount[r][0].equals(artist)) {
                  int temp = Integer.parseInt(artistCount[r][1]);
                  artistCount[r][1] = Integer.toString(temp + 1);
                }
              } catch (NullPointerException e){
                ;
              }
            }
          }
        
      }
    }

    return artistCount;
  }
  public static void main(String [] args) throws Exception{    
    String[][] aList = Array2DSolution("data/regional-us-weekly-latest.csv");
    for (int i = 0; i < aList.length; i++){
      if (aList[i][0] != null) {
        System.out.printf("%s appeared %s times\n", aList[i][0], aList[i][1]);
      }
    }

    System.out.println("\n--\n");

    TopStreamingArtists tsa = new TopStreamingArtists();
    for (int r = 0; r < aList.length ; r++ ) {
      if (aList[r][0] != null) {
        tsa.insert(aList[r][0]);
      }
    }

    tsa.displayAlphabeticalList();

  }
}
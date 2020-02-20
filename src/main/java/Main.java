// import necessary java libraries
import java.io.*;
import java.util.*;

//import CSVReader and dependencies
import com.opencsv.CSVReader;
import java.nio.charset.StandardCharsets;

// Artist node for linked list
class Artist {
  String name;
  Artist next;

  // Artist constructor
  public Artist(String artist) {
    name = artist;
  }
}

// Top Streaming Artists object
class TopStreamingArtists {
  private Artist first; // initialize first

  // constructor
  public TopStreamingArtists(){
    first = null;
  }

  // isEmpty method to check if list is empty
  public boolean isEmpty(){
   return (first == null);
  }

  // displays list in default order as read from file
  public void displayList() {
    Artist current = first; // set current to first

    // traverse through list
    while(current != null) { // check if current is not null
      System.out.println(current.name); // print name
      current = current.next; // proceed to next item
    }
  }

  // displays list in aphabetical order
  public void displayAlphabeticalList() {
    Artist current = first; // set current to first

    // array list to store items
    List<String> l = new ArrayList<>();

    // traverse through linked list and add to array list for sort
    while(current != null) {
      l.add(current.name);
      current = current.next;
    }

    // sort alphabetically, ingoring case sensitivity
    Collections.sort(l, String.CASE_INSENSITIVE_ORDER);

    // print list
    for (String item : l) {
      System.out.println(item);
    }
    
  }

  // insert method to add new artist to list
  public void insert(String artist) {
    Artist newNode = new Artist(artist); // create new node
    newNode.next = first; // assist value of first to node's next
    first = newNode; // assign node to first
  }
}

public class Main {

  public static String[][] Array2DSolution(String file) throws Exception {
    String[][] artistCount; // initialize 2D array

    // read in file (create CSVReader)
    try (FileInputStream fis = new FileInputStream(file);
    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(isr)) {
      String[] nextLine; // store line for processing
      List<String> artists = new ArrayList<>(); // arrayList to store artists

      // ignore first two lines
      reader.readNext();
      reader.readNext();

      // extract artists from file and add to list
      while ((nextLine = reader.readNext()) != null) {
        artists.add(nextLine[2]);
      }

      // initialize artistCount
      artistCount = new String[artists.size()][2];

      // count number of times artist appears
      for (String artist : artists) {
          boolean exists = false; // track if artist already exists in list

          // check if artist appears and set exists flag to true
          for (int j = 0; j < artistCount.length; j++){
            try {
              if (artistCount[j][0].equals(artist)) {
                exists = true;
              }
            } catch (NullPointerException e) {
              ;
            }
          }

          // add artist to list if does not exist
          if (!exists) {
            for (int r = 0; r < artistCount.length ; r++ ) {
              if (artistCount[r][0] == null) {
                artistCount[r][0] = artist;
                artistCount[r][1] = Long.toString(artists.stream().filter(p -> p.equals(artist)).count());
                break;
              }
            }
            // if artist does exist, increment count
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

    return artistCount; // return 2D array
  }

  // main method
  public static void main(String [] args) throws Exception{    
    String[][] aList = Array2DSolution("data/regional-us-weekly-latest.csv"); // create 2D array of artists and number of times in list
    
    // print how many times they appear
    for (int i = 0; i < aList.length; i++){
      if (aList[i][0] != null) {
        System.out.printf("%s appeared %s times\n", aList[i][0], aList[i][1]);
      }
    }

    // spacers
    System.out.println("\n--\n");


    TopStreamingArtists tsa = new TopStreamingArtists(); // create new top streaming artists object

    // add each artist in 2D array to linked list
    for (int r = 0; r < aList.length ; r++ ) {
      if (aList[r][0] != null) {
        tsa.insert(aList[r][0]);
      }
    }

    tsa.displayAlphabeticalList(); // print alphabetical list

  }
}
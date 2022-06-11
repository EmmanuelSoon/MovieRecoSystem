package YCEM.MovieRecoSystem.service;

import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;
import org.springframework.web.multipart.MultipartFile;

import YCEM.MovieRecoSystem.model.*;

public class CSVHelper {
    
    public static List<Rating> csvToMovie(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      List<Rating> Ratings = new ArrayList<Rating>();
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      for (CSVRecord csvRecord : csvRecords) {
        Rating Rating = new Rating(

            );
        Ratings.add(Rating);
      }
      return Ratings;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
    }






    
}



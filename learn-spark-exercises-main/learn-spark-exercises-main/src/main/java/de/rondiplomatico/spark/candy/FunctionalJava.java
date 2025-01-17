package de.rondiplomatico.spark.candy;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.spark.sql.catalyst.expressions.aggregate.Collect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rondiplomatico.spark.candy.base.Utils;
import de.rondiplomatico.spark.candy.base.data.Candy;
import de.rondiplomatico.spark.candy.base.data.Color;
import de.rondiplomatico.spark.candy.base.data.Crush;
import de.rondiplomatico.spark.candy.base.data.Deco;

/**
 * Exercises for the first section of the course.
 *
 * Comprises basic functional operations using the java streaming api.
 *
 * @since 2022-06-22
 * @author wirtzd
 *
 */
@SuppressWarnings("java:S100")
public class FunctionalJava {

	private static final Logger log = LoggerFactory.getLogger(FunctionalJava.class);

	/**
	 * Configure your environment to run this class for section 1.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * E1: Generate crushes
		 */
		 List<Crush> data = e1_crush(1000);

		/**
		 * E2: Filtering
		 */
		 e2_countCandies(data);

		/**
		 * E3: Grouping
		 */
		 e3_countByColor(data);

		/**
		 * E4: Lookups
		 */
		 e4_cityLookup(data);
	}

	/**
	 * Creates a specified amount of candy crush events.
	 *
	 * @param n the number of desired crush events
	 * @return the list of crush events
	 */
	public static List<Crush> e1_crush(int n) {
		//List<Crush> orders = new ArrayList<>(n);
	
	

		/*
		 * TODO E1: Generate crushes
		 *
		 * Implement logic that generates a list of n random Crush events! Use the
		 * {@link Crush} and {@link Candy} constructors along with the {@link Utils}
		 * randXY methods.
		 *
		 * Also log how many events have been generated with log4j at the end, using the
		 * "log" logger.
		 */
		
//		for (int i = 0; i <= n; i++) {
//		    Color color1 = Utils.randColor();
//		    Color color2 = Utils.randColor();
//		    Color color3 = Utils.randColor();
//		    
//		    if (color1.equals(color2) && color2.equals(color3)) {
//		       orders.add(new Crush(new Candy(Utils.randColor(),Utils.randDeco()),Utils.randUser(),Utils.randTime()));
//		    }
//		}	
//		log.info(orders.size() + " candies have been crushed!");
//	     log.info("Generated {} crush events.", orders.size());
//		     
//	     return orders;

	

	List<Crush> orders = IntStream.rangeClosed(0, n)
            .mapToObj(i -> {
                Color color1 = Utils.randColor();
                Color color2 = Utils.randColor();
                Color color3 = Utils.randColor();
                if (color1.equals(color2) && color2.equals(color3)) {
                    return new Crush(new Candy(Utils.randColor(), Utils.randDeco()), Utils.randUser(), Utils.randTime());
                } else {
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());

        log.info("{} candies have been crushed!", orders.size());
        log.info("Generated {} crush events.", orders.size());

        return orders;
    }



		//return orders;


	/**
	 * Performs various counts on crushed candies
	 * 
	 * 
	 * Useful links for further reference:
	 * 
	 * Java Streaming API:
	 * 
	 * @see https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
	 * @see https://www.baeldung.com/java-8-streams
	 * @see https://stackify.com/streams-guide-java-8/
	 * 
	 *      Lambdas:
	 * 
	 * @see https://www.w3schools.com/java/java_lambda.asp
	 * @see https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
	 * 
	 * @param data A list of crushes
	 */
	public static void e2_countCandies(List<Crush> data) {
		/**
		 * Answers the question "how many red striped candies have been crushed?"
		 */
		long res = data.stream() // stream() converts a Java collection to a java stream
				.map(Crush::getCandy) // transforms the stream elements, here selecting the candy object of the crush
				.filter(c -> c.getColor().equals(Color.RED)) // Filters a stream by a specified predicate
				.filter(c -> c.getDeco().equals(Deco.HSTRIPES) || c.getDeco().equals(Deco.VSTRIPES)).count(); // Terminates
																												// the
																												// stream,
																												// processing
																												// all
																												// it's
																												// elements
																												// by
																												// counting
																												// them
		// Log the counted results.
		log.info("The crush data contains {} red striped candies!", res);

		/*
		 * TODO E2: Filtering
		 *
		 * Count how many wrapped candies have been crushed between 12-13 o'clock and
		 * log the results like above.
		 */
//		 int count = 0;
//	        LocalTime startTime = LocalTime.of(12, 0);
//	        LocalTime endTime = LocalTime.of(13, 0);
//	        
//	        for (Crush c : data) {
//	            LocalTime crushTime = c.getTime(); 
//	            if (c.getCandy().getDeco().equals(Deco.WRAPPED) &&
//	                crushTime.isAfter(startTime) && crushTime.isBefore(endTime)) {
//	                count++;
//	            }
//	        }
//		
//		log.info("wrapped candies have been crushed {} time between 12-13 o'clock", count);
//	
		  LocalTime startTime = LocalTime.of(12, 0);
	        LocalTime endTime = LocalTime.of(13, 0);
	       Long count= data.stream().filter(c->c.getCandy().getDeco().equals(Deco.WRAPPED)).filter(c->c.getTime().isAfter(startTime)&&c.getTime().isBefore(endTime)).count();
	       log.info("wrapped candies have been crushed {} time between 12-13 o'clock", count);
	}
	


	/**
	 * Performs various counts on the provided crush data
	 *
	 * @param data
	 */
	public static void e3_countByColor(List<Crush> data) {
		/**
		 * Classical, imperative implementation by using a map to store the counts for
		 * each color.
		 */
//		Map<Color, Integer> res = new EnumMap<>(Color.class);
//		for (Crush c : data) {
//			Color col = c.getCandy().getColor();
//			Integer count = res.get(col);
//			if (count == null) {
//				res.put(col, 1);
//			} else {
//				res.put(col, count + 1);
//			}
//		}
//		res.forEach((c, i) -> log.info("The crush data contains {} {} candies", i, c));

		/*
		 * TODO E3: Grouping
		 *
		 * Implement the same logic as above using the java streaming api. Log your
		 * results and compare!
		 *
		 * Hints: The function "collect" with the "groupingBy" and downstream "counting"
		 * Collectors come in handy.
		 */
		Map<Color,Long> result = data.stream().collect(Collectors.groupingBy(c->c.getCandy().getColor(),Collectors.counting()));
		result.forEach((i,c)->log.info("The crush data contains {} {} candies", i, c));
		/*
		 * TODO E3: Grouping (Bonus question)
		 *
		 * Answer the question:
		 * "How many blue candies have been crushed per decoration type?" Log your
		 * results.
		 */
		Long blueCandiesCount  = data.stream().filter(c->c.getCandy().getColor().equals(Color.BLUE)).collect(Collectors.counting());
		log.info("blue candies have been crushed per decoration type {} ",blueCandiesCount );

	}

	/**
	 * Computes some statistics for candy crushes considering the city the persons
	 * are living in
	 *
	 * @param data
	 */
	public static void e4_cityLookup(List<Crush> data) {

		/**
		 * Get the map of cities from Utils
		 */
		Map<String, String> cities = Utils.getHomeCities();

		/**
		 * Imperative implementation: How may crushes per city?
		 */
		Map<String, Integer> counts = new HashMap<>();
		for (Crush c : data) {
			// Look up the city using the user as key
			String city = cities.get(c.getUser());
			// The "getOrDefault" allows to formulate the counting code more compact -
			// compare e3_countByColor :-)
			counts.put(city, counts.getOrDefault(city, 0) + 1);
		}
		counts.forEach((c, i) -> log.info("There are {} crushes in {}", i, c));

		/*
		 * TODO E4: Lookups
		 *
		 * Implement "How may crushes per city?" using streams, map lookup and
		 * collectors. Log your results.
		 */
		Map<Object, Long> countscity=data.stream().map(c->cities.get(c.getUser())).collect(Collectors.groupingBy(city -> city, Collectors.counting()));
		countscity.forEach((c, i) -> log.info("There are {} crushes in {}", i, c));

		/**
		 * Teach-In: Demonstrating the requirement of "effectively final" fields
		 */
		// cities = null;

		/*
		 * TODO E4: Lookups (Bonus question)
		 *
		 * Implement
		 * "How many candies in Ismaning between 14-15 o'clock, counted by color?" with
		 * java streaming. Use what you have learned before to succeed. Log your
		 * results.
		 */
		LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(15, 0);
        Map<Object, Long> colour= data.stream().filter(c->cities.get(c.getUser()).equals("Ismaning"))
        .filter(c->c.getTime().isAfter(startTime)&&c.getTime().isBefore(endTime))
        .collect(Collectors.groupingBy(c->c.getCandy().getColor(),Collectors.counting()));
        colour.forEach((c,v)->log.info("There are {} {} color crushes Between 14-15 ", v, c));
	}

}

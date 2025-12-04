import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Re3 {
   public static void main( String args[] ) {
      // String to be scanned to find the pattern.
	String line = "2Cab 4 Level smarter than 3 dogs";
	String myregex = "([^D-Z]*)([0-9][0-9]*)";

	Pattern r = Pattern.compile(myregex);

	// Now create matcher object.
	Matcher m = r.matcher(line);
      
	while (m.find( )) {
		System.out.println("Found value (whole): " + m.group(0) );
		System.out.println("Found value (first part): " + m.group(1) );
		System.out.println("Found value (second part): " + m.group(2) + "\n");
	} 
   }
}

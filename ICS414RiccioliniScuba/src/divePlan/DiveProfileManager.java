package divePlan;

import java.util.Calendar;
import java.util.LinkedList;


/**
 * ICS 414 Team Ricciolini Scuba Project
 * DiveProfileManager class manages dive profile data and file systems
 * @author Gerald Abut, Kavika
 */

public class DiveProfileManager {
	public DiveProfileManager() {
		// NOT YET IMPLEMENTED
	}
	
	public void saveDive (Calendar date, LinkedList<Dive> dive) {
		// CALENDAR IS ACTUAL AN ABSTRACT CLASS AND YOU WILL HAVE TO CREATE THE METHODS
		// BUT YOU CAN CHANGE IT TO WHATEVER SUITES YOU (e.g. String, etc.)	
		BufferedWriter writer = null;
        try {
            String diveLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File diveFile = new File(diveLog);

            System.out.println(diveFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(diveFile));
            writer.write("Hello world!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
		
	}
}

package matmik;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class BodgeGlobalLogger {
	
	static public void setup() {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		try {
			logger.addHandler(new FileHandler("log.txt"));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

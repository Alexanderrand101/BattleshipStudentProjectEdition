package matmik;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class BodgeGlobalLogger {
	
	static public void setup() {
		Logger logger1 = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
                Logger logger2 = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME + "1");
		try {
			logger1.addHandler(new FileHandler("log1.txt"));
                        logger2.addHandler(new FileHandler("log2.txt"));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

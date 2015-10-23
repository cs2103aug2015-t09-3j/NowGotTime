package helper;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	
	private static final String DEFAULT_PATH = System.getProperty("user.dir").toString() + "/LogFiles/";
	private static final String DEFAULT_LOG_FILE = DEFAULT_PATH + "MyLogging.log";
	private static Logger logger = Logger.getLogger("My Log");
	private static FileHandler fh;
	
	public MyLogger(){
		File file = new File(DEFAULT_PATH);
		if(file.exists()){
			return;
		}else{
			file.mkdir();
		}
		
		try{
			
			fh = new FileHandler(DEFAULT_LOG_FILE);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	        
		}catch(IOException e){
			logger.log(Level.SEVERE, "Can't log");
		}
	}
		
	public void log(Level level, String msg){
	    logger.log(level, msg); 
	}
	
	public void logp(Level level, String sourceClass, String sourceMethod, String msg){
		logger.logp(level, sourceClass, sourceMethod, msg);
	}
	
	public void entering(String sourceClass, String sourceMethod){
		logger.entering(sourceClass, sourceMethod);
	}
	
	public void exiting(String sourceClass, String sourceMethod){
		logger.exiting(sourceClass, sourceMethod);
	}
	
}

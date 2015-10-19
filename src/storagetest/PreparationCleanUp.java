package storagetest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreparationCleanUp {
	
	public static boolean cleanUp(String baseDirectory){
		File dir = new File(baseDirectory);
		if(dir.isDirectory() && dir.list().length > 0){
			for(File file: dir.listFiles()) {
				if(!file.isDirectory()){
					file.delete(); 
				}else{
					cleanUp(file.getPath());
				}
			}
		}
		
		Path path = Paths.get(baseDirectory);
		
		try {
			Files.delete(path);
			return true;
		} catch(NoSuchFileException e) {
			System.out.println("No such file exist to delete");
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean setUpDirectory(String baseDirectory){
		File dir = new File(baseDirectory);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}		
		return false;
	}

	public static boolean makeNewDirectory(String directoryName){
		File file = new File(directoryName);
		if (!file.exists()) {
			return file.mkdir();
		}
		return false;
	}
}

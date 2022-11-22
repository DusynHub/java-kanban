package kanban.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {

    public static TaskManager getDefault(){

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){

        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackedTaskManager(){

        Path pathOfStorage = Paths.get("src/kanban/taskManagerStorageInFile");
        try{
            if(!Files.exists(pathOfStorage)){
                Files.createDirectory(pathOfStorage);
            }
        } catch(IOException e){
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }

        Path pathOfFile = Paths.get(pathOfStorage.toAbsolutePath().toString()
                + "/TaskStorage.csv");
        try{

            if(!Files.exists(pathOfFile)){
                Files.createFile(pathOfFile);
            }
        }catch(IOException e){
            System.out.println("Ошибка при создании файла хранения FileBackedTaskManager: ");
            e.printStackTrace();
        }

        return new FileBackedTaskManager(pathOfFile);
    }
}

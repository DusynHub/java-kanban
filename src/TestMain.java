import kanban.module.*;
import kanban.service.FileBackedTaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestMain {

    public static <e> void main(String[] args) {

        /*
        Path pathOfStorage = Paths.get("src/kanban/TaskManagerStorageInFile");
        try{
            if(!Files.exists(pathOfStorage)){
                Files.createDirectory(pathOfStorage);
            }
        } catch(IOException e){
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }
        */
        Path pathOfStorage = Paths.get("src/kanban/TaskManagerStorageInFile");
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

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);
//        try{
//
//            if(!Files.exists(pathOfFile)){
//                Files.createFile(pathOfFile);
//            }
//        }catch(IOException e){
//            System.out.println("Ошибка при создании файла: ");
//            e.printStackTrace();
//        }

        RegularTask theBigLebowskiTask;
        RegularTask deathTask;
        RegularTask resentmentTask;
        RegularTask updatedResentmentTask;
        RegularTask importantTask;
        {
            theBigLebowskiTask = new RegularTask(
                    0, "Задача Лебовски",
                    "Где деньги, Лебовски?",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            deathTask = new RegularTask(
                    1, "Что такое Смерть?",
                    "Смерть — это то, что бывает с другими",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
            resentmentTask = new RegularTask(
                    2, "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            updatedResentmentTask = new RegularTask(
                    3, "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    StatusName.DONE,
                    TaskType.REGULAR_TASK);
            importantTask = new RegularTask(
                    4, "Найти ответ на главный вопрос жизни, вселенной и всего такого",
                    "Может быть это 6 х 9 ?",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
        }

        fileBackedTaskManager.createRegularTask(theBigLebowskiTask);
        fileBackedTaskManager.createRegularTask(deathTask);
        fileBackedTaskManager.createRegularTask(resentmentTask);
        fileBackedTaskManager.createRegularTask(updatedResentmentTask);
        fileBackedTaskManager.createRegularTask(importantTask);

        EpicTask cookRice;
        EpicTask doPracticumHomework;
        EpicTask updateDoPracticumHomework;
        EpicTask doTraining;
        {
            cookRice = new EpicTask(
                    0, "Приготовить рис",
                    "Нужен гарнир из коричневого риса",
                    TaskType.EPIC_TASK);
            doPracticumHomework = new EpicTask(
                    0, "Выполнить домашнее задание практикума",
                    "Нужно успеть до 09.10.2022",
                    TaskType.EPIC_TASK);
            updateDoPracticumHomework = new EpicTask(
                    0, "ОБНОВЛЕНИЕ ЭПИК ЗАДАЧИ",
                    "ОБНОВЛЕНИЕ ЭПИК ЗАДАЧИ",
                    TaskType.EPIC_TASK);
            doTraining = new EpicTask(
                    0, "Выполнить треннировку",
                    "Выполнить 3 упражнения по 10 подходов",
                    TaskType.EPIC_TASK);
        }

        fileBackedTaskManager.createEpicTask(cookRice);
        fileBackedTaskManager.createEpicTask(doPracticumHomework);
        fileBackedTaskManager.createEpicTask(updateDoPracticumHomework);
        fileBackedTaskManager.createEpicTask(doTraining);

        SubTask subTaskCookRice1;
        SubTask subTaskCookRice2;
        {
            subTaskCookRice1 = new SubTask(
                    0, "Промыть рис",
                    "Желательно 400 гр",
                    StatusName.DONE, TaskType.SUBTASK, cookRice.getId()
            );
            subTaskCookRice2 = new SubTask(
                    0, "Варить 10 минут",
                    "Не уходить с кухни",
                    StatusName.NEW, TaskType.SUBTASK, cookRice.getId()
            );
        }

        fileBackedTaskManager.createSubTask(subTaskCookRice1);
        fileBackedTaskManager.createSubTask(subTaskCookRice2);

        Scanner sc  = new Scanner(System.in);
        System.out.println("Нажмите Enter, чтобы продолжить");
        //sc.next();
        //fileBackedTaskManager.clearRegularTaskStorage();
        fileBackedTaskManager.getRegularTask(0);
        fileBackedTaskManager.getRegularTask(1);
        fileBackedTaskManager.getRegularTask(2);
        fileBackedTaskManager.getRegularTask(3);
        fileBackedTaskManager.getRegularTask(0);
        fileBackedTaskManager.getRegularTask(1);
        fileBackedTaskManager.getRegularTask(2);
        fileBackedTaskManager.getRegularTask(3);
        fileBackedTaskManager.getRegularTask(0);
        fileBackedTaskManager.getRegularTask(77);
        fileBackedTaskManager.getRegularTask(0);
    }
}

import kanban.module.*;
import kanban.module.storage.TaskStorage;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class BackedTest2 {


    public static void main(String[] args) {

        Path pathOfStorage = Paths.get("src/kanban/TaskManagerStorageInFile");
        try{
            if(!Files.exists(pathOfStorage)){
                Files.createDirectory(pathOfStorage);
            }
        } catch(IOException e){
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }

        Path pathOfFile = Paths.get(pathOfStorage.toAbsolutePath()
                + "/TaskStorage.csv");
        try{

            if(!Files.exists(pathOfFile)){
                Files.createFile(pathOfFile);
            }
        }catch(IOException e){
            System.out.println("Ошибка при создании файла хранения FileBackedTaskManager: ");
            e.printStackTrace();
        }

        RegularTask theBigLebowskiTask;
        RegularTask deathTask;
        RegularTask resentmentTask;
        RegularTask updatedResentmentTask;
        RegularTask importantTask;
        {
            theBigLebowskiTask = new RegularTask(
                    0,
                    "Задача Лебовски",
                    "Где деньги, Лебовски?",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            deathTask = new RegularTask(
                    0,
                    "Что такое Смерть?",
                    "Смерть — это то, что бывает с другими",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
            resentmentTask = new RegularTask(
                    0, "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            updatedResentmentTask = new RegularTask(
                    0, "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    StatusName.DONE,
                    TaskType.REGULAR_TASK);
            importantTask = new RegularTask(
                    2, "Найти ответ на главный вопрос жизни, вселенной и всего такого",
                    "Может быть это 6 х 9 ?",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
        }
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
        SubTask subTaskCookRice1;
        SubTask subTaskCookRice2;
        SubTask updateSubTaskCookRice2;

        TaskManager taskManager = Managers.getFileBackedTaskManager();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int command;

        while(true){

            String userInput = "0";

            while(true){
                System.out.println("\nНажмите любую букву и \"enter\" для печати меню");
                scanner.nextLine();
                printMenu();
                userInput = scanner.nextLine();
                char[] inputToValidate = userInput.toCharArray();
                boolean isInputDigit = true;

                if(inputToValidate.length == 0){
                    isInputDigit = false;
                    System.out.println("Некорректный ввод. Введите число");
                }

                for(int i = 0; i < inputToValidate.length; i++){

                    if(!Character.isDigit(inputToValidate[i])){
                        isInputDigit = false;
                        System.out.println("Некорректный ввод. Введите число");
                        break;
                    }
                }
                if(isInputDigit == true){
                    break;
                }
            }
            command = Integer.parseInt(userInput);

            if(command == 1){
                System.out.println(taskManager.createRegularTask(theBigLebowskiTask));
                System.out.println(taskManager.createRegularTask(deathTask));
                System.out.println(taskManager.createRegularTask(resentmentTask));
                System.out.println(taskManager.createRegularTask(importantTask));
            } else if(command == 2){
                System.out.println(taskManager.createEpicTask(cookRice));
                System.out.println(taskManager.createEpicTask(doPracticumHomework));
                System.out.println(taskManager.createEpicTask(doTraining));
            } else if(command == 3){
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
                System.out.println(taskManager.createSubTask(subTaskCookRice1));
                System.out.println(taskManager.createSubTask(subTaskCookRice2));
            }
            else if(command == 4){
                taskManager.getRegularTaskStorage();
                taskManager.printRegularTaskStorage();
            } else if(command == 5){
                taskManager.getEpicTaskStorage();
                taskManager.printEpicTaskStorage();
            }else if(command == 6){
                taskManager.getSubTaskStorage();
                taskManager.printSubTaskStorage();
            } else if(command == 7){
                System.out.println(taskManager.clearRegularTaskStorage());
            } else if(command == 8){
                System.out.println(taskManager.clearEpicTaskStorage());
            } else if(command == 9){
                System.out.println(taskManager.clearSubTaskStorage());
            } else if(command == 10){
                for(Integer key : taskManager.getRegularTaskStorage().keySet()){
                    System.out.println(taskManager.getRegularTask(key));
                }

                System.out.println(taskManager.getRegularTask(deathTask.getId()));
                System.out.println(taskManager.getRegularTask(resentmentTask.getId()));
            } else if(command == 11){

                for (int i = 0; i < 3; i++) {
                    System.out.println(taskManager.getEpicTask(random.nextInt(100)));
                }
                for(Integer key : taskManager.getEpicTaskStorage().keySet()){
                    System.out.println(taskManager.getEpicTask(key));
                }

            } else if(command == 12){

                for(Integer key : taskManager.getSubTaskStorage().keySet()){
                    System.out.println(taskManager.getSubTask(key));
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println(taskManager.getSubTask(random.nextInt(15)));
                }
            } else if(command == 13){
                System.out.println(taskManager.updateRegularTask(updatedResentmentTask));
            } else if(command == 14){
                System.out.println(taskManager.updateEpicTask(updateDoPracticumHomework));
            } else if(command == 15){
                updateSubTaskCookRice2 = new SubTask(
                        cookRice.getSubTaskStorageForEpic().getStorage().keySet().iterator().next(), "Кто проживает на дне океана?",
                        "Заклятые враги Дона Корлеоне",
                        StatusName.DONE, TaskType.SUBTASK, cookRice.getId()
                );
                System.out.println(taskManager.updateSubTask(updateSubTaskCookRice2));
            } else if(command == 16){
                for (int i = 0; i < 5; i++) {
                    System.out.println(taskManager.removeRegularTask(random.nextInt(15)));
                }
            } else if(command == 17){
                for (int i = 0; i < 5; i++) {
                    System.out.println(taskManager.removeEpicTask(random.nextInt(15)));
                }
            } else if(command == 18){
                for (int i = 0; i < 5; i++) {
                    System.out.println(taskManager.removeSubTask(4));
                }
            } else if(command == 19) {
                TaskStorage task = taskManager.getSubTaskStorageFromEpic(0);
                taskManager.printStorage(task);
                TaskStorage task2 = taskManager.getSubTaskStorageFromEpic(6);
                taskManager.printStorage(task2);
            } else if(command == 20) {
                taskManager.getHistoryOfTasks();
            } else if(command == 0){
                System.out.println("Программа завершена");
                break;
            } else {
                System.out.println("Такой команды нет. Повторите ввод");
            }
        }
    }
    public static void printMenu(){
        System.out.println("Выберите требуемую функцию:");
        System.out.println("1 - Создать обычную задачу");
        System.out.println("2 - Создать эпик задачу");
        System.out.println("3 - Создать подзадачу в эпике");
        System.out.println();
        System.out.println("4 - Получить и распечатать весь список обычных задач");
        System.out.println("5 - Получить и распечатать весь список эпик задач");
        System.out.println("6 - Получить и распечатать весь список подзадач");
        System.out.println();
        System.out.println("7 - Удалить весь список обычных задач");
        System.out.println("8 - Удалить весь список эпик задач");
        System.out.println("9 - Удалить весь список подзадач");
        System.out.println();
        System.out.println("10 - Получить и распечатать обычную задачу");
        System.out.println("11 - Получить и распечатать эпик задачу");
        System.out.println("12 - Получить и распечатать подзадачу");
        System.out.println();
        System.out.println("13 - Обновить обычную задачу");
        System.out.println("14 - Обновить эпик задачу");
        System.out.println("15 - Обновить подзадачу");
        System.out.println();
        System.out.println("16 - Удалить обычную задачу по id");
        System.out.println("17 - Удалить эпик задачу по id");
        System.out.println("18 - Удалить подзадачу по id");
        System.out.println();
        System.out.println("19 - Получить список подзадач эпика по id");

        System.out.println("20 - Получить историю вызова задач");
        System.out.println("0 - Выход из программы");
    }


}

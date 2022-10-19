import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.StatusNames;
import kanban.module.SubTask;
import kanban.service.Managers;
import kanban.service.TaskManager;
import kanban.module.storage.TaskStorage;

import java.util.Random;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {

        RegularTask theBigLebowskiTask;
        RegularTask deathTask;
        RegularTask resentmentTask;
        RegularTask updatedResentmentTask;
        RegularTask importantTask;
        {
            theBigLebowskiTask = new RegularTask(
                    "Задача Лебовски",
                    "Где деньги, Лебовски?",
                    0,
                    StatusNames.NEW);
            deathTask = new RegularTask(
                    "Что такое Смерть?",
                    "Смерть — это то, что бывает с другими",
                    0,
                    StatusNames.IN_PROGRESS);
            resentmentTask = new RegularTask(
                    "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    0,
                    StatusNames.NEW);
            updatedResentmentTask = new RegularTask(
                    "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    0,
                    StatusNames.DONE);
            importantTask = new RegularTask(
                    "Найти ответ на главный вопрос жизни, вселенной и всего такого",
                    "Может быть это 6 х 9 ?",
                    2,
                    StatusNames.IN_PROGRESS);
        }
        EpicTask cookRice;
        EpicTask doPracticumHomework;
        EpicTask updateDoPracticumHomework;
        EpicTask doTraining;
        {
            cookRice = new EpicTask(
                    "Приготовить рис",
                    "Нужен гарнир из коричневого риса",
                    0);
            doPracticumHomework = new EpicTask(
                    "Выполнить домашнее задание практикума",
                    "Нужно успеть до 09.10.2022",
                    0);
            updateDoPracticumHomework = new EpicTask(
                    "ОБНОВЛЕНИЕ ЭПИК ЗАДАЧИ",
                    "ОБНОВЛЕНИЕ ЭПИК ЗАДАЧИ",
                    0);
            doTraining = new EpicTask(
                    "Выполнить треннировку",
                    "Выполнить 3 упражнения по 10 подходов",
                    0);
        }
        SubTask subTaskCookRice1;
        SubTask subTaskCookRice2;
        SubTask updateSubTaskCookRice2;

        TaskManager taskManager = Managers.getDefault();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int command;

        while(true){
            printMenu();
            command = Integer.parseInt(scanner.nextLine());
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
                            "Промыть рис",
                            "Желательно 400 гр",
                            cookRice.getId(),
                            0,
                            StatusNames.DONE);
                    subTaskCookRice2 = new SubTask(
                            "Варить 10 минут",
                            "Не уходить с кухни",
                            cookRice.getId(),
                            0,
                            StatusNames.NEW);
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
                System.out.println(taskManager.getRegularTask(5));
                System.out.println(taskManager.getRegularTask(6));
            } else if(command == 11){
                for (int i = 0; i < 15; i++) {
                    System.out.println(taskManager.getEpicTask(random.nextInt(15)));
                }
            } else if(command == 12){
                for (int i = 0; i < 15; i++) {
                    System.out.println(taskManager.getSubTask(random.nextInt(15)));
                }
            } else if(command == 13){
                System.out.println(taskManager.updateRegularTask(updatedResentmentTask));
            } else if(command == 14){
                System.out.println(taskManager.updateEpicTask(updateDoPracticumHomework));
            } else if(command == 15){
                    updateSubTaskCookRice2 = new SubTask(
                            "Кто проживает на дне океана?",
                            "Заклятые враги Дона Корлеоне",
                            cookRice.getId(),
                            cookRice.getSubTaskStorageForEpic().getStorage().keySet().iterator().next(),
                            StatusNames.DONE);
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


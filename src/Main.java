import kanban.module.RegularTask;
import kanban.service.TaskManager;
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
                    1);
            deathTask = new RegularTask(
                    "Что такое Смерть?",
                    "Смерть — это то, что бывает с другими",
                    0,
                    2);
            resentmentTask = new RegularTask(
                    "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    0,
                    1);
            updatedResentmentTask = new RegularTask(
                    "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    0,
                    3);
            importantTask = new RegularTask(
                    "Найти ответ на главный вопрос жизни, вселенной и всего такого",
                    "Может быть это 6 х 9 ?",
                    2,
                    2);
        }
        TaskManager taskManager = new TaskManager();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int command;

        while(true){
            printMenu();
            command = Integer.parseInt(scanner.nextLine());
            if(command == 1){
//                System.out.println("Введите название обычной задачи");
//                String name = scanner.nextLine();
//                System.out.println("Введите описание обычной задачи");
//                String description = scanner.nextLine();
//                int statusId;
//                while(true) {
//                    printStatusOption();
//                    statusId = Integer.parseInt(scanner.nextLine());
//                    if(statusId < 1 || statusId > 3){
//                        System.out.println("Неверный выбор статуса.Введите числа 1, 2 или 3.");
//                    } else {
//                        break;
//                    }
//                }
                System.out.println(taskManager.createRegularTask(theBigLebowskiTask));
                System.out.println(taskManager.createRegularTask(deathTask));
                System.out.println(taskManager.createRegularTask(resentmentTask));
                System.out.println(taskManager.createRegularTask(importantTask));
            } else if(command == 4){
                taskManager.getRegularTaskStorage();
                taskManager.printRegularTaskStorage();

            } else if(command == 5){

            }
            else if(command == 8){
                System.out.println(taskManager.clearRegularTaskStorage());
            } else if(command == 11){
                for (int i = 0; i < 5; i++) {
                    System.out.println(taskManager.getRegularTask(random.nextInt(15)));
                }
            } else if(command == 14){
                System.out.println(taskManager.updateRegularTask(updatedResentmentTask));
            } else if(command == 17){
                for (int i = 0; i < 5; i++) {
                    System.out.println(taskManager.removeRegularTask(random.nextInt(15)));
                }
            } else if(command == 0){
                System.out.println("Программа завершена");
                break;
            }
        }
    }

    public static void printMenu(){
        System.out.println("Выберите требуемую функцию:");
        System.out.println("1 - Создать обычную задачу");
        System.out.println("2 - Создать эпик задачу");
        System.out.println("3 - Создать подзадачу в эпике");
        System.out.println("4 - Получить и распечатать весь список обычных задач");
        System.out.println("6 - Получить и распечатать весь список эпик задач");
        System.out.println("7 - Получить и распечатать весь список подзадач");
        System.out.println("8 - Удалить весь список обычных задач");
        System.out.println("9 - Удалить весь список эпик задач");
        System.out.println("10 - Удалить весь список подзадач");
        System.out.println("11 - Получить и распечатать обычную задачу");
        System.out.println("12 - Получить и распечатать эпик задачу");
        System.out.println("13 - Получить и распечатать подзадачу");
        System.out.println("14 - Обновить обычную задачу");
        System.out.println("15 - Обновить эпик задачу");
        System.out.println("16 - Обновить подзадачу");
        System.out.println("17 - Удалить обычную задачу по id");
        System.out.println("18 - Удалить эпик задачу по id");
        System.out.println("19 - Удалить подзадачу по id");
        System.out.println("0 - Выход из программы");
    }
    public static void printStatusOption(){
        System.out.println("Выберите статус задачи:");
        System.out.println("1 - To do");
        System.out.println("2 - In Progress");
        System.out.println("3 - Done");
    }
}


import kanban.service.TaskManager;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int command;

        while(true){
            printMenu();
            command = Integer.parseInt(scanner.nextLine());
            if(command == 1){
                System.out.println("Введите название обычной задачи");
                String name = scanner.nextLine();
                System.out.println("Введите описание обычной задачи");
                String description = scanner.nextLine();
                int statusId;
                while(true) {
                    printStatusOption();
                    statusId = Integer.parseInt(scanner.nextLine());
                    if(statusId < 1 || statusId > 3){
                        System.out.println("Неверный выбор статуса.Введите числа 1, 2 или 3.");
                    } else {
                        break;
                    }
                }
                System.out.println(taskManager.createRegularTask(name, description, statusId));
            } else if(command == 4){
                taskManager.getRegularTaskStorage();
                taskManager.printRegularTaskStorage();
            } else if(command == 7){
                System.out.println(taskManager.clearRegularTaskStorage());
            }else if(command == 0){
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
        System.out.println("5 - Получить весь список эпик задач");
        System.out.println("6 - Получить весь список подзадач");
        System.out.println("7 - Удалить весь список обычных задач");
        System.out.println("0 - Выход из программы");
    }

    public static void printStatusOption(){
        System.out.println("Выберите статус задачи:");
        System.out.println("1 - To do");
        System.out.println("2 - In Progress");
        System.out.println("3 - Done");
    }
}


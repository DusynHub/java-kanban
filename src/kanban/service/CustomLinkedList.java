package kanban.service;

import kanban.module.Task;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CustomLinkedList {

    private Node first;
    private Node last;
    private int size = 0;

    private final Map<Integer, Node> nodeStorage = new HashMap<>();

    public void addLast(Task taskToAdd) {

        Node oldLast = last;
        Node newNode = new Node(last, taskToAdd, null);
        last = newNode;

        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }

        int idToTaskIfDoNotExist = -1;
        int taskIdToAdd;

        if (taskToAdd == null) {
            taskIdToAdd = idToTaskIfDoNotExist;
        } else {
            taskIdToAdd = taskToAdd.getId();
        }

        if (nodeStorage.containsKey(taskIdToAdd)) {
            remove(nodeStorage.getOrDefault(taskIdToAdd, null));
            nodeStorage.put(taskIdToAdd, newNode);
        } else {
            nodeStorage.put(taskIdToAdd, newNode);
        }

        size++;
    }

    public void remove(Node e) {
        if (e == null) {
            return;
        }
        Node prevNode = e.prev;
        Node nextNode = e.next;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            e.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            e.next = null;
        }
        size--;
    }

    public ArrayList<Task> getHistoryInList() {

        ArrayList<Task> historyInList = new ArrayList<>(size);
        if (size == 0) {
            System.out.println("Не было ни одного вызова задачи");
            return historyInList;
        }

        Node curNode = first;
        for (int i = 0; i < size; i++) {

            historyInList.add(curNode.item);
            curNode = curNode.next;
        }

        return historyInList;
    }

    public Node getNode(Task task) {
        return nodeStorage.get(task.getId());
    }

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}

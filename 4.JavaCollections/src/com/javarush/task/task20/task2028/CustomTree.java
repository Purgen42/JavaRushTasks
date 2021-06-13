package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;

    private Entry<String> searchResult;

    private int size;

    public CustomTree() {
        root = new Entry<String>("root");
    }

    public String getParent(String s) {
        Entry<String> node = findNode(s);
        if (node == null) return null;
        else return node.parent.elementName;
    }

    @Override
    public int size() {
        size = -1;
        treeSearch((tree, entry) -> tree.size++);

        return size;
    }

    @Override
    public boolean add(String s) {
        findNext().addChild(s);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) throw new UnsupportedOperationException();
        Entry<String> entry = findNode((String) o);
        if (entry == null) return false;
        if (entry.parent.leftChild == entry) entry.parent.leftChild = null;
        if (entry.parent.rightChild == entry) entry.parent.rightChild = null;
        return true;
    }

    private Entry<String> findNode(final String s) {
        searchResult = null;
        TreeCommand findCommand = new TreeCommand() {
            @Override
            public void execute(CustomTree tree, Entry<String> entry) {
                if (entry.elementName.equals(s)) tree.searchResult = entry;
            }
        };

        treeSearch(findCommand);

        return searchResult;
    }

    private Entry<String> findNext() {
        searchResult = null;
        TreeCommand findCommand = new TreeCommand() {
            @Override
            public void execute(CustomTree tree, Entry<String> entry) {
                if ((tree.searchResult == null) && (entry.isAvailableToAddChildren())) tree.searchResult = entry;
            }
        };

        TreeCommand restoreFertilityCommand = new TreeCommand() {
            @Override
            public void execute(CustomTree tree, Entry<String> entry) {
                if (entry.leftChild == null) entry.availableToAddLeftChildren = true;
                if (entry.rightChild == null) entry.availableToAddRightChildren = true;
            }
        };

        treeSearch(findCommand);

        if (searchResult == null) {
            treeSearch(restoreFertilityCommand);
            treeSearch(findCommand);
        }

        return searchResult;
    }


    private void treeSearch(TreeCommand command) {
        Queue<Entry<String>> queue = new ArrayDeque<>();
        queue.offer(root);
        Entry<String> entry;
        while ((entry = queue.poll()) != null) {
//            if (entry != root) System.out.println(entry.elementName + " " + entry.parent.elementName);
            command.execute(this, entry);
            if (entry.leftChild != null) queue.offer(entry.leftChild);
            if (entry.rightChild != null) queue.offer(entry.rightChild);
        }
    }

    interface TreeCommand {
        public void execute(CustomTree tree, Entry<String> entry);
    }


//  Unsupported methods
    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }


//  Nested class
    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        private Entry<T> addChild(String elementName) {
            if (!isAvailableToAddChildren()) return null;
            Entry<T> result = new Entry<>(elementName);
            result.parent = this;
            if (availableToAddLeftChildren) {
                this.leftChild = result;
                availableToAddLeftChildren = false;
            }
            else {
                this.rightChild = result;
                availableToAddRightChildren = false;
            }
            return result;
        }
    }
}

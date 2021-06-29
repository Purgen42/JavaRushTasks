package com.javarush.task.task37.task3701;

import java.lang.reflect.Field;
import java.util.*;

/* 
Круговой итератор
*/

public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements ListIterator<T> {
        int cursor = 0;
        private ListIterator<T> iter = Solution.super.listIterator();

        @Override
        public boolean hasNext() {
            return size() > 0;
        }

        @Override
        public T next() {
            if (!iter.hasNext()) iter = Solution.super.listIterator(0);
            return iter.next();
         }

        @Override
        public boolean hasPrevious() {
            return size() > 0;
        }

        @Override
        public T previous() {
            if (!iter.hasPrevious()) iter = Solution.super.listIterator(size() - 1);
            return iter.previous();
        }

        @Override
        public int nextIndex() {
            if (!iter.hasNext()) return 0;
            return iter.nextIndex();
        }

        @Override
        public int previousIndex() {
            if (!iter.hasPrevious()) return size() - 1;
            return iter.previousIndex();
        }

        @Override
        public void remove() {
            iter.remove();
        }

        @Override
        public void set(T t) {
            iter.set(t);
        }

        @Override
        public void add(T t) {
            iter.add(t);
        }
    }
}

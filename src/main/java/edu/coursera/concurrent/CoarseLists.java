package edu.coursera.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class CoarseLists {

    public static final class CoarseList extends ListSet {

        private final ReentrantLock lock = new ReentrantLock();

        public CoarseList() {
            super();
        }

        @Override
        boolean add(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        boolean remove(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        boolean contains(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                lock.unlock();
            }
        }
    }

    public static final class RWCoarseList extends ListSet {

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public RWCoarseList() {
            super();
        }

        @Override
        boolean add(final Integer object) {
            try {
                readWriteLock.writeLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;
                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        @Override
        boolean remove(final Integer object) {
            try {
                readWriteLock.writeLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;
                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        @Override
        boolean contains(final Integer object) {
            try {
                readWriteLock.readLock().lock();
                Entry curr = this.head.next;
                while (curr.object.compareTo(object) < 0) {
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                readWriteLock.readLock().unlock();
            }
        }
    }
}
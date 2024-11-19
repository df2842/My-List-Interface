import java.util.Iterator;
import java.util.Objects;

/**
 * Linked list implementation of the MyList interface.
 * @author Brian S. Borowski
 * @version 1.0.1 September 23, 2024
 */
public class MyLinkedList<E> implements MyList<E> {
    private Node head, tail;
    private int size;

    /**
     * Constructs an empty list.
     */
    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index    index of the element to return
     * @param element  element to be stored at the specified position
     * @return  the element at the specified position in this list
     * @throws  IndexOutOfBoundsException - if the index is out of range
     *          (index < 0 || index >= size())
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head;
        for (int i = 0; i < index; i++, p = p.next);
        E oldElement = p.element;
        p.element = element;
        return oldElement;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of the element to return
     * @return       the element at the specified position in this list
     * @throws       IndexOutOfBoundsException - if the index is out of range
     *               (index < 0 || index >= size())
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head;
        for (int i = 0; i < index; i++, p = p.next);
        return p.element;
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        Node n = new Node(element);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }

    /**
     * Removes all the elements from this list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new ListItr();
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder("[");
        for (Node p = head; p != null; p = p.next)
        {
            str.append(p.element);
            if (p != tail)
            {
                str.append(", ");
            }
        }
        return str.append("]").toString();
    }

    public void add(int index, E element)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size);
        }
        size++;
        Node n = new Node(element);
        if (index == 0)
        {
            n.next = head;
            if (head == null)
            {
                tail = n;
            }
            head = n;
            return;
        }
        Node p = head;
        for (int i = 0; i < index - 1; i++)
        {
            p = p.next;
        }
        n.next = p.next;
        p.next = n;
        if (n.next == null)
        {
            tail = n;
        }
    }

    public E remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size);
        }
        size--;
        E element;
        if (index == 0)
        {
            element = head.element;
            head = head.next;
            if (head == null)
            {
                tail = null;
            }
            return element;
        }
        Node p = head;
        for (int i = 0; i < index - 1; i++)
        {
            p = p.next;
        }
        element = p.next.element;
        p.next = p.next.next;
        if (p.next == null)
        {
            tail = p;
        }
        return element;
    }

    public int indexOf(E element)
    {
        Node p = head;
        for (int i = 0; i < size; i++)
        {
            if (Objects.equals(p.element, element))
            {
                return i;
            }
            p = p.next;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public int[] indexesOf(E element)
    {
        MyLinkedList<Integer> indexList = new MyLinkedList<>();
        Node p = head;
        for (int i = 0; i < size; i++)
        {
            if (Objects.equals(p.element, element))
            {
                indexList.add(i);
            }
            p = p.next;
        }
        int[] indexes = new int[indexList.size()];
        Node q = (Node)indexList.head;
        for (int i = 0; i < indexList.size(); i++)
        {
            indexes[i] = (int)q.element;
            q = q.next;
        }
        return indexes;
    }

    public void reverse()
    {
        Node p = head;
        Node previous = null;
        tail = p;
        for (int i = 0; i < size; i++)
        {
            Node next = p.next;
            if (next == null)
            {
                head = p;
            }
            p.next = previous;
            previous = p;
            p = next;
        }
    }

    private class ListItr implements Iterator<E> {
        private Node current;

        ListItr() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Node next;
        E element;

        public Node(E element) {
            this.element = element;
        }
    }
}
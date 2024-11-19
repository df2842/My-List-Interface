import java.util.Iterator;
import java.util.Objects;

/**
 * Resizable-array implementation of the MyList interface.
 * @author Brian S. Borowski
 * @version 1.0.1 September 23, 2024
 */
public class MyArrayList<E> implements MyList<E> {
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    Object[] elementData; // non-private to simplify nested class access

    /**
     * Constructs an empty list with the specified initial capacity.
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
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
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        if (size >= elementData.length) {
            Object[] newData = new Object[size * 2 + 1];
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[i];
            }
            elementData = newData;
        }
        elementData[size++] = element;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of the element to return
     * @return       the element at the specified position in this list
     * @throws       IndexOutOfBoundsException - if the index is out of range
     *               (index < 0 || index >= size())
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        return (E)elementData[index];
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
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        E oldValue = (E)elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /**
     * Removes all the elements from this list.
     */
    public void clear() {
        // clear to let GC do its work
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this list (in proper
     * sequence).
     * The returned list iterator is fail-fast -- modification of the elements
     * is not permitted during iteration.
     */
    public Iterator<E> iterator() {
        return new ListItr();
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; i++)
        {
            str.append(elementData[i]);
            if (i != size - 1)
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
        if (index != size)
        {
            element = set(index, element);
            for (int i = index + 1; i < size; i++)
            {
                element = set(i, element);
            }
        }
        add(element);
    }

    public E remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size);
        }
        E element = set(size - 1, null);
        for (int i = size - 2; i >= index; i--)
        {
            element = set(i, element);
        }
        size--;
        return element;
    }

    public int indexOf(E element)
    {
        for (int i = 0; i < size; i++)
        {
            if (Objects.equals(elementData[i], element))
            {
                return i;
            }
        }
        return -1;
    }

    public int[] indexesOf(E element)
    {
        MyArrayList<Integer> indexList = new MyArrayList<>(0);
        for (int i = 0; i < size; i++)
        {
            if (Objects.equals(elementData[i], element))
            {
                indexList.add(i);
            }
        }
        int[] indexes = new int[indexList.size()];
        for (int i = 0; i < indexList.size(); i++)
        {
            indexes[i] = indexList.get(i);
        }
        return indexes;
    }

    public void reverse()
    {
        for (int i = 0; i < size / 2; i++)
        {
            elementData[i] = set(size - i - 1, get(i));
        }
    }

    private class ListItr implements Iterator<E> {
        private int current;

        ListItr() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E)elementData[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
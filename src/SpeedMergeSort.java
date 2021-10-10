import java.util.Comparator;

public class SpeedMergeSort {

    public <T> void mergeSort(T[] array, int low, int high, Comparator<T> comparator ){
        if(low < high){
            int middle = (low + high) / 2;
            mergeSort(array, low, middle, comparator);
            mergeSort(array, middle+1, high, comparator);
            merge(array, low, middle, high, comparator);
        }
    }

    public <T> void merge(T[] array, int low, int middle, int high, Comparator<T> comparator) {
        T[] helper = (T[])new Object[array.length];
        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }
        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;
        while (helperLeft <= middle && helperRight <= high) {
            int value = comparator.compare(helper[helperLeft], helper[helperRight]);
            if ( value <= 0) {
                array[current] = helper[helperLeft];
                helperLeft++;

            } else {
                array[current] = helper[helperRight];
                helperRight++;
            }
            current++;
        }
        int remaining = middle - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            array[current + i] = helper[helperLeft + i];
        }
    }
}


import java.util.Comparator;

public class SpeedThreadsMergeSort<T> {
    private final int NUMBER_THREADS;
    public SpeedThreadsMergeSort(){
        NUMBER_THREADS = 5;
    }
    public SpeedThreadsMergeSort(int NUMBER_THREADS){
        this.NUMBER_THREADS = NUMBER_THREADS;
    }
    public <T> void working(T[] array,Comparator<T> comparator) throws InterruptedException {
        parallelMergeSort(array,0, array.length - 1, comparator, NUMBER_THREADS);
    }
    public <T> void mergeSort(T[] array, int low, int high, Comparator<T> comparator ){
        if (low == high) {
            return;
        }
        if(low < high){
            int middle = (low + high) / 2;
            mergeSort(array, low, middle, comparator);
            mergeSort(array, middle+1, high, comparator);
            merge(array, low, middle, high, comparator);
        }
    }
    public <T> void parallelMergeSort(T[] array, int low, int high, Comparator<T> comparator, int availableThreads ) throws InterruptedException {
        if(low < high){
            if(availableThreads <= 1){
                mergeSort(array, low, high, comparator);
            }
            else {
                int middle = (low + high) / 2;
                threadMerge<T> firstThread = new threadMerge<T>(array, low, middle, availableThreads - 1, comparator);
                threadMerge<T> secondThread = new threadMerge<T>(array, middle + 1, high, availableThreads - 1, comparator);
                firstThread.start();
                secondThread.start();
                firstThread.join();
                secondThread.join();
                merge(array, low, middle, high, comparator);
            }
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
    public class threadMerge<T> extends Thread{
        private final T[] array;
        int low, high, availableThreads;
        Comparator<T> comparator;
        public threadMerge(T[] array, int low, int high, int availableThreads, Comparator<T> comparator){
            this.array = array;
            this.low = low;
            this.high = high;
            this.availableThreads = availableThreads;
            this.comparator = comparator;
        }
        public void run(){
            try {
                parallelMergeSort(array, low, high, comparator, availableThreads);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//class BubbleSort<T extends Comparable<T>> {
//    public void sort(T[] arr) {
//        boolean inOrder;
//        do {
//            inOrder = true;
//            for (int i = 0; i < arr.length - 1; i++) {
//                if (arr[i].compareTo(arr[i + 1]) > 0) {
//                    T aux = arr[i];
//                    arr[i] = arr[i + 1];
//                    arr[i + 1] = aux;
//                    inOrder = false;
//                }
//            }
//        } while (!inOrder);
//    }
//}
//
//
//
//class QuickSort<T extends Comparable<T>> {
//    public int partition(T[] arr, int low, int high) {
//        T pivot = arr[low];
//        int i = low;
//        int j = high;
//        while (i < j) {
//            while (arr[i].compareTo(pivot) < 0&&i<=high-1) {
//                i++;
//            }
//            while (arr[j].compareTo(pivot) > 0 && j>low) {
//                j--;
//            }
//            if (i < j) {
//                T aux = arr[i];
//                arr[i] = arr[j];
//                arr[j] = aux;
//            }
//        }
//        T aux = arr[i];
//        arr[i] = arr[j];
//        arr[j] = aux;
//
//        return j;
//
//    }
//    public void sort(T[] arr, int low, int high) {
//        if(low<high) {
//            int pivot = partition(arr, low, high);
//            sort(arr, low, pivot - 1);
//            sort(arr, pivot + 1, high);
//        }
//    }
//}

import java.util.Comparator;

class MergeSorter <T> implements Sorter2<T>{
    public void merge(T[] array, int left, int right, Comparator<T> comparator) {
        int mid=left+(right-left)/2;
        int n1=mid-left+1;
        int n2=right-mid;

        T[] a=(T[])new Object[n1];
        T[] b=(T[])new Object[n2];

        T[] c=(T[])new Object[array.length];

        for(int i=0; i<n1; i++){
            a[i]=array[left+i];
        }
        for(int i=0; i<n2; i++){
            b[i]=array[mid+1+i];
        }
        int i=0, j=0, k=0;
        while(i<n1&&j<n2){
            if(comparator.compare(a[i],b[j])<0){
                c[k++]=a[i++];
            } else {
                c[k++]=b[j++];
            }
        }

        while (i<n1) c[k++]=a[i++];
        while(j<n2) c[k++]=b[j++];

        for(int l=0;l<k;l++){
            array[left+l]=c[l];
        }

    }

    public void mergeAlg(T[] array, int left, int right, Comparator<T> comparator) {
        if(right>=left) return;
        int mid=left+(right-left)/2;
        merge(array, left, mid, comparator);
        merge(array, mid+1, right, comparator);
        merge(array, left, right, comparator);
    }

    @Override
    public String getName() {
        return "MergeSorter";
    }

    @Override
    public void sort(T[] array,Comparator<T> comparator){
        int len=array.length;
        if (len==0||len<2) return;
        mergeAlg(array, 0, len-1, comparator);
    }
}
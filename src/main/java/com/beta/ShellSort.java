package com.beta;

public class ShellSort{

    private static boolean less(Comparable v , Comparable w){
        return v.compareTo(w)<0;
    }

    private static void exch(Comparable[] a , int i , int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void sort(Comparable[] a){
        int N = a.length;
        int H = 1;
        while (H<N/3) {
            H = 3*H+1;
        }

        while (H >=1 ) {
            for (int i = H; i < N; i++) {
                for(int j = i ; j >= H && less(a[j],a[j-H]); j-=H)
                    exch(a, j ,j-H);
            }
            H = H/3;
        }
    }
}

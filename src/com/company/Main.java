package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter size of array: ");
        int size = sc.nextInt();
        int[] arr = new int[size];
        int key = -1;

        while (key != 0) {
            System.out.println("print 1 to array in order, 2 to array in reverse order, 3 to random array : ");
            key = sc.nextInt();
            switch (key) {
                case (1):
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = i + 1;
                    }
                    break;

                case (2):
                    for (int i = 0, j = size; i < arr.length; i++, j--) {
                        arr[i] = j;
                    }
                    break;

                case (3):
                    Random r = new Random();
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = r.nextInt(size);
                    }
                    break;

                default:
                    break;
            }

            System.out.printf("array: %s%n%n", Arrays.toString(arr));
            ResultSort resultBinary = sortBinary(arr);
            System.out.printf("for binary sort count = %d, sorted array = %s%n%n", resultBinary.getCount(), Arrays.toString(resultBinary.getArr()));
            ResultSort resultLinear = sortLinear(arr);
            System.out.printf("for linear sort count = %d, sorted array = %s%n%n", resultLinear.getCount(), Arrays.toString(resultLinear.getArr()));
            System.out.println("--------------------------------------");
        }

    }

    private static ResultSort sortBinary(int[] sortableArr) {
        int[] arr = sortableArr.clone();
        int tmp, k, count = 0;
        for (int i = 1; i < arr.length; i++) {
            ResultSearch bs = binarySearch(arr, arr[i], 0, i-1);
            k = bs.getIndex();
            count += bs.getCount() + 1;
            for (int j = i; j > k; j--) {
                tmp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = tmp;
            }
        }
        return new ResultSort(arr, count);
    }

    private static ResultSort sortLinear(int[] sortableArr) {
        int[] arr = sortableArr.clone();
        int tmp, k, count = 0;
        for (int i = 1; i < arr.length; i++) {
            ResultSearch ls = linearSearch(arr, arr[i], 0, i-1);
            k = ls.getIndex();
            count += ls.getCount() + 1;
            for (int j = i; j > k; j--) {
                tmp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = tmp;
            }
        }
        return new ResultSort(arr, count);
    }

    private static ResultSearch linearSearch(int[] arr, int searchElement, int firstIndex, int lastIndex) {
        int count = 0;

        for (int i = firstIndex; i <= lastIndex; i++) {
            count++;
            if(arr[i] >= searchElement) {
                return new ResultSearch(count, i);
            }
        }
        return new ResultSearch(count, lastIndex + 1);
    }

    private static ResultSearch binarySearch(int[] arr, int searchElement, int firstIndex, int lastIndex) {
        int leftIndex = firstIndex;
        int rightIndex = lastIndex;
        int count = 0;

        while (leftIndex <= rightIndex) {
            count++;

            int middleIndex = (leftIndex + rightIndex) / 2;
            if (middleIndex == firstIndex) {
                if (searchElement < arr[leftIndex]) {
                    return new ResultSearch(count, leftIndex);
                }
            }

            if (middleIndex == lastIndex) {
                if (searchElement > arr[rightIndex]) {
                    return new ResultSearch(count, rightIndex + 1);
                }
            }

            if (searchElement >= arr[middleIndex] && searchElement <= arr[middleIndex + 1])
                return new ResultSearch(count, middleIndex + 1);

            if (searchElement < arr[middleIndex])
                rightIndex = middleIndex - 1;

            if (searchElement > arr[middleIndex])
                leftIndex = middleIndex + 1;
        }
        return new ResultSearch(count, -1);
    }
}

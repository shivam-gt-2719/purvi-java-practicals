import mpi.*;

public class ArraySum {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Replace with your array

        int localSum = calculatePartialSum(array, rank, size);

        int[] allSums = new int[size];
        MPI.COMM_WORLD.Gather(new int[]{localSum}, 0, 1, MPI.INT, allSums, 0, 1, MPI.INT, 0);

        if (rank == 0) {
            int finalSum = calculateFinalSum(allSums);
            System.out.println("Final Sum: " + finalSum);
        }

        MPI.Finalize();
    }

    private static int calculatePartialSum(int[] array, int rank, int size) {
        int elementsPerProcessor = array.length / size;
        int startIndex = rank * elementsPerProcessor;
        int endIndex = startIndex + elementsPerProcessor;

        int partialSum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            partialSum += array[i];
        }

        System.out.println("Rank " + rank + " Partial Sum: " + partialSum);
        return partialSum;
    }

    private static int calculateFinalSum(int[] allSums) {
        int finalSum = 0;
        for (int sum : allSums) {
            finalSum += sum;
        }
        return finalSum;
    }
}

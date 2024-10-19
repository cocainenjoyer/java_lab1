import java.util.Scanner;

public class HomeTask1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите операцию: add, subtract, multiply, divide, determinant, inverse или transpose:");
        String operation = scanner.nextLine();

        ComplexMatrix matrix1 = inputMatrix(scanner, "первой");
        ComplexMatrix matrix2 = null;

        if (operation.equals("add") || operation.equals("subtract") || operation.equals("multiply") || operation.equals("divide")) {
            matrix2 = inputMatrix(scanner, "второй");
        }

        switch (operation) {
            case "add":
                ComplexMatrix resultAdd = matrix1.add(matrix2);
                System.out.println("Результат сложения:");
                System.out.println(resultAdd);
                break;
            case "subtract":
                ComplexMatrix resultSub = matrix1.subtract(matrix2);
                System.out.println("Результат вычитания:");
                System.out.println(resultSub);
                break;
            case "multiply":
                ComplexMatrix resultMult = matrix1.mult(matrix2);
                System.out.println("Результат умножения:");
                System.out.println(resultMult);
                break;
            case "divide":
                ComplexMatrix resultDiv = matrix1.divide(matrix2);
                System.out.println("Результат деления:");
                System.out.println(resultDiv);
                break;
            case "determinant":
                if (matrix1.get(0, 0) != null && matrix1.get(0, 0).toString() != null) {
                    ComplexNumber det = matrix1.determinant();
                    System.out.println("Определитель матрицы:");
                    System.out.println(det);
                } else {
                    System.out.println("Определитель можно найти только для квадратной матрицы.");
                }
                break;
            case "inverse":
                if (matrix1.get(0, 0) != null && matrix1.get(0, 0).toString() != null) {
                    ComplexMatrix inverse = matrix1.inverse();
                    System.out.println("Обратная матрица:");
                    System.out.println(inverse);
                } else {
                    System.out.println("Обратную матрицу можно найти только для квадратной матрицы.");
                }
                break;
            case "transpose":
                ComplexMatrix transposed = matrix1.transpose();
                System.out.println("Транспонированная матрица:");
                System.out.println(transposed);
                break;
            default:
                System.out.println("Неверная операция.");
                break;
        }

        scanner.close();
    }

    private static ComplexMatrix inputMatrix(Scanner scanner, String matrixOrder) {
        System.out.println("Введите размеры " + matrixOrder + " матрицы (строки и столбцы):");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Введите элементы " + matrixOrder + " матрицы (формат: re im для каждого элемента через пробел):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                matrix.set(i, j, new ComplexNumber(re, im));
            }
        }
        return matrix;
    }
}

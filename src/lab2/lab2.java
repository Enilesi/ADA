//import java.util.Arrays;
//import java.util.Comparator;
//
//class MySorter<T extends Comparable<T>> {
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
//
//    public void sort(T[] arr, Comparator<T> comparator) {
//        boolean inOrder;
//        do {
//            inOrder = true;
//            for (int i = 0; i < arr.length - 1; i++) {
//                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
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
//class Student implements Comparable<Student> {
//    private String name;
//    private int ssn;
//    private double averageGrade;
//    private String faculty;
//
//    public Student(String name, int ssn, double averageGrade, String faculty) {
//        this.name = name;
//        this.ssn = ssn;
//        this.averageGrade = averageGrade;
//        this.faculty = faculty;
//    }
//
//    public int compareTo(Student o) {
//        return Double.compare(this.averageGrade, o.averageGrade);
//    }
//
//    public double getAverageGrade() {
//        return averageGrade;
//    }
//
//    public int getSsn() {
//        return ssn;
//    }
//
//    public String getFaculty() {
//        return faculty;
//    }
//
//    public String toString() {
//        return name + " (" + averageGrade + ")";
//    }
//}
//
//class StudentSsnComparator implements Comparator<Student> {
//    public int compare(Student o1, Student o2) {
//        return Integer.compare(o1.getSsn(), o2.getSsn());
//    }
//}
//
//class MyMainClass {
//    public static void main(String[] args) {
//        Student[] students = {
//                new Student("Alice", 123, 9.5, "CS"),
//                new Student("Bob", 456, 8.0, "Math"),
//                new Student("Charlie", 789, 9.0, "Physics")
//        };
//
//        Sorter<Student> sorter = new Sorter<>();
//
//        System.out.println("Before Sorting:");
//        for (Student s : students) {
//            System.out.println(s);
//        }
//
//        sorter.sort(students);
//
//        System.out.println("\nSorted by Average Grade:");
//        for (Student s : students) {
//            System.out.println(s);
//        }
//
//        sorter.sort(students, new StudentSsnComparator());
//
//        System.out.println("\nSorted by SSN:");
//        for (Student s : students) {
//            System.out.println(s);
//        }
//    }
//}

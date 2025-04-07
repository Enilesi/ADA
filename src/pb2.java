import java.util.Arrays;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;



interface Sorter2<T> {
    String getName();
    public void sort(T[] array, Comparator<T> comparator);
}



class QuickSorter <T> implements Sorter2<T>{
    public void quickSort(T[] array, int left, int right, Comparator<T> comparator) {
        int i,j;
        T pivot;

        pivot=array[left+(right-left)/2];
        i=left;
        j=right;

        while(i<=j){
            while(comparator.compare(array[i],pivot)<0)i++;
            while(comparator.compare(array[j],pivot)>0)j--;

            if(i<=j){
                T temp=array[i];
                array[i]=array[j];
                array[j]=temp;
                i++;
                j--;
            }
        }
        if(left<j) quickSort(array, left, j, comparator);
        if(right>i) quickSort(array, i, right, comparator);
    }

    @Override
    public String getName() {
        return "QuickSorter";
    }

    public void sort(T[] array, Comparator<T> comparator) {
        int len=array.length;
        quickSort(array, 0, len-1, comparator);
    }
}


class BuiltInSorter <T> implements Sorter2<T>{
    @Override
    public String getName() {
        return "Arrays.sort";
    }

    public void sort(T[] array, Comparator<T> comparator) {
        Arrays.sort(array, comparator);
    }
}

class Person{
    private String ssn;
    private String surname;
    private String lastname;
    private String birthday;
    private int age;

    public Person(String ssn, String surname,String lastname, String birthday) {
        this.ssn=ssn;
        this.surname=surname;
        this.lastname=lastname;
        this.birthday=birthday;
        this.age = calculateAge();
    }

    public String getSsn() {return ssn;}
    public String getLastname() {return lastname;}
    public String getSurname() {return surname;}
    public String getBirthday() {return birthday;}
    public int getAge() {return age;}

    @Override
    public String toString() {
        return ssn+" "+surname+" "+lastname+" "+birthday;
    }

    private int calculateAge() {
        Pattern pattern1=Pattern.compile("([0-9]+)/([0-9]+)/([0-9]+)");
        Matcher matcher1=pattern1.matcher(birthday);
        if(matcher1.find()){

            int day=Integer.parseInt(matcher1.group(1));
            int month=Integer.parseInt(matcher1.group(2));
            int year=Integer.parseInt(matcher1.group(3));

            LocalDate birthdayDate = LocalDate.of(year,month,day);
            LocalDate currentDate = LocalDate.now();

            return Period.between(birthdayDate,currentDate).getYears();
        } else {
            throw new IllegalArgumentException("invalid birthday"+birthday);
        }
    }

}

class FirstNameComparator implements Comparator<Person>{
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getSurname().compareTo(p2.getSurname());
    }
}

class LastNameComparator implements Comparator<Person>{
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getLastname().compareTo(p2.getLastname());
    }
}

class AgeComparator implements Comparator<Person>{
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(),p2.getAge());
    }
}

abstract class ParseLines{
    protected int n;

    public ParseLines(Scanner sc) {
        this.n=readNr(sc);
    }

    public int readNr(Scanner sc){
        if(sc.hasNextInt()){
            return sc.nextInt();
        }
        else return -1;
    }
    public abstract void readAllLines();

}

class ParseIntegerLines extends ParseLines{
    private int arr[];
    private static Scanner sc;

    public ParseIntegerLines(Scanner sc) {
        super(sc);
        this.sc = sc;
        this.arr = new int[n];
    }

    public void readAllLines(){
        for(int i=0;i<n;i++){
            if(sc.hasNextInt()){
                arr[i]=sc.nextInt();
            }
            else {
                break;
            }
        }
    }
    public int[] getArray(){return arr;}
}

class ParsePersonLines extends ParseLines{
    private Person persons[];
    private static Scanner sc;

    public ParsePersonLines(Scanner sc) {
        super(sc);
        this.sc = sc;
        this.persons = new Person[n];
    }

    public void readAllLines(){
        for(int i=0;i<n;i++){
            if(i==0) System.out.println(sc.nextLine());
            if(sc.hasNext()){
               String line=sc.nextLine();
                String[] words = line.split(" ");
                if(words.length==4){
                    persons[i]=new Person(words[0],words[1],words[2],words[3]);
                }

            }
            else {
                n=i;
                break;
            }
        }
    }

    public Person[] getPersons(){return persons;}
}

class Stopwatch {
    private long startTime;

    public void start() {
        startTime=System.nanoTime();
    }

    public double getElapsedTime() {
        return (System.nanoTime()-startTime)/1_000_000_000.0;
    }
}

class FileInputV1 {
    public static Integer[] readIntegers(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int n = Integer.parseInt(reader.readLine());

            Integer[] integers = new Integer[n];

            for (int i = 0; i < n; i++) {
                integers[i] = Integer.parseInt(reader.readLine());
            }
            return integers;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file " + filename);
            e.printStackTrace();
            return null;
        }
    }
}

class FileInputV2 {
    public static Person[] readIntegers(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int n = Integer.parseInt(reader.readLine());

            Person[] persons = new Person[n];

            for (int i = 0; i < n; i++) {
                String[] words=reader.readLine().split(" ");
                persons[i] = new Person(words[0],words[1],words[2],words[3]);
            }
            return persons;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file " + filename);
            e.printStackTrace();
            return null;
        }
    }
}



class Main2 {
    public static void main(String[] args) {

        Sorter2<Integer>[] integerSorters = new Sorter2[]{ new QuickSorter<Integer>(), new BuiltInSorter<Integer>()};
        Sorter2<Person>[] personSorters = new Sorter2[]{new QuickSorter<Person>(),new BuiltInSorter<Person>()};
        Comparator<Person>[] personComparators = new Comparator[]{new FirstNameComparator(), new LastNameComparator(), new AgeComparator()};

        Integer[] numbers =  FileInputV1.readIntegers("randomIntegers_1M.txt");
        Person[] people =  FileInputV2.readIntegers("people.txt");


        Stopwatch timer = new Stopwatch();


        for (Sorter2<Integer> sorter : integerSorters) {
            assert numbers != null;
            Integer[] numbersCopy = Arrays.copyOf(numbers, numbers.length);
            timer.start();
            sorter.sort(numbersCopy, Integer::compareTo);
            System.out.println(sorter.getName()+" for Integers: "+timer.getElapsedTime() + " seconds");
        }


        for (Sorter2<Person> sorter : personSorters) {
            for (Comparator<Person> comparator : personComparators) {
                assert people != null;
                Person[] personsCopy = Arrays.copyOf(people, people.length);
                timer.start();
                sorter.sort(personsCopy, comparator);
                System.out.println(sorter.getName()+" for Persons sorted by "+comparator.getClass().getSimpleName() + ": "+timer.getElapsedTime()+" seconds");
            }
        }


    }
}







package lab1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PersonalStack<T>{
    private List <T> list;
    public PersonalStack(){
        list=new ArrayList<>();
    }
    public void push(T t){
        list.add(t);
    }
    public T pop(){
        if(!list.isEmpty()){
            return list.remove(list.size()-1);
        }
        return null;
    }
    public boolean isEmpty(){
        return list.isEmpty();
    }
}



class MainClass{
    public static void main(String[] args){

        PersonalStack<Integer>nrStack=new PersonalStack<>();
        PersonalStack<String> strStack=new PersonalStack<>();

        Scanner sc=new Scanner(System.in);

        Pattern pattern1= Pattern.compile("([-+]?[0-9]+)([a-zA-Z]+)");
        Pattern pattern2= Pattern.compile("([a-zA-Z]+)([-+]?[0-9]+)");
        int n;
        if(sc.hasNextInt()){
            n=sc.nextInt();
        } else throw new RuntimeException("Must read a number first");


                for(int i=0;i<=n;i++){
                    String line=sc.nextLine().replaceAll(" ","");

                    if(!line.isEmpty()){
                        Matcher matcher1=pattern1.matcher(line);
                        Matcher matcher2=pattern2.matcher(line);

                        if(matcher1.find()){
                            nrStack.push(Integer.parseInt(matcher1.group(1)));
                            strStack.push(matcher1.group(2));
                        }
                        else if(matcher2.find()){
                            nrStack.push(Integer.parseInt(matcher2.group(2)));
                            strStack.push(matcher2.group(1));
                        }
                        else throw new IllegalArgumentException("must contain a string and a number. The order doesn't matter");
                    }
                }

                sc.close();

                while(!nrStack.isEmpty()){
                    System.out.println(nrStack.pop());
                }

        while(!strStack.isEmpty()){
            System.out.println(strStack.pop());
        }

    }
}


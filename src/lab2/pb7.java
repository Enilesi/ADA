package lab2;

import java.util.*;
import java.io.*;


class Main{

    public String tagName(String line, int pos){
        String s=new String();
        int i=pos;


        while(i<line.length()) {
            if(line.charAt(i)=='>') return s;
            s += line.charAt(i);
            i++;
        }
        return "";
    }

    public boolean tagChecker(String line,Stack<String> tag){

        int i=0;
        int len=0;
        while(i<line.length()){
            if(line.charAt(i)=='<'){
                    if(i+1<line.length()&&line.charAt(i+1)=='/'){
                                if(!tag.isEmpty()) {

                                    String closedTag=tagName(line,i+2);

                                    if(!closedTag.equals(tag.pop())) return false;
                                } else return false;
                            }
                    else{
                        tag.push(tagName(line,i+1));
                    }

            }
            i++;
        }


        return true;
    }


    public static void main(String args[]){
        Stack<String>readTag=new Stack<>(); // for <tag>
        Main validator=new Main();

        Scanner sc=new Scanner(System.in);
        String fileName=new String();
        if(sc.hasNextLine()){
            fileName=sc.nextLine();
        }
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Couldn't find file with path"+file.getAbsolutePath());
            return;
        }

        try(Scanner scFile=new Scanner(file)){
            while(scFile.hasNext()){
                String line=scFile.nextLine();

                if(!validator.tagChecker(line,readTag)){
                    System.out.println("invalid tags <tag> </tag> ");
                    return;
                }

            }

        }catch(FileNotFoundException e){
            System.out.println("File not found");
        };



        if(readTag.isEmpty())System.out.println("Appropiate tag structure");
        else  System.out.println("invalid tags <tag> </tag> ");





    }
}


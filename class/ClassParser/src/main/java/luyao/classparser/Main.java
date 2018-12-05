package luyao.classparser;



import luyao.classparser.classes.ClassParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by luyao
 * on 2018/8/27 10:28
 */
public class Main {

    public static void main(String[] args) {
        String path="/home/luyao/program/protect/class/ClassParser/src/main/resources/Hello.class";
        try {
            ClassParser parser=new ClassParser(new FileInputStream(new File(path)));
            parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package luyao.parser.classes;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by luyao
 * on 2018/12/14 10:30
 */
public class ClassParserTest {

    @Test
    public void parse() {
        File file=new File("resources/Hello.class");
        try {
            ClassParser classParser=new ClassParser(new FileInputStream(file));
            classParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
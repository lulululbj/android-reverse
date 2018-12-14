package luyao.xmlparser.classes;

import luyao.xmlparser.xml.XmlParser;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by luyao
 * on 2018/12/14 10:30
 */
public class ClassParserTest {

    @Test
    public void parse() {
        File file=new File("E:\\luyao\\github\\my\\android-reverse\\xml\\XmlParser\\src\\main\\resources/Hello.class");
        try {
            ClassParser classParser=new ClassParser(new FileInputStream(file));
            classParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
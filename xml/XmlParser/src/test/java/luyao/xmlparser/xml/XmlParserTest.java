package luyao.xmlparser.xml;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by luyao
 * on 2018/12/14 10:29
 */
public class XmlParserTest {

    @Test
    public void parse() {
        File file=new File("E:\\luyao\\github\\my\\android-reverse\\xml\\XmlParser\\src\\main\\resources/AndroidManifest.xml");
        try {
            XmlParser xmlParser=new XmlParser(new FileInputStream(file));
            xmlParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
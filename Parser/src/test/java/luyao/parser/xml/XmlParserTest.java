package luyao.parser.xml;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by luyao
 * on 2018/12/14 10:29
 */
public class XmlParserTest {

    @Test
    public void parse() {
        File file = new File("resources/AndroidManifest.xml");
        try {
            XmlParser xmlParser = new XmlParser(new FileInputStream(file));
            xmlParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
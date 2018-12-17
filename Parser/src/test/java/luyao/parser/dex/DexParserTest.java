package luyao.parser.dex;

import luyao.parser.xml.XmlParser;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class DexParserTest {

    @Test
    public void parse() {
        File file = new File("/home/luyao/program/my/github/android-reverse/Parser/src/main/resources/Hello.dex");
        try {
            DexParser dexParser = new DexParser(new FileInputStream(file));
            dexParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
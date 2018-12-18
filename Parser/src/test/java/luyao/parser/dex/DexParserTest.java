package luyao.parser.dex;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DexParserTest {

    @Test
    public void parse() {
        File file = new File("resources/Hello.dex");
        try {
            DexParser dexParser = new DexParser(new FileInputStream(file));
            dexParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
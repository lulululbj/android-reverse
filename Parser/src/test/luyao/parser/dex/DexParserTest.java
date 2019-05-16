package luyao.parser.dex;

import luyao.parser.utils.Utils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DexParserTest {

    @Test
    public void parse() {
        File file = new File("resources/Hello.dex");
        try {
            DexParser dexParser = new DexParser(new FileInputStream(file), Utils.readAll(file));
            dexParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
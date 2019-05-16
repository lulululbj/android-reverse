package luyao.parser.arsc;


import org.junit.Test;

import java.io.File;

/**
 * Created by luyao
 * on 2018/12/20 13:45
 */
public class ResParserTest {

    @Test
    public void parse() {
        File file = new File("resources/resources.arsc");
        ResParser resParser = new ResParser(file);
        resParser.parse();
    }

}
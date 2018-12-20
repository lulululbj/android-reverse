package luyao.parser.arsc;

import luyao.parser.arsc.bean.ResSpanStyle;
import luyao.parser.arsc.bean.ResStringPoolHeader;
import luyao.parser.arsc.bean.ResTableHeader;
import luyao.parser.utils.BytesReader;
import luyao.parser.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static luyao.parser.utils.Reader.log;

/**
 * Created by luyao
 * on 2018/12/20 13:43
 */
public class ResParser {

    private BytesReader reader;

    public ResParser(File in) {
        this.reader = new BytesReader(Utils.readAll(in));
    }

    public void parse() {
        parseResTableHeader();
        parseStringPoolType();
    }

    private void parseResTableHeader() {
        ResTableHeader resTableHeader = new ResTableHeader();
        resTableHeader.parse(reader);
        log("ResTableHeader: %s", resTableHeader.toString());
    }

    private void parseStringPoolType() {
        try {
            ResStringPoolHeader stringPoolHeader = new ResStringPoolHeader();
            stringPoolHeader.parse(reader);

            List<Integer> stringOffsets=new ArrayList<>(stringPoolHeader.stringCount);
            for (int i=0;i<stringPoolHeader.stringCount;i++){
                int offset=reader.readInt();

            }
            reader.skip(4 * stringPoolHeader.styleCount); // styleoffsets[]

            List<String> stringPoolList = new ArrayList<>();
            for (int i = 0; i < stringPoolHeader.stringCount; i++) {
                int u16len = reader.readUnsignedByte();
                int u8len = reader.readUnsignedByte();
                String string = new String(reader.read(u8len + 1));
                stringPoolList.add(string);
                log("stringPool[%d]: %s", i, string);
            }

            for (int i=0;i<stringPoolHeader.styleCount;i++){
                int index=reader.readInt();
                int firstChar=reader.readInt();
                int lasstChar=reader.readInt();
                ResSpanStyle resSpanStyle=new ResSpanStyle(index,firstChar,lasstChar);
                reader.skip(4); // 0xffff
            }
        } catch (IOException e) {
            e.printStackTrace();
            log("parse string pool type error!");
        }
    }
}

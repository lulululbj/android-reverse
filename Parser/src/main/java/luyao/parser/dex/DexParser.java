package luyao.parser.dex;

import luyao.parser.dex.bean.DexHeader;
import luyao.parser.utils.Reader;

import java.io.InputStream;

public class DexParser {

    private Reader reader;

    public DexParser(InputStream in){
        reader=new Reader(in,true);
    }

    public void parse(){
        DexHeader dexHeader=new DexHeader(reader);
        dexHeader.parse();
    }
}

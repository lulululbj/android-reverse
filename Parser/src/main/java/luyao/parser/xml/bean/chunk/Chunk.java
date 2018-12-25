package luyao.parser.xml.bean.chunk;

/**
 * Created by luyao
 * on 2018/12/14 16:17
 */
public abstract class Chunk {

    int chunkType;
    int chunkSize;
    int lineNumber;

    Chunk(int chunkType){
        this.chunkType=chunkType;
    }

    public abstract String toXmlString();
}

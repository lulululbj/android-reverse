package luyao.xmlparser.xml.bean.chunk;

import luyao.xmlparser.xml.bean.Xml;

/**
 * Created by luyao
 * on 2018/12/14 16:27
 */
public class EndTagChunk extends Chunk {
    private int nameSpaceUri;
    private int name;

    public EndTagChunk(int nameSpaceUri, int name) {
        super(Xml.END_TAG_CHUNK_TYPE);
        this.nameSpaceUri = nameSpaceUri;
        this.name = name;
    }


    public int getNameSpaceUri() {
        return nameSpaceUri;
    }

    public void setNameSpaceUri(int nameSpaceUri) {
        this.nameSpaceUri = nameSpaceUri;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}

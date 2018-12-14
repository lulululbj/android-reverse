package luyao.xmlparser.xml.bean.chunk;

import luyao.xmlparser.xml.bean.Attribute;
import luyao.xmlparser.xml.bean.Xml;
import org.w3c.dom.Attr;

import java.util.List;

/**
 * Created by luyao
 * on 2018/12/14 16:24
 */
public class StartTagChunk extends Chunk {

    private int nameSpaceUri;
    private int name;
    private List<Attribute> attributeList;

    public StartTagChunk(int nameSpaceUri, int name, List<Attribute> attributeList) {
        super(Xml.START_TAG_CHUNK_TYPE);
        this.nameSpaceUri = nameSpaceUri;
        this.name = name;
        this.attributeList = attributeList;
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

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}

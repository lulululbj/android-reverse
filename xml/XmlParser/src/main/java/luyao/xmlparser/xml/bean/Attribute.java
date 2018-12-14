package luyao.xmlparser.xml.bean;

/**
 * Created by luyao
 * on 2018/12/14 14:00
 */
public class Attribute {

    private int namespaceUri;
    private int name;
    private int valueStr;
    private int type;
    private int data;


    public Attribute(int namespaceUri, int name, int valueStr, int type, int data) {
        this.namespaceUri = namespaceUri;
        this.name = name;
        this.valueStr = valueStr;
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "namespaceUri='" + namespaceUri + '\'' +
                ", name='" + name + '\'' +
                ", valueStr='" + valueStr + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public  int getData(){
        return data;
    }

    public int getNamespaceUri() {
        return namespaceUri;
    }

    public void setNamespaceUri(int namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getValueStr() {
        return valueStr;
    }

    public void setValueStr(int valueStr) {
        this.valueStr = valueStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(int data) {
        this.data = data;
    }
}

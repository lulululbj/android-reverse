package luyao.parser.arsc.bean;

import luyao.parser.utils.BytesReader;

import java.io.IOException;

/**
 * Created by luyao
 * on 2018/12/21 10:57
 */
public class ResTablePackageHeader {

/*
struct ResTable_package
{
    struct ResChunk_header header;

    // If this is a base package, its ID.  Package IDs start
    // at 1 (corresponding to the value of the package bits in a
    // resource identifier).  0 means this is not a base package.
    uint32_t id;

    // Actual name of this package, \0-terminated.
    uint16_t name[128];

    // Offset to a ResStringPool_header defining the resource
    // type symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    uint32_t typeStrings;

    // Last index into typeStrings that is for public use by others.
    uint32_t lastPublicType;

    // Offset to a ResStringPool_header defining the resource
    // key symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    uint32_t keyStrings;

    // Last index into keyStrings that is for public use by others.
    uint32_t lastPublicKey;

    uint32_t typeIdOffset;
};
*/

    public ResChunkHeader chunkHeader;
    public int id; // 包的ID,等于Package Id,一般用户包的值Package Id为0X7F,系统资源包的Package Id为0X01。
    public String name;  // 包名称
    public int typeStrings; // 资源类型字符串池在 ResTablePackage 中的偏移量
    public int lastPublicType; // 最后一个导出的Public类型字符串在类型字符串资源池中的索引，目前这个值设置为类型字符串资源池的元素个数。
    public int keyStrings; // 资源名称字符池在 ResTablePackage 中的偏移
    public int lastPublicKey; // 最后一个导出的Public资源名称字符串在资源项名称字符串资源池中的索引，目前这个值设置为资源项名称字符串资源池的元素个数。


    public ResTablePackageHeader(BytesReader reader) throws IOException {
        chunkHeader = new ResChunkHeader(reader);
        id=reader.readInt();
        name = new String(reader.read(256));
        typeStrings = reader.readInt();
        lastPublicType = reader.readInt();
        keyStrings = reader.readInt();
        lastPublicKey = reader.readInt();
    }

    @Override
    public String toString() {
        return "ResTablePackageHeader{" +
                "\nchunkHeader=" + chunkHeader.toString() +
                ", \nid=" + id +
                ", \nname='" + name + '\'' +
                ", \ntypeStrings=" + typeStrings +
                ", \nlastPublicType=" + lastPublicType +
                ", \nkeyStrings=" + keyStrings +
                ", \nlastPublicKey=" + lastPublicKey +
                '}';
    }
}

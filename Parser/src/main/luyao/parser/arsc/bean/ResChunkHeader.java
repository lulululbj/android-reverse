package luyao.parser.arsc.bean;

import luyao.parser.utils.BytesReader;

import java.io.IOException;

/**
 * Created by luyao
 * on 2018/12/20 13:47
 */
public class ResChunkHeader {

    /*
struct ResChunk_header
{
    // Type identifier for this chunk.  The meaning of this value depends
    // on the containing chunk.
    uint16_t type;

    // Size of the chunk header (in bytes).  Adding this value to
    // the address of the chunk allows you to find its associated data
    // (if any).
    uint16_t headerSize;

    // Total size of this chunk (in bytes).  This is the chunkSize plus
    // the size of any data associated with the chunk.  Adding this value
    // to the chunk allows you to completely skip its contents (including
    // any child chunks).  If this value is the same as chunkSize, there is
    // no data associated with the chunk.
    uint32_t size;
};

     */

    public int resType; // 当前 chunk 类型
    public int headerSize; // 当前 chunk header 大小
    public int size; // 当前 chunk 大小

    public ResChunkHeader(BytesReader reader) throws IOException {
        resType = reader.readShort();
        headerSize = reader.readUnsignedShort();
        size = reader.readInt();
    }

    @Override
    public String toString() {
        return "ResChunkHeader{" +
                "resType=" + resType +
                ", headerSize=" + headerSize +
                ", size=" + size +
                '}';
    }
}

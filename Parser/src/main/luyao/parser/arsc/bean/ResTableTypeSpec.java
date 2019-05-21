package luyao.parser.arsc.bean;


import luyao.parser.utils.BytesReader;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by luyao
 * on 2018/12/21 14:22
 */
public class ResTableTypeSpec {

/*
struct ResTable_typeSpec
{
    struct ResChunk_header header;

    // The type identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the type bits in a
    // resource identifier).  0 is invalid.
    uint8_t id;

    // Must be 0.
    uint8_t res0;
    // Must be 0.
    uint16_t res1;

    // Number of uint32_t entry configuration masks that follow.
    uint32_t entryCount;

    enum {
        // Additional flag indicating an entry is public.
        SPEC_PUBLIC = 0x40000000
    };
};

*/

    public ResChunkHeader resChunkHeader;
    public int id;  // 标识资源的Type ID,Type ID是指资源的类型ID。
                    // 资源的类型有animator、anim、color、drawable、layout、menu、raw、string和xml等等若干种，每一种都会被赋予一个ID。
    public int res0; // 始终为 0
    public int res1; // 始终为 0
    public int entryCount; // 等于本类型的资源项个数,指名称相同的资源项的个数。
    public int[] configMask;

    public ResTableTypeSpec(ResChunkHeader resChunkHeader) {
        this.resChunkHeader = resChunkHeader;
    }

    public void parse(BytesReader reader) throws IOException {
        this.id = reader.readByte();
        this.res0 = reader.readByte();
        this.res1 = reader.readUnsignedShort();
        this.entryCount = reader.readInt();
        this.configMask=new int[entryCount];
        for (int i = 0; i < entryCount; i++) {
            configMask[i] = reader.readInt();
        }
    }

    @Override
    public String toString() {
        return "ResTableTypeSpec{" +
                "resChunkHeader=" + resChunkHeader +
                ", id=" + id +
                ", res0=" + res0 +
                ", res1=" + res1 +
                ", entryCount=" + entryCount +
                ", configMask=" + Arrays.toString(configMask) +
                '}';
    }
}

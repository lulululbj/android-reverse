package luyao.parser.arsc.bean;

import luyao.parser.utils.BytesReader;
import luyao.parser.utils.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static luyao.parser.utils.Reader.log;

/**
 * Created by luyao
 * on 2018/12/24 15:16
 */
public class ResTableType {

/*
struct ResTable_type
{
    struct ResChunk_header header;

    enum {
        NO_ENTRY = 0xFFFFFFFF
    };

    // The type identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the type bits in a
    // resource identifier).  0 is invalid.
    uint8_t id;

    // Must be 0.
    uint8_t res0;
    // Must be 0.
    uint16_t res1;

    // Number of uint32_t entry indices that follow.
    uint32_t entryCount;

    // Offset from header where ResTable_entry data starts.
    uint32_t entriesStart;

    // Configuration this collection of entries is designed for.
    ResTable_config config;
};
*/

    public ResTableType(ResChunkHeader chunkHeader) {
        this.chunkHeader = chunkHeader;
    }

    public ResChunkHeader chunkHeader;
    public int id; // 标识资源的 type id
    public int res0; // 始终为 0
    public int res1; // 始终为 0
    public int entryCount; // 本类型的资源项个数,指名称相同的资源项的个数
    public int entriesStart;  // 资源项数据块相对头部的偏移量
    public ResTableConfig config;  // 指向一个ResTable_config,用来描述配置信息,地区,语言,分辨率等
    public int[] entryOffsets;
    public List<ResTableEntry> resTableEntryList = new ArrayList<>();

    public void parse(BytesReader reader) {
        try {
            this.id = reader.readByte();
            this.res0 = reader.readByte();
            this.res1 = reader.readUnsignedShort();
            this.entryCount = reader.readInt();
            this.entriesStart = reader.readInt();
            this.config = new ResTableConfig();
            config.parse(reader);

            entryOffsets = new int[entryCount];
            for (int i = 0; i < entryCount; i++) {
                entryOffsets[i] = reader.readInt();
            }

            // TODO 后面并不一定是 entryCount 项
            int length = chunkHeader.size - entriesStart;
            byte[] data = reader.read(length);
            BytesReader dataReader = new BytesReader(data);
            while (dataReader.avaliable() > 0) {
                int size = dataReader.readUnsignedShort();
                int flags = dataReader.readUnsignedShort();
                int resStringPoolIndex = dataReader.readInt();
                if (flags == 1) {
                    ResTableMapEntry resTableMapEntry = new ResTableMapEntry(size, flags, resStringPoolIndex);
                    resTableMapEntry.parse(dataReader);
                    resTableEntryList.add(resTableMapEntry);
                } else if (flags == 0) {
                    ResValue resValue = new ResValue();
                    resValue.parse(dataReader);
                    log("ResValue : %s",resValue.toString());
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(List<String> keyStringList) {
        for (ResTableEntry resTableEntry : resTableEntryList) {
            log("keyString: %s", keyStringList.get(resTableEntry.string_pool_index));
            if (resTableEntry instanceof ResTableMapEntry) {

            } else {

            }
        }
        return null;
    }
}

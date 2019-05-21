package luyao.parser.arsc.bean;

import luyao.parser.utils.BytesReader;

import java.io.IOException;

/**
 * Created by luyao
 * on 2018/12/20 14:13
 */
public class ResStringPoolHeader {

    public static final int SORTED_FLAG = 1 << 0;
    public static final int UTF8_FLAG = 1 << 8;

    /*
        struct ResStringPool_header
        {
            struct ResChunk_header header;

            // Number of strings in this pool (number of uint32_t indices that follow
            // in the data).
            uint32_t stringCount;

            // Number of style span arrays in the pool (number of uint32_t indices
            // follow the string indices).
            uint32_t styleCount;

            // Flags.
            enum {
                // If set, the string index is sorted by the string values (based
                // on strcmp16()).
                SORTED_FLAG = 1<<0,

                // String pool is encoded in UTF-8
                UTF8_FLAG = 1<<8
            };
            uint32_t flags;

            // Index from header of the string data.
            uint32_t stringsStart;

            // Index from header of the style data.
            uint32_t stylesStart;
        };
     */

    public ResChunkHeader resChunkHeader;
    public int stringCount; // 字符串个数
    public int styleCount; // 字符串样式个数
    public int flags; // 字符串的属性,可取值包括0x000(UTF-16),0x001(字符串经过排序)、0X100(UTF-8)和他们的组合值
    public int stringsStart; // 字符串内容偏移量
    public int stylesStart; // 字符串样式内容偏移量

    public void parse(BytesReader reader) throws IOException {
        resChunkHeader = new ResChunkHeader(reader);
        stringCount = reader.readInt();
        styleCount = reader.readInt();
        flags = reader.readInt();
        stringsStart = reader.readInt();
        stylesStart = reader.readInt();
    }
}

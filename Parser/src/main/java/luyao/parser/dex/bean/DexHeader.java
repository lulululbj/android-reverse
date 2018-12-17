package luyao.parser.dex.bean;

import luyao.parser.utils.Reader;
import luyao.parser.utils.TransformUtils;

import java.io.IOException;

import static luyao.parser.utils.Reader.log;

public class DexHeader {

    private Reader reader;

    public DexHeader(Reader reader) {
        this.reader = reader;
    }

    public void parse() {
        try {
            String magic = TransformUtils.byte2HexStr(reader.readOrigin(8));
            log("magic: %s", magic);

            int checkSum = reader.readInt();
            log("checkSum: %d", checkSum);

            String signature = TransformUtils.byte2HexStr(reader.readOrigin(20));
            log("signature: %s", signature);

            int file_size = reader.readInt();
            log("file_size: %d", file_size);

            int header_size = reader.readInt();
            log("header_size: %d", header_size);

            int endian_tag = reader.readInt();
            log("endian_tag: %d", endian_tag);

            int link_size = reader.readInt();
            log("link_size: %d", link_size);

            int link_off = reader.readInt();
            log("link_off: %d", link_off);

            int map_off = reader.readInt();
            log("map_off: %d", map_off);

            int string_ids__size = reader.readInt();
            log("string_ids__size: %d", string_ids__size);

            int string_ids_off = reader.readInt();
            log("string_ids_off: %d", string_ids_off);

            int type_ids__size = reader.readInt();
            log("type_ids__size: %d", type_ids__size);

            int type_ids_off = reader.readInt();
            log("type_ids_off: %d", type_ids_off);

            int proto_ids__size = reader.readInt();
            log("proto_ids__size: %d", proto_ids__size);

            int proto_ids_off = reader.readInt();
            log("proto_ids_off: %d", proto_ids_off);

            int field_ids__size = reader.readInt();
            log("field_ids__size: %d", field_ids__size);

            int field_ids_off = reader.readInt();
            log("field_ids_off: %d", field_ids_off);

            int method_ids_size = reader.readInt();
            log("method_ids_size: %d", method_ids_size);

            int method_ids_off = reader.readInt();
            log("method_ids_off: %d", method_ids_off);

            int class_defs_size = reader.readInt();
            log("class_defs_size: %d", class_defs_size);

            int class_defs_off = reader.readInt();
            log("class_defs_off: %d", class_defs_off);

            int data_size = reader.readInt();
            log("data_size: %d", data_size);

            int data_off = reader.readInt();
            log("data_off: %d", data_off);

        } catch (IOException e) {
            e.printStackTrace();
            log("parse dex header error!");
        }
    }
}

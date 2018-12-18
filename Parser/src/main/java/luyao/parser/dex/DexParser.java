package luyao.parser.dex;

import luyao.parser.dex.bean.*;
import luyao.parser.utils.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static luyao.parser.utils.Reader.log;

public class DexParser {

    private Dex dex;
    private Reader reader;
    private List<Integer> dexStringIds = new ArrayList<>();
    private List<Integer> dexTypeIds = new ArrayList<>();
    private List<DexProtoId> dexProtoIds = new ArrayList<>();
    private List<DexFieldId> dexFieldIds = new ArrayList<>();
    private List<DexMethodId> dexMethodIds = new ArrayList<>();
    private List<DexClass> dexClasses = new ArrayList<>();

    public DexParser(InputStream in) {
        reader = new Reader(in, true);
        dex = new Dex();
    }

    public void parse() {
        parseHeader();
        parseDexString();
        parseDexType();
        parseDexProto();
        parseDexField();
        parseDexMethod();
        parseDexClass();
    }

    private void parseHeader() {
        DexHeader dexHeader = new DexHeader(reader);
        dexHeader.parse();
        dex.setDexHeader(dexHeader);
    }

    private void parseDexString() {
        log("\nparse DexString");
        try {
            int stringIdsSize = dex.getDexHeader().string_ids__size;
            for (int i = 0; i < stringIdsSize; i++) {
                int string_data_off = reader.readInt();
                dexStringIds.add(string_data_off);
                log("string[%d] data off: %d", i, string_data_off);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexType() {
        log("\nparse DexType");
        try {
            int typeIdsSize = dex.getDexHeader().type_ids__size;
            for (int i = 0; i < typeIdsSize; i++) {
                int descriptor_idx = reader.readInt();
                dexTypeIds.add(descriptor_idx);
                log("type[%d] descriptor idx: %d", i, descriptor_idx);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexProto() {
        log("\nparse DexProto");
        try {
            int protoIdsSize = dex.getDexHeader().proto_ids__size;
            for (int i = 0; i < protoIdsSize; i++) {
                int shorty_idx = reader.readInt();
                int return_type_idx = reader.readInt();
                int parameters_off = reader.readInt();
                DexProtoId dexProtoId = new DexProtoId(shorty_idx, return_type_idx, parameters_off);
                log("proto[%d]: %s", i, dexProtoId.toString());
                dexProtoIds.add(dexProtoId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexField() {
        log("\nparse DexField");
        try {
            int fieldIdsSize = dex.getDexHeader().field_ids__size;
            for (int i = 0; i < fieldIdsSize; i++) {
                int class_idx = reader.readUnsignedShort();
                int type_idx = reader.readUnsignedShort();
                int name_idx = reader.readInt();
                DexFieldId dexFieldId = new DexFieldId(class_idx, type_idx, name_idx);
                log("field[%d]: %s", i, dexFieldId.toString());
                dexFieldIds.add(dexFieldId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexMethod() {
        log("\nparse DexMethod");
        try {
            int methodIdsSize = dex.getDexHeader().method_ids_size;
            for (int i = 0; i < methodIdsSize; i++) {
                int class_idx = reader.readUnsignedShort();
                int proto_idx = reader.readUnsignedShort();
                int name_idx = reader.readInt();
                DexMethodId dexMethodId = new DexMethodId(class_idx, proto_idx, name_idx);
                log("method[%d]: %s", i, dexMethodId.toString());
                dexMethodIds.add(dexMethodId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexClass() {
        log("\nparse DexClass");
        try {
            int classDefsSize = dex.getDexHeader().class_defs_size;
            for (int i = 0; i < classDefsSize; i++) {
                int class_idx = reader.readInt();
                int access_flags = reader.readInt();
                int superclass_idx = reader.readInt();
                int interfaces_off = reader.readInt();
                int source_file_idx = reader.readInt();
                int annotations_off = reader.readInt();
                int class_data_off = reader.readInt();
                int staticValuesOff = reader.readInt();
                DexClass dexClass = new DexClass(class_idx, access_flags, superclass_idx,
                        interfaces_off, source_file_idx, annotations_off, class_data_off, staticValuesOff);
                log("class[%d]: %s", i, dexClass.toString());
                dexClasses.add(dexClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

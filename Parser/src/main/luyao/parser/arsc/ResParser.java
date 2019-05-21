package luyao.parser.arsc;

import luyao.parser.arsc.bean.*;
import luyao.parser.utils.BytesReader;
import luyao.parser.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static luyao.parser.utils.Reader.log;

/**
 * Created by luyao
 * on 2018/12/20 13:43
 */
public class ResParser {

    private ResArsc resArsc;
    private ResTableHeader resTableHeader;
    private ResStringPoolHeader stringPoolHeader;
    private BytesReader reader;

    private List<String> stringPoolList = new ArrayList<>();
    private List<String> typeStringList = new ArrayList<>();
    private List<String> keyStringList = new ArrayList<>();
    private List<ResTableType> tableTypeList = new ArrayList<>();
    private List<ResTableTypeSpec> tableTypeSpecList = new ArrayList<>();

    public ResParser(File in) {
        this.reader = new BytesReader(Utils.readAll(in));
    }

    public void parse() {
        parseResTableHeader();
        stringPoolHeader = parseStringPoolType(stringPoolList);
        parseTablePackageType();
        parseResTableType();
    }

    private void parseResTableHeader() {

        resArsc = new ResArsc();
        resTableHeader = new ResTableHeader();
        resTableHeader.parse(reader);
        log("ResTableHeader: %s", resTableHeader.toString());
    }

    private ResStringPoolHeader parseStringPoolType(List<String> stringPoolList) {
        int currentPosition = reader.getCurrentPosition();
        ResStringPoolHeader stringPoolHeader = new ResStringPoolHeader();
        try {

            stringPoolHeader.parse(reader);
            List<Integer> stringOffsets = new ArrayList<>(stringPoolHeader.stringCount);
            for (int i = 0; i < stringPoolHeader.stringCount; i++) {
                int offset = reader.readInt();
                stringOffsets.add(offset);
            }

            List<Integer> styleOffsets = new ArrayList<>();
            for (int i = 0; i < stringPoolHeader.styleCount; i++) {
                styleOffsets.add(reader.readInt());
            }

            int position = reader.getCurrentPosition();
            for (int i = 0; i < stringPoolHeader.stringCount; i++) {
                int u16len = reader.read(position + stringOffsets.get(i), 1)[0];
                int u8len = reader.read(position + stringOffsets.get(i), 1)[0];
                int length = 0;
                int skipLength = 0;
                if (stringPoolHeader.flags == ResStringPoolHeader.UTF8_FLAG) {
                    length = u8len;
                    skipLength = 1; // 如果是 utf-8，则字符串以 0x00结尾
                } else {
                    length = u16len;
                    skipLength = 2; // 如果是 utf-16，则字符串以 0x0000结尾
                }
                String string = "";
                try {
                    string = new String(reader.read(position + stringOffsets.get(i) + 2, u8len));
                    reader.skip(skipLength);
                } catch (Exception e) {
                    log("   parse string[%d] error!", i);
                }

                stringPoolList.add(string);
//                log("   stringPool[%d]: %s", i, string);
            }

            for (int i = 0; i < stringPoolHeader.styleCount; i++) {
                int index = reader.readInt();
                int firstChar = reader.readInt();
                int lastChar = reader.readInt();
                ResSpanStyle resSpanStyle = new ResSpanStyle(index, firstChar, lastChar);
                log(resSpanStyle.toString());
                reader.skip(4); // 0xffff
            }
            reader.moveTo(currentPosition + stringPoolHeader.resChunkHeader.size);
            return stringPoolHeader;
        } catch (IOException e) {
            log("   parse string pool type error!");
        }
        return null;
    }

    private void parseTablePackageType() {
//        reader.moveTo(resTableHeader.resChunkHeader.headerSize + stringPoolHeader.resChunkHeader.size);
        try {
            ResTablePackageHeader packageHeader = new ResTablePackageHeader(reader);
            log(packageHeader.toString());

            log("typeStrings: ");
            // ResTableHeader size + String Pool size +  Package header size
            reader.moveTo(resTableHeader.resChunkHeader.headerSize + stringPoolHeader.resChunkHeader.size
                    + packageHeader.typeStrings);
            parseStringPoolType(typeStringList); // 资源类型字符串池

            log("keyStrings: ");
            reader.moveTo(resTableHeader.resChunkHeader.headerSize + stringPoolHeader.resChunkHeader.size
                    + packageHeader.keyStrings);
            parseStringPoolType(keyStringList); // 资源名称字符池
        } catch (IOException e) {
            log("parse table package error!");
        }
    }

    private void parseResTableType() {
        try {
            while (reader.avaliable() > 0) {
                ResChunkHeader resChunkHeader = new ResChunkHeader(reader);
                switch (resChunkHeader.resType) {
                    case ResType.RES_TABLE_TYPE_SPEC_TYPE:
                        ResTableTypeSpec tableTypeSpec = new ResTableTypeSpec(resChunkHeader);
                        tableTypeSpec.parse(reader);
                        tableTypeSpecList.add(tableTypeSpec);
                        log(tableTypeSpec.toString());
                        break;

                    case ResType.RES_TABLE_TYPE_TYPE:
                        ResTableType resTableType = new ResTableType(resChunkHeader);
                        resTableType.parse(reader);
                        tableTypeList.add(resTableType);
                        break;
                }
            }
            printTableType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printTableType() {
        for (ResTableType tableType : tableTypeList) {
            List<ResTableEntry> resTableEntryList = tableType.resTableEntryList;
            for (ResTableEntry tableEntry : resTableEntryList) {
                if (tableEntry.flags == 0) {
                    List<ResTableMap> resTableMapList = ((ResTableMapEntry) tableEntry).resTableMapList;
                    for (ResTableMap resTableMap : resTableMapList) {
                        log("   ResTableEntry: %s ResValue: %s", keyStringList.get(tableEntry.string_pool_index),
                                keyStringList.get(resTableMap.resValue.data));
                    }

                } else if (tableEntry.flags == 1) {
                    log("   ResTableMapEntry: %s", keyStringList.get(tableEntry.string_pool_index));
                }
            }
        }
    }
}

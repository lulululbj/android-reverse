package luyao.parser.arsc.bean;

import luyao.parser.utils.BytesReader;

import java.io.IOException;

/**
 * Created by luyao
 * on 2018/12/24 16:04
 */
public class ResTableMap {

//    struct ResTable_map
//    {
//        ResTable_ref name; // 资源名称
//        Res_value value; // 资源值
//    }

    public int resTableRefName;
    public ResValue resValue;


    public void parse(BytesReader reader) throws IOException {
        this.resTableRefName = reader.readInt();

        resValue = new ResValue();
        resValue.parse(reader);
    }
}

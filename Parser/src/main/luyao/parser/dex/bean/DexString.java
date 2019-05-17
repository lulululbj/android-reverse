package luyao.parser.dex.bean;

/**
 * Created by luyao
 * on 2018/12/19 10:01
 */
public class DexString {

    public int string_data_off; // 字符串的偏移量
    public String string_data; // 字符串的内容

    public DexString(int string_data_off, String string_data) {
        this.string_data_off = string_data_off;
        this.string_data = string_data;
    }

    @Override
    public String toString() {
        return "DexString{" +
                "string_data_off=" + string_data_off +
                ", string_data=" + string_data +
                '}';
    }
}

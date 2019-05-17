package luyao.parser.dex.bean;

/**
 * Created by luyao
 * on 2018/12/18 15:53
 */
public class DexMethodId {

    public int class_idx; // 指向 type_ids
    public int proto_idx; // 指向 proto_ids
    public int name_idx; // 指向 string_ids

    public DexMethodId(int class_idx, int proto_idx, int name_idx) {
        this.class_idx = class_idx;
        this.proto_idx = proto_idx;
        this.name_idx = name_idx;
    }

    @Override
    public String toString() {
        return "DexMethodId{" +
                "class_idx=" + class_idx +
                ", proto_idx=" + proto_idx +
                ", name_idx=" + name_idx +
                '}';
    }
}

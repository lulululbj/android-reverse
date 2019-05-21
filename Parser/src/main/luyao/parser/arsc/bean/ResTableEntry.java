package luyao.parser.arsc.bean;

/**
 * Created by luyao
 * on 2018/12/24 15:40
 */
public class ResTableEntry {

/*
struct ResTable_entry
{
    // Number of bytes in this structure.
    uint16_t size;

    enum {
        // If set, this is a complex entry, holding a set of name/value
        // mappings.  It is followed by an array of ResTable_map structures.
        FLAG_COMPLEX = 0x0001,
        // If set, this resource has been declared public, so libraries
        // are allowed to reference it.
        FLAG_PUBLIC = 0x0002,
        // If set, this is a weak resource and may be overriden by strong
        // resources of the same name/type. This is only useful during
        // linking with other resource tables.
        FLAG_WEAK = 0x0004
    };
    uint16_t flags;

    // Reference into ResTable_package::keyStrings identifying this entry.
    struct ResStringPool_ref key;
};
*/

    public int size; // 该结构体大小
    /*
     * flags 为 0，后面会跟一个 Res_value
     * flags 为 1，后面会跟一个 ResTable_map 数组
     */
    public int flags=0;
    public int string_pool_index; // 资源项名称在资源项名称字符串池中的索引


    public ResTableEntry(int size, int flags, int string_pool_index) {
        this.size = size;
        this.flags = flags;
        this.string_pool_index = string_pool_index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getString_pool_index() {
        return string_pool_index;
    }

    public void setString_pool_index(int string_pool_index) {
        this.string_pool_index = string_pool_index;
    }
}

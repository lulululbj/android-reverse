package luyao.xmlparser.classes.bean;


import luyao.xmlparser.utils.Reader;

/**
 * Created by luyao
 * on 2018/8/27 14:52
 */
abstract class Constant {

    protected int tag;

    public Constant() {
    }

    public Constant(int tag) {
        this.tag = tag;
    }

    public abstract void read(Reader reader);
}

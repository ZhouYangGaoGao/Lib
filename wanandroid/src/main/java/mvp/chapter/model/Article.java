package mvp.chapter.model;

public class Article {
    /**
     * apkLink :
     * audit : 1
     * author : 鸿洋
     * canEdit : false
     * chapterId : 408
     * chapterName : 鸿洋
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 14254
     * link : https://mp.weixin.qq.com/s/psrDADxwl782Fbs_vzxnQg
     * niceDate : 1天前
     * niceShareDate : 16小时前
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1594310400000
     * realSuperChapterId : 407
     * selfVisible : 0
     * shareDate : 1594395237000
     * shareUser :
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/408/1"}]
     * title : Android UI 渲染机制的演进，你需要了解什么？
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */
    private boolean collect;
    private int id;
    private String title;
    private String link;
    private String envelopePic;
    private String desc;
    private String author;
    private String niceShareDate;

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public String getDesc() {
        return desc;
    }

    public String getAuthor() {
        return author;
    }
}

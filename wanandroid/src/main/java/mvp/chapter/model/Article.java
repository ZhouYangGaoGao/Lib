package mvp.chapter.model;

import android.text.TextUtils;

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
        return TextUtils.isEmpty(title) ? name : title;
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


    /**
     * 常用网站
     * icon :
     * id : 16
     * link : https://github.com/android-cn/android-dev-com
     * name : 国外大牛博客集合
     * order : 2
     * visible : 1
     */

    private String name;
    public String getName() {
        return name;
    }

    /**
     * banner
     * desc : 享学~
     * id : 29
     * imagePath : https://wanandroid.com/blogimgs/2087429c-f6ac-43dd-9ffe-8af871b6deb8.png
     * isVisible : 1
     * order : 0
     * title : &ldquo;学不动了，也得学！&rdquo;
     * type : 0
     * url : https://mp.weixin.qq.com/s/PRv6SAZlklz4DRm1EsBdew
     */

    private String imagePath;
    private String url;
    public String getImagePath() {
        return imagePath;
    }
    public String getUrl() {
        return url;
    }


    /**
     *
     * HotKey
     * id : 9
     * link :
     * name : Studio3
     * order : 1
     * visible : 1
     */

}

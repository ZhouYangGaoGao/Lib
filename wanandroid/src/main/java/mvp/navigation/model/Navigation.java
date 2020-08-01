package mvp.navigation.model;

import java.util.List;

import mvp.chapter.model.Article;

public class Navigation {
    private String name;
    private String cid;

    private List<Article> articles;

    public String getName() {
        return name;
    }

    public String getCid() {
        return cid;
    }

    public List<Article> getArticles() {
        return articles;
    }

}

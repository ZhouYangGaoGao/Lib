package bean;

/**
 * 卡片配置信息
 */
public class Card {
    public int cardRadius = 10;//卡片圆角
    public int cardElevation = 7;//卡片阴影
    public int cardColor = 0xffffffff;//卡片背景色 正常
    public int cardColorPress = 0x11000000;//卡片背景色 按压
    public int rippleColor = 0x33000000;//item水波纹颜色
    public boolean card = true;//是否是卡片形式
    public boolean needHeardSpace = true;//需要头部间隙
    public boolean needFootSpace = true;//需要尾部间隙
}

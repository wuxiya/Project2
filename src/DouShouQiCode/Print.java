package DouShouQiCode;

/**
 * 专门打印各种情况
 * 帮助，地图，错误
 * Created by CiCi on 2016/11/26.
 */
public class Print {

    public Print() {

    }

    /**
     * printMistake()用于打印各种犯规情况
     * move的返回值传入此方法中，不同的代号代表不同的犯规类型
     *
     * @param daiHao
     */
    public static String printMistake(int daiHao) {
        switch (daiHao)

        {
            case 01:
                return "亲，上下左右是wsad，你搞不清楚左右吗？";

            case 02:
                return "越界了！何弃疗啊亲";

            case 03:
                return "喂！自己人";

            case 04:
                return "不要回家哟";

            case 06:
                return "别跳河！年轻人，有什么想不开的？";

            case 07:
                return "拳头没有人家大——快跑吧";

            case 12:
                return "老鼠都跳水了，怎么能吃大象呢";

            case 16:
                return "河中有对方老鼠，狮虎不能跳河";

            case 18:
                return "大象不能吃老鼠";
        }
        return " ";
    }
}

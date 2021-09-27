import java.util.Arrays;
import java.util.Scanner;

/**
 * Project name(项目名称)：数字转人民币读法
 * Package(包名): PACKAGE_NAME
 * Author(作者）: mao
 * Author QQ：1296193245
 * Date(创建日期)： 2021/9/27
 * Time(创建时间)： 19:16
 * Version(版本): 1.0
 * Description(描述)：
 * 实现这个方法的思路是，首先把这个浮点数分成整数部分和小数部分。提取整数部分很容易，直接将这个浮点数强制类型转换成一个整数即可，
 * 这个整数就是浮点数的整数部分。再使用浮点数减去整数将可以得到这个浮点数的小数部分。
 * 然后分开处理整数部分和小数部分，其中小数部分的处理比较简单，直接截断到保留 2 位数字，转换成几角几分的字符串。
 * 整数部分的处理则稍微复杂一点，但只要认真分析不难发现，中国的数字习惯是 4 位一节的，
 * 一个 4 位的数字可被转成几千几百几十几，至于后面添加什么单位则不确定：
 * 如果这节 4 位数字出现在 1~4 位，则后面添加单位元。
 * 如果这节 4 位数字出现在 5~8 位，则后面添加单位万。
 * 如果这节 4 位数字出现在 9~12 位，则后面添加单位亿。
 */

public class test
{
    private String[] hanArr = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private String[] unitArr = {"十", "百", "千"};
    private String[] unitArr2 = {"分", "角"};

    /**
     * 把一个浮点数分解成整数部分和小数部分字符串
     *
     * @param num 需要被分解的浮点数
     * @return 分解出来的整数部分和小数部分。第一个数组元素是整数部分，笫二个数组元素是小数部分
     */
    private String[] divide(double num)
    {
        long zheng = (long) num;
        long xiao = Math.round((num - zheng) * 100);
        return new String[]{zheng + "", String.valueOf(xiao)};
    }

    /**
     * 将数字转成人民币读法
     *
     * @param str[] 被分解成整数部分和小数部分的字符串数组
     * @return 人民币读法的汉字字符串
     */
    private String toHanStr(String[] str)
    {
        int zhengLen = str[0].trim().split("").length; // 整数的长度
        int xiaoLen = str[1].split("").length; // 小数的长度
        String result = "";
        // 依次遍历整数的每一位数字
        for (int i = 0; i < zhengLen; i++)
        {
            // 把char型数字转换成int型数字，3 种方法如下：
            // 方法1：因为它们的ASCII码值恰好相差48，因此把char型数字减去48得到int型数字，例如‘4’被转换成4
            // int num = str[0].charAt(i) - 48;
            // 方法2：先将char型数字转换为String字符串，再用Integer（int的包装类，提供了String转换为int的方法，后面教程中我们会详细讲解）转换为int型数字
            // int num = Integer.parseInt(String.valueOf(str[0].charAt(i)));
            // 方法3：char型数字减去字符0来转换为int型数字
            int num = str[0].charAt(i) - '0';
            // 如果不是最后一位数字，而且数字不是零，则需要添加单位（千、百、十）
            if (i != zhengLen - 1 && num != 0)
            {
                result += hanArr[num] + unitArr[zhengLen - 2 - i];
            }
            else
            {
                // 否则添加“元”
                result += hanArr[num] + "元";
            }
        }
        // 依次遍历小数的每一位数字
        for (int i = 0; i < xiaoLen; i++)
        {
            int num = str[1].charAt(i) - '0';
            // 如果数字不是零，则需要添加单位（角，分）
            if (num != 0)
            {
                result += hanArr[num] + unitArr2[xiaoLen - 1 - i];
            }
        }
        return result;
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        test nr = new test();
        System.out.println("请输入一个浮点数：");
        String num = sc.next();
        String[] strs = nr.divide(Double.parseDouble(num));
        System.out.println("分解成整数部分和小数部分结果为：" + Arrays.toString(strs));
        System.out.println("人民币读法为：" + nr.toHanStr(strs));
    }
}

package com.jvmlearn.lamdba;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * lamdba 表达式
 */
public class LamdbaTest {

    // map():将流中的元素进行再次加工形成一个新流，流中的每一个元素映射为另外的元素。
    // filter(): 返回结果生成新的流中只包含满足筛选条件的数据
    // limit()：返回指定数量的元素的流。返回的是Stream 里前面的n 个元素。
    // skip()：和limit()相反，将前几个元素跳过（取出）再返回一个流，如果流中的元素小于或者等于n，就会返回一个空的流。
    // sorted()：将流中的元素按照自然排序方式进行排序。
    // distinct()：将流中的元素去重之后输出。
    // peek()：对流中每个元素执行操作，并返回一个新的流，返回的流还是包含原来流中的元素。
    List<Transaction> mTransactionList = null;

    @Before
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        mTransactionList = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
    @Test
    public void test01() {

        List<Transaction> wTransactionListNew = null;
        wTransactionListNew = mTransactionList.stream().filter(transaction -> transaction.getYear()==2011)
        .sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());

        wTransactionListNew.stream().forEach(System.out :: println);
    }


    //2. 交易员都在哪些不同的城市工作过？
    @Test
    public void test2() {

        List<String> wTransactionListNew = null;
        wTransactionListNew = mTransactionList.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct().collect(Collectors.toList());

        wTransactionListNew.stream().forEach(System.out :: println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test3() {

        List<Transaction> wTransactionListNew = null;
        wTransactionListNew = mTransactionList.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(transaction -> transaction.getTrader().getName())).collect(Collectors.toList());

        wTransactionListNew.stream().forEach(System.out :: println);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test4() {


        List<String> wTransactionListNew = null;
        wTransactionListNew = mTransactionList.stream().map(transaction -> transaction.getTrader().getName())
                .distinct().collect(Collectors.toList());

        wTransactionListNew.stream().forEach(System.out :: println);

    }

    //5. 有没有交易员是在米兰工作的？
    @Test
    public void test5() {

        boolean wTransactionListNew = mTransactionList.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(wTransactionListNew);
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6() {

        int wTransactionListNew = mTransactionList.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .mapToInt(transaction -> transaction.getValue()).sum();
        System.out.println(wTransactionListNew);
    }

    //7. 所有交易中，最高的交易额是多少
    @Test
    public void test7() {
        int wTransactionListNew = mTransactionList.stream().mapToInt(transaction -> transaction.getValue()).max().getAsInt();
        System.out.println(wTransactionListNew);
    }

    //8. 找到交易额最小的交易
    @Test
    public void test8() {

        List<Transaction> wTransactionListNew = null;
        wTransactionListNew = mTransactionList.stream().filter(transaction -> transaction.getValue() ==  (mTransactionList.stream().mapToInt(transaction2 -> transaction2.getValue()).min().getAsInt()))
                .collect(Collectors.toList());
        wTransactionListNew.forEach(System.out :: println);
    }
}

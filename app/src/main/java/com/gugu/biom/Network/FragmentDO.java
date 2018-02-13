package com.gugu.biom.Network;

/**
 * Created by gugu on 2018-02-12.
 */

public class FragmentDO {

    private int tFirst;
    private int tSecond;
    private int tThird;
    private int tFourth;
    private int tFifth;
    private int tSixth;

    private FragmentDO() {

    }

    private static class ForInstance {
        private static final FragmentDO instance = new FragmentDO();
    }

    public static FragmentDO getInstance() {
        return ForInstance.instance;
    }

    public int gettFirst() {
        return tFirst;
    }

    public void settFirst(int tFirst) {
        this.tFirst = tFirst;
    }

    public int gettSecond() {
        return tSecond;
    }

    public void settSecond(int tSecond) {
        this.tSecond = tSecond;
    }

    public int gettThird() {
        return tThird;
    }

    public void settThird(int tThird) {
        this.tThird = tThird;
    }

    public int gettFourth() {
        return tFourth;
    }

    public void settFourth(int tFourth) {
        this.tFourth = tFourth;
    }

    public int gettFifth() {
        return tFifth;
    }

    public void settFifth(int tFifth) {
        this.tFifth = tFifth;
    }

    public int gettSixth() {
        return tSixth;
    }

    public void settSixth(int tSixth) {
        this.tSixth = tSixth;
    }
}

package org.example;

import java.util.List;

public class TypeWithMembers {

    private int x = 10;

    public TypeWithMembers(int n) {
        x = n;
    }

    public void test(int x, long y, Character z) {

    }


    public <T> int method2(List<? super T> p1) {
        return 0;
    }

}

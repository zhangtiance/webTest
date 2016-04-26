package ztc.webTest.commonTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSortTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("3");
		list.add("2");
		list.add("4");
		list.add("5");
		Collections.sort(list);
		for (String str : list) {
			System.out.println(str);
		}
	}
}

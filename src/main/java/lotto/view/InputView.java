package lotto.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputView() {
    }

    public static String getMoneyToPurchase() {
        System.out.println("구입 금액을 입력해주세요.");
        return SCANNER.nextLine();
    }
}

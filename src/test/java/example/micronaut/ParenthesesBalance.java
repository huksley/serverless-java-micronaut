package example.micronaut;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class ParenthesesBalance {
    static Logger log = LoggerFactory.getLogger(ParenthesesBalance.class);

    public static boolean checkParentheses(String s) {
        String left = "{[(";
        String right = "}])";
        Stack<Character> p = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (left.indexOf(ch) >= 0) {
                p.push(ch);
            } else
            if (right.indexOf(ch) >= 0) {
                if (p.empty()) {
                    return false;
                }
                Character prev = p.pop();
                if (left.indexOf(prev) != right.indexOf(ch)) {
                    return false;
                }
            }
        }
        return p.empty();
    }

    @Test
    public void testIt() {
        Assert.assertEquals(checkParentheses("{}()"), true);
        Assert.assertEquals(checkParentheses("({()})"), true);
        Assert.assertEquals(checkParentheses("{}("), false);
        Assert.assertEquals(checkParentheses("([]{}[]())"), true);
        Assert.assertEquals(checkParentheses("){}("), false);

    }

}

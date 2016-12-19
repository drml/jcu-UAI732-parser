package com.doktor.tests;

import com.doktor.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Kleene algebra regular expression validator test.
 *
 * Created by Ondřej Doktor on 5.12.2016.
 */

@RunWith(value = Parameterized.class)
public class ValidatorTest {

    private Validator validator;
    private String input;
    private boolean expected;


    public ValidatorTest(String input, boolean expected)
    {
        this.input = input;
        this.expected = expected;
        validator = new Validator(true);
    }

    @Test
    public void test_testValidate() throws Exception
    {
        if (expected) {
            assertTrue(validator.validate(input));
        } else {
            assertFalse(validator.validate(input));
        }

    }

    @Parameterized.Parameters(name = "{index}: testValidate({0}) = {1}")
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{

                // positive tests
                {"a", true},
                {"abc", true},
                {"a(bc)", true},
                {"((a)bc)", true},
                {"((bc))", true},
                {"(((b)c))", true},
                {"a+b", true},
                {"(a+b)", true},
                {"(ace+b)", true},
                {"(a(s+f)ce+b)", true},
                {"(a+b)*", true},
                {"(a*+b)*", true},
                {"(a*+b*)*", true},
                {"a*bc*", true},
                {"a*(bc)*", true},
                {"a*(bc)", true},
                {"(a)*(bc)", true},
                {"(aa + ab(bb)*ba)*(b + ab(bb)*a) (a(bb)*a + (b + a(bb)*ba)(aa + ab(bb)*ba)*(b + ab(bb)*a))*", true},

                // negative tests
                {"", false},
                {"()", false},
                {"(", false},
                {")", false},
                {"*", false},
                {"(*", false},
                {"(+*", false},
                {"+*", false},
                {"a+*", false},
                {"(a)bc)", false},
                {"((()c))", false},
        });
    }


}
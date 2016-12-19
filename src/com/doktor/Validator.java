package com.doktor;

import java.util.Stack;

/**
 * This class serves as a validator of Kleene algebra regular expressions.
 * https://en.wikipedia.org/wiki/Kleene_algebra#Definition
 *
 * The validator utilizes the principles of a deterministic pushdown automaton (DPDA)
 * Used formal grammar is LL(1) compliaint:
 *
 * S -> E#
 * E -> PH
 * H -> +E | E | lambda
 * P -> (EZHC | RC
 * Z -> )
 * C -> h | lambda
 * R -> LN
 * N -> R | lambda
 * L -> a|b|c|.... anything except ()+*#
 *
 * Created by Ondřej Doktor on 5.12.2016.
 */
public final class Validator {

    /**
     * Temporaty stack for input string.
     * Not to be confused with the automaton stack!
     */
    private Stack<Character> inputStack;

    /**
     * List of special characters
     */
    private static final String specialChars = "()+*#";     // # is used as EOL

    /**
     * Debug flag
     */
    private boolean debug;

    public Validator()
    {
        this.debug = false;
    }

    /**
     * Kleene algebra regular expressions validator
     *
     * @param debug When TRUE, syntactic error details will go to the standard error output
     */
    public Validator(boolean debug)
    {
        this.debug = debug;
    }

    /**
     * Validates given input against the Kleene algebra syntax.
     * Syntax errors are sent to the standard error output
     *
     * @param input expression to validate
     * @return result
     * @see System
     */
    public boolean validate(String input)
    {

        // prepare input (crude lexial analysis)
        inputStack = new Stack<>();
        inputStack.push('#');
        for (int x = input.length() - 1; x >= 0; x--) {

            if (Character.isSpaceChar(input.charAt(x))) {        // ommiting whitespace
                continue;
            }

            inputStack.push(input.charAt(x));
        }

        // begin analysis
        try {
            S();

            if (!inputStack.empty()) {
                throw new InvalidSyntaxException("Stack not empty");
            }

            return true;

        } catch (InvalidSyntaxException e) {

            if (debug){
                System.err.println("InvalidSyntaxException parsing '" + input + "' (char " + (input.length() - inputStack.size() + 1) + "): " + e.getMessage());
            }

            return false;
        }
    }

    /**
     * Proxy for PEEK operation.
     *
     * @return
     */
    private char peek()
    {
        return inputStack.peek();
    }

    /**
     * Proxy for POP operation.
     *
     * @return
     */
    private char pop()
    {
        return inputStack.pop();
    }

    // Syntax analysis

    private void S() throws InvalidSyntaxException
    {
        E();
        if (pop() != '#') {
            throw new InvalidSyntaxException("parsing error (unexpected end of file)");
        }
    }

    private void E() throws InvalidSyntaxException
    {
        P();
        H();
    }

    private void P() throws InvalidSyntaxException
    {
        char next = peek();
        if (next == '(') {
            pop();
            E();
            Z();
            H();
            C();
            return;
        } else {
            R();
            C();
        }

    }

    private void H() throws InvalidSyntaxException
    {
        char next = peek();
        if (next == '*' || next == ')' || next == '#') {
            return;
        } else if (next == '+') {
            pop();
            E();
        } else {
            E();
        }
    }

    private void R() throws InvalidSyntaxException
    {
        L();
        N();
    }

    private void N() throws InvalidSyntaxException
    {
        char next = peek();
        if (next == '+' || next == '*' || next == '#' || next == ')') {
            return;
        } else {
            H();
        }
    }

    private void Z() throws InvalidSyntaxException
    {
        char next = peek();
        if (next == ')') {
            pop();
            return;
        }

        throw new InvalidSyntaxException("expecting ')', but '" + next + "' given");
    }

    private void C() throws InvalidSyntaxException
    {
        char next = peek();
        if (next == '*') {
            pop();
            return;
        }
        if (next == '+' || next == ')' || next == '#') {
            return;
        }

        throw new InvalidSyntaxException("expecting '*' or '+' or ')' or '#', '" + next + "' given");
    }


    private void L() throws InvalidSyntaxException
    {
        char next = peek();
        if (specialChars.indexOf(next) == -1) {       // if next is NOT a special char
            pop();
            return;
        }

        throw new InvalidSyntaxException("expecting literal, but '" + next + "' given");
    }


}

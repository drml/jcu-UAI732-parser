package com.doktor;

/**
 * ZADÁNÍ:
 *
 * Sestrojte gramatiku pro regulární výraz (dle přednášek)
 *
 * Gramatiku upravte tak, aby byla LL1 (tj. odstraníte konflikty first-first a first-follow,
 * aby bylo možné jednoznačně určit, které pravidlo se vybere.
 *
 * Metodou rekurzivního sestupu naprogramujte parser, který ověří, zda daný výraz je syntakticky správně a který ne.
 * Program bude akceptovat vstupní řetěz jako parametr příkazu a výsledek A/N vytiskne na standardní výstup.
 * Program kromě A/N nebude nic tisknout a nebude mít žádné uživatelské rozhraní. Bude to program pro příkazovou řádku.
 */

/**
 * Main class
 *
 * Created by Ondřej Doktor on 5.12.2016.
 */
public class Main {

    public static void main(String[] args)
    {
        Validator validator = new Validator();

        if (args.length < 1) {
            return;
        }

        if (validator.validate(args[0])) {
            System.out.println("A");
        } else {
            System.out.println("N");
        }
    }
}

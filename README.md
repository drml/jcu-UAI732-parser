# Řešení úkolu kurzu UAI 732 Teoretická informatika - parser regulárních výrazů

## Zadání

Sestrojte gramatiku pro regulární výraz (dle přednášek)

Gramatiku upravte tak, aby byla LL1 (tj. odstraníte konflikty first-first a first-follow,
aby bylo možné jednoznačně určit, které pravidlo se vybere.

Metodou rekurzivního sestupu naprogramujte parser, který ověří, zda daný výraz je syntakticky správně a který ne.
Program bude akceptovat vstupní řetěz jako parametr příkazu a výsledek A/N vytiskne na standardní výstup.
Program kromě A/N nebude nic tisknout a nebude mít žádné uživatelské rozhraní. Bude to program pro příkazovou řádku.

## Gramatika

```sh
 S -> E#
 E -> PH
 H -> +E | E | lambda
 P -> (EZHC | RC
 Z -> )
 C -> h | lambda
 R -> LN
 N -> R | lambda
 L -> a|b|c|.... anything except ()+*#
```

## Další informace

https://en.wikipedia.org/wiki/Kleene_algebra#Definition

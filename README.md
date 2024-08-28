## Végleges wiki

Last edited by Gerzsenyi Patrik Alexander 3 months ago

# BomberGuy

## A feladat

A játék egy 2 dimenziós pályán játszódik, amely négyzet alakú mezőkből áll. A játékot 2 vagy 3 játékos játsza, akiknek egy-egy bomberman figurát irányítva céljuk,
hogy egyedüliként maradjanak életben. A játékpálya mezőin fal elemek, dobozok, szörnyek és maguk a játékosok figurái helyezkednek el. A játékosok
bombákat lehelyezve felrobbanthatják a dobozokat, szörnyeket és a játékosokat (akár saját magukat is). Egy játékos veszít, ha
felrobban, vagy ha egy szörny elkapja.

## Fejlesztői eszközök

A játék megvalósításához a java nyelvet választottuk, hozzá pedig a LibGDX keretrendszert.

## A játék menete

A játék egy új ablakban nyílik meg, melyben elindul a menü. Itt 3 opciónk van, elindítani egy játékot, módosítani az irányítást a beállításokban és kilépni.

## A játék indítása

Mielőtt tényleg játszhatnánk, ki kell választanunk, hogy hány játékossal szeretnénk megtenni azt, melyik pályán, és hány kört szeretnénk játszani.

## A játékról

Miután beállítottuk, hogy hogyan szeretnénk játszani, egy játékkör elindul a kiválasztott beállításoknak megfelelően.

Itt a játékosok a saját beállításaiknak megfelelően mozgathatják karaktereiket, bombákat helyezhetnek le, ládákat, szörnyeket és más játékosokat
robbanthatnak fel.

### Bombák

A játékosok bombákat helyezhetnek le a pályára, melyek pár másodpercen belül felrobbanak.

Ezek a robbanások nem haladnak át a falakon, azonban a szörnyeket és a pajzs nélküli játékosokat megsemmisíti. Továbbá a ládákat is elpusztíthatja,
melyek Erősítéseket tartalmazhatnak

### Erősítések

Nem minden felrobbantott láda tartalmazhat erősítést, és amelyik tartalmaz, az ezek közül sorsol egyet:

```
Bombák száma + 1 Robbanás hatótáva + 1 Szellem Sérthetetlenség Görkorcsolya Detonátor Akadály
```
## A játék vége

A játéknak akkor van vége, ha csak egy játékos maradt életben, ekkor az a játékos a győztes. Azonban ha a túlélő játékos bizonyos időn belül meghal,
akkor a játék kimenetele döntetlen. A körök után a játék kiírja, hogy az adott köröket melyik játékos nyerte, és ha nem az utolsó kört játsszuk, akkor
elindíthatjuk a következőt.



## UML



# Bomberguy

A játék egy 2 dimenziós pályán játszódik, amely négyzet alakú mezőkből áll. A játékot 2 vagy 3 játékos játsza, akiknek egy-egy bomberman figurát irányítva céljuk,
hogy egyedüliként maradjanak életben. A játékpálya mezőin fal elemek, dobozok, szörnyek és maguk a játékosok figurái helyezkednek el. A játékosok
bombákat lehelyezve felrobbanthatják a dobozokat, szörnyeket és a játékosokat (akár saját magukat is). Egy játékos veszít, ha
felrobban, vagy ha egy szörny elkapja.

## Fejlesztői eszközök

A játék megvalósításához a java nyelvet választottuk, hozzá pedig a LibGDX keretrendszert.

## A játék menete

![menu](https://github.com/user-attachments/assets/9c477533-2a17-411d-a3f5-d93b0e98919d)

A játék egy új ablakban nyílik meg, melyben elindul a menü. Itt 3 opciónk van, elindítani egy játékot, módosítani az irányítást a beállításokban és kilépni.

![settings](https://github.com/user-attachments/assets/842d4aef-b8e6-40ef-8b84-8a923edd012a)
![setKey](https://github.com/user-attachments/assets/5346e821-5841-45ad-a73c-593e87a26d9c)

## A játék indítása

Mielőtt tényleg játszhatnánk, ki kell választanunk, hogy hány játékossal szeretnénk megtenni azt, melyik pályán, és hány kört szeretnénk játszani.

![playernum](https://github.com/user-attachments/assets/c0e74bbd-62f1-492c-8ce5-22f2ee6d7445)
![mapchooser](https://github.com/user-attachments/assets/940a97af-3d76-481d-9de9-e97e3da4e1a1)
![roundchooser](https://github.com/user-attachments/assets/2da08a79-da41-40b6-80c6-1c20059e1c8e)

## A játékról

Miután beállítottuk, hogy hogyan szeretnénk játszani, egy játékkör elindul a kiválasztott beállításoknak megfelelően.

![initial_game](https://github.com/user-attachments/assets/7f3b59ac-eba8-4068-a3c0-8b83bc821c28)

Itt a játékosok a saját beállításaiknak megfelelően mozgathatják karaktereiket, bombákat helyezhetnek le, ládákat, szörnyeket és más játékosokat
robbanthatnak fel.

### Bombák

A játékosok bombákat helyezhetnek le a pályára, melyek pár másodpercen belül felrobbanak.

![bomb](https://github.com/user-attachments/assets/aa003fa6-a908-4f4d-9397-0fd02dfdc165)
![explosion](https://github.com/user-attachments/assets/b59b7259-5c37-4b21-80a6-9193d06296c5)

Ezek a robbanások nem haladnak át a falakon, azonban a szörnyeket és a pajzs nélküli játékosokat megsemmisíti. Továbbá a ládákat is elpusztíthatja,
melyek Erősítéseket tartalmazhatnak

### Erősítések

Nem minden felrobbantott láda tartalmazhat erősítést, és amelyik tartalmaz, az ezek közül sorsol egyet:

<ul>
  <li>Lerakható bombák számának növelése</li>
  <li>Robbanás hatótávjának növelése</li>
  <li>Szellem, a játékos áthaladhat az akadályokon és az ellenfeleken</li>
  <li>Sérthetetlenség</li>
  <li>Görkorcsolya, gyorsított csúszós mozgás</li>
  <li>Detonátor, a bombát gomb nyomásra robbanthatja</li>
  <li>Builder, a játékos akadályokat tehet le a pályára</li>
</ul>

## A játék vége

A játéknak akkor van vége, ha csak egy játékos maradt életben, ekkor az a játékos a győztes. Azonban ha a túlélő játékos bizonyos időn belül meghal,
akkor a játék kimenetele döntetlen. A körök után a játék kiírja, hogy az adott köröket melyik játékos nyerte, és ha nem az utolsó kört játsszuk, akkor
elindíthatjuk a következőt.

![round_over](https://github.com/user-attachments/assets/0d59f899-dce2-4ceb-b058-9cd2fee3978a)
![gameover](https://github.com/user-attachments/assets/f1a7cc6e-4143-47de-8590-3e618d083a59)

## UML

![image (1)](https://github.com/user-attachments/assets/f836796b-a2c7-4a48-97db-2ad73d6ae5aa)

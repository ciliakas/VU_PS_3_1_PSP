# VU_PS_3_1_PSP

---

Author: Arnas Rimkus

Group: 4

Initial Commit

Užduoties tikslas - sukurti vartotojo kūrimo modulį.

Gautas objektas turi būti validuotas ir jei nekorektiškas turi grąžinti klaidos kodą. Jei objektas korektiškas, tada
reikia įgyvendinti paprastą objekto išsaugojimą. Tai gali būti tekstinis failas arba lengva DB (H2, Derby ar SQLite).
Jei objektas sukurtas korektiškai, tai grąžinamas visas objektas atgal su userID.

Modulis turėtų palaikyti ištraukimą, ištrinimą ir redagavimą pagal userID.

Vartotojas - objektas, kuris paduodamas jūsų komponentui iš Frontendo, bet šito nėra būtina modeliuoti.

Objekto laukai:

    Vardas
    Pavardė
    Telefono numeris
    Email
    Adresas
    Slaptažodis

Šitai užduočiai jums reikės pasinaudoti kito studento sukurta validavimo biblioteka. Ją keisti yra kategoriškai
draudžiama. Jums reikia naudotis ją tokia, kokia ji yra ir prisitaikyti savo programą ir jei reikia pasinaudoti
šablonais.

Stiliaus taškai - jei jūsų įgyvendinime bus sumažinta priklausomybė nuo bibliotekos API ir bus lengva pakeisti vieną
biblioteką kitą nekeičiant per daug kodo.

Būtina parašyti modulio unit testus, kurie tikrina modulio kokybę.
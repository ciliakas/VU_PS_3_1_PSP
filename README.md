# VU_PS_3_1_PSP

---
Author: Arnas Rimkus

Group: 4 

Test notes:

I'm more of a TS and C# developer, tried my best but my Java code might look weird or not match conventions.

Test naming convention used: https://enterprisecraftsmanship.com/posts/you-naming-tests-wrong/

---

Test Author: Jaroslav Kochanovskis

Group: 4 

Test feedback - the tests were clear and easy to use, however they were not extensive enough in some cases so I added more.

---

**Implementation notes:**


**Email validator:**

Syntax rules implemented:

The local part can have these ASCII characters:
    *lowercase Latin letters: abcdefghijklmnopqrstuvwxyz,
    *uppercase Latin letters: ABCDEFGHIJKLMNOPQRSTUVWXYZ,
    *digits: 0123456789,
    *special characters: !#$%&'*+-/=?^_`{|}~,
    *dot: . (not first or last character or repeated unless quoted),
    *~~space punctuations such as: "(),:;<>@[\] (with some restrictions),~~
    *~~comments: () (are allowed within parentheses, e.g. (comment)john.smith@example.com).~~

Domain part:
    *lowercase Latin letters: abcdefghijklmnopqrstuvwxyz,
    *uppercase Latin letters: ABCDEFGHIJKLMNOPQRSTUVWXYZ,
    *digits: 0123456789,
    *hyphen: - (not first or last character),
    *~~can contain IP address surrounded by square brackets: jsmith@[192.168.2.1] or jsmith@[IPv6:2001:db8::1].~~
    
Also checking if TLD is valid using a master list of tlds.


**Phone validator:**

* Checks against all added and default rules when validating, if one passes then returns true.

* When adding new rules, if the rule is invalid (is a duplicate rule or has length < 1 or prefix doesn't contain only digits or a plus at the start)

* Length checking logic: number length should be equal to prefix length plus required length (e.g. prefix="+370", length = 8, then total length should be 4 + 8 = 12)


**Password validator:**

* Throws error if not provided with a non empty/not null list of special symbols to check for.

* Throws error if length requirement is < 1

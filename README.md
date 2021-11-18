# VU_PS_3_1_PSP

---
Author: Arnas Rimkus

Group: 4 

---

Library Use Part.

Used library implementation by: Deimantas Eidukevičius, 2 grupė

I wasn't sure what kind of program exactly we were supposed to write, so I wrote a spring boot rest api.

Wrote unit tests for the user controller and user handler, to make sure those parts worked as intended.

Wrapped the library in used for easier replacement and to fix it's issues. Library implementation had some limitations - phone validator only works with phone numbers that start with a '+', also had fixed length so no way to validate non Lithuanian phone numbers without a lot of work. Email validator didn't properly validate the domains of emails - for example, this "email@." would pass a valid email address.

---

Test Author: Jaroslav Kochanovskis

Group: 4 

Test feedback - the tests were clear and easy to use, however they were not extensive enough in some cases so I added more.

---

**Implementation notes:**


**Email validator:**

Syntax rules implemented:

The local part can have these ASCII characters:

* lowercase Latin letters: abcdefghijklmnopqrstuvwxyz
* uppercase Latin letters: ABCDEFGHIJKLMNOPQRSTUVWXYZ
* digits: 0123456789
* special characters: !#$%&'*+-/=?^_`{|}~
* dot: . (not first or last character or repeated unless quoted)
* ~~space punctuations such as: "(),:;<>@[\] (with some restrictions)~~
* ~~comments: () (are allowed within parentheses, e.g. (comment)john.smith@example.com)~~

Domain part:

* lowercase Latin letters: abcdefghijklmnopqrstuvwxyz
* uppercase Latin letters: ABCDEFGHIJKLMNOPQRSTUVWXYZ
* digits: 0123456789
* hyphen: - (not first or last character)
* ~~can contain IP address surrounded by square brackets: jsmith@[192.168.2.1] or jsmith@[IPv6:2001:db8::1]~~

Also checking if TLD is valid using a master list of tlds.


**Phone validator:**

* Checks against all added and default rules when validating, if one passes then returns true.
* When adding new rules, if the rule is invalid (is a duplicate rule or has length < 1 or prefix doesn't contain only digits or a plus at the start)
* Length checking logic: number length should be equal to prefix length plus required length (e.g. prefix="+370", length = 8, then total length should be 4 + 8 = 12)


**Password validator:**

* Throws error if not provided with a non empty/not null list of special symbols to check for.
* Throws error if length requirement is < 1

---

Test notes:

I'm more of a TS and C# developer, tried my best but my Java code might look weird or not match conventions.

Test naming convention used: https://enterprisecraftsmanship.com/posts/you-naming-tests-wrong/

---

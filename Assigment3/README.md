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


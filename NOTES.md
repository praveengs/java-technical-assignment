# Notes

Please add here any notes, assumptions and design decisions that might help up understand your though process.

# TODO
There should be a better way to initialise the rules, rather than passing a list of discount rules into the 
basket constructor. But for now, this is convenient.

The buy one get one, and buy two for 1 pound scenarios can be improved, by adding parameters like n and m for 
buy-n-get-m-free and and buy-n-for-m-pound. This will enable the same class to be reused for future discount 
rules as well. But adhering to the test use case for simplicity now.
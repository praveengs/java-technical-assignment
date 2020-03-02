# Notes

Please add here any notes, assumptions and design decisions that might help up understand your though process.

Any product has a product id and a group name.
Any item is obliged to support the contract of providing the product and its price information.
The above two combined provides the discount calculator the ability to compute discounts based on the product 
information of an item.

# TODO
At the moment, any new discount computation on a new product information will result in the modification of 
the product class. This doesn't go well with the closed for modification, open for extension principle. So an
idea will be to use a model like ProductInfo to encapsulate the product information. New attributes can be then
added independently without having to modify the structure of the code.

There should be a better way to initialise the rules, rather than passing a list of discount rules into the 
basket constructor. But for now, this is convenient.

The buy one get one, and buy two for 1 pound scenarios can be improved, by adding parameters like n and m for 
buy-n-get-m-free and and buy-n-for-m-pound. This will enable the same class to be reused for future discount 
rules as well. But adhering to the test use case for simplicity now.
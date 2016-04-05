# Sort-Hash-Join

DATABASE SYSTEMS (CSE 441)

● Sort-Merge join
        1. Create sorted sublists for R and S, each of size M buffers.
        2. Use 1 block for each sublist and get min. of R & S. Join this minimum 
           Y value with other table and return. Check for B(R)+B(S)<M
● Hash-Join
        1. Create M-1 hashed sublists for R and S
        2. For each Ri and Si thus created, load the portion of smaller of the two 
           in the main memory and create a search structure over it. You can use M-1 
           buffers to achieve this. Then recursively load the other file in the remaining buffer
           and for each record of this file, search corresponding records (with same join 
           attribute value) from the other file.

● Join condition 
    (R.Y==S.Y).
    Use 1 buffer for output which is filled by row returned by getnext() and when it
    gets full, append it to output file and continue.
● Input Parameters:
    You will be given as an input the files containing relations R and S and the value of M blocks.
● Attribute Type:
    Note that all attributes, X, Y and Z are strings and Y may be a non-key attribute.
● Block size: 
    Assume that each block can store 100 tuples for both relations, R and S.
● Input Format: 
    ./a.out <path of R file> <path of S file> <sort/hash> <M>
Output:
●    Output File: 
  <R filename>_<S filename>_join (Kindly note it should contain only R & S filename
  and not their path).

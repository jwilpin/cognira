## Word Count

Count the occurrences of unique words in a text document.
**Input**: text document containing one or more paragraphs.
**Output**: list of unique words and count of occurrences, sorted by greatest count.

### running

    sbt runMain WordCountApp <file name>

By default, the text document located in *src/main/resources/word_count.txt* has the following content:

> Alpha zeta gamma alpha delta zeta alpha.

The expected list of output:

> alpha 3   
> zeta 2   
> gamma 1  
> delta 1

    sbt "runMain WordCountApp"
    [info] running WordCountApp 
    alpha 3
    zeta 2
    delta 1
    gamma 1


## Document Similarity

Determine the unique words in a pair of text documents and compute a similarity metric  
for them, as defined by the following function:

> **Jaccard similarity** =  
> (count of unique words in common) /  
> (total number of unique words in the two documents)

### running

    sbt runMain DefaultDocumentSimilarityApp <file name> <file name> 

By default, the text documents are located in:

- *src/main/resources/document_similarity_1.txt* with the following content:

>Alpha zeta gamma alpha delta zeta alpha.

- *src/main/resources/document_similarity_2.txt* with the following content:

>Beta alpha, gamma delta. Alpha beta epsilon theta.

Expected similarity value: ***0.428571*** (Note: 3 common, 7 total)

    sbt "runMain DocumentSimilarityApp"
    [info] running DocumentSimilarityApp 
    0.42857142857142855
